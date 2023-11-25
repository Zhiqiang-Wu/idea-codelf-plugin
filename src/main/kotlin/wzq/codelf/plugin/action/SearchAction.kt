package wzq.codelf.plugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager

/**
 * @author 吴志强
 * @date 2023/11/25
 */
class SearchAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Codelf")
        toolWindow?.show()
        // TODO 打开 toolWindow 后直接根据选中值搜索
    }
}