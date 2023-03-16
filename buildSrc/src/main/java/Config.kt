object Config {
    object AppConfig {
        const val appId = "app.te.alo_chef"
        const val compileSdkVersion = 33
        const val minSdkVersion = 23
        const val versionCode = 1
        const val versionCodeTest = 3
        const val versionName = "17-03-2023 (1.0.0)"
        const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val cmakePath = "jni/CMakeLists.txt"
    }

    object Dependencies {
        const val jitPackURL = "https://jitpack.io"
        const val huwawiPackUrl = "https://developer.huawei.com/repo/"
        const val gradleVersion = "com.android.tools.build:gradle:${Versions.gradleVersion}"
        const val appGalleryConnect = "com.huawei.agconnect:agcp:${Versions.appGalleryConnect}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val navigationSafeArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidNavigation}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}"
        const val google_services = "com.google.gms:google-services:${Versions.google_services}"
        const val google_secret =
            "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.google_secret}"
        const val proto_buf =
            "com.google.protobuf:protobuf-gradle-plugin:${Versions.classPath_protobuf}"

    }

    object Plugins {
        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinKapt = "kotlin-kapt"
        const val navigationSafeArgs = "androidx.navigation.safeargs"
        const val hilt = "dagger.hilt.android.plugin"
        const val kotlin_extensions = "kotlin-android-extensions"
        const val ktLint = "org.jlleitschuh.gradle.ktlint"
        const val google_services = "com.google.gms.google-services"
        const val huawei_services = "com.huawei.agconnect"
        const val proto_buf = "com.google.protobuf"
        const val google_secrets = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
        const val appsweep = "com.guardsquare.appsweep"
        const val checkDependencyUpdates = "name.remal.check-dependency-updates"
    }

    object Modules {
        const val data = ":data"
        const val prettyPopUp = ":prettyPopUp"
    }

}