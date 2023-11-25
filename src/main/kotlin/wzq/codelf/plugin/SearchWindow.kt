package wzq.codelf.plugin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import lombok.Cleanup
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import wzq.codelf.plugin.ui.SearchPanel
import java.nio.charset.StandardCharsets

/**
 * @author 吴志强
 * @date 2023/11/21
 */
class SearchWindow : ToolWindowFactory {

    private val objectMapper = lazy { ObjectMapper() }

    private val httpClient = lazy { HttpClients.createDefault() }

    private val searchPanel = SearchPanel {
        this.onSearch(it)
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val content = ContentFactory.getInstance().createContent(this.searchPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    private fun onSearch(text: String) {
        if (text.isBlank()) {
            return
        }

        val q = text.trim()

        // TODO 分页，页大小
        val url = "https://searchcode.com/api/codesearch_I/?q=$q&p=0&per_page20"
        val httpGet = HttpGet(url)

        @Cleanup
        val response = this.httpClient.value.execute(httpGet)
        val statusLine = response.statusLine
        if (statusLine.statusCode != HttpStatus.SC_OK) {
            println(statusLine.reasonPhrase)
            return
        }

        val responseStr: String = EntityUtils.toString(response.entity, StandardCharsets.UTF_8)
        val responseBody: ObjectNode = this.objectMapper.value.readValue(responseStr, ObjectNode::class.java)

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
                        // TODO 正则定义为常量
                        it.value.replace("""^(\-|\/)*""".toRegex(), "").replace("""(\-|\/)*${'$'}""".toRegex(), "")
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

        this.searchPanel.setContent(variableArr)
    }
}
