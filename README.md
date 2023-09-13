<h1 align="center">EasyPermissions</h1>

[![Mavenn Central](https://img.shields.io/maven-central/v/io.github.rumitpatel/easy-permissions)](https://central.sonatype.com/artifact/io.github.rumitpatel/easy-permissions)
![Compose Version](https://img.shields.io/badge/Compose-1.5.3-brightgreen)
[![Github](https://img.shields.io/badge/Github-RumitPatel-blue?logo=github)](https://github.com/RumitPatel/android-easy-permissions)
<a href="https://android-arsenal.com/api?level=26"><img alt="API" src="https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat"/></a>
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>


🚧  Note: The project is almost completed and waiting for feedbacks and issues. You are most welcome to test it and generate issue or feedback🙂.


EasyPermissions is created to make the runtime permissions easier.

[👉 Setup Guide](#how-do-i-use-easypermissions) | [Report new issue](https://github.com/RumitPatel/android-easy-permissions/issues/new)



## Download
Mention dependency in ```build.gradle(app)```

For Groovy:
```groovy
implementation "io.github.rumitpatel:easy-permissions:1.0.5"
```

For Kotlin DSL:
```kotlin
implementation("io.github.rumitpatel:easy-permissions:1.0.5")
```

## Declear permissions in menifest

For example. If you want to request Camera, Coarse-location and Storage permissions, first, declare them in the AndroidManifest.xml.

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.permissionx.app">

    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
</manifest>
```

## How do I use EasyPermissions?
**Step:1** Create an object of EasyPermission.
```kotlin
private var easyPermissions: EasyPermission? = null
```

**Step:2** Initiate permission obj in ```obCreate()``` method. Also handle callback here.

```kotlin
private fun initializePermissionObj() {
    easyPermissions = EasyPermissions.Builder(this@PermissionDemoActivity)
        .setPermissionType(EasyPermissions.PermissionType.PERMISSION_CAMERA_AND_STORAGE)
        .setOnPermissionListener(object : OnPermissionsListener {
            override fun onGranted() {
                toast(getString(R.string.permission_granted))
            }

            override fun onDeclined(shouldRequestAgain: Boolean) {
                toast(getString(R.string.permission_declined))
                if (shouldRequestAgain) {
                    // You can request again by calling "easyPermissions?.launch()" here.
                } else {
                    //Never ask again selected, dismissed the dialog, or device policy prohibits the app from having that permission
                    // For example, Settings dialog opened here.
                    showSettingsDialog()
                }
            }
        })
        .build()
}
```

**Step:3** Simply launch the launcher.

```kotlin
buttonTest.setOnClickListener {
    easyPermissions?.launch()
}
```
**Note:** Please make sure the required permissions are added in ```Androidmanifest.xml``` too.


## PermissionType examples
1. ```PermissionType.PERMISSION_CAMERA_AND_STORAGE```: It will open the dialog with permissions required for capture and retrieve image.

2. ```PermissionType.PERMISSION_STORAGE```: It will open the dialog with permissions required for storage.

3. ```PermissionType.PERMISSION_LOCATION```: It will open the dialog with permissions required for ```ACCESS_COARSE_LOCATION``` and ```ACCESS_FINE_LOCATION```.

4. ```PermissionType.PERMISSION_SPECIFIC```: You can open the permission dialog for single permission too.


## Output:

![easy-permissions-demo-gif-2](https://github.com/RumitPatel/android-easy-permissions/assets/81635516/72a637b3-ebeb-4984-8b31-666a6f8b9548)

