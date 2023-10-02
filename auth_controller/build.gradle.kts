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

    implementation(libs.sb.starter.web)
    implementation(libs.gson)

    implementation(libs.bundles.exposed)
    implementation(libs.bundles.arrow)
    implementation(libs.bundles.coroutines)
}