package com.example.adminoffice.ui.theme.Utils

import android.content.Context
import android.content.SharedPreferences
import com.example.adminoffice.ui.theme.DabsUser

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

fun saveRoleToLocalStorage(context: Context, token: String) {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString("role", token)
    editor.apply()
}


fun getRoleFromLocalStorage(context: Context): String {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Retrieve the token from SharedPreferences, defaulting to an empty string if not found
    return sharedPreferences.getString("role", "") ?: ""
}


fun saveUsertoLocal(context: Context, user: DabsUser) {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString("fname", user.firstname)
    editor.putString("lname", user.lastName)
    editor.putString("id", user._id)
    editor.putString("profile", user.profilePicture)
    editor.putString("role", user.role)
    editor.putString("email", user.email)
    editor.putString("cnic", user.cnic)
    editor.putString("contactNo", user.contactNo)
    editor.apply()
}

fun clearUser(context: Context){
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    var usser = DabsUser(_id = "",
        firstname = "",
        lastName = "",
        email = "",
        contactNo = "",
        cnic = "",
        profilePicture = "",
        role = "")
    saveUsertoLocal(context,usser)
    editor.apply()

}


fun getUserFromLocal(context: Context): DabsUser {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    var usser = DabsUser(_id = sharedPreferences.getString("id", "").toString(),
        firstname = sharedPreferences.getString("fname", "").toString(),
        lastName = sharedPreferences.getString("lname", "").toString(),
        email = sharedPreferences.getString("email", "").toString(),
        contactNo = sharedPreferences.getString("contactNo", "").toString(),
        cnic = sharedPreferences.getString("cnic", "").toString(),
        profilePicture = sharedPreferences.getString("profile", "").toString(),
        role = sharedPreferences.getString("role", "").toString())
    // Retrieve the token from SharedPreferences, defaulting to an empty string if not found
    return usser
}