package com.jasmeet.openinapp.appComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jasmeet.openinapp.R

@Composable
fun NetworkImage(
    imageModifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    imgUrl :Any,
    contentDescription: String? = null,


    ) {
    Box(
        containerModifier .border(
            width = 1.dp,
            color = Color(0xffd8d8d8),
            shape = RoundedCornerShape(size = 8.dp)
        )
    ) {
        SubcomposeAsyncImage(
        model = imgUrl,
        loading = {
            Text(text = "Loading")
        },
        contentDescription = contentDescription,
        error = {
            Image(
                painter = painterResource(id = R.drawable.img_error),
                contentDescription = "error",
                modifier = Modifier.background(Color.White).padding(2.dp)
            )
        },
        modifier = imageModifier
    )

    }

}