plugins {
    alias(libs.plugins.kotlin.jvm).apply(false)
}

allprojects {
    group = "com.davidluna"
    version = "0.0.1"
    repositories {
        mavenCentral()
    }
}

subprojects {
    plugins.apply("kotlin")
}