plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Dependencies.kotlin_version}")
    implementation("androidx.appcompat:appcompat:${Dependencies.appcompat_ver}")
    implementation("androidx.core:core-ktx:${Dependencies.ktx_ver}")
    implementation("androidx.constraintlayout:constraintlayout:${Dependencies.constraint_layout_ver}")
    testImplementation("junit:junit:${Dependencies.junit_ver}")
    androidTestImplementation("androidx.test.ext:junit:${Dependencies.test_junit_ver}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Dependencies.espresso_ver}")
}