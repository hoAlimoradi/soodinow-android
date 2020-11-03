import com.paya.buildSrc.Dependencies

plugins {
	id("com.android.library")
	kotlin("android")
	kotlin("android.extensions")
	kotlin("kapt")
	id("kotlin-kapt")
	id("dagger.hilt.android.plugin")
	id("io.objectbox")
	id("androidx.navigation.safeargs.kotlin")
	id("kotlin-android")
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
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}
	
	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
		}
	}
	
	buildFeatures {
		dataBinding = true
	}
	
}

dependencies {
	implementation(fileTree(mapOf("dir" to "libs","include" to listOf("*.jar"))))
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Dependencies.Versions.KOTLIN_VERSION}")
	implementation("androidx.appcompat:appcompat:${Dependencies.Versions.APPCOMPAT_VER}")
	implementation("androidx.core:core-ktx:${Dependencies.Versions.KTX_VER}")
	implementation("androidx.constraintlayout:constraintlayout:${Dependencies.Versions.CONSTRAINT_LAYOUT_VER}")
	implementation("androidx.legacy:legacy-support-v4:1.0.0")
	
	
	testImplementation("junit:junit:${Dependencies.Versions.JUNIT_VER}")
	androidTestImplementation("androidx.test.ext:junit:${Dependencies.Versions.TEST_JUNIT_VER}")
	androidTestImplementation("androidx.test.espresso:espresso-core:${Dependencies.Versions.ESPRESSO_VER}")
	
	implementation("androidx.navigation:navigation-fragment-ktx:${Dependencies.Versions.NAVIGATION_COMPONENT_VER}")
	implementation("androidx.navigation:navigation-ui-ktx:${Dependencies.Versions.NAVIGATION_COMPONENT_VER}")
	
	implementation("com.google.dagger:hilt-android:${Dependencies.Versions.HILT_VER}")
	kapt("com.google.dagger:hilt-android-compiler:${Dependencies.Versions.HILT_VER}")
	implementation("androidx.hilt:hilt-lifecycle-viewmodel:${Dependencies.Versions.ANDROIDX_HILT_VER}")
	kapt("androidx.hilt:hilt-compiler:${Dependencies.Versions.ANDROIDX_HILT_VER}")
	
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Dependencies.Versions.LIFECYCLE_VER}")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Dependencies.Versions.LIFECYCLE_VER}")
	kapt("androidx.lifecycle:lifecycle-compiler:${Dependencies.Versions.LIFECYCLE_VER}")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Dependencies.Versions.LIFECYCLE_VER}")
	implementation("android.arch.lifecycle:extensions:2.1.0")
	
	kapt ("com.android.databinding:compiler:${Dependencies.Versions.DATA_BINDING_VER}")
	
	implementation ("com.alimuzaffar.lib:pinentryedittext:${Dependencies.Versions.PIN_ENTRY_EDIT_TEXT}")
	
	implementation ("androidx.viewpager2:viewpager2:${Dependencies.Versions.VIEW_PAGER_VERSION}")
	implementation ("com.google.android.material:material:${Dependencies.Versions.MATERIAL_VER}")
	implementation ("com.tbuonomo.andrui:viewpagerdotsindicator:${Dependencies.Versions.VIEW_PAGER_DOTS_INDICATOR_VER}")
	
	implementation ("com.github.PhilJay:MPAndroidChart:${Dependencies.Versions.MP_ANDROID_CHART}")
	
	
	
	implementation(project(Dependencies.Modules.DOMAIN))
}
