package com.rums.android_compose_example.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.rums.android_compose_example.R

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

val fonts = FontFamily(
    Font(R.font.jost_bold, weight = FontWeight.Bold),
    Font(R.font.jost_medium, weight = FontWeight.Medium),
    Font(R.font.jost_regular, weight = FontWeight.Normal),
    Font(R.font.jost_semibold, weight = FontWeight.SemiBold)
)

val jost_bold = FontFamily(Font(R.font.jost_bold, weight = FontWeight.Bold))
val jost_medium = FontFamily(Font(R.font.jost_medium, weight = FontWeight.Medium))
val jost_regular = FontFamily(Font(R.font.jost_regular, weight = FontWeight.Normal))
val jost_semibold = FontFamily(Font(R.font.jost_semibold, weight = FontWeight.SemiBold))