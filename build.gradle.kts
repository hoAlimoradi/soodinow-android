
buildscript {

    repositories {
        google()
        jcenter()
        maven(url = "https://maven.google.com")
        gradlePluginPortal()
        maven(url = "https://maven.fabric.io/public")
    }


    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${com.paya.buildSrc.Dependencies.Versions.HILT_VER}")
        classpath ("io.objectbox:objectbox-gradle-plugin:${com.paya.buildSrc.Dependencies.Versions.OBJECT_BOX_VER}")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:${com.paya.buildSrc.Dependencies.Versions.NAVIGATION_COMPONENT_VER}")
    
        classpath ("com.github.ben-manes:gradle-versions-plugin:0.33.0")
        classpath ("org.jetbrains.kotlin:kotlin-android-extensions:1.4.10")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts.kts.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://maven.google.com")
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.33.0"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}