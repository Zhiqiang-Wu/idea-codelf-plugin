package wzq.codelf.plugin.ui.layout

import com.intellij.util.ui.JBUI
import java.awt.Container
import java.awt.Dimension
import java.awt.FlowLayout

/**
 * https://stackoverflow.com/questions/3679886/how-can-i-let-jtoolbars-wrap-to-the-next-line-flowlayout-without-them-being-hi
 */
class ModifiedFlowLayout(align: Int) : FlowLayout(align) {

    override fun minimumLayoutSize(target: Container?): Dimension {
        return this.computeMinSize(target)
    }

    override fun preferredLayoutSize(target: Container?): Dimension {
        return this.computeSize(target)
    }

    private fun computeMinSize(target: Container?): Dimension {
        target?.treeLock?.let {
            synchronized(it) {
                var minX = Int.MAX_VALUE
                var minY = Int.MIN_VALUE
                var foundOne = false

                val componentCount = target.componentCount
                for (i in 0 until componentCount) {
                    val component = target.getComponent(i)
                    if (component.isVisible) {
                        foundOne = true
                        val preferredSize = component.preferredSize
                        minX = minX.coerceAtMost(preferredSize.width)
                        minY = minY.coerceAtMost(preferredSize.height)
                    }
                }

                if (foundOne) {
                    return Dimension(minX, minY)
                }
                return Dimension(0, 0)
            }
        }

        return Dimension(0, 0)
    }

    private fun computeSize(target: Container?): Dimension {
        target?.treeLock?.let {
            synchronized(it) {
                var width = target.width
                if (width == 0) {
                    width = Int.MAX_VALUE
                }

                var insets = target.insets
                if (insets == null) {
                    insets = JBUI.emptyInsets()
                }

                val maxWidth = width - (insets.left + insets.right + this.hgap * 2)
                var rowHeight = 0
                var x = 0
                var y = insets.top + this.vgap
                var reqdWidth = 0

                val componentCount = target.componentCount
                for (i in 0 until componentCount) {
                    val component = target.getComponent(i)
                    if (component.isVisible) {
                        val preferredSize = component.preferredSize
                        if (x == 0 || x + preferredSize.width <= maxWidth) {
                            if (x > 0) {
                                x += this.hgap
                            }
                            x += preferredSize.width
                            rowHeight = rowHeight.coerceAtLeast(preferredSize.height)
                        } else {
                            x = preferredSize.width
                            y += this.vgap + rowHeight
                            rowHeight = preferredSize.height
                        }
                        reqdWidth = reqdWidth.coerceAtLeast(x)
                    }
                }
                y += rowHeight
                y += insets.bottom
                return Dimension(reqdWidth + insets.left + insets.right, y)
            }
        }
        return Dimension(0, 0)
    }
}