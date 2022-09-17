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

object Versions {
    const val ACTIVITY = "1.5.1"
    const val CORE = "1.9.0"
    const val KOTLIN = "1.7.0"
    const val HILT = "2.43.2"
    const val LOGGING_INTERCEPTOR = "4.9.3"
    const val RETROFIT = "2.9.0"
    const val PAGING = "3.1.1"
    const val LIFECYCLE = "2.5.1"
    const val PICASSO = "2.71828"
    const val FRAGMENT = "1.5.2"
    const val ROOM = "2.4.3"
}

object Sdk {
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 33
}

object HiltLibs {
    const val ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val CORE = "com.google.dagger:hilt-core:${Versions.HILT}"
    const val COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    const val ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
}

object AndroidXLibs {
    const val APP_COMPACT= "androidx.appcompat:appcompat:${Versions.ACTIVITY}"
    const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${Versions.PAGING}"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
}

object SquareUpLibs {
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val PICASSO = "com.squareup.picasso:picasso:${Versions.PICASSO}"
}

object AndroidXTestLibs {
    const val JUNIT = "androidx.test.ext:junit:1.1.3"
}

object TestLibs {
    const val JUNIT = "junit:junit:4.13.2"
}

object AndroidLibs {
    const val MATERIAL = "com.google.android.material:material:1.6.1"
}