package com.jasmeet.openinapp.tokenManager

import android.content.Context
import android.content.SharedPreferences

class TokenManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE)
    }


    /**
     * This method saves the token in the shared preferences the token will be received from the API
     * after the login process
     */
    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    /**
     * This method returns the token saved in the shared preferences
     */
    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    /**
     * This method clears the token saved in the shared preferences this can be used when user logs out or token expires
     */
    fun clearToken() {
        sharedPreferences.edit().remove("token").apply()
    }
}
