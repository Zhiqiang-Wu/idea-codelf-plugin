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

    private val searchPanel = SearchPanel {
        this.onSearch(it)
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val content = ContentFactory.getInstance().createContent(this.searchPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    private fun onSearch(text: String) {
        if (text.isBlank()) {
            return
        }
    }
}
