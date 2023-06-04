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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.az.f1graphics.R


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
        Color.White.copy(red = 0.1f, alpha = 0.8f),
        1,
        "HAMILTON",
        16.7f,
        2.7f
    ),
    PitLaneUIData(
        R.drawable.account_circle,
        Color.Blue.copy(alpha = 0.8f),
        14,
        "VERSTAPPEN",
        10.7f
    )
)

@Composable
@Preview(device = "spec:width=411dp,height=891dp")
fun PitLaneList(
    modifier: Modifier = Modifier,
    pitLaneStateList: List<PitLaneUIData> = mockPitLaneUIData
) {
    LazyColumn(content = {
        items(pitLaneStateList.size) {
            PitLaneCard(data = pitLaneStateList[it])
        }
    })
}

@Composable
@Preview
fun PitLaneCard(
    modifier: Modifier = Modifier,
    data: PitLaneUIData = PitLaneUIData(
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
            .background(Color.Black)
            .width(150.dp)
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
                modifier = Modifier.padding(start = paddingValue, end = paddingValue)
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
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "${data.stopTime}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontStyle = FontStyle.Italic),
                    color = data.teamColor,
                    modifier = Modifier.size(39.dp)
                )
            } else {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "PIT",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "${data.pitLaneTime}",
                        style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

}