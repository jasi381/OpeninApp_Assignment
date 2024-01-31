package com.jasmeet.openinapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jasmeet.openinapp.R
import com.jasmeet.openinapp.appComponents.GraphComponent
import com.jasmeet.openinapp.appComponents.TopAppBarComponent
import com.jasmeet.openinapp.viewModel.MainViewModel

/**
 * @author: Jasmeet Singh
 */
@Composable
fun LinkScreen(
    viewModel: MainViewModel,
    navHost: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    val scrollState = rememberScrollState()
    Scaffold(
        bottomBar = {
        }
    ) {
        Column(
            Modifier
                .background(Color(0xFFF5F5F5))
                .padding(bottom = it.calculateBottomPadding())
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            TopAppBarComponent(title = "Dashboard", icon = R.drawable.ic_settings)
            GraphComponent()
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

