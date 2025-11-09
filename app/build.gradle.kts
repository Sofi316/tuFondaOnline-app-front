plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.tufondaonline"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.tufondaonline"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

// Reemplaza tu bloque dependencies completo con este
dependencies {
    // ---- Catálogo de Versiones (libs) ----
    // Estas son las dependencias que ya tienes en tu archivo libs.versions.toml
    implementation(platform(libs.androidx.compose.bom)) // BOM gestiona las versiones de Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)

    // ---- Dependencias de Mapbox ----
    // Aplicamos la exclusión aquí. Asegúrate que estas versiones están en tu libs.versions.toml
    implementation(libs.mapbox.maps.compose.ndk27) {
        exclude(group = "xmlpull", module = "xmlpull")
    }
    implementation(libs.mapbox.maps.android.ndk27) {
        exclude(group = "xmlpull", module = "xmlpull")
    }

    // ---- Dependencias de Google Play Services ----
    // Asegúrate que estas versiones están en tu libs.versions.toml
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.kotlinx.coroutines.play.services)

    // ---- Dependencias de Test ----
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // ---- Dependencias de Debug ----
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
