package com.example.adminoffice.ui.theme.Customer

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Customer.CartFunctions.AddRoomInCart
import com.example.adminoffice.ui.theme.Customer.CartFunctions.ClearCart
import com.example.adminoffice.ui.theme.Customer.CartFunctions.addRoomToCart
import com.example.adminoffice.ui.theme.Customer.CartFunctions.getHotelID
import com.example.adminoffice.ui.theme.Customer.CartFunctions.getRoomsInCart
import com.example.adminoffice.ui.theme.Customer.CartFunctions.saveHotelIDInToken
import com.example.adminoffice.ui.theme.Customer.Functions.CompareHotel
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.DabsUser
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Dashboard
import com.example.adminoffice.ui.theme.Utils.Screens.Profiling.Login
import com.example.adminoffice.ui.theme.Utils.Screens.Profiling.Register
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.clearUser
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.saveRoleToLocalStorage
import com.example.adminoffice.ui.theme.Utils.saveTokenToLocalStorage
import com.example.adminoffice.ui.theme.Utils.saveUsertoLocal
import org.json.JSONObject

object SplashScreen  : Screen {
    private var toke by mutableStateOf("")
    private var role by mutableStateOf("")
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){

        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var isErrorPassword by remember { mutableStateOf(false) }
        var isErrorEmail by remember { mutableStateOf(false) }
        var secure by remember{ mutableStateOf(true) }
        author(context){
            if(it){
                if(role=="customer"){
                    navigator.replace(LandingPage)
                }
                else if(role=="admin"){
                    navigator.replace(Dashboard)
                }
                else if(role == "owner"){
                    navigator.replace(Dashboard)
                }
            }
            else{
                navigator.replace(LandingPage)
            }
        }
        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }
//        var Token = getTokenFromLocalStorage(context = context)
//        if(Token.isNotEmpty()){
//            navigator.push(Dashboard)
//        }
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                //Icon(painterResource(id = R.drawable.ic_launcher_foreground) , contentDescription = null, modifier = Modifier.size(40.dp))
                Image(
                    painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/splash.jpg?alt=media&token=1351d5ac-d6a2-404e-b89a-2f67003fe594"),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
    // Login Function
    fun author(context: Context, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}auth/getAuthroizedUser"

        // Request parameters
        val params = JSONObject()
        params.put("token",getTokenFromLocalStorage(context))
        Log.d("Login",params.toString())
        val progressDialog = ProgressDialog(context)
        if(!isInternetAvailable(context)){
            Toast
                .makeText(
                    context,
                    "Internet is not Available",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        else{
            val request = object : JsonObjectRequest(
                Request.Method.POST, url, params,
                { response ->
                    var profilePicture = ""
                    // Handle successful login response
                    Log.d("Auth12313", response.toString())
                    role = response.getJSONObject("user").getString("role")
                    var firstName = response.getJSONObject("user").getString("firstName")
                    var lastName = response.getJSONObject("user").getString("lastName")
                    var _id = response.getJSONObject("user").getString("_id")
                    var email = response.getJSONObject("user").getString("email")
                    var contactNo = response.getJSONObject("user").getString("contactNo")
                    var cnic = response.getJSONObject("user").getString("cnic")
                    if(response.getJSONObject("user").has("profilePicture")){
                       profilePicture = response.getJSONObject("user").getString("profilePicture")
                    }
                    var DabsUser = DabsUser(_id,firstName,lastName,email,contactNo,cnic,profilePicture,
                        role)
                    saveUsertoLocal(context,DabsUser)
                    // Assuming the API returns a JSON object with a field "valid" indicating user validity

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("Auth12313 Error", error.toString())
                    Log.e("Auth12313 Error", error.networkResponse.data.toString())
                    Log.e("Auth12313 Error", error.networkResponse.statusCode.toString())
//                    Toast
//                        .makeText(
//                            context,
//                            "Connection Error or try with different Credentials",
//                            Toast.LENGTH_SHORT
//                        )
//                        .show()
                    callback(false)
                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
//                    headers["Authorization"] = "${getTokenFromLocalStorage(context)}"
                    headers["Authorization"] = ""
                    return headers
                }
            }


            // Add the request to the RequestQueue.
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
        }

    }

}