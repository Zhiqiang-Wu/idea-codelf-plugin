package wzq.codelf.plugin

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * @author 吴志强
 * @date 2023/11/23
 */
object CodelfIcons {
    @JvmField
    val SEARCH: Icon = IconLoader.getIcon("/icons/search.svg", CodelfIcons::class.java)

    @JvmField
    val FILTER: Icon = IconLoader.getIcon("/icons/filter.svg", CodelfIcons::class.java)
}
