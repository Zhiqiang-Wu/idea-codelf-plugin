package wzq.codelf.plugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import wzq.codelf.plugin.ui.SearchPanel

/**
 * @author 吴志强
 * @date 2023/11/21
 */
class SearchWindow : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val searchPanel = SearchPanel()
        val viewContent = ContentFactory.getInstance().createContent(searchPanel, "", false)
        toolWindow.contentManager.addContent(viewContent)

        /*val viewBrowser = searchPanel.viewBrowser
        val devToolsBrowser = JBCefBrowser.createBuilder()
            .setCefBrowser(viewBrowser.cefBrowser.devTools)
            .setClient(viewBrowser.jbCefClient)
            .build()
        val devToolsContent = ContentFactory.getInstance().createContent(devToolsBrowser.component, "DevTools", false)
        toolWindow.contentManager.addContent(devToolsContent)*/
    }
}
