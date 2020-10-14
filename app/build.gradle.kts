import com.paya.buildSrc.Dependencies

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
    buildToolsVersion = "30.0.1"

    defaultConfig {
        applicationId = "com.paya.soodinow"
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

    implementation("androidx.appcompat:appcompat:${Dependencies.Versions.APPCOMPAT_VER}")
    implementation("androidx.core:core-ktx:${Dependencies.Versions.KTX_VER}")
    implementation("androidx.constraintlayout:constraintlayout:${Dependencies.Versions.CONSTRAINT_LAYOUT_VER}")
    
    implementation ("com.google.dagger:hilt-android:${Dependencies.Versions.HILT_VER}")
    kapt ("com.google.dagger:hilt-android-compiler:${Dependencies.Versions.HILT_VER}")
    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:${Dependencies.Versions.ANDROIDX_HILT_VER}")
    kapt ("androidx.hilt:hilt-compiler:${Dependencies.Versions.ANDROIDX_HILT_VER}")
    
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.11.0")
    
    implementation(project(Dependencies.Modules.DOMAIN))
    implementation(project(Dependencies.Modules.PRESENTATION))
    implementation(project(Dependencies.Modules.DATA))
    implementation(project(Dependencies.Modules.COMMON))
    
    testImplementation("junit:junit:${Dependencies.Versions.JUNIT_VER}")
    androidTestImplementation("androidx.test.ext:junit:${Dependencies.Versions.TEST_JUNIT_VER}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Dependencies.Versions.ESPRESSO_VER}")
}