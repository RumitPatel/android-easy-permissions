package com.rums.android_compose_example.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rums.android_compose_example.listeners.OnPermissionListener

class EasyPermission(
    private val activity: AppCompatActivity? = null,
    private val model: String? = null,
    private val year: Int = 0,
    private val mOnPermissionListener: OnPermissionListener? = null,
    private val requestMultiplePermissions: ActivityResultLauncher<Array<String>>? = null
) {

    private constructor(initializer: Initializer) : this(
        initializer.activity,
        initializer.model,
        initializer.year,
        initializer.onPermissionListener,
        initializer.requestMultiplePermissions
    )

    class Initializer(activity: AppCompatActivity?) {
        var activity: AppCompatActivity? = activity
            private set

        var requestMultiplePermissions: ActivityResultLauncher<Array<String>>? = null
            private set

        var onPermissionListener: OnPermissionListener? = null
            private set

        var model: String? = null
            private set

        var year: Int = 0
            private set


        fun setModel(model: String) = apply { this.model = model }

        fun setYear(year: Int) = apply { this.year = year }

        fun setOnPermissionListener(onPermissionListener: OnPermissionListener?) = apply {
            this.onPermissionListener = onPermissionListener

            this.requestMultiplePermissions = activity?.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                var isAnyDeclined = false
                permissions.entries.forEach {
                    Log.d("DEBUG", "${it.key} = ${it.value}")
                    if (!it.value) {
                        isAnyDeclined = true
                    }
                }
                if (isAnyDeclined) {
                    onPermissionListener?.onDeclined()
                } else {
                    onPermissionListener?.onGranted()
                }
            }
        }

        fun build() = EasyPermission(this@Initializer)
    }

    fun launch() {
        hasCameraAndStoragePermissions(activity as Context)
    }

    private fun hasCameraAndStoragePermissions(context: Context): Boolean {
        val permissionCamera: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val permissionWriteExternal: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionReadExternal: Int =
            ContextCompat.checkSelfPermission(context, permissionReadMediaImage)

        val listPermissionsNeeded = ArrayList<String>()
        if (isLowerThanAndroid10()) {
            if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (permissionReadExternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(permissionReadMediaImage)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            requestMultiplePermissions?.launch(
                listPermissionsNeeded.toArray(arrayOf(listPermissionsNeeded.size.toString()))
            )
            return false
        }
        mOnPermissionListener?.onGranted()
        return true
    }
}


