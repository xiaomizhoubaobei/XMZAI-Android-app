# ============================================================
# 通用配置
# ============================================================

# 保留行号信息，便于调试崩溃日志
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# 保留注解
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# ============================================================
# Android 标准配置
# ============================================================

# 保留 native 方法
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留自定义 View
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留 Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# 保留 Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 保留 R 文件
-keep class **.R$* {
    *;
}

# 保留枚举
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ============================================================
# Retrofit
# ============================================================

-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes Signature
-keepattributes Exceptions

# 保留 Retrofit 的 Service 接口
-keep,allowobfuscation interface retrofit2.Call
-keep,allowobfuscation interface * extends retrofit2.Call

# 保留 Retrofit 返回类型的泛型签名
-keep,allowobfuscation interface retrofit2.Callback
-keep,allowobfuscation interface * extends retrofit2.Callback

# 保留 Retrofit 服务接口方法
-keep,allowobfuscation @retrofit2.http.* interface * {
    *;
}

# Retrofit + RxJava
-dontwarn retrofit2.adapter.rxjava2.*
-keep,allowobfuscation class retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory { *; }

# ============================================================
# OkHttp
# ============================================================

-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# 保留 OkHttp 拦截器
-keep,allowobfuscation class okhttp3.Interceptor { *; }
-keep,allowobfuscation class okhttp3.Response { *; }

# 保留 OkHttp 的 WebSocket 相关
-dontwarn okhttp3.internal.**
-dontwarn okio.internal.**

# ============================================================
# Gson
# ============================================================

# 保留 Gson 类型适配器
-keepattributes Signature
-keep attributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.TypeAdapter { *; }
-keep class * implements com.google.gson.TypeAdapterFactory { *; }
-keep class * implements com.google.gson.JsonSerializer { *; }
-keep class * implements com.google.gson.JsonDeserializer { *; }

# 保留所有使用 @SerializedName 注解的字段
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# 保留所有使用 Gson 的数据模型类（根据需要调整包名）
-keep class xmzai.mizhoubaobei.top.network.model.** { *; }
-keep class xmzai.mizhoubaobei.top.data.model.** { *; }
-keep class xmzai.mizhoubaobei.top.bean.** { *; }
-keep class xmzai.mizhoubaobei.top.entity.** { *; }
-keep class xmzai.mizhoubaobei.top.model.** { *; }

# ============================================================
# FastJSON
# ============================================================

-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keep class com.alibaba.fastjson.serializer.** { *; }
-keep class com.alibaba.fastjson.parser.** { *; }

# 保留 FastJSON 序列化的实体类（仅保留 @JSONField 注解字段）
-keepclassmembers class * {
    @com.alibaba.fastjson.annotation.JSONField <fields>;
}

# ============================================================
# EventBus
# ============================================================

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# 保留使用 EventBus 的类（EventBus 会通过反射访问）
-keep class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

# ============================================================
# MMKV
# ============================================================

-keep class com.tencent.mmkv.** { *; }
-keep class com.tencent.mmkv.MMKV { *; }
-keep class com.tencent.mmkv.MMKVContentProvider { *; }

# ============================================================
# kotlinx-serialization
# ============================================================

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class xmzai.mizhoubaobei.top.**$$serializer { *; }
-keepclassmembers class xmzai.mizhoubaobei.top.** {
    *** Companion;
}
-keepclasseswithmembers class xmzai.mizhoubaobei.top.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# ============================================================
# jsbridge (WebView JS 桥接)
# ============================================================

-keep class com.github.lzyzsd.jsbridge.** { *; }
-keepclassmembers class * {
    @com.github.lzyzsd.jsbridge.BridgeMethod <methods>;
}

# 保留项目中所有 JS 接口类
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# ============================================================
# Room
# ============================================================

-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Room Entity、DAO keep 规则（Room 使用注解处理器生成实现）
-keep @androidx.room.Entity class *
-dontwarn androidx.room.Entity

-keep @androidx.room.Dao interface *
-dontwarn androidx.room.Dao

# 保留 Room 的 TypeConverter
-keepclassmembers,allowobfuscation class * {
    @androidx.room.TypeConverter <methods>;
}

# 保留 Room 数据库相关类的字段
-keepclassmembers class * extends androidx.room.RoomDatabase {
    *** Dao;
}

# ============================================================
# Glide
# ============================================================

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule { <init>(...); }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** { **[] $VALUES; public *; }
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder { *** rewind(); }

# ============================================================
# Lottie
# ============================================================

-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** { *; }

# ============================================================
# DataStore
# ============================================================

-dontwarn androidx.datastore.**
-keep class androidx.datastore.** { *; }

# ============================================================
# RxJava / RxAndroid
# ============================================================

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.*LinkedQueue* {
    rx.internal.util.atomic.LinkedQueueNode* producerNode;
    rx.internal.util.atomic.LinkedQueueNode* consumerNode;
}
