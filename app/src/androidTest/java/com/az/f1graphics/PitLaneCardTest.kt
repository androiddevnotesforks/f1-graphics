package com.az.f1graphics

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.az.f1graphics.ui.composables.PitLaneList
import com.az.f1graphics.ui.composables.PitLaneUIData
import com.az.f1graphics.ui.theme.F1GraphicsTheme
import org.junit.Rule
import org.junit.Test

class MyComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pitTimeIsDisplayedIfStopTimeIsNull() {
        val mockPitLaneUIData = listOf(
            PitLaneUIData(
                R.drawable.account_circle,
                Color.Blue.copy(alpha = 0.8f),
                14,
                "OCON",
                10.7f,
                null
            )
        )

        composeTestRule.setContent {
            F1GraphicsTheme {
                PitLaneList(modifier = Modifier.fillMaxWidth(), mockPitLaneUIData)
            }
        }

        composeTestRule.onNodeWithText("PIT").assertIsDisplayed()
        composeTestRule.onNodeWithText("STOP\nTIME").assertDoesNotExist()
        composeTestRule.onNodeWithText("${mockPitLaneUIData.first().stopTime}").assertDoesNotExist()

    }

    @Test
    fun pitTimeIsNotDisplayedIfStopTimeExists() {
        val mockPitLaneUIData = listOf(
            PitLaneUIData(
                R.drawable.account_circle,
                Color.Blue.copy(alpha = 0.8f),
                14,
                "OCON",
                10.7f,
                2.9f
            )
        )

        composeTestRule.setContent {
            F1GraphicsTheme {
                PitLaneList(modifier = Modifier.fillMaxWidth(), mockPitLaneUIData)
            }
        }

        composeTestRule.onNodeWithText("PIT").assertDoesNotExist()
        composeTestRule.onNodeWithText("STOP\nTIME").assertIsDisplayed()
        composeTestRule.onNodeWithText("${mockPitLaneUIData.first().stopTime}").assertIsDisplayed()

    }
}