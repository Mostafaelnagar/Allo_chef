# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

## okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.apache.commons.**
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

## Retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

## Bottom Navigation
-keep public class com.google.android.material.bottomnavigation.* { *; }

# To prevent obfusticating model classes
-keep class app.te.alo_chef.domain.videos_articles.entity.request.* { *; }
-keep class app.te.alo_chef.domain.auth.entity.model.* { *; }
-keep class app.te.alo_chef.domain.auth.entity.model.subscriptions.* { *; }
-keep class app.te.alo_chef.domain.auth.entity.model.payments.* { *; }
-keep class app.te.alo_chef.domain.auth.entity.model.payments.payment_result.* { *; }
-keep class app.te.alo_chef.domain.auth.entity.model.disounts.* { *; }
-keep class app.te.alo_chef.domain.auth.entity.request.* { *; }

## Localization Helper
-keep class com.zeugmasolutions.localehelper.* { *; }

## Keep proto data store
-keep class androidx.datastore.*.** {*;}
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite* {
   <fields>;
}
 -keepclassmembers class androidx.datastore.preferences.PreferencesProto$PreferenceMap {
     private androidx.datastore.preferences.protobuf.MapFieldLite preferences_;
 }

 -keepclassmembers class androidx.datastore.preferences.PreferencesProto$Value {
      private java.lang.Object value_;
      private int valueCase_;
 }

 # for removing android logging
 -assumenosideeffects class android.util.Log {
     public static boolean isLoggable(java.lang.String, int);
     public static int v(...);
     public static int i(...);
     public static int w(...);
     public static int d(...);
     public static int e(...);
 }
 -assumenosideeffects class
                   android.util.Log {
     public static int v(...);
     public static int i(...);
     public static int w(...);
     public static int d(...);
     public static int e(...);
 }
 -assumenosideeffects class kotlin.jvm.internal.Intrinsics {
     public static void checkNotNull(...);
     public static void checkExpressionValueIsNotNull(...);
         public static void checkNotNullExpressionValue(...);
         public static void checkParameterIsNotNull(...);
         public static void checkNotNullParameter(...);
         public static void checkReturnedValueIsNotNull(...);
         public static void checkFieldIsNotNull(...);
         public static void throwUninitializedPropertyAccessException(...);
         public static void throwNpe(...);
         public static void throwJavaNpe(...);
         public static void throwAssert(...);
         public static void throwIllegalArgument(...);
         public static void throwIllegalState(...);
 }
# for huawi services
     -ignorewarnings
     -keepattributes *Annotation*
     -keepattributes Exceptions
     -keepattributes InnerClasses
     -keepattributes Signature
     -keepattributes SourceFile,LineNumberTable
     -keep class com.huawei.hianalytics.**{*;}
     -keep class com.huawei.updatesdk.**{*;}
     -keep class com.huawei.hms.**{*;}