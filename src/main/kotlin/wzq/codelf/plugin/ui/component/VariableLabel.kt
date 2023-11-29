package wzq.codelf.plugin.ui.component

import com.intellij.ui.components.JBLabel
import com.intellij.util.Alarm
import wzq.codelf.plugin.Variable
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

/**
 * @author 吴志强
 * @date 2023/11/24
 */
class VariableLabel(variable: Variable) : JBLabel(), MouseListener {

    private val popupMenu = lazy { VariablePopupMenu(variable) }

    // TODO 圆角

    init {
        // this.setHorizontalAlignment(SwingConstants.CENTER);
        val color = colorArr[colorArr.indices.random()]
        this.text = textTemplate.format(color, variable.name)

        this.addMouseListener(this)
    }

    override fun mouseClicked(e: MouseEvent?) {
    }

    override fun mousePressed(e: MouseEvent?) {
    }

    override fun mouseReleased(e: MouseEvent?) {
    }

    override fun mouseEntered(e: MouseEvent?) {
        e?.let {
            showAlarm.value.cancelAllRequests()
            showAlarm.value.addRequest({
                this.popupMenu.value.show(
                    it.component.parent,
                    it.component.x,
                    it.component.y + it.component.height + 5
                )
            }, 400)
        }
    }

    override fun mouseExited(e: MouseEvent?) {
        showAlarm.value.cancelAllRequests()
        hideAlarm.value.addRequest({
            if (!this.popupMenu.value.isFocus()) {
                this.popupMenu.value.isVisible = false
            }
        }, 200)
    }

    private companion object {
        val showAlarm = lazy { Alarm() }

        val hideAlarm = lazy { Alarm() }

        val textTemplate = """
            <html>
            <body>
            <div style="font-size: 13px; display: inline-block; background-color: %s; padding: 5px; color: white;">%s</div>
            </body>
            </html>
        """.trimIndent()

        // TODO 多加几个颜色
        val colorArr =
            arrayOf("red", "orange", "olive", "green", "teal", "blue", "purple")
    }
}