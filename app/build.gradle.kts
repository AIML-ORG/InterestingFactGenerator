plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
}

android {
	namespace = "com.example.myapplication"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.myapplication"
		minSdk = 32
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

	implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
	implementation("androidx.core:core-ktx:1.13.1")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.12.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")

	// ONNX
	implementation("com.microsoft.onnxruntime:onnxruntime-android:1.19.2")
	implementation("com.microsoft.onnxruntime:onnxruntime-training-android:1.19.2")
	implementation("com.microsoft.onnxruntime:onnxruntime-extensions-android:0.12.4")

}