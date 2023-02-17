// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        // Configure the Maven repository address for the HMS Core SDK.
        maven(url = Config.Dependencies.huwawiPackUrl)

    }
    dependencies {
        classpath(Config.Dependencies.gradleVersion)
//        classpath(Config.Dependencies.appGalleryConnect)
        classpath(Config.Dependencies.kotlin)
        classpath(Config.Dependencies.navigationSafeArgs)
        classpath(Config.Dependencies.hilt)
        classpath(Config.Dependencies.google_services)
        classpath(Config.Dependencies.proto_buf)
        classpath(Config.Dependencies.google_secret)
    }
}

plugins {
    id(Config.Plugins.ktLint) version Versions.ktLint
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}

subprojects {
    apply(plugin = Config.Plugins.ktLint) // To apply ktLint to all included modules

    repositories {
        mavenCentral()
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = Config.Dependencies.jitPackURL)
        // Configure the Maven repository address for the HMS Core SDK.
        maven(url = Config.Dependencies.huwawiPackUrl)

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register("installGitHooks", Copy::class) {
    from("${rootProject.rootDir}/pre-commit")
    into("${rootProject.rootDir}/.git/hooks")
}