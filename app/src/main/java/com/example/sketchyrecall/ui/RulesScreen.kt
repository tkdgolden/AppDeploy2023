package com.example.sketchyrecall.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.sketchyrecall.R
import com.example.sketchyrecall.ui.theme.SketchyRecallTheme

@Composable
fun RulesScreen(
    modifier: Modifier = Modifier
) {
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
        RulesScreen()
    }
}