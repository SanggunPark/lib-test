plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

val javaVersion = JavaVersion.VERSION_11
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    // Do not use, no effect; will be overridden by kotlinDslPluginOptions.jvmTarget, see KotlinDslCompilerPlugins.
    kotlinOptions.jvmTarget = javaVersion.toString()
}
java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}