package com.example.sketchyrecall.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
    ) {
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
        Button (
            onClick = {
                onRulesButtonClicked()
            }
        ) {
            Text(
                text = stringResource(R.string.rules),
                fontSize = 24.sp
            )
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