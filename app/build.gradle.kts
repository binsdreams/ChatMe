plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android") // ✅ Ensure this is here
    id("kotlin-kapt") // ✅ Required for annotation processing
    id("kotlin-parcelize")
}

android {
    namespace = "com.chatapp.demo"
    compileSdk = 35

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.chatapp.demo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.material)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.lottie)
    //noinspection UseTomlInstead
    implementation("androidx.room:room-runtime:2.6.1")
    // Hilt Dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.google.services)

    implementation(libs.room.android)
    kapt(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.coroutines)
    implementation(libs.security)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.benchmark.common)
    implementation(libs.androidx.ui.test.junit4.android)

    // AndroidX Test Libraries
    androidTestImplementation(libs.androidx.junit) // JUnit extension for Android
    androidTestImplementation(libs.androidx.runner)  // Android test runner
    androidTestImplementation(libs.androidx.rules)  // Test rules (optional but useful)
    testImplementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.mockito.kotlin.v531)
    // JUnit for unit tests
    testImplementation(libs.junit)
    testImplementation(libs.mockk)  // Make sure to use the latest version
    testImplementation("org.robolectric:robolectric:4.5") {
        exclude(
            group = "androidx.webkit",
            module = "webkit"
        )
        exclude(
            group = "android.webkit",
            module = "webkit"
        )
    }
    testImplementation(libs.androidx.core.testing)
}