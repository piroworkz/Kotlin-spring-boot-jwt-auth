dependencies {
    implementation(project(":domain"))
}
tasks.test {
    useJUnitPlatform()
}