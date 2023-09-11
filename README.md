# android-easy-permission
🚧  Note: The project is under beta testing. 

Easy permissions is created to make the runtime permissions process easier.

**Step:1** Put this like in ```build.gradle(app)```
```
implementation("io.github.rumitpatel:easy-permissions:1.0.3")
```


**Step:2** Create an object of EasyPermission.

```
private var easyPermission: EasyPermission? = null
```

**Step:3** Initiate permission obj in obCreate() method.

```
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

**Step:4** Simply launch the launcher.

``` easyPermission?.launch() ```
