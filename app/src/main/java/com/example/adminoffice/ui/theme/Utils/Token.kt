package com.example.adminoffice.ui.theme.Utils

import android.content.Context
import android.content.SharedPreferences
fun saveTokenToLocalStorage(context: Context, token: String) {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString("token", token)
    editor.apply()
}


fun getTokenFromLocalStorage(context: Context): String {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Retrieve the token from SharedPreferences, defaulting to an empty string if not found
    return sharedPreferences.getString("token", "") ?: ""
}