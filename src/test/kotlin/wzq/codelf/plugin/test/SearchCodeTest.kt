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

        results.map {
            val node = it as ObjectNode

            val repo = node.get("repo").asText().replace("git://github.com", "https://github.com")
            node.put("repo", repo)
        }
    }

    companion object {
        val OBJECT_MAPPER = ObjectMapper();
    }
}