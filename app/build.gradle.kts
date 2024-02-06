import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.greenkim"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.greenkim"
        minSdk = 33
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        dataBinding = true
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
repositories {

}
dependencies {
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material:material-icons-core:1.6.0")
    implementation("com.github.prolificinteractive:material-calendarview:2.0.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.2.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.0.0")
    // optional - RxJava3 support
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.0.0")
    implementation("com.kizitonwose.calendar:view:2.4.1")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
    implementation("com.google.dagger:hilt-android:2.44")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    kapt("com.google.dagger:hilt-compiler:2.44")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.compose.ui:ui-viewbinding:1.3.0")
    implementation("com.google.android.material:material:1.11.0-alpha01")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}