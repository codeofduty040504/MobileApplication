package com.example.adminoffice.ui.theme

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import org.json.JSONObject

data class DabsUser(
    val _id:String,
    val firstname:String,
    val lastName: String,
    val email:String,
    val contactNo:String,
    val cnic : String,
    val profilePicture:String,
    val role:String
)
// Login Function
