
buildscript {

    repositories {
        google()
        jcenter()
        maven(url = "https://maven.google.com")
        gradlePluginPortal()
        maven(url = "https://maven.fabric.io/public")
    }


    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${com.paya.buildSrc.Dependencies.Versions.HILT_VER}")
        classpath ("io.objectbox:objectbox-gradle-plugin:${com.paya.buildSrc.Dependencies.OBJECT_BOX_VER}")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:${com.paya.buildSrc.Dependencies.Versions.NAVIGATION_COMPONENT_VER}")
    
    
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

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}