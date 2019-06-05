# Add project specific ProGuard rules here.

-ignorewarnings

############################### Retain generic rules.################################
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*


############################### Rules for okio.################################
-dontwarn okio.**


############################### Rules for Batch SDK################################
#warning
-dontwarn com.batch.android.mediation.**
-dontwarn com.batch.android.BatchPushService
#class
-keep class com.batch.** {
  *;
}
-keep class com.google.android.gms.** {
  *;
}
#push notification
-keep public class * extends android.content.BroadcastReceiver


########################### Rules for Greenrobot[EventBus] Library########################
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


########################### Rules for calligraphy Library########################
-keep class uk.co.chrisjenx.calligraphy.* { *; }
-keep class uk.co.chrisjenx.calligraphy.*$* { *; }


########################### Rules for Butterknife########################
#warning
#-dontwarn butterknife.internal.**
#-dontwarn butterknife.Views$InjectViewProcessor
#
##class
# # Retain generated class which implement Unbinder.
#-keep public class * implements butterknife.Unbinder { public <init>(...); }
#    # Prevent obfuscation of types which use ButterKnife annotations since the simple name
#    # is used to reflectively look up the generated ViewBinding.
#-keep class butterknife.*
#-keep class butterknife.** { *; }
#-keepclasseswithmembernames class * { @butterknife.* <methods>; }
#-keepclasseswithmembernames class * { @butterknife.* <fields>; }
#-keep class **$$ViewInjector { *; }
#-keepnames class * { @butterknife.InjectView *;}
#-keep class **$$ViewInjector { *; }



########################### Rules for Fabric[crashlytics]########################
#warning
-dontwarn com.crashlytics.**
#class
-keep class com.crashlytics.** { *; }
-keep public class * extends java.lang.Exception



########################### Rules for OKHttp Library########################
#warning
-dontwarn com.squareup.okhttp.*
-dontwarn okhttp3.**
#class
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }



########################### Rules for Henson-Dart Library#########################
#warning
-dontwarn com.f2prateek.dart.internal.**
#class
-keep class **$$ExtraInjector { *; }
-keepclasseswithmembernames class * {
    @com.f2prateek.dart.* <fields>;
}
#for dart 2.0 only
-keep class **Henson { *; }
-keep class **$$IntentBuilder { *; }



########################### Rules for Mokito Library#########################
-dontwarn org.mockito.**



########################### Rules for Moshi Library#########################
#warning
-dontwarn com.squareup.moshi.**
#class
-keep class com.squareup.moshi.** { *; }
-keep interface com.squareup.moshi.** { *; }
-keepclassmembers class ** {
    @com.squareup.moshi.FromJson *;
    @com.squareup.moshi.ToJson *;
}

########################### Rules for Retrofit2 Library##########################
#warning
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform

#attributes
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations
-keepattributes EnclosingMethod

#class
-keep class retrofit.** { *; }
-keep class retrofit2.** { *; }

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class package.with.model.classes.** { *; }

-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}


########################### Rules for specific API##########################
#attributes
-keepattributes SourceFile,LineNumberTable

#class
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.xml.stream.** { *; }
-keep class com.google.appengine.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class sun.misc.Unsafe { *; }


-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *

-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}



############################### Generic rules for custom views in MVP/MVVM pattern###################
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
############################### Retain generic rules for Support Library if need.################################
# Hide warnings about references to newer platforms in the library
# Google Play Services library
#-dontwarn com.google.common.**
#-dontwarn android.support.v7.**
#-keep class android.support.v7.** { *; }
#-keep interface android.support.v7.** { *; }
#-keep class com.google.gson.** { *; }
#-keep public class com.google.gson.** {public private protected *;}