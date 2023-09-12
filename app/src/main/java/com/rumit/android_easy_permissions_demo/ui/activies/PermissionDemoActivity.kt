package com.rumit.android_easy_permissions_demo.ui.activies

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.rumit.android_easy_permissions_demo.R
import com.rumit.android_easy_permissions_demo.ui.compose_screen.PermissionDemoScreen
import com.rumit.android_easy_permissions_demo.ui.theme.AndroidComposeExampleTheme
import com.rumit.easypermissions.listeners.OnPermissionListener
import com.rumit.easypermissions.utils.EasyPermissions
import com.rumit.easypermissions.utils.showSettingsDialog
import com.rumit.easypermissions.utils.toast

class PermissionDemoActivity : AppCompatActivity() {
    private lateinit var mContext: Context
    private var easyPermissions: EasyPermissions? = null
    private var easyPermissionForSinglePermission: EasyPermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContent {
            AndroidComposeExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.background
                ) {
                    PermissionDemoScreen(
                        onTestButtonPressed = { easyPermissions?.launch() },
                        onTestSingleButtonPressed = { easyPermissionForSinglePermission?.launch() }
                    )
                }
            }
        }

        initializePermissionObj()
    }

    private fun initializePermissionObj() {
        easyPermissions = EasyPermissions.Initializer(this@PermissionDemoActivity)
            .setPermissionType(EasyPermissions.PermissionType.PERMISSION_CAMERA_AND_STORAGE)
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
                    showSettingsDialog()
                }
            })
            .build()

        easyPermissionForSinglePermission = EasyPermissions.Initializer(this@PermissionDemoActivity)
            .setPermissionType(EasyPermissions.PermissionType.PERMISSION_SINGLE)
            .setSinglePermission(Manifest.permission.ACCESS_COARSE_LOCATION)
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
                    showSettingsDialog()
                }
            })
            .build()
    }
}