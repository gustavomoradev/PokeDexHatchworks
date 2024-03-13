import com.android.build.gradle.tasks.detectAnnotationProcessors
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.moradev.pokedexhatchworks"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moradev.pokedexhatchworks"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.moradev.pokedexhatchworks.CustomTestRunner"

//        buildConfigField("String", "API_KEY","\"${System.getenv("api.key")}\"")
//        buildConfigField("String", "API_HOST","\"${System.getenv("api.host")}\"")

        val properties = Properties()
        properties.load(project.rootProject.file("project.properties").inputStream())
        buildConfigField("String", "API_KEY","\"${properties.getProperty("api.key")}\"")
        buildConfigField("String", "API_HOST","\"${properties.getProperty("api.host")}\"")
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
        dataBinding = true
        viewBinding = true
    }
    android.buildFeatures.buildConfig = true





}

dependencies {

    //ui
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

    implementation("com.airbnb.android:lottie:6.4.0")
    implementation("androidx.palette:palette-ktx:1.0.0")
//    implementation("io.coil-kt:coil:2.6.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    //di
    implementation("com.google.dagger:hilt-android:2.48")

    //firebase
    implementation("com.google.firebase:firebase-crashlytics:18.6.2")
    implementation("com.google.firebase:firebase-analytics:21.5.1")
    implementation("androidx.test.ext:junit-ktx:1.1.5")


    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    kapt(    "com.google.dagger:hilt-android-compiler:2.48")

    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.51")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.51")
    kaptAndroidTest( "com.google.dagger:hilt-android-compiler:2.51")



    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    //retrofit connections
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")

    //lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    //Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")

    testImplementation("io.mockk:mockk:1.13.10")
//    testImplementation("io.mockk.mockk:1.12.2")

//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0' //Ya estaba, la hemos actualizado.
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    testImplementation("androidx.fragment:fragment-ktx:1.6.2")
    debugImplementation("androidx.fragment:fragment-testing:1.6.2")


}