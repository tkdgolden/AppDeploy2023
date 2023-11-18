package com.example.sketchyrecall

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UITests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupSketchyRecallNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            SketchyRecallApp(navController = navController)
        }
    }

    @Test
    fun sketchyRecallNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(SketchyRecallScreen.Landing.name)
    }

    @Test
    fun sketchyRecallNavHost_verifyBackNavButtonNotShownOnLanding() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun sketchyRecallNavHost_landingClickRules_navigatesToRules() {
        composeTestRule.onNodeWithStringId(R.string.rules_button)
            .performClick()
        navController.assertCurrentRouteName(SketchyRecallScreen.Rules.name)
    }

    @Test
    fun sketchyRecallNavHost_landingClickStart_navigatesToGame() {
        composeTestRule.onNodeWithStringId(R.string.start)
            .performClick()
        navController.assertCurrentRouteName(SketchyRecallScreen.Game.name)
    }

    @Test
    fun sketchyRecallNavHost_rulesClickBack_navigatesToLanding() {
        navigateToRulesScreen()
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText)
            .performClick()
        navController.assertCurrentRouteName(SketchyRecallScreen.Landing.name)
    }

    @Test
    fun sketchyRecallNavHost_gameClickBack_navigatesToLanding() {
        navigateToGameScreen()
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText)
            .performClick()
        navController.assertCurrentRouteName(SketchyRecallScreen.Landing.name)
    }

    private fun navigateToRulesScreen() {
        composeTestRule.onNodeWithStringId(R.string.rules_button)
            .performClick()
    }

    private fun navigateToGameScreen() {
        composeTestRule.onNodeWithStringId(R.string.start)
            .performClick()
    }


}
