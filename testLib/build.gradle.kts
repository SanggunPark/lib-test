plugins {
    androidLibrary
    kotlinAndroid
}

android {
    namespace = "com.metaverse.world.testLib"

    defaultConfig {
        minSdk = 23
        compileSdk = AppConfig.compileSdkVersion

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures.buildConfig = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}


dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.kotlinSerialization)
    implementation(group = "commons-codec", name = "commons-codec", version = "1.15")
    retrofit()
    okHttp()
    coroutines()
    koin()
    implementation(Dependencies.googlePlayServicesAuth)
    implementation(Dependencies.timber)
    kotest()
    androidTest()

    testImplementation("io.kotest.extensions:kotest-extensions-koin:1.1.0")
}