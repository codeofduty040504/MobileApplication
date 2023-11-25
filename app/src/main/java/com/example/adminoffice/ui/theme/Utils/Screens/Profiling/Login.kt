package com.example.adminoffice.ui.theme.Utils.Screens.Profiling

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Dashboard
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.saveTokenToLocalStorage
import org.json.JSONObject

object Login  : Screen {
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
                    navigator.push(LandingPage)
                }
                else if(role=="Admin"){
                    navigator.push(Dashboard)
                }
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
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(100.dp))
                Image(
                    painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2FAsset%201.png?alt=media&token=f9d050ca-ee90-4fe3-a44d-fe9d896ff3f4&_gl=1*khoe9m*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NzcyMzYxOS4zMC4xLjE2OTc3MjM4OTguNjAuMC4w"),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape((CornerSize(10.dp)))),
                    contentScale = ContentScale.FillBounds
                )
                Text(text = "Welcome Back !!", color = GlobalStrings.CustomerColorMain,
                    fontSize = 24.sp
                )
                OutlinedTextField(
                    shape = RoundedCornerShape(5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red,
                        placeholderColor = Color.Gray,
                        disabledPlaceholderColor = Color.Gray
                    ),
                    value = email.value,
                    onValueChange = {
                        if (it.length <= 30) email.value = it
                        // email.value=it
//                    isErrorEmail = !isValidEmail(email.value)
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, contentDescription = "person")
                    },
                    label = {
                        Text(text = "Email", color = Color.Gray)
                    },
                    placeholder = {
                        Text(text = "Enter Email")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true
                )
                if (isErrorEmail) {
                    Text(
                        text = "Please provide a valid Email",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start),
                        fontSize = 11.sp
                    )
                }



                OutlinedTextField(
                    visualTransformation = if(secure){
                        PasswordVisualTransformation()
                    }
                    else{
                        VisualTransformation.None
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red,
                        placeholderColor = Color.Gray,
                        disabledPlaceholderColor = Color.Gray,
                    ),
                    value = password.value,
                    onValueChange = {
                        if (it.length <= 15) password.value=it
//                    isErrorPassword = !isValidPassword(password.value)
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.Lock, contentDescription = "password")
                    },
                    trailingIcon = { IconButton(onClick = { secure= !secure }) {
                        Icon(painter = painterResource(id = R.drawable.eye),contentDescription = null)
                    }},
                    label = {
                        Text(text = "Password",color = Color.Gray)
                    },
                    placeholder = {
                        Text(text = "Enter Password")
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
                if (isErrorPassword) {
                    Text(
                        text = "Password must contain at least one uppercase, one lowercase, one number, and one special character.",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start),
                        fontSize = 11.sp,
                        lineHeight = 11.sp
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Box(modifier = Modifier
                    .background(GlobalStrings.CustomerColorMain, RoundedCornerShape(15.dp))
                    .clickable {
                        isErrorPassword = !isValidPassword(password.value)
                        isErrorEmail = !isValidEmail(email.value)
                        if (!isErrorPassword && !isErrorEmail) {
                            loginUser(context, email.value, password.value) { isSuccess ->
                                if (toke.isNotEmpty()) {
                                    if (isSuccess) {
                                        if (role == "admin") {
                                            saveTokenToLocalStorage(context, toke)
                                            navigator.push(Dashboard)
                                        } else if (role == "customer") {
                                            saveTokenToLocalStorage(context, toke)
                                            navigator.push(LandingPage)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp)
                    .size(400.dp, 30.dp), contentAlignment = Alignment.Center){
                    Text(text = "Log in", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.Black)
                }
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Dont have an account?", color = Color.Gray)
                    Box(
                        Modifier
                            .padding(horizontal = 3.dp)
                            .clickable { navigator.push(Register) }){
                        Text(text = "Register", color = Color.Blue)
                    }
                }
                Text(text = "Or continue with", modifier = Modifier.padding(10.dp))
                Image(
                    painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/google.png?alt=media&token=23aa40a5-5dea-4ea1-9d7a-d0b6997e679e"),
                    contentDescription = "image",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(50.dp)
                        .clip(RoundedCornerShape((CornerSize(10.dp))))
                )
//
//            Box(modifier = Modifier.clickable {
//                saveHotelIDInToken(context,"Hi from Kumail")
//            }){
//                Text(text = "Send")
//            }
//            Box(modifier = Modifier.clickable {
//                var bvc= getHotelID(context)
//                Log.d("HUJIA",bvc)
//            }){
//                Text(text = "Get")
//            }
//
//            Box(modifier = Modifier.clickable {
//                addRoomToCart(context,"Super123asddd")
//            }){
//                Text(text = "Send Room")
//            }
//            Box(modifier = Modifier.clickable {
//                var bvc= getRoomsInCart(context)
//                Log.d("HUJIA",bvc.toString())
//                bvc.forEach { it->
//                    Log.d("HUJIA",it)
//                }
//            }){
//                Text(text = "Get Room")
//            }
//            Box(modifier = Modifier.clickable {
//                AddRoomInCart(context,"Hi from Kumail","Superoooopp")
//            }){
//                Text(text = "ASDDD")
//            }
//            Box(modifier = Modifier.clickable {
//                ClearCart(context)
//            }){
//                Text(text = "Clean")
//            }
            }
        }
    }
    // Login Function
    fun loginUser(context: Context, email: String, password: String, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}auth/login"

        // Request parameters
        val params = JSONObject()
        params.put("email", email)
        params.put("password", password)
        Log.d("Login",params.toString())
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()
        if(!isInternetAvailable(context)){
            Toast
                .makeText(
                    context,
                    "Internet is not Available",
                    Toast.LENGTH_SHORT
                )
                .show()
            progressDialog.dismiss()
        }
        else{
            val request = object : JsonObjectRequest(
                Request.Method.POST, url, params,
                { response ->
                 try{
                     // Handle successful login response
                     Log.d("Login Response", response.toString())
                     // You can check the response here to verify if the user is valid
                     toke = response.getString("token")
                     Log.d("LoginTOKEN", toke)
                     role = response.getString("role")
                     progressDialog.dismiss()

                     // Assuming the API returns a JSON object with a field "valid" indicating user validity

                     callback(true)
                 }
                 catch (e:Exception){
                     e.printStackTrace()
                 }
                },
                { error ->
                    // Handle error response
                    Log.e("Login Error", error.toString())
                    Log.e("Login Error", error.networkResponse.data.toString())
                    Log.e("Login Error", error.networkResponse.statusCode.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Connection Error or try with different Credentials",
                            Toast.LENGTH_SHORT
                        )
                        .show()
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
                    // Handle successful login response
                    Log.d("Auth", response.toString())
                    role = response.getJSONObject("user").getString("role")


                    // Assuming the API returns a JSON object with a field "valid" indicating user validity

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("Login Error", error.toString())
                    Log.e("Login Error", error.networkResponse.data.toString())
                    Log.e("Login Error", error.networkResponse.statusCode.toString())
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

    fun isValidPassword(password: String): Boolean {
        val uppercaseRegex = Regex("(?=.*[A-Z])")
        val lowercaseRegex = Regex("(?=.*[a-z])")
        val digitRegex = Regex("(?=.*[0-9])")
        val specialCharRegex = Regex("[!#\$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+")

        return password.length >= 8 && uppercaseRegex.containsMatchIn(password) &&
                lowercaseRegex.containsMatchIn(password) && digitRegex.containsMatchIn(password) &&
                specialCharRegex.containsMatchIn(password)
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

        return emailRegex.matches(email)
    }
}