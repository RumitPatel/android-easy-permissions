package com.rumit.easypermissions.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rumit.easypermissions.listeners.OnPermissionListener

class EasyPermissions(
    private val activity: AppCompatActivity? = null,
    private val permissionType: PermissionType? = null,
    private val singlePermission: String? = null,
    private val mOnPermissionListener: OnPermissionListener? = null,
    private val requestMultiplePermissions: ActivityResultLauncher<Array<String>>? = null
) {

    enum class PermissionType {
        PERMISSION_CAMERA_AND_STORAGE,
        STORAGE,
        PERMISSION_NOTIFICATION,
        PERMISSION_LOCATION,
        PERMISSION_SINGLE
    }


    private val permissionReadMediaImage =
        if (isAndroid13OrGreater()) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE

    private constructor(initializer: Initializer) : this(
        initializer.activity,
        initializer.permissionType,
        initializer.singlePermission,
        initializer.onPermissionListener,
        initializer.permissionResultLauncher
    )

    class Initializer(activity: AppCompatActivity?) {
        var activity: AppCompatActivity? = activity
            private set

        var permissionResultLauncher: ActivityResultLauncher<Array<String>>? = null
            private set

        var onPermissionListener: OnPermissionListener? = null
            private set

        var permissionType: PermissionType? = null
            private set

        var singlePermission: String? = null
            private set


        fun setPermissionType(permissionType: PermissionType?) =
            apply { this.permissionType = permissionType }

        fun setSinglePermission(singlePermission: String?) =
            apply { this.singlePermission = singlePermission }

        fun setOnPermissionListener(onPermissionListener: OnPermissionListener?) = apply {
            this.onPermissionListener = onPermissionListener

            this.permissionResultLauncher = activity?.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                var isAnyPermissionDeclined = false
                var isAnyPermissionDeclinedPermanently = false
                var isAnyPermissionDeclinedTemporary = false
                permissions.entries.forEach {
                    val permission = it.key
                    val permitted = it.value
                    Log.d("DEBUG", "$permission = $permitted")
                    if (!it.value) {
                        isAnyPermissionDeclined = true
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                activity!!,
                                permission
                            )
                        ) {
                            //Show permission explanation dialog...
                            isAnyPermissionDeclinedTemporary = true
                        } else {
                            //Never ask again selected, or device policy prohibits the app from having that permission.
                            //So, disable that feature, or fall back to another situation...
                            isAnyPermissionDeclinedPermanently = true
                        }
                    }
                }
                if (isAnyPermissionDeclinedTemporary) {
                    onPermissionListener?.onDeclinedTemporary()
                } else if (isAnyPermissionDeclinedPermanently) {
                    onPermissionListener?.onDeclinedPermanently()
                } else if (isAnyPermissionDeclined) {
                    onPermissionListener?.onDeclined()
                } else {
                    onPermissionListener?.onGranted()
                }
            }
        }

        fun build() = EasyPermissions(this@Initializer)
    }

    fun launch() {
        when (permissionType) {
            PermissionType.PERMISSION_CAMERA_AND_STORAGE -> {
                checkAndRequestCameraAndStoragePermissions(activity as Context)
            }

            PermissionType.STORAGE -> {
                checkAndRequestStoragePermissions(activity as Context)
            }

            PermissionType.PERMISSION_NOTIFICATION -> {
                checkAndRequestNotificationPermissions(activity as Context)
            }

            PermissionType.PERMISSION_LOCATION -> {
                checkAndRequestLocationPermissions(activity as Context)
            }

            PermissionType.PERMISSION_SINGLE -> {
                checkAndRequestSinglePermissions(activity as Context)
            }

            else -> {

            }
        }
    }

    private fun checkAndRequestSinglePermissions(context: Context): Boolean {
        if (singlePermission.isNullOrEmpty()) {
            return false
        }

        val permissionPostNotification: Int =
            ContextCompat.checkSelfPermission(context, singlePermission)

        val listPermissionsNeeded = ArrayList<String>()

        if (permissionPostNotification != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(singlePermission)
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

    private fun checkAndRequestCameraAndStoragePermissions(context: Context): Boolean {
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

    private fun checkAndRequestStoragePermissions(context: Context): Boolean {
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

    private fun checkAndRequestNotificationPermissions(context: Context): Boolean {
        if (!isAndroid13OrGreater()) {
            return false
        }

        val permissionPostNotification: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)

        val listPermissionsNeeded = ArrayList<String>()

        if (permissionPostNotification != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.POST_NOTIFICATIONS)
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

    private fun checkAndRequestLocationPermissions(context: Context): Boolean {
        val permissionFineLocation: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionCorseLocation: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)


        val listPermissionsNeeded = ArrayList<String>()
        if (isLowerThanAndroid10()) {
            if (permissionCorseLocation != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
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