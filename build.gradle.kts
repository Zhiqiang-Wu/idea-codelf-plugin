import com.diffplug.spotless.LineEnding
import org.jetbrains.changelog.Changelog

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.3.0"
    id("com.diffplug.spotless") version "7.0.2"
    id("org.jetbrains.changelog") version "2.2.1"
}

group = "wzq.codelf.plugin"
version = "1.0.10"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create("IC", "2025.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
    }

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation("cn.hutool:hutool-http:5.8.29")

    implementation("org.apache.xmlgraphics:batik-swing:1.18") {
        exclude(group = "xml-apis", module = "xml-apis")
        exclude(group = "commons-logging", module = "commons-logging")
        exclude(group = "commons-io", module = "commons-io")
    }

    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")
    testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

spotless {
    lineEndings = LineEnding.UNIX
    kotlin {
        ktlint()
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "242"
        }

        changeNotes = provider {
            changelog.render(Changelog.OutputType.HTML)
        }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
        options.encoding = "UTF-8"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "21"
        kotlinOptions.apiVersion = "2.1"
        kotlinOptions.languageVersion = "2.1"
    }
}
