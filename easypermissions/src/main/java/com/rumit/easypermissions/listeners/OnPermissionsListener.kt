package com.rumit.easypermissions.listeners

interface OnPermissionsListener {
    fun onGranted()
    fun onDeclined(shouldRequestAgain: Boolean)
}