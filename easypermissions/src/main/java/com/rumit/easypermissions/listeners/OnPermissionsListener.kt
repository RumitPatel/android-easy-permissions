package com.rumit.easypermissions.listeners

interface OnPermissionsListener {
    fun onGranted()
    fun onDeclined()
    fun onDeclinedTemporary()
    fun onDeclinedPermanently()
}