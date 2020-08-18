import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
}

group = "dev.forcetower.unes"
val baseName = "breaker"
version = "1.0.0"

repositories {
    mavenCentral()
}
dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.8.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.1")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines:+")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")

    testImplementation(kotlin("test-junit"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}