package wzq.codelf.plugin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.intellij.openapi.Disposable
import com.intellij.util.Alarm
import com.intellij.util.AlarmFactory
import lombok.Cleanup
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import wzq.codelf.plugin.ui.SearchPanel
import java.nio.charset.StandardCharsets
import javax.swing.JComponent

/**
 * @author 吴志强
 * @date 2023/11/27
 */
class SearchView : Disposable {

    private val searchPanel = SearchPanel {
        this.onSearch(it)
    }

    private val requestAlarm =
        lazy { AlarmFactory.getInstance().create(Alarm.ThreadToUse.POOLED_THREAD, this) }

    private val updateAlarm = lazy { AlarmFactory.getInstance().create() }

    private val httpClient = lazy { HttpClients.createDefault() }

    private fun onSearch(text: String) {
        if (text.isBlank()) {
            return
        }

        this.searchPanel.setLoading(true)
        requestAlarm.value.cancelAllRequests()
        // 在专门的线程中请求
        requestAlarm.value.addRequest({
            val variables = this.listVariables(text)
            if (variables != null) {
                updateAlarm.value.addRequest({
                    this.searchPanel.setContent(variables)
                    this.searchPanel.setLoading(false)
                }, 0)
            } else {
                updateAlarm.value.addRequest({
                    this.searchPanel.setLoading(false)
                }, 0)
            }
        }, 0)
    }

    private fun listVariables(text: String): Array<Variable>? {
        val q = text.trim()

        // TODO 翻译

        val url = "https://searchcode.com/api/codesearch_I/?q=$q"
        val httpGet = HttpGet(url)

        @Cleanup
        val response = this.httpClient.value.execute(httpGet)
        val statusLine = response.statusLine
        if (statusLine.statusCode != HttpStatus.SC_OK) {
            return null
        }

        val responseStr: String = EntityUtils.toString(response.entity, StandardCharsets.UTF_8)
        val responseBody: ObjectNode = objectMapper.value.readValue(responseStr, ObjectNode::class.java)

        val results: ArrayNode = responseBody.get("results") as ArrayNode

        val keyWordRegex = """([\-_\w\d\/\${'$'}]*)?$q([\-_\w\d\${'$'}]*)?""".toRegex()

        var variableArr = arrayOf<Variable>()
        // 用于去重
        val nameSet = mutableSetOf<String>()

        results
            .map {
                val node = it as ObjectNode
                val repo = node.get("repo").asText().replace("git://github.com", "https://github.com")
                node.put("repo", repo)
                node
            }
            .forEach {
                val repo = it.get("repo").asText()
                val language = it.get("language").asText()

                val lines = it.get("lines") as ObjectNode
                val lineList = mutableListOf<String>()
                for (field in lines.fields()) {
                    val line = field.value.asText()
                    if (line.indexOf(";base64,") >= 0 && line.length > 256) {
                        continue
                    }
                    lineList.add(line)
                }
                // 去除换行符
                val lineJoin = lineList.joinToString("").replace("\\R".toRegex(), " ")

                keyWordRegex.findAll(lineJoin)
                    .map {
                        it.value.replace(regex, "").replace(regex2, "")
                    }
                    .forEach l@{
                        // 限制长度
                        if (it.length > 63) {
                            return@l
                        }
                        // 排除链接
                        if (it.indexOf("/") >= 0) {
                            return@l
                        }
                        // 防止重复
                        val lowercase = it.lowercase()
                        if (nameSet.contains(lowercase)) {
                            return@l
                        }
                        nameSet.add(lowercase)

                        variableArr = variableArr.plus(Variable(it, language, repo))
                    }
            }

        return variableArr
    }

    fun getComponent(): JComponent {
        return this.searchPanel
    }

    override fun dispose() {
    }

    private companion object {
        private val regex = """^(\-|\/)*""".toRegex()

        private val regex2 = """(\-|\/)*${'$'}""".toRegex()

        private val objectMapper = lazy { ObjectMapper() }
    }
}