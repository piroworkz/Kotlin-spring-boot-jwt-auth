import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    war
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

group = libs.versions.group
version = libs.versions.version

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
//    implementation(project(":usecases"))
//    implementation(project(":domain"))
//    implementation(project(":data"))
    implementation(libs.bundles.arrow)
    implementation(libs.commons.codec)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.springboot)
    implementation(libs.bundles.jjwt)
    runtimeOnly(libs.h2.database)
    testImplementation(libs.bundles.springboot.test)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
