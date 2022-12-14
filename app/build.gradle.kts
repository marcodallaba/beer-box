/*
 * Copyright 2022 Marco Dalla Ba'
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = Sdk.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AppConfiguration.APPLICATION_ID
        minSdk = Sdk.MIN_SDK_VERSION
        targetSdk = Sdk.TARGET_SDK_VERSION
        versionCode = AppConfiguration.VERSION_CODE
        versionName = AppConfiguration.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
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
        viewBinding = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(AndroidXLibs.CORE)
    implementation(AndroidXLibs.APP_COMPACT)
    implementation(AndroidXLibs.FRAGMENT)
    implementation(AndroidLibs.MATERIAL)
    implementation(AndroidXLibs.PAGING_RUNTIME)
    implementation(AndroidXLibs.LIFECYCLE_VIEWMODEL)
    implementation(AndroidXLibs.ROOM_RUNTIME)
    implementation(AndroidXLibs.ROOM_KTX)
    implementation(AndroidXLibs.ROOM_PAGING)
    kapt(AndroidXLibs.ROOM_COMPILER)

    testImplementation(TestLibs.JUNIT)
    androidTestImplementation(AndroidXTestLibs.JUNIT)

    implementation(HiltLibs.ANDROID)
    kapt(HiltLibs.ANDROID_COMPILER)

    implementation(SquareUpLibs.LOGGING_INTERCEPTOR)
    implementation(SquareUpLibs.RETROFIT)
    implementation(SquareUpLibs.CONVERTER_GSON)
    implementation(SquareUpLibs.PICASSO)

}