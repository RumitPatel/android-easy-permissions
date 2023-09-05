package com.rums.android_compose_example.z_test_permission

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.rums.android_compose_example.ui.theme.AndroidComposeExampleTheme
import com.rums.android_compose_example.z_test_permission.ui.TestPermissionApp

class PermissionDemoActivity : ComponentActivity() {
    private var easyPermission: EasyPermission? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.background
                ) {
                    TestPermissionApp(
                        onTestButtonPressed = {
                            testPermission()
                        }
                    )
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
                    Toast.makeText(this@PermissionDemoActivity, "Granted", Toast.LENGTH_SHORT).show()
                }

                override fun onDeclined() {
                    Toast.makeText(this@PermissionDemoActivity, "Declined", Toast.LENGTH_SHORT).show()
                }
            })
            .build()
    }

    private fun testPermission() {
        easyPermission?.launch()

    }
}


