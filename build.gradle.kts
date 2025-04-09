import com.diffplug.spotless.LineEnding

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.3.0"
    id("com.diffplug.spotless") version "7.0.2"
}

group = "wzq.codelf.plugin"
version = "1.0.8"

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
        create("IC", "251.23774.200")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
    }

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    implementation("cn.hutool:hutool-http:5.8.29")

    implementation("org.apache.xmlgraphics:batik-swing:1.17") {
        exclude(group = "xml-apis", module = "xml-apis")
        exclude(group = "commons-logging", module = "commons-logging")
        exclude(group = "commons-io", module = "commons-io")
    }

    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")
    testImplementation(platform("org.junit:junit-bom:5.11.3"))
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

        changeNotes = """
      Initial version
    """.trimIndent()
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
