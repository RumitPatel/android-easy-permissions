package com.rums.android_compose_example.z_test_permission.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rums.android_compose_example.R
import com.rums.android_compose_example.ui.common_views.MyText


@Composable
fun TestPermissionApp(
    onTestButtonPressed: () -> Unit = {}
) {
    Scaffold() { padding ->
        PermissionTestHomeScreen(
            modifier = Modifier.padding(padding),
            onTestButtonPressed = onTestButtonPressed
        )
    }
}


@Composable
fun PermissionTestHomeScreen(
    modifier: Modifier = Modifier,
    onTestButtonPressed: () -> Unit = {}
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(52.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(R.color.colorPrimary)
            ),
            onClick = {
                onTestButtonPressed()
            }) {
            MyText(
                text = stringResource(R.string.test).uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}