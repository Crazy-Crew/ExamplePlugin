plugins {
    id("io.github.goooler.shadow") version "8.1.7"

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

repositories {
    maven("https://repo.papermc.io/repository/maven-public")

    maven("https://repo.crazycrew.us/releases")

    mavenLocal()
}

dependencies {
    compileOnly("us.crazycrew.crazycrates:api:0.5")

    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

paperweight {
    reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
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