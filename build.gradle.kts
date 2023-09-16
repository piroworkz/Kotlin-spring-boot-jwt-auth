plugins {
    alias(libs.plugins.kotlin.jvm).apply(false)
}

allprojects {
    group = "com.davidluna"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

subprojects {
    plugins.apply("war")
    plugins.apply("kotlin")
}