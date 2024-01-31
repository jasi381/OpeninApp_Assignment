package com.jasmeet.openinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jasmeet.openinapp.screens.LinkScreen
import com.jasmeet.openinapp.tokenManager.TokenManager
import com.jasmeet.openinapp.ui.theme.OpeninAppTheme
import com.jasmeet.openinapp.viewModel.MainViewModel

/**
 * @author: Jasmeet Singh
 */
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenManager = TokenManager(this)

        val savedToken = tokenManager.getToken()

        if (savedToken.isNullOrEmpty()) {
            /**
             * If the token is not saved, obtain it and save it
             * For example, you might get the token during the login process
             */

            val obtainedToken = this.getString(R.string.tokken)
            tokenManager.saveToken(obtainedToken)
        }

        viewModel = MainViewModel(tokenManager)
        enableEdgeToEdge()

        setContent {
            OpeninAppTheme {
                LinkScreen(viewModel = viewModel)
            }
        }
    }
}
