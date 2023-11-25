package wzq.codelf.plugin.ui

import com.intellij.openapi.ui.addKeyboardAction
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import java.awt.event.KeyEvent
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.KeyStroke

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchNorthPanel(onSearch: (text: String) -> Unit) : JBPanel<SearchNorthPanel>() {

    init {
        this.layout = BoxLayout(this, BoxLayout.Y_AXIS)

        val firstRow = Box.createHorizontalBox()
        this.add(firstRow)

        val qTextField = JBTextField()
        qTextField.addKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER.toChar())) {
            onSearch(qTextField.text)
        }
        firstRow.add(qTextField)

        val secondRow = Box.createHorizontalBox()
        this.add(secondRow)

        // TODO 国际化
        val searchButton = JButton("Search")
        searchButton.addActionListener {
            onSearch(qTextField.text)
        }
        secondRow.add(searchButton)

        val resetButton = JButton("Reset")
        resetButton.addActionListener {
            qTextField.text = ""
        }
        secondRow.add(resetButton)

        // TODO 语言筛选

        val secondRowPlaceholder = Box.createHorizontalGlue()
        secondRow.add(secondRowPlaceholder)
    }
}
