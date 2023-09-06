package com.rums.android_compose_example.ui.compose_screen

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rums.android_compose_example.R


@Composable
fun PermissionDemoScreen(
    onTestButtonPressed: () -> Unit = {},
    onTestSingleButtonPressed: () -> Unit = {}
) {
    Scaffold() { padding ->
        PermissionTestHomeScreen(
            modifier = Modifier.padding(padding),
            onTestButtonPressed = onTestButtonPressed,
            onTestSingleButtonPressed = onTestSingleButtonPressed
        )
    }
}


@Composable
fun PermissionTestHomeScreen(
    modifier: Modifier = Modifier,
    onTestButtonPressed: () -> Unit = {},
    onTestSingleButtonPressed: () -> Unit = {}
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
            Text(
                text = stringResource(R.string.camera_and_storage_permission).uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
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
                onTestSingleButtonPressed()
            }) {
            Text(
                text = stringResource(R.string.single_permission).uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}