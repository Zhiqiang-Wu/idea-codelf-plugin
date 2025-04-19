package wzq.codelf.plugin

import com.intellij.AbstractBundle
import com.intellij.DynamicBundle
import java.util.ResourceBundle

/**
 * @author 吴志强
 * @date 2023/12/2
 */
object CodelfBundle : AbstractBundle(CodelfBundle::class.java, "messages.CodelfBundle") {
    private val adaptedControl = ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES)

    /**
     * 根据语言包插件使用不同的 ResourceBundle
     */
    override fun findBundle(
        pathToBundle: String,
        baseLoader: ClassLoader,
        control: ResourceBundle.Control,
    ): ResourceBundle {
        val dynamicBundle =
            ResourceBundle.getBundle(pathToBundle, DynamicBundle.getLocale(), baseLoader, adaptedControl)
        return dynamicBundle ?: super.findBundle(pathToBundle, baseLoader, control)
    }
}
