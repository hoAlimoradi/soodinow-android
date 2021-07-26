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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Dependencies.Versions.KOTLIN_VERSION}")
    implementation("androidx.core:core-ktx:${Dependencies.Versions.KTX_VER}")
    
    implementation ("io.objectbox:objectbox-kotlin:${Dependencies.Versions.OBJECT_BOX_VER}")

   // implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependencies.Versions.COROUTINES_VER}")
    
    implementation("com.squareup.retrofit2:retrofit:${Dependencies.Versions.RETROFIT_VER}")
    implementation ("com.squareup.retrofit2:converter-gson:${Dependencies.Versions.GSON_CONVERTER_VER}")
    implementation("com.squareup.moshi:moshi:${Dependencies.Versions.MOSHI_VER}")
    implementation ("se.ansman.kotshi:api:${Dependencies.Versions.KOTSHI_VER}")
    kapt("se.ansman.kotshi:compiler:${Dependencies.Versions.KOTSHI_VER}")

    implementation ("com.google.dagger:hilt-android:${Dependencies.Versions.HILT_VER}")
    kapt ("com.google.dagger:hilt-android-compiler:${Dependencies.Versions.HILT_VER}")

    testImplementation("junit:junit:${Dependencies.Versions.JUNIT_VER}")
}
