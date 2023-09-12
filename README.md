# android-easy-permission
🚧  Note: The project is under beta version. You are most welcome to test it and generate issue or feedback🙂.

Easy permissions is created to make the runtime permissions process easier.

# Download
Put this like in ```build.gradle(app)```
```kotlin
implementation("io.github.rumitpatel:easy-permissions:1.0.3")
```

# How do I use EasyPermissions?
**Step:1** Create an object of EasyPermission.
```kotlin
private var easyPermissions: EasyPermission? = null
```

**Step:2** Initiate permission obj in ```obCreate()``` method. Also handle callback here.

```kotlin
private fun initializePermissionObj() {
        easyPermissions = EasyPermissions.Initializer(this@PermissionDemoActivity)
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


# PermissionType examples
1. ```PermissionType.PERMISSION_CAMERA_AND_STORAGE```: It will open the dialog with permissions required for capture and retrieve image.

2. ```PermissionType.PERMISSION_STORAGE```: It will open the dialog with permissions required for storage.

3. ```PermissionType.PERMISSION_LOCATION```: It will open the dialog with permissions required for ```ACCESS_COARSE_LOCATION``` and ```ACCESS_FINE_LOCATION```.

4. ```PermissionType.PERMISSION_SINGLE```: You can open the permission dialog for single permission too.
