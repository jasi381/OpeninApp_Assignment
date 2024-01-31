package com.jasmeet.openinapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jasmeet.openinapp.R
import com.jasmeet.openinapp.appComponents.GraphComponent
import com.jasmeet.openinapp.appComponents.TopAppBarComponent
import com.jasmeet.openinapp.viewModel.MainViewModel

/**
 * @author: Jasmeet Singh
 */
@Composable
fun LinkScreen(viewModel: MainViewModel) {

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }
    Scaffold(
        bottomBar = {
        }
    ) {
        Column(
            Modifier
                .background(Color(0xFFF5F5F5))
                .padding(bottom = it.calculateBottomPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TopAppBarComponent(title = "Dashboard", icon = R.drawable.ic_settings)
            GraphComponent()
        }
    }
}

