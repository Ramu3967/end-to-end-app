plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 34

        testInstrumentationRunner = "com.example.data.HiltTestRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")

    implementation(project(":domain"))

    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt("com.google.dagger:hilt-compiler:${rootProject.extra["hiltVersion"]}")

    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofitVersion"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofitVersion"]}")

    implementation("com.google.code.gson:gson:${rootProject.extra["gsonVersion"]}")
    implementation("com.squareup.okhttp3:okhttp:${rootProject.extra["okHttpVersion"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okHttpVersion"]}")

    implementation("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["roomVersion"]}")
    kapt("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")


    // android test deps
    androidTestImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hiltVersion"]}")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")

    androidTestImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hiltVersion"]}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")

    testImplementation ("org.mockito:mockito-core:${rootProject.extra["mockitoVersion"]}")
    // Dependencies for both test and AndroidTest
    debugImplementation("com.google.truth:truth:${rootProject.extra["truthVersion"]}")
    debugImplementation("androidx.arch.core:core-testing:${rootProject.extra["androidxCoreTestingVersion"]}")

    debugImplementation("com.squareup.okhttp3:mockwebserver:${rootProject.extra["mockWebServerVersion"]}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

kapt {
    correctErrorTypes = true
}
