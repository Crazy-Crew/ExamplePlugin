plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "1.5.7"

    `java-library`

    // kotlin("jvm") version "1.9.10"
}

rootProject.group = "com.ryderbelserion.template"
rootProject.description = "A plugin template."
rootProject.version = "0.2"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.triumphteam.dev/snapshots/")

    maven("https://repo.crazycrew.us/releases/")
}

dependencies {
    // compileOnly(libs.jetbrains.kotlin)

    // https://github.com/ryderbelserion/Cluster
    // compileOnly(libs.cluster.paper)

    // https://github.com/TriumphTeam/triumph-cmds
    // compileOnly(libs.triumph.cmds)

    // https://github.com/AuthMe/ConfigMe
    // compileOnly(libs.config.me)

    paperweight.paperDevBundle("1.20.2-R0.1-SNAPSHOT")
}

/*kotlin {
    jvmToolchain(17)
}*/

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
    /*compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
            javaParameters = true
        }
    }*/

    val jarsDir = File("$rootDir/jars")

    assemble {
        if (jarsDir.exists()) jarsDir.delete() else jarsDir.mkdirs()

        dependsOn(reobfJar)
    }

    reobfJar {
        outputJar.set(file("$jarsDir/${rootProject.name}-${rootProject.version}.jar"))
    }

    processResources {
        val props = mapOf(
            "name" to rootProject.name,
            "group" to rootProject.group,
            "version" to rootProject.version,
            "description" to rootProject.description,
            "apiVersion" to "1.20",
        )

        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}