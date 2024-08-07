package wzq.codelf.plugin

import cn.hutool.http.HttpUtil
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.intellij.openapi.Disposable
import com.intellij.util.Alarm
import lombok.Cleanup
import wzq.codelf.plugin.ui.SearchPanel
import javax.swing.JComponent

/**
 * @author 吴志强
 * @date 2023/11/27
 */
class SearchView : Disposable {

    private val searchPanel = SearchPanel { text, language ->
        this.onSearch(text, language)
    }

    private val requestAlarm = lazy { Alarm(Alarm.ThreadToUse.POOLED_THREAD, this) }

    private val updateAlarm = lazy { Alarm() }

    private fun onSearch(text: String, languages: Set<Language>) {
        if (text.isBlank()) {
            return
        }

        this.searchPanel.setLoading(true)
        requestAlarm.value.cancelAllRequests()
        // 在专门的线程中请求
        requestAlarm.value.addRequest({
            val variables = this.listVariables(text, languages)
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

    private fun listVariables(text: String, languages: Set<Language>): Array<Variable>? {
        val q = text.trim()

        // TODO 翻译

        var url = "https://searchcode.com/api/codesearch_I/?q=$q"
        if (languages.isNotEmpty()) {
            val lanString = languages
                .map {
                    it.value
                }
                .joinToString("&") {
                    "lan=${it}"
                }
            url = "${url}&${lanString}"
        }

        @Cleanup
        val response = HttpUtil.createGet(url).execute()
        if (!response.isOk) {
            return null
        }

        val responseBody: ObjectNode = Global.objectMapper.readValue(response.body(), ObjectNode::class.java)

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
    }
}
