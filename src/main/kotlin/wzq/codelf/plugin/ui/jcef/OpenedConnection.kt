package wzq.codelf.plugin.ui.jcef

import org.cef.callback.CefCallback
import org.cef.misc.IntRef
import org.cef.misc.StringRef
import org.cef.network.CefResponse
import java.net.URLConnection

/**
 * @author 吴志强
 * @date 2024/1/13
 */
class OpenedConnection(private val connection: URLConnection) : ResourceHandlerState {

    private val inputStream = connection.getInputStream()

    override fun getResponseHeaders(
        cefResponse: CefResponse?,
        responseLength: IntRef?,
        redirectUrl: StringRef?
    ) {
        cefResponse?.status = 200
        responseLength?.set(this.inputStream.available())

        val urlString = this.connection.url.toString()
        cefResponse?.mimeType = if (urlString.endsWith(".html")) {
            "text/html"
        } else if (urlString.endsWith(".js")) {
            "text/javascript;charset=utf-8"
        } else if (urlString.endsWith(".css")) {
            "text/css;charset=utf-8"
        } else {
            cefResponse?.status = 404
            ""
        }
    }

    override fun readResponse(
        dataOut: ByteArray?,
        bytesToRead: Int,
        bytesRead: IntRef?,
        callback: CefCallback?
    ): Boolean {
        dataOut ?: return false
        bytesRead ?: return false

        val available = this.inputStream.available()
        if (available <= 0) {
            return false
        }

        val maxBytesToRead = available.coerceAtMost(bytesToRead)
        val read = this.inputStream.read(dataOut, 0, maxBytesToRead)
        bytesRead.set(read);

        return true
    }

    override fun close() {
        this.inputStream.close()
    }
}