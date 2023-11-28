package wzq.codelf.plugin.ui

import com.intellij.openapi.ui.addKeyboardAction
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import wzq.codelf.plugin.CodelfIcons
import wzq.codelf.plugin.Language
import wzq.codelf.plugin.ui.component.JScrollPopupMenu
import wzq.codelf.plugin.ui.component.LanguageCheckBox
import java.awt.event.KeyEvent
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.KeyStroke

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchNorthPanel(onSearch: (text: String, languages: Set<Language>) -> Unit) : JBPanel<SearchNorthPanel>() {

    private val languageFilterPopupMenu = JScrollPopupMenu()

    init {
        this.layout = BoxLayout(this, BoxLayout.Y_AXIS)

        val firstRow = Box.createHorizontalBox()
        this.add(firstRow)

        Language.values().forEach {
            val menuItem = LanguageCheckBox(it)
            this.languageFilterPopupMenu.add(menuItem)
        }

        val filterButton = JButton()
        filterButton.icon = CodelfIcons.FILTER
        filterButton.addActionListener {
            this.languageFilterPopupMenu.show(filterButton, 3, filterButton.height)
        }
        firstRow.add(filterButton)

        val qTextField = JBTextField()
        qTextField.addKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER.toChar())) {
            onSearch(qTextField.text, this.getSelectedLanguage())
        }
        firstRow.add(qTextField)

        val secondRow = Box.createHorizontalBox()
        this.add(secondRow)

        // TODO 国际化
        val searchButton = JButton("Search")
        searchButton.addActionListener {
            onSearch(qTextField.text, this.getSelectedLanguage())
        }
        secondRow.add(searchButton)

        val resetButton = JButton("Reset")
        resetButton.addActionListener {
            qTextField.text = ""
            this.resetSelectedLanguage()
        }
        secondRow.add(resetButton)

        val secondRowPlaceholder = Box.createHorizontalGlue()
        secondRow.add(secondRowPlaceholder)
    }

    private fun resetSelectedLanguage() {
        this.languageFilterPopupMenu.components
            .filterIsInstance<LanguageCheckBox>()
            .forEach {
                it.isSelected = false
            }
    }

    private fun getSelectedLanguage(): Set<Language> {
        return this.languageFilterPopupMenu.components
            .filterIsInstance<LanguageCheckBox>()
            .filter {
                it.isSelected
            }.map {
                it.getLanguage()
            }
            .toSet()
    }
}
