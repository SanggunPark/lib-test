plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath("io.github.gradle-nexus:publish-plugin:1.1.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

// Create variables with empty default values
ext["signing.keyId"] = ""
ext["signing.password"] = ""
ext["signing.key"] = ""
ext["ossrhUsername"] = ""
ext["ossrhPassword"] = ""
ext["sonatypeStagingProfileId"] = ""

// Create variables with empty default values
val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    // Read local.properties file first if it exists
    val p = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
    ext["ossrhUsername"] = p.getProperty("ossrhUsername")
    ext["ossrhPassword"] = p.getProperty("ossrhPassword")
    ext["sonatypeStagingProfileId"] = p.getProperty("sonatypeStagingProfileId")
    ext["signing.keyId"] = p.getProperty("keyId")
    ext["signing.password"] = p.getProperty("signing.password")
    ext["signing.key"] = p.getProperty("signing.key")
} else {
    // Use system environment variables
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
    ext["sonatypeStagingProfileId"] = System.getenv("SONATYPE_STAGING_PROFILE_ID")
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.key"] = System.getenv("SIGNING_KEY")
}

// Set up Sonatype repository
nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId.set(rootProject.ext["sonatypeStagingProfileId"] as String?)
            username.set(rootProject.ext["ossrhUsername"] as String?)
            password.set(rootProject.ext["ossrhPassword"] as String?)
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}