# -----------------------------
# Kotlin Metadata (always keep)
# -----------------------------
-keep class kotlin.Metadata { *; }

# -----------------------------
# AndroidX Navigation
# -----------------------------
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# -----------------------------
# Retrofit + Gson
# -----------------------------
# Gson reflection needs model fields retained
-keep class com.example.seekhoanimeapp.data.dto.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Retrofit / OkHttp support
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**

# -----------------------------
# Room Database
# -----------------------------
# Keep entities/DAOs


# -----------------------------
# Glide (Image loading)
# -----------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**

# -----------------------------
# Media3 / ExoPlayer
# -----------------------------
-keep class androidx.media3.** { *; }
-keep class com.google.android.exoplayer2.** { *; }
-dontwarn androidx.media3.**
-dontwarn com.google.android.exoplayer2.**

# -----------------------------
# Android Framework
# -----------------------------
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.os.Bundle);
}

-keepclassmembers class * extends androidx.fragment.app.Fragment {
    public void *(android.os.Bundle);
}

# Keep Parcelable (Room / Bundles)
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
