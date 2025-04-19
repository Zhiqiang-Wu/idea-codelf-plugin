package wzq.codelf.plugin.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import lombok.Cleanup
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets

/**
 * @author 吴志强
 * @date 2023/11/23
 */
class SearchCodeTest {
    @Test
    fun codeSearch() {
        val str = "https://searchcode.com/api/codesearch_I/?q=filter&p=0&per_page20"
        val httpGet = HttpGet(str)

        @Cleanup
        val httpClient = HttpClients.createDefault()

        @Cleanup
        val response = httpClient.execute(httpGet)

        val statusLine = response.statusLine
        if (statusLine.statusCode != HttpStatus.SC_OK) {
            println(statusLine.reasonPhrase)
            return
        }

        val responseStr: String = EntityUtils.toString(response.entity, StandardCharsets.UTF_8)
        val responseBody: ObjectNode = OBJECT_MAPPER.readValue(responseStr, ObjectNode::class.java)
        // println(responseBody.toPrettyString())

        val results: ArrayNode = responseBody.get("results") as ArrayNode

        println(results.toPrettyString())
    }

    @Test
    fun replaceR() {
        val str = "git://github.\rco\n\rm/1\n\n3132"
        val replace = str.replace("\\R".toRegex(), " ")
        println(replace)
    }

    @Test
    fun regex() {
        val q = "filter"
        var line =
            """
            {"198":"\t},","199":"\tfilter: function(callback, thisObject){","200":"\t\t// summary:","201":"\t\t//\t\tFilters the query results, based on","202":"\t\t//\t\thttps://developer.mozilla.org/en/Core_JavaScript_1.5_Reference/Objects/Array/filter.","29":"\t//\t\t| var query = store.queryEngine({foo:\"bar\"}, {count:10});","30":"\t//\t\t| query(someArray) -> filtered array","31":"\t//\t\tThe returned query function may have a \"matches\" property that can be"}
            """.trimIndent()
        line = line.replace("\\R".toRegex(), " ")
        val regex = "([\\-_\\w\\d\\/\\$]*)?$q([\\-_\\w\\d\\$]*)?".toRegex()
        val findAll = regex.findAll(line)
        findAll
            .map {
                it.value
            }.map {
                it.replace("""^(\-|\/)*""".toRegex(), "").replace("""(\-|\/)*${'$'}""".toRegex(), "")
            }.forEach {
                println(it)
            }
    }

    companion object {
        val OBJECT_MAPPER = ObjectMapper()
    }
}
