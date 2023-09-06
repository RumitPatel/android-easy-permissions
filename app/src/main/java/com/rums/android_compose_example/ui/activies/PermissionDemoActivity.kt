package com.rums.android_compose_example.ui.activies

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.rums.android_compose_example.R
import com.rums.android_compose_example.listeners.OnPermissionListener
import com.rums.android_compose_example.ui.compose_screen.PermissionDemoScreen
import com.rums.android_compose_example.ui.theme.AndroidComposeExampleTheme
import com.rums.android_compose_example.utils.EasyPermission
import com.rums.android_compose_example.utils.toast

class PermissionDemoActivity : AppCompatActivity() {
    private lateinit var mContext: Context
    private var easyPermission: EasyPermission? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContent {
            AndroidComposeExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.background
                ) {
                    PermissionDemoScreen(onTestButtonPressed = { testPermission() })
                }
            }
        }

        initPermitObj()
    }

    private fun initPermitObj() {
        easyPermission = EasyPermission.Initializer(this@PermissionDemoActivity)
            .setYear(2)
            .setModel("2015")
            .setOnPermissionListener(object : OnPermissionListener {
                override fun onGranted() {
                    toast(getString(R.string.permission_granted))
                }

                override fun onDeclined() {
                    toast(getString(R.string.permission_declined))
                }
            })
            .build()
    }

    private fun testPermission() {
        easyPermission?.launch()

    }
}


