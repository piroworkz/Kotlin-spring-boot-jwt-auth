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
    implementation(project(":core"))
    implementation(project(":auth_controller"))

    implementation(libs.sb.starter.web)
    providedRuntime(libs.spring.boot.starter.tomcat)

    implementation(libs.bundles.exposed)

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
