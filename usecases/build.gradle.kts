plugins {
    war
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.bundles.arrow)
    implementation(libs.spring.context)
    testImplementation(project(":testShared"))
    testImplementation(libs.bundles.testing)
}