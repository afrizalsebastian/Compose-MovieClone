import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY") ?: throw GradleException("Missing TMDB_API_KEY config")
val tmdbBaseUrl = localProperties.getProperty("TMDB_BASE_URL") ?: throw GradleException("Missing TMDB_BASE_URL config")
val ytBaseUrl = localProperties.getProperty("YT_BASE_URL") ?: throw GradleException("Missing YT_BASE_URL config")
val ytApiKey = localProperties.getProperty("YT_API_KEY") ?: throw GradleException("Missing YT_API_KEY config")
val ytApiBaseUrl = localProperties.getProperty("YT_API_BASE_URL") ?: throw GradleException("Missing YT_API_BASE_URL config")

android {
    namespace = "com.github.afrizalsebastian.compose_movieclone"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.github.afrizalsebastian.compose_movieclone"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "TMDB_API_KEY",
            "\"$tmdbApiKey\""
        )

        buildConfigField(
            "String",
            "TMDB_BASE_URL",
            "\"$tmdbBaseUrl\""
        )
        
        buildConfigField(
            "String",
            "YT_BASE_URL",
            "\"$ytBaseUrl\""
        )

        buildConfigField(
            "String",
            "YT_API_KEY",
            "\"$ytApiKey\""
        )

        buildConfigField(
            "String",
            "YT_API_BASE_URL",
            "\"$ytApiBaseUrl\""
        )
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.com.squareup.retrofit)
    implementation(libs.com.squareup.retrofit.converter.gson)
    implementation(libs.io.coil.compose)
    implementation(libs.io.coil.network)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}