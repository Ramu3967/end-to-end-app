plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.data"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.data"
        minSdk = 26
        targetSdk = 34
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

    // modules
    implementation(project(":domain"))

    // DI
    implementation ("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt ("com.google.dagger:hilt-compiler:${rootProject.extra["hiltVersion"]}")


    // network
    implementation ("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofitVersion"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofitVersion"]}")

    implementation ("com.google.code.gson:gson:${rootProject.extra["gsonVersion"]}")
    implementation ("com.squareup.okhttp3:okhttp:${rootProject.extra["gsonVersion"]}")
    implementation ("com.squareup.okhttp3:logging-interceptor:${rootProject.extra["okHttpVersion"]}")

    // cache/room
    implementation ("androidx.room:room-runtime:${rootProject.extra["roomVersion"]}")
    implementation ("androidx.room:room-ktx:${rootProject.extra["roomVersion"]}")
    kapt ("androidx.room:room-compiler:${rootProject.extra["roomVersion"]}")



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

kapt {
    correctErrorTypes = true
}