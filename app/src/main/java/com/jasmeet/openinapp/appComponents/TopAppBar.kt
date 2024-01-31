package com.jasmeet.openinapp.appComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.openinapp.ui.theme.figtree

/**
 * @author: Jasmeet Singh
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    title: String,
    onActionClicked: () -> Unit = { },
    icon: Int
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(Color(0xFF0E6FFF)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 26.sp,
                    lineHeight = 24.sp,
                    fontFamily = figtree,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                )
            )

            ElevatedCard(
                onClick = {
                    onActionClicked.invoke()
                },
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFF2B81FF),
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 25.dp,
                    pressedElevation = 35.dp,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(26.dp)
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

