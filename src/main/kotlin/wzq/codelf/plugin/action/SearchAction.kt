package wzq.codelf.plugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.wm.ToolWindowManager

/**
 * @author 吴志强
 * @date 2023/11/21
 */
class SearchAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val selectedText = e.getData(CommonDataKeys.EDITOR)?.selectionModel?.selectedText
        ToolWindowManager.getInstance(project).getToolWindow("Codelf")?.show();
    }
}