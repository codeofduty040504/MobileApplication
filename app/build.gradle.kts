plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.adminoffice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.adminoffice"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    //  implementation("com.google.firebase:firebase-storage:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Navigator
    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0-rc02")

    // BottomSheetNavigator
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.0.0-rc02")

    // TabNavigator
    implementation("cafe.adriel.voyager:voyager-tab-navigator:1.0.0-rc02")

    // Transitions
    implementation("cafe.adriel.voyager:voyager-transitions:1.0.0-rc02")

    // Android

    // Android ViewModel integration
    implementation("cafe.adriel.voyager:voyager-androidx:1.0.0-rc02")

    // Koin integration
    implementation("cafe.adriel.voyager:voyager-koin:1.0.0-rc02")

    // Hilt integration
    implementation("cafe.adriel.voyager:voyager-hilt:1.0.0-rc02")

    // LiveData integration
    implementation("cafe.adriel.voyager:voyager-livedata:1.0.0-rc02")

    // Desktop + Android

    // Kodein integration
    implementation("cafe.adriel.voyager:voyager-kodein:1.0.0-rc02")

    // RxJava integration
    implementation("cafe.adriel.voyager:voyager-rxjava:1.0.0-rc02")

    // Volley
    implementation("com.android.volley:volley:1.2.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))

    // Add the dependency for the Cloud Storage library
    // When using the BoM, you don't specify versions in Firebase library dependencies
  //  implementation("com.google.firebase:firebase-storage-ktx")
   // implementation("com.google.firebase:firebase-storage-ktx:20.2.1")
    implementation ("com.google.code.gson:gson:2.8.8")
    // Stripe Android SDK
    implementation ("com.stripe:stripe-android:20.30.2")
    // Includes Google Maps
    implementation("com.google.maps.android:maps-compose:2.12.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.maps.android:android-maps-utils:0.5")
    // Stream SDK
    implementation("io.getstream:stream-chat-android-compose:5.4.0")
    // Client + offline + UI components
    // implementation("io.getstream:stream-chat-android-ui-components:6.0.4")
//    // Client + offline
//    implementation("io.getstream:stream-chat-android-offline:6.0.2")
//    // Client only
//    implementation("io.getstream:stream-chat-android-client:6.0.2")
    implementation ("io.socket:socket.io-client:1.0.0")
    // AR
    implementation("io.github.sceneview:arsceneview:0.10.0")
    // Coil
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")
    implementation("com.beust:klaxon:5.5")

}