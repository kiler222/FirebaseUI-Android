@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.backend.common.onlyIf

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
        mavenLocal()
    }

    dependencies {
        classpath(Config.Plugins.android)
        classpath(Config.Plugins.kotlin)
        classpath(Config.Plugins.google)
        classpath(Config.Plugins.mavenPublish)
        classpath(Config.Plugins.buildInfo)
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.20.0"
}

tasks.withType<Sign>().configureEach {
    onlyIf { false } //project.extra["isReleaseVersion"] as Boolean }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        mavenLocal()
    }

    if (name != "lint" && name != "internal" && name != "lintchecks") {
        apply(plugin = "checkstyle")

        configure<CheckstyleExtension> { toolVersion = "8.10.1" }
        tasks.register<Checkstyle>("checkstyle") {
            configFile = file("$rootDir/library/quality/checkstyle.xml")
            source("src")
            include("**/*.java")
            exclude("**/gen/**")
            classpath = files()
        }
    }
}
