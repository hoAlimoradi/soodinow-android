import com.paya.buildSrc.Dependencies

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("io.objectbox")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.1"

  /*  compileSdkVersion(31)
    buildToolsVersion = "31.0.0-rc4"*/

    /*applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "Soodinow - ${variant.versionName}.apk"
                println("OutputFileName: $outputFileName")
                output.outputFileName = outputFileName
            }
    }*/

    applicationVariants.all(object : Action<com.android.build.gradle.api.ApplicationVariant> {
        override fun execute(variant: com.android.build.gradle.api.ApplicationVariant) {
            println("variant: ${variant}")
            variant.outputs.all(object : Action<com.android.build.gradle.api.BaseVariantOutput> {
                override fun execute(output: com.android.build.gradle.api.BaseVariantOutput) {

                    val outputImpl = output as com.android.build.gradle.internal.api.BaseVariantOutputImpl
                    val fileName = output.outputFileName
                        .replace("-release", "Soodinow-r${variant.versionName}")
                        .replace("-debug", "Soodinow-debug${variant.versionName}")
                    println("output file name: ${fileName}")
                    outputImpl.outputFileName = fileName
                }
            })
        }
    })

    defaultConfig {
        applicationId = "com.paya.soodinow"
        minSdkVersion(21)
        targetSdkVersion(30)
        //targetSdkVersion(31)
        versionCode = 5
        versionName = "1.1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appAuthRedirectScheme"] = "hadiidbouk-appAuthWebView"
    }
    
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

    }



    buildFeatures {
        android.buildFeatures.viewBinding = true
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
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:${Dependencies.Versions.ANDROIDX_HILT_VER}")
//    kapt ("androidx.hilt:hilt-compiler:${Dependencies.Versions.ANDROIDX_HILT_VER}")
    
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.11.0")
    kapt ("com.android.databinding:compiler:${Dependencies.Versions.DATA_BINDING_VER}")

    implementation("io.github.inflationx:calligraphy3:${Dependencies.Versions.CALLIGRAPHY}")
    implementation("io.github.inflationx:viewpump:${Dependencies.Versions.VIEWPUMP}")

    implementation (platform("com.google.firebase:firebase-bom:${Dependencies.Versions.FIREBASEـBOM}"))
    implementation ("com.google.firebase:firebase-crashlytics-ktx")
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("io.sentry:sentry-android:${Dependencies.Versions.SENTRY}")

    implementation(project(Dependencies.Modules.DOMAIN))
    implementation(project(Dependencies.Modules.PRESENTATION))
    implementation(project(Dependencies.Modules.DATA))
    implementation(project(Dependencies.Modules.COMMON))
    
    testImplementation("junit:junit:${Dependencies.Versions.JUNIT_VER}")
    androidTestImplementation("androidx.test.ext:junit:${Dependencies.Versions.TEST_JUNIT_VER}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Dependencies.Versions.ESPRESSO_VER}")
    debugImplementation ("com.squareup.leakcanary:leakcanary-android:${Dependencies.Versions.LEAK_CANARY}")
}