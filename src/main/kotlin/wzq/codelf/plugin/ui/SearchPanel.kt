package wzq.codelf.plugin.ui

import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.ScrollPaneConstants

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchPanel : JBPanel<SearchPanel>() {

    init {
        this.layout = BorderLayout(0, 0)
        this.border = BorderFactory.createEmptyBorder(0, 10, 0, 10)

        this.add(SearchNorthPanel(), BorderLayout.NORTH)

        this.add(
            JBScrollPane(
                SearchCenterPanel(),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
            ),
            BorderLayout.CENTER
        )
    }
}
