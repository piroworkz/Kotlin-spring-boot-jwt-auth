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

dependencies {
    implementation(project(":domain"))
    implementation(project(":usecases"))
    implementation(project(":data"))

    implementation(libs.sb.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    providedRuntime(libs.spring.boot.starter.tomcat)
    implementation(libs.gson)

    implementation(libs.bundles.exposed)
    implementation(libs.bundles.arrow)
    implementation(libs.bundles.coroutines)

    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
    implementation(libs.kotlin.reflect)
    implementation(libs.mysql.connector)

    implementation(libs.commons.codec)
    implementation(libs.bundles.jjwt)

    testImplementation(libs.sb.starter.test)
    testImplementation(libs.bundles.testing)
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
