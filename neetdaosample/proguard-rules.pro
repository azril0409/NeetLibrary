# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Deo\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
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

##----------------------------------
-dontwarn library.neetoffice.com.**
-keep class library.neetoffice.com.** { *; }
-keep interface library.neetoffice.com.** { *; }
-keep @library.neetoffice.com.neetannotation.NotProguard class * {*;}
-keep @library.neetoffice.com.neetannotation.Handler interface * {*;}
-keep class *{
    @library.neetoffice.com.neetannotation.* <fields>;
}
-keepclassmembers  class *{
    @library.neetoffice.com.neetannotation.* <methods>;
    public  org.springframework.web.client.RestTemplate getRestTemplate();
    public  void getRestTemplate(org.springframework.web.client.RestTemplate);
    public  void setRootUrl(java.lang.String);
}
##----------------------------------
-keepclassmembers class * {
    @library.neetoffice.com.neetdao.DatabaseField <fields>;
    @library.neetoffice.com.neetdao.Id <fields>;
}