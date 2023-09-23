import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    war
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":usecases"))
    implementation(project(":data"))
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.sb.starter.web)
    implementation(libs.sb.starter.data.jdbc)
    implementation(libs.kotlin.reflect)

    implementation(libs.gson)

    implementation(libs.bundles.arrow)
    implementation(libs.commons.codec)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.jjwt)
    runtimeOnly(libs.h2.database)


    testImplementation(libs.sb.starter.test)
    testImplementation(libs.bundles.testing)
}

configurations.all {
    exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-websocket")
    exclude(group = "org.apache.tomcat.embed", module = "tomcat-embed-el")
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
