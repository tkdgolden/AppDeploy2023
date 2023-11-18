package com.example.sketchyrecall.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sketchyrecall.R
import com.example.sketchyrecall.ui.theme.SketchyRecallTheme

@Composable
fun LandingScreen(
    onRulesButtonClicked: () -> Unit,
    onStartButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Button (
            onClick = {
                onStartButtonClicked()
            }
        ) {
            Text(
                text = stringResource(R.string.start),
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button (
            onClick = {
                onRulesButtonClicked()
            }
        ) {
            Text(
                text = stringResource(R.string.rules),
                fontSize = 24.sp)
        }
    }
}

@Preview
@Composable
fun LandingPreview() {
    SketchyRecallTheme {
        LandingScreen(
            onRulesButtonClicked = {},
            onStartButtonClicked = {}
        )
    }
}