/*plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}*/

// Replace above with the code below:

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" // needed for Room library
}

android {
    buildFeatures {
        viewBinding = true
    }

    namespace = "com.example.cocktailranking"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cocktailranking"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("androidx.cardview:cardview:1.0.0")


    // Navigation components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines for background tasks
    implementation(libs.kotlinx.coroutines.android)

    // Gson for JSON parsing
    implementation(libs.gson)

    // Retrofit for API requests
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Lifecycles and ViewModels
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Coil image loading library
    implementation(libs.coil)

}