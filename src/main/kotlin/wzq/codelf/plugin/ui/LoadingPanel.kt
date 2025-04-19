package wzq.codelf.plugin.ui

import com.intellij.ui.components.JBPanel
import com.intellij.util.ui.JBUI
import org.apache.batik.swing.JSVGCanvas
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

/**
 * @author 吴志强
 * @date 2023/11/27
 */
class LoadingPanel(
    uri: String,
) : JBPanel<LoadingPanel>() {
    private val svgCanvas = JSVGCanvas()

    init {
        this.svgCanvas.uri = uri

        this.layout = GridBagLayout()

        val gbc = GridBagConstraints()
        gbc.insets = JBUI.insets(5)
        gbc.gridx = 0
        gbc.gridy = 0
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.BOTH

        this.add(this.svgCanvas)
    }
}
