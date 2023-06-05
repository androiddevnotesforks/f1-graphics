package com.az.f1graphics.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import com.az.f1graphics.ui.theme.AlpineBlue
import com.az.f1graphics.ui.theme.MercedesTeal
import kotlinx.coroutines.delay


data class PitLaneUIData(
    val teamLogo: Int,
    val teamColor: Color,
    val driverPosition: Int,
    val driverName: String,
    val pitLaneTime: Float,
    val stopTime: Float? = null
)

private val mockPitLaneUIData = listOf(
    PitLaneUIData(
        R.drawable.account_circle,
        MercedesTeal,
        1,
        "HAMILTON",
        16.7f,
        2.7f
    ), PitLaneUIData(
        R.drawable.account_circle,
        AlpineBlue,
        14,
        "OCON",
        10.7f
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
                            Color.Black.copy(alpha = 0.4f), Color.Black.copy(alpha = 0.9f)
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
        R.drawable.account_circle,
        Color.White.copy(red = 0.1f, alpha = 0.8f),
        1,
        "HAMILTON",
        16.7f,
        2.7f
    )
) {

    Column(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.1f), Color.Black.copy(alpha = 0.9f)
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
                    contentDescription = null
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
            if (data.stopTime != null) {
                Text(
                    text = "STOP\nTIME",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp),
                    fontFamily = f1Bold
                )

                Text(
                    text = "${data.stopTime}",
                    style = MaterialTheme.typography.headlineLarge.copy(fontStyle = FontStyle.Italic),
                    color = data.teamColor,
                    fontFamily = f1Regular,
                    modifier = Modifier.padding(end = 8.dp)
                )
            } else {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "PIT",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontFamily = f1Regular
                    )

                    Text(
                        text = "${data.pitLaneTime}",
                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontFamily = f1Bold
                    )
                }
            }
        }
    }

}

@Composable
fun Stopwatch() {
    var isRunning by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(0L) }
    var elapsedMillis by remember { mutableStateOf(0L) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10)
            elapsedMillis = System.currentTimeMillis() - startTime
        }
    }

    Button(
        onClick = {
            if (isRunning) {
                elapsedMillis = System.currentTimeMillis() - startTime
                isRunning = false
            } else {
                startTime = System.currentTimeMillis() - elapsedMillis
                isRunning = true
            }
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        if (isRunning) {
            Text(text = "Stop")
        } else {
            Text(text = "Start")
        }
    }

    Text(
        text = String.format(
            "%02d:%03d",
            elapsedMillis / 1000,
            elapsedMillis % 1000
        ),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 16.dp)
    )
}