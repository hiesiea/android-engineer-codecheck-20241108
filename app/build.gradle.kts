plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.navigation.safeargs.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "jp.co.yumemi.android.code_check"
    compileSdk = 35

    defaultConfig {
        applicationId = "jp.co.yumemi.android.codecheck"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.browser:browser:1.8.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    implementation(libs.kotlinx.coroutines.android)

    implementation("io.ktor:ktor-client-android:2.3.10")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.10")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.10")
    implementation("io.ktor:ktor-client-logging:2.3.10")

    implementation("org.slf4j:slf4j-android:1.7.36")

    implementation("io.coil-kt:coil-compose:2.7.0")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")

    testImplementation("junit:junit:4.13.2")

    testImplementation("org.robolectric:robolectric:4.13")

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)

    testImplementation("io.ktor:ktor-client-mock:2.3.10")

    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")

    testImplementation("app.cash.turbine:turbine:1.2.0")

    testImplementation(libs.compose.ui.test.junit4)

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

tasks.withType<AbstractTestTask> {
    // Disable unit tests for release build type (Robolectric limitations)
    if (name == "testReleaseUnitTest") {
        enabled = false
    }
}
