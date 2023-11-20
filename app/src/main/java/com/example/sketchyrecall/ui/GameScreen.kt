package com.example.sketchyrecall.ui


import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.compose.SketchyRecallTheme
import com.example.compose.md_theme_light_onPrimaryContainer
import com.example.sketchyrecall.R
import com.example.sketchyrecall.helpers.generatePrompt
import java.util.Random


const val studyTime = 10
const val drawTime = 5

@Composable
fun GameStart(
    onPlayAgainButtonClicked : () -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center
) {
    var phase by remember { mutableStateOf( 1 ) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxSize(),
        contentAlignment = alignment
    ) {
        GetImage()
        if ((phase == 2) or (phase == 3)) {
            Box(
               modifier = modifier
                   .background(md_theme_light_onPrimaryContainer)
            ) {}
        }
    }
        Log.d("TAG", "gamestart func")
        when (phase) {
            1 -> phase = study()
            2 -> phase = draw()
            3 -> phase = timesUp()
            4 -> phase = reveal(onPlayAgainButtonClicked)
        }
    }
//}

@Composable
fun study() : Int {
    Log.d("TAG", "study func")
    var timerText by remember { mutableStateOf("$studyTime seconds left")}
    var timeRemaining by remember { mutableIntStateOf( studyTime ) }
    timeRemaining = customTimer(studyTime)
    timerText = timerText(timeRemaining)

    if (timeRemaining == 0) {
        return 2
    } else {
        StudyScreen(timerText)
        return 1
    }
}

@Composable
fun draw() : Int {
    Log.d("TAG", "draw func")
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
fun GetImage() {
    val prompt = generatePrompt()
    println("THIS IS PROMPT $prompt")
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://us-central1-booksearch-325400.cloudfunctions.net/image?prompt=${prompt}")
            .memoryCachePolicy(
                CachePolicy.DISABLED)
            .build(),
        placeholder = painterResource(R.drawable.loading),
        contentDescription = "Game image from dezgo",
        onError = { err -> println("Got Error $err") },
        modifier = Modifier.height(500.dp).width(500.dp)
    )
}

@Composable
fun StudyScreen(timerText: String, modifier: Modifier = Modifier) {
    Log.d("TAG", "study SCREEN func")

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = stringResource(R.string.study_rules),
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.height(400.dp))
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.timer),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(20.dp)
                    .size(80.dp)
            )
            Text(
                    modifier = Modifier.padding(20.dp),
                    text = timerText
            )
        }
    }
}

@Composable
fun DrawScreen(timerText: String, modifier: Modifier = Modifier) {
    Log.d("TAG", "draw SCREEN func")
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = stringResource(R.string.draw_rules),
            modifier = Modifier.padding(20.dp)
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.timer),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(20.dp)
                    .size(200.dp)
            )
            Text(
                text = timerText,
                fontSize = 40.sp
            )
        }
    }
}

@Composable
fun timesUp(
    modifier: Modifier = Modifier
) : Int {
    Log.d("TAG", "times up func")

    var toReveal by remember { mutableStateOf( false ) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = stringResource(R.string.times_up_rules),
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                println("CLICK")
                toReveal = true
            }
        ) {
            Text(
                text = stringResource(R.string.reveal)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
    if (toReveal) {
        println("4")
        return 4
    } else {
        println("3")
        return 3
    }
}

@Composable
fun reveal(
    onPlayAgainButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) : Int {
    Log.d("TAG", "reveal func")
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = stringResource(R.string.reveal_rules),
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.height(400.dp))
        Button(
            modifier = Modifier.padding(20.dp),
            onClick = {
                onPlayAgainButtonClicked()
            }
        ) {
            Text(
                text = stringResource(R.string.again),
            )
        }
    }
    return 4
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
        timesUp()
    }
}

@Preview(showBackground = true)
@Composable
fun RevealUpPreview() {
    SketchyRecallTheme {
//        reveal()
    }
}