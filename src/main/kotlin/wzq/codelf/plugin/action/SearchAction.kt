package wzq.codelf.plugin.action

import com.intellij.ide.browsers.BrowserLauncher
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

/**
 * @author 吴志强
 * @date 2023/11/21
 */
class SearchAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val selectedText = e.getData(CommonDataKeys.EDITOR)?.selectionModel?.selectedText ?: ""
        BrowserLauncher.instance.browse("https://unbug.github.io/codelf/#${selectedText}")
    }
}