package wzq.codelf.plugin.ui.component

import wzq.codelf.plugin.ui.layout.ScrollPopupMenuLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.MouseWheelEvent
import javax.swing.JPopupMenu
import javax.swing.JScrollBar

/**
 * https://stackoverflow.com/questions/9288350/adding-vertical-scroll-to-a-jpopupmenu
 */
open class JScrollPopupMenu(private var maximumVisibleRows: Int) : JPopupMenu() {

    private val popupScrollBar = JScrollBar(JScrollBar.VERTICAL)

    constructor() : this(10)

    init {
        this.layout = ScrollPopupMenuLayout()

        this.popupScrollBar.addAdjustmentListener {
            this.doLayout()
            this.repaint()
        }
        this.popupScrollBar.isVisible = false
        this.add(this.popupScrollBar)

        this.addMouseWheelListener {
            val amount = if (it.scrollType == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                it.unitsToScroll * this.popupScrollBar.unitIncrement
            } else {
                if (it.wheelRotation < 0) {
                    -this.popupScrollBar.blockIncrement
                } else {
                    this.popupScrollBar.blockIncrement
                }
            }
            this.popupScrollBar.value += amount
            it.consume()
        }
    }

    override fun paintChildren(g: Graphics?) {
        g?.clipRect(this.insets.left, this.insets.top, this.width, this.height - this.insets.top - this.insets.bottom)
        super.paintChildren(g)
    }

    override fun addImpl(comp: Component?, constraints: Any?, index: Int) {
        super.addImpl(comp, constraints, index)
        if (this.maximumVisibleRows < this.componentCount - 1) {
            this.popupScrollBar.isVisible = true
        }
    }

    override fun remove(pos: Int) {
        val index = pos + 1
        super.remove(index)
        if (this.maximumVisibleRows >= this.componentCount - 1) {
            this.popupScrollBar.isVisible = false
        }
    }

    override fun show(invoker: Component?, x: Int, y: Int) {
        if (this.popupScrollBar.isVisible) {
            var extent = 0
            var max = 0
            var i = 0
            var unit = -1
            var width = 0
            for (comp in this.components) {
                if (comp is JScrollBar) {
                    continue
                }
                val preferredSize = comp.preferredSize
                width = width.coerceAtLeast(preferredSize.width)
                if (unit < 0) {
                    unit = preferredSize.height
                }
                if (i++ < this.maximumVisibleRows) {
                    extent += preferredSize.height
                }
                max += preferredSize.height
            }

            val insets = this.insets
            val widthMargin = insets.left + insets.right
            val heightMargin = insets.top + insets.bottom

            this.popupScrollBar.unitIncrement = unit
            this.popupScrollBar.blockIncrement = extent
            this.popupScrollBar.setValues(0, heightMargin + extent, 0, heightMargin + max)

            width += this.popupScrollBar.preferredSize.width + widthMargin
            this.setPopupSize(Dimension(width, heightMargin + extent))
        }

        super.show(invoker, x, y)
    }
}