package com.rumit.android_easy_permission.listeners

interface OnPermissionListener {
    fun onGranted()
    fun onDeclined()
    fun onDeclinedTemporary()
    fun onDeclinedPermanently()
}