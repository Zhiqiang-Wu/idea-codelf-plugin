package wzq.codelf.plugin.ui

import com.intellij.ui.components.JBPanel
import wzq.codelf.plugin.Variable
import wzq.codelf.plugin.ui.component.VariableLabel
import wzq.codelf.plugin.ui.layout.ModifiedFlowLayout
import java.awt.FlowLayout

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class SearchCenterPanel : JBPanel<SearchCenterPanel>() {

    init {
        this.layout = ModifiedFlowLayout(FlowLayout.LEFT)
    }

    fun setContent(variables: Array<Variable>) {
        this.removeAll()
        variables.forEach {
            this.add(VariableLabel(it))
        }
    }
}