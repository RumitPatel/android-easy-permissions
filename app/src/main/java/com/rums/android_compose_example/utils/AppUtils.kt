package com.rums.android_compose_example.utils

import android.Manifest
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.rums.android_compose_example.R

val permissionReadMediaImage =
    if (isAndroid13OrGreater()) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE

fun isLowerThanAndroid10(): Boolean {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.R
}

fun isAndroid13OrGreater(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}