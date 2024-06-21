plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-parcelize")
}

android {
    namespace = "com.android.blendit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.blendit"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://capstone-blendit.et.r.appspot.com/\"")
        buildConfigField("String", "UNSPLASH_BASE_URL", "\"https://api.unsplash.com/\"")
        buildConfigField("String", "UNSPLASH_CLIENT_ID", "\"40UKUXlN8TA1hhnprHta1XAzOMCPlB3snN8f4LQGSC8\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Camera
    implementation ("androidx.camera:camera-camera2:1.3.3")
    implementation ("androidx.camera:camera-lifecycle:1.3.3")
    implementation ("androidx.camera:camera-view:1.3.3")

    // Coroutines KTX
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Fragment KTX
    implementation (libs.androidx.fragment.ktx)

    // Image Network
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Livedata
    implementation(libs.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)
}