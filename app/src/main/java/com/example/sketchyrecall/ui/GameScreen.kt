package com.example.sketchyrecall.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sketchyrecall.R
import com.example.sketchyrecall.ui.theme.SketchyRecallTheme

@Composable
fun Study(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.image)
        )
        Text(
            text = stringResource(R.string.placeholder_timer)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudyPreview() {
    SketchyRecallTheme {
        Study()
    }
}

@Composable
fun Draw(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.placeholder_timer)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrawPreview() {
    SketchyRecallTheme {
        Draw()
    }
}

@Composable
fun TimesUp(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.reveal)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimesUpPreview() {
    SketchyRecallTheme {
        TimesUp()
    }
}

@Composable
fun Reveal(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.image)
        )
        Button(
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.again)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RevealUpPreview() {
    SketchyRecallTheme {
        Reveal()
    }
}