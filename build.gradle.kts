// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
}

buildscript{
    extra.apply {
        set("gsonVersion","2.10")
        set("retrofitVersion","2.9.0")
        set("hiltVersion","2.46.1")
        set("okHttpVersion","4.9.3")
        set("robolectric","4.8")
        set("mockWebServerVersion","4.9.3")
        set("mockitoVersion","3.9.0")
        set("truthVersion","1.1.2")
        set("kotlinVersion","1.7.1")
    }

}