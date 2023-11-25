package wzq.codelf.plugin.ui.component

import com.intellij.ide.browsers.BrowserLauncher
import com.intellij.openapi.ui.JBMenuItem
import com.intellij.openapi.ui.JBPopupMenu
import wzq.codelf.plugin.Variable
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

/**
 * @author 吴志强
 * @date 2023/11/25
 */
class VariablePopupMenu(variable: Variable): JBPopupMenu() {

    init {
        val repoMenuItem = JBMenuItem("Repo")
        repoMenuItem.addActionListener {
            BrowserLauncher.instance.browse(variable.repo)
        }
        this.add(repoMenuItem)

        val copyMenuItem = JBMenuItem("Copy")
        copyMenuItem.addActionListener {
            val content = StringSelection(variable.name)
            Toolkit.getDefaultToolkit().systemClipboard.setContents(content, null)
        }
        this.add(copyMenuItem)
    }
}