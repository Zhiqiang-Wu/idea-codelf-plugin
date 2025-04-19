package wzq.codelf.plugin.ui.component

import com.intellij.ui.components.JBCheckBox
import wzq.codelf.plugin.Language

/**
 * @author 吴志强
 * @date 2023/11/28
 */
class LanguageCheckBox(
    private val language: Language,
) : JBCheckBox(language.toString()) {
    fun getLanguage(): Language = this.language
}
