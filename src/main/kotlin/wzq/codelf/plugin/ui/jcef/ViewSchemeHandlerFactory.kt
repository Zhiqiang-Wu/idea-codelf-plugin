package wzq.codelf.plugin.ui.jcef

import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.callback.CefSchemeHandlerFactory
import org.cef.handler.CefResourceHandler
import org.cef.network.CefRequest

/**
 * @author 吴志强
 * @date 2024/1/10
 */
class ViewSchemeHandlerFactory : CefSchemeHandlerFactory {

    override fun create(
        browser: CefBrowser?,
        frame: CefFrame?,
        schemeName: String?,
        request: CefRequest?
    ): CefResourceHandler {
        return ViewResourceHandler()
    }
}