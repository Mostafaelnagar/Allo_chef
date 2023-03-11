import com.google.protobuf.gradle.*

plugins {
    id(Config.Plugins.androidApplication)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.kotlinKapt)
    id(Config.Plugins.kotlin_extensions)
    id(Config.Plugins.navigationSafeArgs)
    id(Config.Plugins.hilt)
    id(Config.Plugins.proto_buf)
    id(Config.Plugins.google_secrets)
    id(Config.Plugins.google_services)
//    id(Config.Plugins.huawei_services)
    id(Config.Plugins.checkDependencyUpdates) version "1.5.0"

//    id(Config.Plugins.appsweep) version "latest.release"
}
protobuf {
    protoc {
        artifact = Libraries.datastore_protoc
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

android {
    compileSdk = Config.AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = Config.AppConfig.appId
        minSdk = Config.AppConfig.minSdkVersion
        targetSdk = Config.AppConfig.compileSdkVersion
        versionCode = Config.AppConfig.versionCodeTest
        versionName = Config.AppConfig.versionName
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
        testInstrumentationRunner = Config.AppConfig.testRunner

    }

    buildTypes {
        signingConfigs {
            create("releaseConfig") {
                storeFile = file("/home/t-e-s/Osman.jks")
                storePassword = "te2018"
                keyAlias = "te"
                keyPassword = "te2018"
            }
//            create("debugConfig") {
//                storeFile = file("/home/t-e-s/Osman.jks")
//                storePassword = "te2018"
//                keyAlias = "te"
//                keyPassword = "te2018"
//            }

        }
        getByName("debug") {
//            signingConfig = signingConfigs.getByName("debugConfig")

            isDebuggable = true
            manifestPlaceholders["appName"] = "@string/app_name"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            manifestPlaceholders["appRoundIcon"] = "@mipmap/ic_launcher_round"

        }



        getByName("release") {
            signingConfig = signingConfigs.getByName("releaseConfig")
            isMinifyEnabled = true
            isShrinkResources = true
            manifestPlaceholders["appName"] = "@string/app_name"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            manifestPlaceholders["appRoundIcon"] = "@mipmap/ic_launcher_round"
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

    }

    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    dataBinding {
        isEnabled = true
    }
    lint {
//        isCheckReleaseBuilds = false
//        // Or, if you prefer, you can continue to check for errors in release builds,
//        // but continue the build even when errors are found:
//        isAbortOnError = false
    }
    buildFeatures {
        viewBinding = true
    }
//    appsweep {
//        apiKey = Libraries.appsweep_key
//    }
    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }
    bundle {
        language {
            enableSplit = false
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //Room
    implementation(Libraries.roomVersion)
    kapt(Libraries.roomCompiler)
    implementation(Libraries.roomktx)
    implementation(Libraries.roomCommon)
    implementation(Libraries.startup)
    implementation(Libraries.store_update)

    // Networking
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitConverter)
    implementation(Libraries.gson)
    implementation(Libraries.interceptor)
    implementation(Libraries.chuckLogging)

    // Utils
    implementation(Libraries.playServices)
    implementation(Libraries.localization)
    implementation(Libraries.multidex)
    implementation(Libraries.permissions)
    implementation(Libraries.gson)
// paging
    implementation(Libraries.paging_version)
    implementation(Libraries.paging_version_ktx)

    // Hilt
    implementation(Libraries.hilt)
    // Firebase
    implementation(platform(Libraries.firebase_platform))
    implementation(Libraries.firebase_messaging)
    implementation(Libraries.hms_core)
    implementation(Libraries.hms_push)
    implementation(Libraries.hms_remote_config)
    implementation(Libraries.firebase_auth_ktx)
    implementation(Libraries.firebase_auth)
    implementation(Libraries.facebook_auth)
    kapt(Libraries.hiltDaggerCompiler)
    // Support
    implementation(Libraries.appCompat)
    implementation(Libraries.coreKtx)
    implementation(Libraries.androidSupport)

    // Arch Components
    implementation(Libraries.viewModel)
    implementation(Libraries.lifeData)
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewModelState)

    // Kotlin Coroutines
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesAndroid)
    //DATA STORE
    implementation(Libraries.datastore_preferences)
    implementation(Libraries.datastore_core)
    implementation(Libraries.datastore_protobuf)

    // Map
    implementation(Libraries.map)
    implementation(Libraries.playServicesLocation)
    implementation(Libraries.rx)


    // UI
    implementation(Libraries.materialDesign)
    implementation(Libraries.navigationFragment)
    implementation(Libraries.navigationUI)
    implementation(Libraries.loadingAnimations)
    implementation(Libraries.alerter)
    implementation(Libraries.coil)
    implementation(Libraries.ssp)
    implementation(Libraries.ssdp)
    implementation(Libraries.shimmer)
    implementation(Libraries.splash_screen)
    //EXO PLAYER
    implementation(Libraries.exoplayer)
    implementation(Libraries.exoplayer_core)
    implementation(Libraries.exoplayer_dash)
    implementation(Libraries.exoplayer_ui)
    implementation(Libraries.exoplayer_smooth_streaming)
    //Pin code
    implementation(Libraries.pin_code)
    //badge
    implementation(Libraries.badge)
    //sSCustomBottomNavigation
    implementation(Libraries.sSCustomBottomNavigation)

    //AdvancedWebView
    implementation(Libraries.AdvancedWebView)

    //cardSlider
    implementation(Libraries.cardSlider)

    //file_picker
    implementation(Libraries.file_picker)
 //myfatoorah
    implementation(Libraries.myfatoorah)

    // Project Modules
    implementation(project(Config.Modules.prettyPopUp))

}
