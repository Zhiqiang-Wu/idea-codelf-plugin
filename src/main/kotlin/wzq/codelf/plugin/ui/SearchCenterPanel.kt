package wzq.codelf.plugin.ui

import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import wzq.codelf.plugin.Variable
import wzq.codelf.plugin.ui.component.VariableLabel
import wzq.codelf.plugin.ui.layout.ModifiedFlowLayout
import java.awt.CardLayout
import java.awt.FlowLayout
import javax.swing.ScrollPaneConstants

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchCenterPanel : JBPanel<SearchCenterPanel>() {

    private val contentPanel = ContentPanel()

    init {
        this.layout = CardLayout()

        val scrollPane = JBScrollPane(
            this.contentPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        )
        this.add(scrollPane, "content")

        val uri = this.javaClass.getResource("/icons/loading.svg")?.toURI().toString()
        val loadingPanel = LoadingPanel(uri)
        this.add(loadingPanel, "loading")
    }

    fun setLoading(loading: Boolean) {
        val layout = this.layout as CardLayout
        if (loading) {
            layout.show(this, "loading")
        } else {
            layout.show(this, "content")
        }
    }

    fun setContent(variables: Array<Variable>) {
        this.contentPanel.setContent(variables)
    }

    class ContentPanel: JBPanel<ContentPanel>() {

        init {
            this.layout = ModifiedFlowLayout(FlowLayout.LEFT)
        }

        fun setContent(variables: Array<Variable>) {
            this.removeAll()
            variables.forEach {
                this.add(VariableLabel(it))
            }
            // 及时渲染
            // this.revalidate()
        }
    }
}