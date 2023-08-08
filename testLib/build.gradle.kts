import Build_gradle.Config.PUBLISH_ARTIFACT_ID
import Build_gradle.Config.PUBLISH_GROUP_ID
import Build_gradle.Config.PUBLISH_VERSION

plugins {
    androidLibrary
    kotlinAndroid
    `maven-publish`
    signing
}

group = PUBLISH_GROUP_ID
version = PUBLISH_VERSION

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


object Config {
    const val PUBLISH_GROUP_ID = "io.github.sanggunpark"
    const val PUBLISH_VERSION = "1.0.0"
    const val PUBLISH_ARTIFACT_ID = "test-lib"
}


tasks {
    register("sourcesJar", Jar::class) {
        archiveClassifier.set("sources")
        from(
            (project.extensions.getByType<com.android.build.gradle.BaseExtension>().sourceSets.getByName("main").java.srcDirs("java") as com.android.build.gradle.internal.api.DefaultAndroidSourceDirectorySet).srcDirs,
            (project.extensions.getByType<com.android.build.gradle.BaseExtension>().sourceSets.getByName("release").java.srcDirs("java") as com.android.build.gradle.internal.api.DefaultAndroidSourceDirectorySet).srcDirs
        )
    }
}

(project as ExtensionAware).extensions.configure<com.android.build.gradle.LibraryExtension>("android") {
    publishing.singleVariant("release")
}

group = PUBLISH_GROUP_ID
version = PUBLISH_VERSION

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                afterEvaluate { from(components["release"]) }
                artifact(tasks.getByName("sourcesJar"))
                groupId = PUBLISH_GROUP_ID
                artifactId = PUBLISH_ARTIFACT_ID
                version = PUBLISH_VERSION

                // Mostly self-explanatory metadata
                pom {
                    name.set("Test Library")
                    description.set("Test")
                    url.set("https://github.com/SanggunPark/lib-test")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("SanggunPark")
                            name.set("Sanggun Park")
                            email.set("psgxxx@nm-metaworld.com")
                        }
                    }

                    // Version control info
                    scm {
                        connection.set("scm:git:github.com/SanggunPark/lib-test.git")
                        developerConnection.set("scm:git:ssh://github.com/SanggunPark/lib-test.git")
                        url.set("https://github.com/SanggunPark/lib-test/tree/main")
                    }
                }

            }

            }
        }
    }

signing {
    useInMemoryPgpKeys(
        rootProject.ext["signing.keyId"] as String?,
        rootProject.ext["signing.key"] as String?,
        rootProject.ext["signing.password"] as String?
    )
    sign(publishing.publications)
}