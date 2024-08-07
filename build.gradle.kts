plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "wzq.codelf.plugin"
version = "1.0.7"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenLocal()
    mavenCentral()
}

intellij {
    version.set("2024.2")
    type.set("IC")

    plugins.set(listOf(/* Plugin Dependencies */))
}

dependencies {
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
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    test {
        useJUnitPlatform()
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
