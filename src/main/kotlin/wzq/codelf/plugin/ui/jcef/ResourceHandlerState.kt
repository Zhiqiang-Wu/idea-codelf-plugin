package wzq.codelf.plugin.ui.jcef

import org.cef.callback.CefCallback
import org.cef.misc.IntRef
import org.cef.misc.StringRef
import org.cef.network.CefResponse

/**
 * @author 吴志强
 * @date 2024/1/13
 */
interface ResourceHandlerState {

    fun getResponseHeaders(cefResponse: CefResponse?, responseLength: IntRef?, redirectUrl: StringRef?)

    fun readResponse(dataOut: ByteArray?, bytesToRead: Int, bytesRead: IntRef?, callback: CefCallback?): Boolean

    fun close() {}
}
