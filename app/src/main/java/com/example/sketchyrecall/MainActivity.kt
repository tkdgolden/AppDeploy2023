package com.example.sketchyrecall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sketchyrecall.ui.theme.SketchyRecallTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SketchyRecallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Landing()
                }
            }
        }
    }
}

@Composable
fun Landing(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button (
            onClick = {}
        ) {
            Text( text = stringResource(R.string.start), fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button (
            onClick = {
            }
        ) {
            Text(
                text = stringResource(R.string.rules),
                fontSize = 24.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingPreview() {
    SketchyRecallTheme {
        Landing()
    }
}

@Composable
fun Rules(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.rules_title),
            fontSize = 40.sp
        )
        Text(
            text = stringResource(R.string.rules_p)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RulesPreview() {
    SketchyRecallTheme {
        Rules()
    }
}

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