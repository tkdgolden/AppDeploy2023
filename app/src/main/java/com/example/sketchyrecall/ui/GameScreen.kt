package com.example.sketchyrecall.ui

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import com.example.sketchyrecall.R
import com.example.sketchyrecall.ui.theme.SketchyRecallTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.util.DebugLogger
import java.util.logging.Logger


const val studyTime = 10
const val drawTime = 5

@Composable
fun GameStart(
    modifier: Modifier = Modifier
) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://api.dezgo.com/text2image")
        .header("X-Dezgo-Key", "DEZGO-E29CC2E400C0FE386D576F7F5BB453D7DDF406B5EB1064F99C9369BAD18832C7960EB909")
        .post(
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("prompt", "coloring book page colorful colored dog monster teeth")
                .build()
        )
        .build();

    println("WTF")
    val model = ImageLoader.Builder(LocalContext.current)
        .okHttpClient {
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    println("CALLING API??")
                    val chainedRequest = chain.request().newBuilder()
                        .url("https://api.dezgo.com/text2image")
                        .header(
                            "X-Dezgo-Key",
                            "DEZGO-E29CC2E400C0FE386D576F7F5BB453D7DDF406B5EB1064F99C9369BAD18832C7960EB909"
                        )
                        .post(
                            MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart(
                                    "prompt",
                                    "coloring book page colorful colored dog monster teeth"
                                )
                                .build()
                        )
                        .build();
                    chain.proceed(chainedRequest)
                }
  .build()
        }
        .memoryCachePolicy(CachePolicy.ENABLED)
        .logger(DebugLogger())
        .error(R.drawable.placeholder)
        .build()

        model.enqueue(ImageRequest.Builder(LocalContext.current)
            .data("https://api.dezgo.com/text2image")
            .target(
                onStart = { placeholder ->
                    // Handle the placeholder drawable.
                    println("STARTING TO LOAD")
                },
                onSuccess = { result ->
                    // Handle the successful result.
                    println("SUCCESS")
                },
                onError = { error ->
                    // Handle the error drawable.
                    println("ERROR $error")
                }
            )            .build())

        var painter = rememberAsyncImagePainter(
            model = model
        )

    println(painter.state)
    val state = painter.state
    if (state is AsyncImagePainter.State.Error) {
        println(state.result)
    }
    println(painter.request.toString())

        var imageState by remember { mutableStateOf(painter)}
//
//    client.newCall(request).enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            println("Got error $e")
//        }
//        // response currently prints the raw data of the image, so we need to convert it
//        override fun onResponse(call: Call, response: Response) {
//            println("Got response ${response.body?.byteStream()}")
//            val imageUri = response.body?.byteStream()
//            if (imageUri != null) {
//                // Set the image URI using rememberImagePainter
//                imageState = rememberAsyncImagePainter(imageUri)
//            }
//
//
//
//        }
//    })




    Log.d("TAG", "gamestart func")

    var phase by remember { mutableIntStateOf(1) }
    when (phase) {
        1 -> phase = study(imageState)
        2 -> phase = draw()
        3 -> phase = timesUp()
        4 -> phase = reveal()
    }
}

@Composable
fun study(imageState: Painter) : Int {
    Log.d("TAG", "study func")

    var timerText by remember { mutableStateOf("$studyTime seconds left")}
    var timeRemaining by remember { mutableIntStateOf( studyTime ) }
    timeRemaining = customTimer(studyTime)
    timerText = timerText(timeRemaining)

    return if (timeRemaining == 0) {
        2
    } else {
        StudyScreen(timerText, imageState)
        1
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
fun StudyScreen(timerText: String, imageState: Painter, modifier: Modifier = Modifier) {
    Log.d("TAG", "study SCREEN func")

    println(imageState)

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = stringResource(R.string.study_rules),
            modifier = Modifier.padding(20.dp)
        )
        AsyncImage(
            model = "https://us-central1-booksearch-325400.cloudfunctions.net/image",
            contentDescription = "game image from dezgo"
        )
//        Image(
//            // painter = imageState,
//            painter = remem "https://us-central1-booksearch-325400.cloudfunctions.net/image",
//            contentDescription = stringResource(R.string.image)
//        )
        Text(
            modifier = Modifier.padding(20.dp),
            text = timerText
        )
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
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = stringResource(R.string.draw_rules),
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = timerText,
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.weight(1f))
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
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = stringResource(R.string.times_up_rules),
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
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
        return 4
    }
    return 3
}

@Composable
fun reveal(
    modifier: Modifier = Modifier
) : Int {
    Log.d("TAG", "reveal func")

    var toGame by remember { mutableStateOf( false ) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = stringResource(R.string.reveal_rules),
            modifier = Modifier.padding(20.dp)
        )
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.image)
        )
        Button(
            modifier = Modifier.padding(20.dp),
            onClick = {
                toGame = true
            }
        ) {
            Text(
                text = stringResource(R.string.again),
            )
        }
    }

    if (toGame) {
        return 1
    }
    return 4
}

@Preview(showBackground = true)
@Composable
fun StudyPreview() {
    val model = ImageLoader.Builder(LocalContext.current)
        .okHttpClient {
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val chainedRequest = chain.request().newBuilder()
                        .url("https://api.dezgo.com/text2image")
                        .header(
                            "X-Dezgo-Key",
                            "DEZGO-E29CC2E400C0FE386D576F7F5BB453D7DDF406B5EB1064F99C9369BAD18832C7960EB909"
                        )
                        .post(
                            MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart(
                                    "prompt",
                                    "coloring book page colorful colored dog monster teeth"
                                )
                                .build()
                        )
                        .build();
                    chain.proceed(chainedRequest)
                }
                .build()
        }

    val painter = rememberAsyncImagePainter(
        model = model
    )

    SketchyRecallTheme {
        study(painter)
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
        reveal()
    }
}