package com.example.sketchyrecall.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.SketchyRecallTheme
import com.example.sketchyrecall.R
import java.util.Timer
import kotlin.concurrent.timerTask

const val pretendAPIWait: Long = 2000

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
        var ready by remember { mutableStateOf(false) }
        Timer().schedule(timerTask {
            ready = true
        }, pretendAPIWait)
        Text(
            text = stringResource(R.string.welcome),
            modifier = Modifier.padding(32.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            lineHeight = 50.sp,
            fontSize = 40.sp
        )
        if (ready) {
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
        } else {
            Icon(
                painter = painterResource(R.drawable.loading),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(20.dp)
                    .size(40.dp)
            )
            Text(
                text = stringResource(R.string.waiting),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        Button (
            onClick = {
                onRulesButtonClicked()
            }
        ) {
            Text(
                text = stringResource(R.string.rules_button),
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