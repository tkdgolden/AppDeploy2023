package com.example.sketchyrecall

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

const val pretendAPIWait : Long = 2000
const val clickStartWait : Long = 2500
const val studyWait : Long = 3000
const val drawWait : Long = 6000
const val timesUpWait : Long = 9000
const val timesUpTotalWait : Long = 12000

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
        Thread.sleep(pretendAPIWait)
        composeTestRule.onNodeWithStringId(R.string.start)
            .performClick()
        navController.assertCurrentRouteName(SketchyRecallScreen.Game.name)
    }

    @Test
    fun sketchyRecallNavHost_earlyLandingClickStart_doesNOTNavigateToGame() {
        composeTestRule.onNodeWithStringId(R.string.start).assertDoesNotExist()
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
        Thread.sleep(clickStartWait)
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText)
            .performClick()
        navController.assertCurrentRouteName(SketchyRecallScreen.Landing.name)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun timer_studyTimeStarted_showsStudy() {
        navigateToGameScreen()
        Thread.sleep(clickStartWait)
        val studyText = composeTestRule.activity.getString(R.string.study_rules)
        composeTestRule.waitUntilExactlyOneExists(hasText(studyText), studyWait)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun timer_studyTimeOut_showsDraw() {
        navigateToGameScreen()
        Thread.sleep(2500)
        navController.assertCurrentRouteName(SketchyRecallScreen.Game.name)
        val drawText = composeTestRule.activity.getString(R.string.draw_rules)
        composeTestRule.waitUntilExactlyOneExists(hasText(drawText), drawWait)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun timer_drawTimeOut_showsTimesUp() {
        navigateToGameScreen()
        Thread.sleep(timesUpWait)
        navController.assertCurrentRouteName(SketchyRecallScreen.Game.name)
        val timesUpText = composeTestRule.activity.getString(R.string.times_up_rules)
        composeTestRule.waitUntilExactlyOneExists(hasText(timesUpText), timesUpWait)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun navigateGame_clickReveal_showsReveal() {
        navigateToTimesUp()
        Thread.sleep(timesUpWait)
        val revealButtonText = composeTestRule.activity.getString(R.string.reveal)
        composeTestRule.onNodeWithText(revealButtonText)
            .performClick()
        val revealRulesText = composeTestRule.activity.getString(R.string.reveal_rules)
        composeTestRule.onNodeWithText(revealRulesText).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun navigateGame_clickAgain_showsStudy() {
        navigateToReveal()
        Thread.sleep(timesUpTotalWait + pretendAPIWait)
        val againButtonText = composeTestRule.activity.getString(R.string.again)
        composeTestRule.onNodeWithText(againButtonText)
            .performClick()
        val studyText = composeTestRule.activity.getString(R.string.study_rules)
        composeTestRule.onNodeWithText(studyText).assertExists()
    }

    @Test
    fun navigateGame_earlyClickAgain_doesNOTNavigateToStudy() {
        navigateToReveal()
        val revealRulesText = composeTestRule.activity.getString(R.string.reveal_rules)
        composeTestRule.onNodeWithText(revealRulesText).assertExists()
        val againButtonText = composeTestRule.activity.getString(R.string.again)
        composeTestRule.onNodeWithText(againButtonText).assertDoesNotExist()
    }

    private fun navigateToRulesScreen() {
        composeTestRule.onNodeWithStringId(R.string.rules_button)
            .performClick()
        navController.assertCurrentRouteName(SketchyRecallScreen.Rules.name)
    }

    private fun navigateToGameScreen() {
        Thread.sleep(pretendAPIWait)
        composeTestRule.onNodeWithText("Start")
            .performClick()
        navController.assertCurrentRouteName(SketchyRecallScreen.Game.name)
    }

    @OptIn(ExperimentalTestApi::class)
    private fun navigateToTimesUp() {
        navigateToGameScreen()
        Thread.sleep(pretendAPIWait)
        navController.assertCurrentRouteName(SketchyRecallScreen.Game.name)
        val timesUpText = composeTestRule.activity.getString(R.string.times_up_rules)
        composeTestRule.waitUntilExactlyOneExists(hasText(timesUpText), timesUpWait)
    }

    private fun navigateToReveal() {
        navigateToTimesUp()
        Thread.sleep(timesUpWait)
        val revealButtonText = composeTestRule.activity.getString(R.string.reveal)
        composeTestRule.onNodeWithText(revealButtonText)
            .performClick()
        val revealRulesText = composeTestRule.activity.getString(R.string.reveal_rules)
        composeTestRule.onNodeWithText(revealRulesText).assertExists()
    }
}
