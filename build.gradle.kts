plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.5.2"
}

group = "com.asu.plugins"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven {
        isAllowInsecureProtocol = true
        setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
    }
    maven {
        isAllowInsecureProtocol = true
        url = uri("https://maven.aliyun.com/repository/public")
    }
    maven {
        isAllowInsecureProtocol = true
        url = uri("https://maven.aliyun.com/repository/google")
    }
    maven {
        isAllowInsecureProtocol = true
        url = uri("https://maven.aliyun.com/repository/gradle-plugin")
    }
    maven {
        isAllowInsecureProtocol = true
        url = uri("https://maven.aliyun.com/repository/spring-plugin")
    }
    maven {
        isAllowInsecureProtocol = true
        url = uri("https://maven.aliyun.com/repository/apache-snapshots")
    }
    maven {
        isAllowInsecureProtocol = true
        url = uri("http://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("com.intellij.java"))
}

dependencies {
    implementation("com.alibaba:druid:1.2.15")
}

tasks {
    runIde {
        systemProperties["idea.auto.reload.plugins"] = false
        jvmArgs = listOf(
            "-Xms512m",
            "-Xmx2048m",
        )
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("212")
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
