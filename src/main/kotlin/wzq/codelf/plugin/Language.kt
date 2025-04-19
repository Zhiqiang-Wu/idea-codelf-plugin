package wzq.codelf.plugin

import lombok.AllArgsConstructor
import lombok.Getter

/**
 * @author 吴志强
 * @date 2023/11/24
 */
@Getter
@AllArgsConstructor
enum class Language(
    val value: Int,
    private val description: String,
) {
    JAVA(23, "Java"),
    JAVASCRIPT(22, "JavaScript"),
    C(28, "C"),
    CPP(16, "C++"),
    C_SHAP(6, "C#"),
    PYTHON(19, "Python"),
    KOTLIN(145, "kotlin"),
    GO(55, "Go"),
    RUST(147, "Rust"),
    TYPESCRIPT(151, "TypeScript"),
    SQL(37, "SQL"),
    DART(88, "Dart"),
    PHP(24, "PHP"),
    RUBY(32, "Ruby"),
    ERLANG(25, "Erlang"),
    HTML(3, "HTML"),
    CSS(18, "CSS"),
    JSX(206, "JSX"),
    VUE(355, "Vue"),
    LESS(135, "Less"),
    SASS(133, "Sass"),
    SWIFT(137, "Swift"),
    SCALA(47, "Scala"),
    BASH(41, "Bash"),
    LUA(54, "Lua"),
    QML(107, "QML"),
    SHELL(31, "Shell"),
    ;

    override fun toString(): String = this.description
}
