package wzq.codelf.plugin.ui.component

import com.intellij.ui.components.JBLabel
import wzq.codelf.plugin.Variable

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class VariableLabel(variable: Variable) : JBLabel() {

    init {
        // this.setHorizontalAlignment(SwingConstants.CENTER);
        val color = colorArr[colorArr.indices.random()]
        this.text = textTemplate.format(color, variable.name)
    }

    private companion object {
        val textTemplate = """
            <html>
            <body>
            <div style="font-size: 13px; display: inline-block; background-color: %s; padding: 5px; border-radius: 50px; color: white;">%s</div>
            </body>
            </html>
        """.trimIndent()

        val colorArr =
            arrayOf("red", "orange", "olive", "green", "teal", "blue", "purple")
    }
}