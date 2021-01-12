
object App {
    const val applicationId = "br.com.phi.challenge"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val consumerRoles = "consumer-rules.pro"
}

object Versions {

    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0"
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.2"

    const val kotlin = "1.3.72"
    const val appCompat = "1.0.0"
    const val coreKtx = "1.3.1"
    const val recycleView = "1.0.0"
    const val cardView = "1.0.0"
    const val constraintLayout = "2.0.4"
    const val retrofit = "2.7.2"
    const val retrofitAdapter = "2.4.0"
    const val okhttp3 = "4.2.2"
    const val googleMaterial = "1.2.0"
    const val dataBinding = "3.1.4"
    const val lifecycle = "2.1.0"
    const val androidxLifecycle = "2.2.0"
    const val junit = "4.13.1"
    const val mockito = "1.9.3"
    const val archTesting = "2.1.0"
    const val kotlinxCoroutines = "1.4.2"
    const val testAndroidx = "1.3.0"
    const val testEtx = "1.1.2"
    const val mockitoCore = "3.3.3"
    const val mockitoInline = "2.28.2"
    const val liveDataTesting = "0.2.0"
    const val mockitoKotlin = "2.0.0-RC3"
    const val espresso = "3.3.0"
    const val picasso = "2.71828"
    const val koin = "2.0.1"
    const val coroutines = "1.3.9"
}

object Deps {

    // Kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    // AndroidX
    const val app_compat_androidx  = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val recycle_view = "androidx.recyclerview:recyclerview:${Versions.recycleView}"
    const val card_view = "androidx.cardview:cardview:${Versions.cardView}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycle = "android.arch.lifecycle:extensions:${Versions.lifecycle}"

    // Google
    const val google_material = "com.google.android.material:material:${Versions.googleMaterial}"

    // Data binding
    const val data_binding = "com.android.databinding:compiler:${Versions.dataBinding}" // kapt

    // Retrofit & Square
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofit_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitAdapter}"

    // Http3 and logging
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"

    // Koin
    const val koin_android = "org.koin:koin-android:${Versions.koin}"
    const val koin_scope =  "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koin_viewmodel =  "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    // Coroutines
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // ViewModel and Lifecycle
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidxLifecycle}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycle}"
    const val lifecycle_common = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidxLifecycle}"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}"

    // Tests
    const val junit = "junit:junit:${Versions.junit}"
    const val junit_test = "androidx.test.ext:junit:${Versions.testEtx}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    const val arch_testing = "android.arch.core:core-testing:${Versions.archTesting}"
    const val kotlix_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutines}"
    const val test_running = "androidx.test:runner:${Versions.testAndroidx}"
    const val test_rules = "androidx.test:rules:${Versions.testAndroidx}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val test_junit = "androidx.test.ext:junit:${Versions.testEtx}"
    const val liveDataTesting = "com.jraska.livedata:testing:${Versions.liveDataTesting}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"

    // Picasso
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}