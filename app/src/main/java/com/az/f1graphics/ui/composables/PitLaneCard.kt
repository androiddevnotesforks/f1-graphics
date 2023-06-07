package com.az.f1graphics.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.az.f1graphics.R
import com.az.f1graphics.ui.fonts.f1Bold
import com.az.f1graphics.ui.fonts.f1Regular
import com.az.f1graphics.ui.theme.MercedesTeal
import com.az.f1graphics.ui.theme.RedBullBlue
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random


data class PitLaneUIData(
    val teamLogo: Int,
    val teamColor: Color,
    val driverPosition: Int,
    val driverName: String,
    val pitLaneTime: Float,
    var stopTime: Float? = null
)

private val mockPitLaneUIData = listOf(
    PitLaneUIData(
        R.drawable.mercedes_logo,
        MercedesTeal,
        1,
        "HAMILTON",
        16.7f
    ), PitLaneUIData(
        R.drawable.rb_logo,
        RedBullBlue,
        14,
        "PEREZ",
        10.7f
    ),
    PitLaneUIData(
        R.drawable.mercedes_logo,
        MercedesTeal,
        5,
        "RUSSELL",
        16.7f
    )
)

private val widgetWidth = 170.dp

@Composable
@Preview
fun PitLaneList(
    modifier: Modifier = Modifier, pitLaneStateList: List<PitLaneUIData> = mockPitLaneUIData
) {

    Column {
        Text(
            text = "PIT LANE",
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
                .width(widgetWidth)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f), Color.Black.copy(alpha = 0.9f)
                        )
                    )
                ),
            textAlign = TextAlign.Center,
            color = Color.Red.copy(alpha = 0.7f),
            fontFamily = f1Bold
        )

        LazyColumn(content = {
            items(pitLaneStateList.size) {
                PitLaneCard(modifier = modifier, data = pitLaneStateList[it])
            }
        })
    }

}

@Composable
@Preview
fun PitLaneCard(
    modifier: Modifier = Modifier, data: PitLaneUIData = PitLaneUIData(
        R.drawable.mercedes_logo,
        MercedesTeal,
        1,
        "HAMILTON",
        16.7f
    )
) {

    Column(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.7f), Color.Black.copy(alpha = 0.9f)
                    )
                )
            )
            .width(widgetWidth)
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.White.copy(alpha = 0.9f))
                .fillMaxWidth()
        ) {

            val paddingValue = if (data.driverPosition > 9) 4.dp else 8.dp

            Text(
                text = "${data.driverPosition}",
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                modifier = Modifier.padding(start = paddingValue, end = paddingValue),
                fontFamily = f1Regular
            )

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(data.teamColor)
            ) {
                Image(
                    painter = painterResource(id = data.teamLogo),
                    contentDescription = null,
                )
            }

            Text(
                text = data.driverName,
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                fontFamily = f1Bold
            )
        }


        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            var displayPitStopTime by remember { mutableStateOf(false) }

            AnimatedVisibility(visible = displayPitStopTime) {
                PitStopTimeUI(data.stopTime, data.teamColor)
            }

            if (!displayPitStopTime) {
                var isRunning by remember { mutableStateOf(false) }
                var startTime by remember { mutableStateOf(0L) }
                var elapsedMillis by remember { mutableStateOf(0L) }

                LaunchedEffect(isRunning) {
                    while (isRunning) {
                        delay(5)
                        elapsedMillis = System.currentTimeMillis() - startTime
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (isRunning) {
                                elapsedMillis = System.currentTimeMillis() - startTime
                                isRunning = false
                            } else {
                                startTime = System.currentTimeMillis() - elapsedMillis
                                isRunning = true
                            }
                        }
                ) {
                    Text(
                        text = "PIT",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 6.dp, end = 6.dp),
                        fontFamily = f1Regular
                    )

                    val seconds = (elapsedMillis / 1000) % 60
                    val hundreds = ((elapsedMillis / 10) % 100) / 10

                    val startPadding = if (seconds < 10) 6.dp else 3.dp

                    Text(
                        text = "$seconds:${hundreds}",
                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = startPadding, end = 3.dp),
                        fontFamily = f1Bold
                    )

                    if (seconds > 8) {
                        val random = Random.nextFloat() * (4.0f - 2.0f) + 2.0f
                        val rounded =
                            BigDecimal(random.toDouble()).setScale(1, RoundingMode.HALF_UP)
                                .toFloat()
                        data.stopTime = rounded
                        displayPitStopTime = true
                    }

                }
            }
        }
    }

}

@Composable
@Preview
private fun PitStopTimeUI(stopTime: Float? = 2.4f, teamColor: Color = MercedesTeal) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "STOP\nTIME",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.padding(8.dp),
            fontFamily = f1Bold
        )

        Text(
            text = "$stopTime",
            style = MaterialTheme.typography.headlineLarge.copy(fontStyle = FontStyle.Italic),
            color = teamColor,
            fontFamily = f1Regular,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}