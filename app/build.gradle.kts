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
    packaging {
        resources {
            // Excluir los archivos LICENSE.md duplicados que causan el conflicto
            excludes += "META-INF/LICENSE.md"

            // A veces también es necesario excluir estos otros archivos comunes:
            excludes += "META-INF/LICENSE-apache-2.0.txt"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

}

// Reemplaza tu bloque dependencies completo con este
dependencies {
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.3")

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.camera:camera-camera2:1.5.0")
    implementation("androidx.camera:camera-lifecycle:1.5.0")
    implementation("androidx.camera:camera-view:1.5.0")
    // Para la vista previa en compose (o usa AndroidView)
    implementation("androidx.camera:camera-compose:1.0.0-alpha02")
    // Para manejar los permisos fácilmente
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")
    // Dependencia para cargar la imagen después de ser capturada
    implementation("io.coil-kt:coil-compose:2.1.0")
    // ---- Catálogo de Versiones (libs) ----
    // Estas son las dependencias que ya tienes en tu archivo libs.versions.toml
    implementation(platform(libs.androidx.compose.bom)) // BOM gestiona las versiones de Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
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
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.runtime)

    // ---- Dependencias de Test ----
    // Pruebas Unitarias
    // MockK
    testImplementation("io.mockk:mockk:1.13.8")
    // Si también haces pruebas instrumentadas (androidTest), añade esta línea también:
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
    // --- Instrumented Tests (androidTest - UI Tests) ---

    // Dependencias base de AndroidX Test (Versiones actualizadas y compatibles)
    // Usa las versiones del libs.versions.toml si existen, si no, usa cableado:
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Usa una version consistente
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Usa una version consistente

    // Dependencias de Compose UI Test (usando el BOM para compatibilidad)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")
    // ---- Dependencias de Debug ----
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.compose.material:material-icons-extended")
}
