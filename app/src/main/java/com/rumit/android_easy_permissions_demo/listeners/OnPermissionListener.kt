package com.rumit.android_easy_permissions_demo.listeners

interface OnPermissionListener {
    fun onGranted()
    fun onDeclined()
    fun onDeclinedTemporary()
    fun onDeclinedPermanently()
}