import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "1.6.2"

    `java-library`

    idea
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

base {
    archivesName.set(rootProject.name)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public")

    maven("https://repo.crazycrew.us/releases")

    mavenLocal()
}

dependencies {
    compileOnly("us.crazycrew.crazycrates:api:0.4")

    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
    val jarsDir = File("$rootDir/jars")

    assemble {
        doFirst {
            delete(jarsDir)

            jarsDir.mkdirs()
        }

        dependsOn(reobfJar)

        doLast {
            runCatching {
                val file = File("$jarsDir/${project.name.uppercaseFirstChar().lowercase()}")

                file.mkdirs()

                copy {
                    from(rootProject.layout.buildDirectory.file("libs/${rootProject.name}-${project.version}.jar"))
                    into(file)
                }
            }.onSuccess {
                delete(rootProject.layout.buildDirectory.get())
            }
        }
    }

    processResources {
        val properties = hashMapOf(
            "name" to rootProject.name,
            "version" to rootProject.version,
            "group" to rootProject.group,
            "description" to rootProject.description,
            "apiVersion" to providers.gradleProperty("apiVersion").get(),
            "authors" to providers.gradleProperty("authors").get()
        )

        filesMatching("plugin.yml") {
            expand(properties)
        }
    }
}