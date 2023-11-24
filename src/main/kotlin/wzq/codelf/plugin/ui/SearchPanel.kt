package wzq.codelf.plugin.ui

import com.intellij.ui.components.JBPanel
import java.awt.BorderLayout
import javax.swing.BorderFactory

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchPanel: JBPanel<SearchPanel>() {

    init {
        this.layout = BorderLayout(0, 0)
        this.border = BorderFactory.createEmptyBorder(0, 10, 0, 10)

        this.add(SearchNorthPanel(), BorderLayout.NORTH)

        this.add(SearchCenterPanel(), BorderLayout.CENTER)
    }
}
