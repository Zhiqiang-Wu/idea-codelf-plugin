package wzq.codelf.plugin.ui

import com.intellij.openapi.Disposable
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import wzq.codelf.plugin.Variable
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.ScrollPaneConstants

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchPanel(onSearch: (text: String) -> Unit) : JBPanel<SearchPanel>(), Disposable {

    private var searchCenterPanel = SearchCenterPanel()

    init {
        this.layout = BorderLayout(0, 0)
        this.border = BorderFactory.createEmptyBorder(0, 10, 0, 10)

        this.add(SearchNorthPanel(onSearch), BorderLayout.NORTH)

        this.add(
            JBScrollPane(
                this.searchCenterPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
            ),
            BorderLayout.CENTER
        )
    }

    fun setContent(variables: Array<Variable>) {
        this.searchCenterPanel.setContent(variables)
    }

    override fun dispose() {
    }
}
