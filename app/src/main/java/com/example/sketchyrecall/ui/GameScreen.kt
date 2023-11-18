package com.example.sketchyrecall.ui

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sketchyrecall.R
import com.example.sketchyrecall.ui.theme.SketchyRecallTheme

const val studyTime = 3
const val drawTime = 5

@Composable
fun GameStart(
    onTimesUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var phase by remember { mutableIntStateOf(1) }
    when (phase) {
        1 -> phase = study()
        2 -> phase = draw()
        3 -> onTimesUp()
    }
}

@Composable
fun study() : Int {
    var timerText by remember { mutableStateOf("$studyTime seconds left")}
    var timeRemaining by remember { mutableIntStateOf( studyTime ) }
    timeRemaining = customTimer(studyTime)
    timerText = timerText(timeRemaining)

    return if (timeRemaining == 0) {
        2
    } else {
        StudyScreen(timerText)
        1
    }
}

@Composable
fun draw() : Int {
    var timerText by remember { mutableStateOf("$drawTime seconds left")}
    var timeRemaining by remember { mutableIntStateOf( drawTime ) }
    timeRemaining = customTimer(drawTime)
    timerText = timerText(timeRemaining)

    return if (timeRemaining == 0) {
        3
    } else {
        DrawScreen(timerText)
        2
    }
}

@Composable
fun customTimer(totalTime: Int) : Int {
    Log.d("TAG", "in custom timer")
    var timeRemaining by remember { mutableStateOf(totalTime) }
    val timer = object : CountDownTimer(((totalTime * 1000).toLong()), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            timeRemaining = millisUntilFinished.toInt() / 1000
        }
        override fun onFinish() {
        }
    }
    if (timeRemaining == totalTime) {
        timer.start()
    }
    return timeRemaining
}

@Composable
fun timerText(timeRemaining: Int) : String {
    return "$timeRemaining seconds left"
}

@Composable
fun StudyScreen(timerText: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.image)
        )
        Text(
            text = timerText
        )
    }
}

@Composable
fun DrawScreen(timerText: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = timerText
        )
    }
}

@Composable
fun TimesUp(
    onRevealButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var toReveal by remember { mutableStateOf( false ) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = {
                toReveal = true
            }
        ) {
            Text(
                text = stringResource(R.string.reveal)
            )
        }
    }

    if (toReveal) {
        onRevealButtonClicked()
    }
}

@Composable
fun Reveal(
    onPlayAgainButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var toGame by remember { mutableStateOf( false ) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.image)
        )
        Button(
            onClick = {
                toGame = true
            }
        ) {
            Text(
                text = stringResource(R.string.again)
            )
        }
    }

    if (toGame) {
        onPlayAgainButtonClicked()
    }
}

@Preview(showBackground = true)
@Composable
fun StudyPreview() {
    SketchyRecallTheme {
        study()
    }
}

@Preview(showBackground = true)
@Composable
fun DrawPreview() {
    SketchyRecallTheme {
        draw()
    }
}

@Preview(showBackground = true)
@Composable
fun TimesUpPreview() {
    SketchyRecallTheme {
//        TimesUp()
    }
}

@Preview(showBackground = true)
@Composable
fun RevealUpPreview() {
    SketchyRecallTheme {
//        Reveal()
    }
}