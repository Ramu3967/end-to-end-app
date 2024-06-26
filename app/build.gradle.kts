plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.end_to_end_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.end_to_end_app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.end_to_end_app.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    sourceSets {
        // configuring the androidTest SS to use the debug assets
        sourceSets {
            named("androidTest") {
                assets.srcDirs("src/debug/assets")
            }
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = false
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")


    // network
    implementation ("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofitVersion"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofitVersion"]}")

    implementation ("com.google.code.gson:gson:${rootProject.extra["gsonVersion"]}")
    implementation ("com.squareup.okhttp3:okhttp:${rootProject.extra["gsonVersion"]}")
    implementation ("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okHttpVersion"]}")

    // DI
    implementation ("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt ("com.google.dagger:hilt-compiler:${rootProject.extra["hiltVersion"]}")

    // Cors and flows
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["kotlinVersion"]}")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["kotlinVersion"]}")

    // cache/room
    implementation ("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    implementation ("androidx.room:room-ktx:${rootProject.extra["roomVersion"]}")
    kapt ("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")
    
    //compose-deps
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation ("com.github.bumptech.glide:compose:1.0.0-alpha.1")
    implementation ("androidx.navigation:navigation-compose:2.4.0-beta02")



    // for unit tests
    testImplementation ("org.robolectric:robolectric:${rootProject.extra["robolectric"]}")
//    testImplementation("com.squareup.okhttp3:mockwebserver:${rootProject.extra["mockWebServerVersion"]}")
    testImplementation ("org.mockito:mockito-core:${rootProject.extra["mockitoVersion"]}")

    // android test deps
    androidTestImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hiltVersion"]}")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")

    androidTestImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hiltVersion"]}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")


    // Dependencies for both test and AndroidTest
    debugImplementation("com.google.truth:truth:${rootProject.extra["truthVersion"]}")
    debugImplementation("androidx.arch.core:core-testing:${rootProject.extra["androidxCoreTestingVersion"]}")
    debugImplementation("com.squareup.okhttp3:mockwebserver:${rootProject.extra["mockWebServerVersion"]}")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}

kapt {
    correctErrorTypes = true
}