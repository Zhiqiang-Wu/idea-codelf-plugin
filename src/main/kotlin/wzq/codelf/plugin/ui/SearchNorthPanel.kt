package wzq.codelf.plugin.ui

import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchNorthPanel : JBPanel<SearchNorthPanel>() {

    init {
        this.layout = BoxLayout(this, BoxLayout.Y_AXIS)

        val firstRow = Box.createHorizontalBox()
        this.add(firstRow);

        val qTextField = JBTextField()
        firstRow.add(qTextField);

        val secondRow = Box.createHorizontalBox()
        this.add(secondRow)

        // TODO
        val searchButton = JButton("Search")
        secondRow.add(searchButton)

        val resetButton = JButton("Reset")
        resetButton.addActionListener {
            qTextField.text = ""
        }
        secondRow.add(resetButton)

        val secondRowPlaceholder = Box.createHorizontalGlue()
        secondRow.add(secondRowPlaceholder)
    }
}
