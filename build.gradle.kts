// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        maven(url = "https://maven.google.com")
        google()
        jcenter()
        gradlePluginPortal()
        maven(url = "https://maven.fabric.io/public")
    }


    dependencies {
//        classpath("com.android.tools.build:gradle:4.0.0")

//        classpath(kotlin("", version = Dependencies.kotlin_version))
//        classpath(kotlin("gradle-plugin", version = Dependencies.kotlin_version))

        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        maven(url = "https://maven.google.com")
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}