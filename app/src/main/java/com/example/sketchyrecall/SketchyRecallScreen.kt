package com.example.sketchyrecall

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sketchyrecall.ui.GameStart
import com.example.sketchyrecall.ui.LandingScreen
import com.example.sketchyrecall.ui.RulesScreen

enum class SketchyRecallScreen(@StringRes val title: Int) {
    Landing(title = R.string.app_name),
    Game(title = R.string.game),
    Rules(title = R.string.rules)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SketchyRecallAppBar(
    currentScreen: SketchyRecallScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SketchyRecallApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SketchyRecallScreen.valueOf(
        backStackEntry?.destination?.route ?: SketchyRecallScreen.Landing.name
    )
    Scaffold(
        topBar = {
            SketchyRecallAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigate(SketchyRecallScreen.Landing.name) }
            )
        }
    ) { innerPadding -> Modifier.padding(innerPadding)
        NavHost(
            navController = navController,
            startDestination = SketchyRecallScreen.Landing.name
        ) {
//          Routes FROM Landing
            composable(route = SketchyRecallScreen.Landing.name) {
                LandingScreen(
                    onRulesButtonClicked = {
                        navController.navigate(SketchyRecallScreen.Rules.name)
                    },
                    onStartButtonClicked = {
                        navController.navigate(SketchyRecallScreen.Game.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
//          Routes FROM Rules
            composable(route = SketchyRecallScreen.Rules.name) {
                RulesScreen(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
//          Routes FROM Game
            composable(route = SketchyRecallScreen.Game.name) {
                GameStart(
                    modifier = Modifier
                        .fillMaxSize()

                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewSketchyRecallApp() {
    SketchyRecallApp()
}