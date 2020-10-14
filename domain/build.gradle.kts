import com.paya.buildSrc.Dependencies

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("io.objectbox")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.1"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Dependencies.KOTLIN_VERSION}")
    implementation("androidx.core:core-ktx:${Dependencies.KTX_VER}")
    
    implementation ("io.objectbox:objectbox-kotlin:${Dependencies.OBJECT_BOX_VER}")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependencies.COROUTINES_VER}")

    implementation("com.squareup.moshi:moshi:${Dependencies.MOSHI_VER}")
    implementation ("se.ansman.kotshi:api:${Dependencies.KOTSHI_VER}")
    kapt("se.ansman.kotshi:compiler:${Dependencies.KOTSHI_VER}")

    implementation ("com.google.dagger:hilt-android:${Dependencies.HILT_VER}")
    kapt ("com.google.dagger:hilt-android-compiler:${Dependencies.HILT_VER}")

    testImplementation("junit:junit:${Dependencies.JUNIT_VER}")
}
