package wzq.codelf.plugin.ui

import com.intellij.ui.components.JBPanel
import wzq.codelf.plugin.Language
import wzq.codelf.plugin.Variable
import java.awt.BorderLayout
import javax.swing.BorderFactory

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchPanel(
    onSearch: (text: String, languages: Set<Language>) -> Unit,
) : JBPanel<SearchPanel>() {
    private val searchCenterPanel = SearchCenterPanel()

    init {
        this.layout = BorderLayout(0, 0)
        this.border = BorderFactory.createEmptyBorder(0, 10, 0, 10)

        this.add(SearchNorthPanel(onSearch), BorderLayout.NORTH)

        this.add(this.searchCenterPanel, BorderLayout.CENTER)
    }

    fun setLoading(loading: Boolean) {
        this.searchCenterPanel.setLoading(loading)
    }

    fun setContent(variables: Array<Variable>) {
        this.searchCenterPanel.setContent(variables)
    }
}
