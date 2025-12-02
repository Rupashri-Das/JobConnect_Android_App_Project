plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // 1. ADD: Plugin for Room annotation processing
    kotlin("kapt")
}

android {
    namespace = "com.rupashri.jobconnect"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.rupashri.jobconnect"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // 2. ADD: Needed for Room to function
        kapt {
            arguments {
                arg("room.schemaLocation", "$project.buildDir/schemas")
            }
        }
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
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // 3. ADD: Compose Navigation (For handling screens/routes)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // 4. ADD: ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // 5. ADD: Room Database (For data persistence)
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version") // Annotation processor

    // 6. NEW: Required for Icons.Filled.Bookmark and Icons.Filled.BookmarkBorder
    implementation("androidx.compose.material:material-icons-extended:1.6.8")

    // --- NEW: UNIT TESTING DEPENDENCIES ---
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1") // Coroutine Test Dispatchers
    testImplementation("org.mockito:mockito-core:5.11.0") // Mocking Library (Mockito)
    testImplementation("androidx.arch.core:core-testing:2.2.0") // InstantTaskExecutorRule for LiveData
    // --- END NEW DEPENDENCIES ---

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
