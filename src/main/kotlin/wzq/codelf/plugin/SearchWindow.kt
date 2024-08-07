package wzq.codelf.plugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

/**
 * @author 吴志强
 * @date 2023/11/21
 */
class SearchWindow : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val searchView = SearchView()
        val content = ContentFactory.getInstance().createContent(searchView.getComponent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}
