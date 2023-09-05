package com.rums.android_compose_example.z_test_permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

const val REQUEST_CODE_CAMERA_AND_RECORD_AUDIO_PERMISSION = 1024
const val REQUEST_CODE_LOCATION_PREMISSION = 1027
const val REQUEST_CODE_NOTIFICATION = 1030

object GrantPermission {


    private val permissionReadMediaImage =
        if (isAndroid13OrGreater()) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE

    val mPermissionArrayCameraGallery = if (isLowerThanAndroid10()) arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        permissionReadMediaImage
    ) else arrayOf(
        Manifest.permission.CAMERA, permissionReadMediaImage
    )

    val mPermissionArrayLocation = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    )

    var mPermissionArrayCameraAudio = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    fun hasCameraAndStoragePermissions(context: Context): Boolean {
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
            ActivityCompat.requestPermissions(
                context as Activity,
                listPermissionsNeeded.toArray(arrayOf(listPermissionsNeeded.size.toString())),
                REQUEST_CODE_CAMERA_AND_RECORD_AUDIO_PERMISSION
            )
            return false
        }
        return true
    }

    fun isAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun shouldShowAnyRequestPermissionRationale(
        activity: Activity, permissions: Array<String>
    ): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true
            }
        }
        return false
    }

    fun hasLocationPermissions(context: Context): Boolean {

        val permissionsNeeded: ArrayList<String> = ArrayList()
        for (permission: String in mPermissionArrayLocation) {
            if (ContextCompat.checkSelfPermission(
                    context, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(permission)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                permissionsNeeded.toTypedArray(),
                REQUEST_CODE_LOCATION_PREMISSION
            )
            return false
        }
        return true
    }

    fun hasCameraAndAudioPermissions(context: Context?): Boolean {
        val permissionCamera =
            ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA)
        val permissionRecordAudio =
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        val listPermissionsNeeded: MutableList<String> = java.util.ArrayList()
        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO)
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_CODE_CAMERA_AND_RECORD_AUDIO_PERMISSION
            )
            return false
        }
        return true
    }

    fun hasNotificationPermissions(context: Context): Boolean {
        if (isAndroid13OrGreater()) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PermissionChecker.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE_NOTIFICATION
                )
                return false
            }
        }
        return true
    }
}

private fun isLowerThanAndroid10(): Boolean {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.R
}

private fun isAndroid13OrGreater(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

private fun checkTestPermission() {

}

