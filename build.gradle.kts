// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.42" apply false
}

buildscript{
    extra.apply {
        set("gsonVersion","2.10")
        set("retrofitVersion","2.9.0")
        set("hiltVersion","2.42")
        set("okHttpVersion","4.9.3")
    }

}