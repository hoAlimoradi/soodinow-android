import com.example.buildSrc.Dependencies

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("io.objectbox")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.0"

    defaultConfig {
        applicationId = "com.example.mvvm_arch"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

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

    implementation("androidx.appcompat:appcompat:${Dependencies.APPCOMPAT_VER}")
    implementation("androidx.core:core-ktx:${Dependencies.KTX_VER}")
    implementation("androidx.constraintlayout:constraintlayout:${Dependencies.CONSTRAINT_LAYOUT_VER}")
    
    implementation ("com.google.dagger:hilt-android:${Dependencies.Versions.HILT_VER}")
    kapt ("com.google.dagger:hilt-android-compiler:${Dependencies.Versions.HILT_VER}")
    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:${Dependencies.Versions.ANDROIDX_HILT_VER}")
    kapt ("androidx.hilt:hilt-compiler:${Dependencies.Versions.ANDROIDX_HILT_VER}")

//    implementation ("io.objectbox:objectbox-kotlin:${Dependencies.OBJECT_BOX_VER}")
    
    implementation(project(Dependencies.Modules.DOMAIN))
    implementation(project(Dependencies.Modules.PRESENTATION))
    implementation(project(Dependencies.Modules.DATA))
    implementation(project(Dependencies.Modules.COMMON))
    
    testImplementation("junit:junit:${Dependencies.JUNIT_VER}")
    androidTestImplementation("androidx.test.ext:junit:${Dependencies.TEST_JUNIT_VER}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Dependencies.ESPRESSO_VER}")
}