# Smart Campus Android Project

This project has been restructured to be compatible with Android Studio.

## How to Build APK

1.  **Open Android Studio**.
2.  Select **Open** and choose the `smartcampus` folder (the root folder containing `build.gradle.kts` and `settings.gradle.kts`).
3.  Wait for Gradle Sync to complete. Android Studio might ask to download the required Gradle version or Android SDK components. Allow it to do so.
4.  Once synced, go to **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5.  The APK will be generated in `app/build/outputs/apk/debug/app-debug.apk`.

## Project Structure

-   `app/src/main/java/com/selim/smartcampus/`: Contains your Kotlin source code.
-   `app/build.gradle.kts`: Module-level build configuration (dependencies).
-   `build.gradle.kts`: Project-level build configuration.
