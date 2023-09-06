package com.rums.android_compose_example.listeners

interface OnPermissionListener {
    fun onGranted()
    fun onDeclined()
    fun onDeclinedTemporary()
    fun onDeclinedPermanently()
}