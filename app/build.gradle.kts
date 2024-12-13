plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.example.myapplication"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.example.myapplication"
		minSdk = 33
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		viewBinding = true
	}
}

dependencies {

	implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
	implementation("androidx.core:core-ktx:1.15.0")
	implementation("androidx.fragment:fragment-ktx:1.8.5")

	implementation("androidx.appcompat:appcompat:1.7.0")
	implementation("com.google.android.material:material:1.12.0")
	implementation("androidx.constraintlayout:constraintlayout:2.2.0")
//	implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

	implementation("com.squareup.okhttp3:okhttp:4.12.0")
	implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Or latest version

	implementation("com.google.code.gson:gson:2.11.0")
	implementation("com.squareup.retrofit2:converter-gson:2.11.0")
}