package wzq.codelf.plugin.ui.component

import com.intellij.ide.browsers.BrowserLauncher
import com.intellij.openapi.ui.JBMenuItem
import com.intellij.openapi.ui.JBPopupMenu
import wzq.codelf.plugin.CodelfBundle
import wzq.codelf.plugin.Variable
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

/**
 * @author 吴志强
 * @date 2023/11/25
 */
class VariablePopupMenu(variable: Variable) : JBPopupMenu(), MouseListener {

    private var focus = false

    init {
        val repoMenuItem = JBMenuItem(CodelfBundle.getMessage("ui.menu.item.repo"))
        repoMenuItem.addActionListener {
            BrowserLauncher.instance.browse(variable.repo)
        }
        repoMenuItem.addMouseListener(this)
        this.add(repoMenuItem)

        val copyMenuItem = JBMenuItem(CodelfBundle.getMessage("ui.menu.item.copy"))
        copyMenuItem.addActionListener {
            val content = StringSelection(variable.name)
            Toolkit.getDefaultToolkit().systemClipboard.setContents(content, null)
        }
        copyMenuItem.addMouseListener(this)
        this.add(copyMenuItem)

        val languageMenuItem = JBMenuItem("[${variable.language}]")
        languageMenuItem.addMouseListener(this)
        languageMenuItem.isEnabled = false
        this.add(languageMenuItem)
    }

    fun isFocus(): Boolean {
        return this.focus
    }

    override fun setVisible(b: Boolean) {
        if (!b) {
            this.focus = false
        }
        super.setVisible(b)
    }

    override fun mouseClicked(e: MouseEvent?) {
    }

    override fun mousePressed(e: MouseEvent?) {
    }

    override fun mouseReleased(e: MouseEvent?) {
    }

    override fun mouseEntered(e: MouseEvent?) {
        this.focus = true
    }

    override fun mouseExited(e: MouseEvent?) {
        this.focus = false
    }
}