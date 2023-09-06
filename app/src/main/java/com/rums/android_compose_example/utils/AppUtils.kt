package com.rums.android_compose_example.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rums.android_compose_example.R


fun isLowerThanAndroid10(): Boolean {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.R
}

fun isAndroid13OrGreater(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.openSettingPage() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}

fun Context.showSettingsDialog() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(getString(R.string.permission_alert))
    builder.setMessage(getString(R.string.goto_setting_and_permission))
    builder.setPositiveButton(getString(R.string.str_settings)) { dialog: DialogInterface, _: Int ->
        dialog.dismiss()
        openSettingPage()
    }
    builder.setNegativeButton(getString(R.string.str_cancel)) { dialog: DialogInterface, _: Int ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.setCancelable(false)
    if (!dialog.isShowing) {
        dialog.show()
    }
}