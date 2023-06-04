package com.az.f1graphics.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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

@Composable
@Preview
fun PitLaneCard(
    modifier: Modifier = Modifier,
    driverPosition: Int = 1,
    driverTeamLogo: Int = R.drawable.account_circle,
    driverTeamColor: Color = Color.White.copy(red = 0.1f, alpha = 0.8f),
    driverName: String = "HAMILTON",
    totalTimeInPitLane: Float = 16.7f,
    timeInTeamPit: Float = 2.7f
) {

    Column(
        modifier = modifier
            .background(Color.Black)
            .wrapContentWidth()
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.White.copy(alpha = 0.9f))
                .wrapContentWidth()
        ) {
            Text(
                text = "$driverPosition",
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic)
            )

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(driverTeamColor)
            ) {
                Image(
                    painter = painterResource(id = driverTeamLogo),
                    contentDescription = null
                )
            }

            Text(
                text = driverName,
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic)
            )
        }


        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    text = "$totalTimeInPitLane",
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Text(
                text = "$timeInTeamPit",
                style = MaterialTheme.typography.headlineSmall.copy(fontStyle = FontStyle.Italic),
                color = driverTeamColor,
                modifier = Modifier.size(39.dp)
            )
        }
    }

}