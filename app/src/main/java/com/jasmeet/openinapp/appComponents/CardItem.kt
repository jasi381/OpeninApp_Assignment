package com.jasmeet.openinapp.appComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.openinapp.ui.theme.figtree

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(
    icon: Int,
    title: String,
    description: String,
    iconDescription: String? = null,
    imgBackground: Color = Color(0xffebe6f9),
    imgTint: Color = Color(0xff5c33cf),
    onItemClick : () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(120.dp)
            .wrapContentHeight(),
        onClick = {
            onItemClick.invoke()
        }
    ) {
        Surface(
            shape = CircleShape,
            color = imgBackground,
            modifier = Modifier
                .padding(12.dp)
                .size(32.dp)
                .background(Color.Transparent)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = iconDescription,
                modifier = Modifier
                    .padding(5.dp)
                    .size(20.dp),
                tint = imgTint
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = figtree,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
            ),
            modifier = Modifier.padding(start = 12.dp, end = 21.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = figtree,
                fontWeight = FontWeight(400),
                color = Color(0xFF999CA0),
            ),
            modifier = Modifier.padding(bottom = 12.dp, start = 12.dp, end = 21.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}