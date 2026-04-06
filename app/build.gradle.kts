plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("kotlin-parcelize")
}

// 辅助函数：获取签名版本配置（环境变量优先）
fun getSigningVersion(envVar: String, defaultValue: Boolean): Boolean {
    val envValue = System.getenv(envVar)
    if (envValue != null) {
        return envValue.toBoolean()
    }
    val propValue = project.findProperty(envVar) as String?
    return propValue?.toBoolean() ?: defaultValue
}

android {
    namespace = "xmzai.mizhoubaobei.top"
    compileSdk = 35

    defaultConfig {
        applicationId = "xmzai.mizhoubaobei.top"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    android {
    signingConfigs {
        create("release") {
            // 优先级：环境变量 > gradle.properties
            // 这样支持 CI/CD 自动化构建和本地开发两种场景
            
            val envStoreFile = System.getenv("KEYSTORE_FILE")
            val envStorePassword = System.getenv("KEYSTORE_PASSWORD")
            val envKeyAlias = System.getenv("KEY_ALIAS")
            val envKeyPassword = System.getenv("KEY_PASSWORD")
            
            val propStoreFile = project.findProperty("RELEASE_STORE_FILE") as String?
            val propStorePassword = project.findProperty("RELEASE_STORE_PASSWORD") as String?
            val propKeyAlias = project.findProperty("RELEASE_KEY_ALIAS") as String?
            val propKeyPassword = project.findProperty("RELEASE_KEY_PASSWORD") as String?
            
            // 确定最终使用的配置值
            val finalStoreFile = envStoreFile ?: propStoreFile
            val finalStorePassword = envStorePassword ?: propStorePassword
            val finalKeyAlias = envKeyAlias ?: propKeyAlias
            val finalKeyPassword = envKeyPassword ?: propKeyPassword
            
            // 验证必要参数
            if (finalStoreFile == null) {
                error("签名配置错误：未找到密钥库文件路径。请设置环境变量 KEYSTORE_FILE 或在 gradle.properties 中设置 RELEASE_STORE_FILE")
            }
            if (finalStorePassword == null) {
                error("签名配置错误：未找到密钥库密码。请设置环境变量 KEYSTORE_PASSWORD 或在 gradle.properties 中设置 RELEASE_STORE_PASSWORD")
            }
            if (finalKeyAlias == null) {
                error("签名配置错误：未找到密钥别名。请设置环境变量 KEY_ALIAS 或在 gradle.properties 中设置 RELEASE_KEY_ALIAS")
            }
            if (finalKeyPassword == null) {
                error("签名配置错误：未找到密钥密码。请设置环境变量 KEY_PASSWORD 或在 gradle.properties 中设置 RELEASE_KEY_PASSWORD")
            }
            
            // 应用配置
            storeFile = file(finalStoreFile)
            storePassword = finalStorePassword
            keyAlias = finalKeyAlias
            keyPassword = finalKeyPassword
            
            // 签名版本控制（支持环境变量和 gradle.properties）
            enableV1Signing = getSigningVersion("ENABLE_V1_SIGNING", true)
            enableV2Signing = getSigningVersion("ENABLE_V2_SIGNING", true)
            enableV3Signing = getSigningVersion("ENABLE_V3_SIGNING", true)
            enableV4Signing = getSigningVersion("ENABLE_V4_SIGNING", true)
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Release 构建强制使用签名配置
            signingConfig = signingConfigs.getByName("release")
        }
        
        debug {
            // Debug 构建使用默认 debug 签名
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4")
    //popup window
    implementation("com.github.zyyoona7:EasyPopup:1.1.2")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.3")
    // Retrofit 的 Moshi 转换器
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    //okhttp
    implementation("com.squareup.okhttp3:okhttp:3.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    //rxjava
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.3")
    implementation("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")
    //dataStore
    implementation("androidx.datastore:datastore-preferences:1.1.4")

    // Lifecycle 依赖
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    //kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0")
    //room
    implementation("androidx.room:room-runtime:2.4.3")
    kapt("androidx.room:room-compiler:2.4.0")
    implementation("com.google.code.gson:gson:2.8.0")
    implementation("androidx.room:room-ktx:2.4.3")
    //recycleView item侧滑栏
    implementation("com.github.mcxtzhang:SwipeDelMenuLayout:V1.2.1")

    //动态获取权限
    implementation("pub.devrel:easypermissions:3.0.0")

    //webView数据桥接
    implementation("com.github.lzyzsd:jsbridge:1.0.4")
    // Markwon核心库
    implementation("io.noties.markwon:core:4.6.2")
    // 常用扩展插件
    implementation("io.noties.markwon:html:4.5.0")
    implementation("io.noties.markwon:ext-strikethrough:4.6.0")
    implementation("io.noties.markwon:ext-tasklist:4.6.0")
    implementation("io.noties.markwon:ext-tables:4.6.0")
    implementation("io.noties.markwon:linkify:4.6.2")
    // LaTeX 数学公式渲染插件
    implementation("io.noties.markwon:ext-latex:4.6.2")
    // 图片加载（使用Glide）
    implementation("io.noties.markwon:image-glide:4.6.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("jp.wasabeef:glide-transformations:4.3.0")

    // PhotoView 图片缩放库
    implementation("com.github.chrisbanes:PhotoView:2.3.0")

    //Material库
    implementation("com.google.android.material:material:1.9.0")

    // Kotlin序列化
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    implementation("com.airbnb.android:lottie:4.2.1")

    implementation("com.tencent:mmkv-static:1.3.4")

    //eventbus
    implementation("org.greenrobot:eventbus:3.3.1")

    implementation("com.alibaba:fastjson:1.2.46")

    //地区选择器
    implementation("com.github.joielechong:countrycodepicker:2.4.2")
    //判断手机运营商的库
    implementation("io.michaelrocks:libphonenumber-android:8.13.35")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ndk")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-messaging-directboot")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.android.gms:play-services-analytics:18.1.0")
    implementation("com.google.android.gms:play-services-gcm:17.0.0")

    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //switchButton
    implementation("com.kyleduo.switchbutton:library:2.1.0")

    implementation("com.github.lzyzsd:jsbridge:1.0.4")

    implementation("com.google.android.flexbox:flexbox:3.0.0")
}
 