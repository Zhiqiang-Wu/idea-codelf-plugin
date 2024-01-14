package wzq.codelf.plugin.ui

import com.intellij.ui.components.JBPanel
import com.intellij.ui.jcef.JBCefBrowser
import org.cef.CefApp
import wzq.codelf.plugin.Language
import wzq.codelf.plugin.ui.jcef.ViewSchemeHandlerFactory
import java.awt.BorderLayout
import javax.swing.BorderFactory

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchPanel : JBPanel<SearchPanel>() {

    private val viewBrowser = JBCefBrowser()

    init {
        this.layout = BorderLayout(0, 0)
        this.border = BorderFactory.createEmptyBorder(0, 10, 0, 10)

        this.add(SearchNorthPanel { text, language ->
            this.onSearch(text, language)
        }, BorderLayout.NORTH)

        this.viewBrowser.loadURL("http://inner/index.html")
        CefApp.getInstance().registerSchemeHandlerFactory("http", "inner", ViewSchemeHandlerFactory())
        this.add(this.viewBrowser.component, BorderLayout.CENTER)
    }

    private fun onSearch(text: String, languages: Set<Language>) {
        if (text.isBlank()) {
            return
        }

        val lanStr = languages.map { it.value }.joinToString(", ")

        val js = """
            document.dispatchEvent(new CustomEvent("jcef:search", {detail: {q: '${text}', lan: [${lanStr}]}}));
        """.trimIndent()

        val cefBrowser = this.viewBrowser.cefBrowser

        cefBrowser.executeJavaScript(js, cefBrowser.url, 0)
    }
}
