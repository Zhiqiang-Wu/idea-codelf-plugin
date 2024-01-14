package wzq.codelf.plugin.ui.jcef

import org.cef.callback.CefCallback
import org.cef.handler.CefResourceHandlerAdapter
import org.cef.misc.IntRef
import org.cef.misc.StringRef
import org.cef.network.CefRequest
import org.cef.network.CefResponse

/**
 * @author 吴志强
 * @date 2024/1/10
 */
class ViewResourceHandler : CefResourceHandlerAdapter() {

    private var state: ResourceHandlerState = ClosedConnection()

    override fun processRequest(request: CefRequest?, callback: CefCallback?): Boolean {
        val url = request?.url ?: return false

        return if (url.startsWith("http://inner")) {
            val name = url.replace("http://inner", "/view")
            val resource = this.javaClass.getResource(name) ?: return false
            this.state = OpenedConnection(resource.openConnection())
            callback?.Continue()
            true
        } else {
            false
        }
    }

    override fun readResponse(
        dataOut: ByteArray?,
        bytesToRead: Int,
        bytesRead: IntRef?,
        callback: CefCallback?
    ): Boolean {
        return this.state.readResponse(dataOut, bytesToRead, bytesRead, callback)
    }

    override fun getResponseHeaders(response: CefResponse?, responseLength: IntRef?, redirectUrl: StringRef?) {
        return this.state.getResponseHeaders(response, responseLength, redirectUrl)
    }

    override fun cancel() {
        this.state.close()
        this.state = ClosedConnection()
    }
}