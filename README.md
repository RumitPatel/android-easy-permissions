# android-easy-permission
🚧  Note: The project is under beta testing. 

Easy permissions is created to make the runtime permissions process easier.

# Download
Put this like in ```build.gradle(app)```
```kotlin
implementation("io.github.rumitpatel:easy-permissions:1.0.3")
```

# How do I use EasyPermissions?
**Step:1** Create an object of EasyPermission.
```kotlin
private var easyPermission: EasyPermission? = null
```

**Step:2** Initiate permission obj in obCreate() method. Also handle callback here.

```kotlin
private fun initializePermissionObj() {
        easyPermission = EasyPermissions.Initializer(this@PermissionDemoActivity)
            .setPermissionType(EasyPermissions.PermissionType.PERMISSION_CAMERA_AND_GALLERY)
            .setOnPermissionListener(object : OnPermissionListener {
                override fun onGranted() {
                    toast(getString(R.string.permission_granted))
                }

                override fun onDeclined() {
                    toast(getString(R.string.permission_declined))
                }

                override fun onDeclinedTemporary() {

                }

                override fun onDeclinedPermanently() {
                    // showSettingsDialog()
                }
            })
            .build()
    }
```

**Step:3** Simply launch the launcher.

```kotlin
buttonTest.setOnClickListener {
     easyPermission?.launch()
}
```
**Note:** Please make sure the required permissions are added in ```Androidmanifest.xml``` too.


# PermissionType
1. PermissionType.PERMISSION_CAMERA_AND_GALLERY: It will open the dialog with permissions required for capture.
2. PermissionType.PERMISSION_LOCATION: It will open the dialog with permissions required for corese and fine location.
3. PermissionType.PERMISSION_SINGLE: You can open the permission dialog for single permission too.
