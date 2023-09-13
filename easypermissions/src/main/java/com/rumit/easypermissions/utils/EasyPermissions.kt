package com.rumit.easypermissions.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rumit.easypermissions.listeners.OnPermissionsListener

class EasyPermissions(
    private val activity: AppCompatActivity? = null,
    private val permissionType: PermissionType? = null,
    private val specificPermissions: Array<String?>? = null,
    private val mOnPermissionsListener: OnPermissionsListener? = null,
    private val requestMultiplePermissions: ActivityResultLauncher<Array<String>>? = null
) {

    enum class PermissionType {
        PERMISSION_CAMERA_AND_STORAGE,
        STORAGE,
        PERMISSION_NOTIFICATION,
        PERMISSION_LOCATION,
        PERMISSION_SPECIFIC
    }


    private val permissionReadMediaImage =
        if (isAndroid13OrGreater()) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE

    private constructor(builder: Builder) : this(
        builder.activity,
        builder.permissionType,
        builder.specificPermissions,
        builder.onPermissionsListener,
        builder.permissionResultLauncher
    )

    class Builder(activity: AppCompatActivity?) {
        var activity: AppCompatActivity? = activity
            private set

        var permissionResultLauncher: ActivityResultLauncher<Array<String>>? = null
            private set

        var onPermissionsListener: OnPermissionsListener? = null
            private set

        var permissionType: PermissionType? = null
            private set

        var specificPermissions: Array<String?>? = null
            private set


        fun setPermissionType(permissionType: PermissionType?) =
            apply { this.permissionType = permissionType }

        fun setSpecificPermissions(specificPermissions: Array<String?>?) =
            apply { this.specificPermissions = specificPermissions }

        fun setOnPermissionListener(onPermissionsListener: OnPermissionsListener?) = apply {
            this.onPermissionsListener = onPermissionsListener
            try {
                this.permissionResultLauncher = activity?.registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    var isAnyPermissionDeclinedPermanently = false
                    var isAnyPermissionDeclinedTemporary = false
                    permissions.entries.forEach {
                        val permission = it.key
                        val permitted = it.value
                        Log.d("DEBUG", "$permission = $permitted")
                        if (!it.value) {
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
                        onPermissionsListener?.onDeclined(true)
                    } else if (isAnyPermissionDeclinedPermanently) {
                        onPermissionsListener?.onDeclined(false)
                    } else {
                        onPermissionsListener?.onGranted()
                    }
                }
            } catch (e: Exception) {
                if (e is IllegalStateException) {
                    activity?.toast(initialization_required_text)
                    return@apply
                }
            }
        }

        fun build() = EasyPermissions(this@Builder)
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

            PermissionType.PERMISSION_SPECIFIC -> {
                checkAndRequestSpecificPermissions(activity as Context)
            }

            else -> {

            }
        }
    }

    private fun checkAndRequestSpecificPermissions(context: Context): Boolean {
        if (specificPermissions.isNullOrEmpty()) {
            return false
        }

        val listPermissionsNeeded = ArrayList<String>()
        specificPermissions.forEach {
            val specificPermission = it
            if (specificPermission != null && !TextUtils.isEmpty(specificPermission)) {
                val permissionPostNotification: Int =
                    ContextCompat.checkSelfPermission(context, specificPermission)
                if (permissionPostNotification != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(specificPermission)
                }
            }
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            requestMultiplePermissions?.launch(
                listPermissionsNeeded.toArray(arrayOf(listPermissionsNeeded.size.toString()))
            )
            return false
        }
        mOnPermissionsListener?.onGranted()
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
        mOnPermissionsListener?.onGranted()
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
        mOnPermissionsListener?.onGranted()
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
        mOnPermissionsListener?.onGranted()
        return true
    }

    private fun checkAndRequestLocationPermissions(context: Context): Boolean {
        val permissionFineLocation: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionCoarseLocation: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)


        val listPermissionsNeeded = ArrayList<String>()
        if (isLowerThanAndroid10()) {
            if (permissionCoarseLocation != PackageManager.PERMISSION_GRANTED) {
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
        mOnPermissionsListener?.onGranted()
        return true
    }
}