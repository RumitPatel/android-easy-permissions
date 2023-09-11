package com.rumit.easypermissions.listeners

interface OnPermissionListener {
    fun onGranted()
    fun onDeclined()
    fun onDeclinedTemporary()
    fun onDeclinedPermanently()
}