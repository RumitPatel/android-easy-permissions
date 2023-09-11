package com.rumit.easypermissions.utils

import android.content.Context
import android.widget.Toast

object EasyPermissionUtils {
    fun displayToastTest(context: Context, message:String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}