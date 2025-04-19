package wzq.codelf.plugin.ui.layout

import com.intellij.util.ui.JBUI
import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.LayoutManager
import javax.swing.JScrollBar

/**
 * https://stackoverflow.com/questions/9288350/adding-vertical-scroll-to-a-jpopupmenu
 */
class ScrollPopupMenuLayout : LayoutManager {
    override fun addLayoutComponent(
        name: String?,
        comp: Component?,
    ) {
    }

    override fun removeLayoutComponent(comp: Component?) {
    }

    override fun preferredLayoutSize(parent: Container?): Dimension {
        var visibleAmount = Int.MAX_VALUE
        val dim = Dimension()
        parent?.components?.let {
            for (comp in it) {
                if (!comp.isVisible) {
                    continue
                }
                if (comp is JScrollBar) {
                    visibleAmount = comp.visibleAmount
                } else {
                    dim.width = dim.width.coerceAtLeast(comp.preferredSize.width)
                    dim.height += comp.preferredSize.height
                }
            }
        }
        val insets = parent?.insets ?: JBUI.emptyInsets()
        dim.height = (dim.height + insets.top + insets.bottom).coerceAtMost(visibleAmount)
        return dim
    }

    override fun minimumLayoutSize(parent: Container?): Dimension {
        var visibleAmount = Int.MAX_VALUE
        val dim = Dimension()
        parent?.components?.let {
            for (comp in it) {
                if (!comp.isVisible) {
                    continue
                }
                if (comp is JScrollBar) {
                    visibleAmount = comp.visibleAmount
                } else {
                    dim.width = dim.width.coerceAtLeast(comp.minimumSize.width)
                    dim.height += comp.minimumSize.height
                }
            }
        }
        val insets = parent?.insets ?: JBUI.emptyInsets()
        dim.height = (dim.height + insets.top + insets.bottom).coerceAtMost(visibleAmount)
        return dim
    }

    override fun layoutContainer(parent: Container?) {
        parent ?: return

        val insets = parent.insets

        var width = parent.width - insets.left - insets.right
        val height = parent.height - insets.top - insets.bottom

        val x = insets.left
        var y = insets.top
        var position = 0

        for (comp in parent.components) {
            if (comp !is JScrollBar || !comp.isVisible) {
                continue
            }
            val preferredSize = comp.preferredSize
            comp.setBounds(x + width - preferredSize.width, y, preferredSize.width, height)
            width -= preferredSize.width
            position = comp.value
        }

        y -= position

        for (comp in parent.components) {
            if (!comp.isVisible || comp is JScrollBar) {
                continue
            }
            val preferredSize = comp.preferredSize
            comp.setBounds(x, y, width, preferredSize.height)
            y += preferredSize.height
        }
    }
}
