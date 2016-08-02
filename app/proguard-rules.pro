# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yuyuhang/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn retrofit.**
-dontwarn retrofit2.**
-dontwarn android.webkit.WebView


-keep class retrofit.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontskipnonpubliclibraryclasses
-dontpreverify           # 混淆时是否做预校验
-allowaccessmodification
-verbose                # 混淆时是否记录日志
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#第三方库
-keep public class com.centerm.iso8583.**{*;}
-keep class com.alibaba.fastjson.**{*;}
-keep public class org.apache.commons.codec1.** {*;}
-keep public class org.apache.http.entity.mime.** {*;}
-keep class org.apache.log4j.** {*;}
-keep public class com.nineoldandroids.**{*;}
-keep public class com.android.volley.**{*;}
-keep class de.mindpipe.android.logging.log4j.**{*;}
-keep public class com.squareup.picasso.**{*;}

-keep public class * extends android.support.**{*;}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * {
    public <methods>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class com.lakala.cloudpos.autoactive.data.** {
    *;
 }

#保持R文件不被混淆
-keep class **.R$* {
    *;
}

#-keepnames class * implements java.io.Serializable
-keep public class * implements java.io.Serializable {
        public *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#ignore warning
-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**
-dontwarn butterknife.internal.**
-dontwarn cn.bmob.**


-keep class fm.jiechao.jcvideoplayer_lib.** { *; }
-keep class com.jude.swipbackhelper.** { *; }
-keep class com.cjj.** { *; }
-keep class com.flyco.** { *; }
-keep class com.yuyh.sprintnba.http.bean.** { *; }
-keep class com.yuyh.sprintnba.ui.** { *; }

-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keep class cn.bmob.** { *; }
-keep class com.readystatesoftware.** { *; }
#-keep butterknife.internal.** { *; }
#-keep butterknife.internal.** { *; }
-keep class com.alibaba.fastjson.** { *; }
-keep class com.facebook.** { *; }
-keep class org.greenrobot.eventbus.** { *; }
-keep class okhttp3.** { *; }
-keep class rx.** { *; }
#-keep com.alibaba.fastjson.** { *; }



