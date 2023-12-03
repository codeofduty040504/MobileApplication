package com.example.adminoffice.ui.theme.Utils.Screens.Profiling

import android.annotation.SuppressLint
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.MaskVisualTransformation
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
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
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Utils.Screens.Dashboard
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.saveTokenToLocalStorage
import org.json.JSONObject
object Register  : Screen {
    private var toke by mutableStateOf("")
    private var message by mutableStateOf("")
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController

    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var isErrorPassword by remember { mutableStateOf(false) }
        var isErrorConfirmPassword by remember { mutableStateOf(false) }
        var isErrorCNIC by remember { mutableStateOf(false) }
        var isErrorLastName by remember { mutableStateOf(false) }
        var isErrorFirstName by remember { mutableStateOf(false) }
        var isErrorPhoneNumber by remember { mutableStateOf(false) }
        var isErrorEmail by remember { mutableStateOf(false) }
        var secure by remember{ mutableStateOf(false) }
        var secureConfirm by remember{ mutableStateOf(false) }

        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }
        val confirmPassword = remember {
            mutableStateOf("")
        }
        val CNIC = remember {
            mutableStateOf("")
        }
        val FirstName = remember {
            mutableStateOf("")
        }
        val LastName = remember {
            mutableStateOf("")
        }
        val PhoneNumber = remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Create your account", color = GlobalStrings.CustomerColorMain,
                fontSize = 24.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.size(180.dp,
                        if(isErrorFirstName){
                            75.dp
                        }else{
                            60.dp
                        }
                    )
                ) {
                    OutlinedTextField(
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color.Red,
                            placeholderColor = Color.Gray,
                            disabledPlaceholderColor = Color.Gray
                        ),
                        value = FirstName.value,
                        onValueChange = {
                            if (it.length <= 10)
                            FirstName.value=it
//                            isErrorFirstName = isValidFirstName(FirstName.value)
                        },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.man),contentDescription = null)
                        },
                        label = {
                            Text(text = "First Name", color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Enter First Name")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier.size(180.dp,60.dp)
                    )
                    if (isErrorFirstName) {
                        Text(
                            text = "Please provide a valid First Name",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                }
                Column(
                    modifier = Modifier.size(180.dp,
                        if(isErrorLastName){
                            75.dp
                        }else{
                            60.dp
                        }
                    )
                ) {
                    OutlinedTextField(
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color.Red,
                            placeholderColor = Color.Gray,
                            disabledPlaceholderColor = Color.Gray
                        ),
                        value = LastName.value,
                        onValueChange = {
                            if (it.length <= 10)
                            LastName.value=it
//                            isErrorLastName = isValidLastName(LastName.value)
                        },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.man),contentDescription = null)
                        },
                        label = {
                            Text(text = "Last Name", color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Enter Last Name")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier.size(180.dp,60.dp)
                    )
                    if (isErrorLastName) {
                        Text(
                            text = "Please provide a valid Last Name",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                }
            }
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
                    if (it.length <= 35)
                    email.value=it
//                    isErrorEmail = isValidEmail(email.value)
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
                shape = RoundedCornerShape(5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorBorderColor = Color.Red,
                    placeholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray
                ),
                value = CNIC.value,
                onValueChange = {
                    if (it.length <= 13)
                    CNIC.value=it
//                    isErrorCNIC = isValidCNIC(CNIC.value)
                },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.card),contentDescription = null)
                },
                label = {
                    Text(text = "CNIC", color = Color.Gray)
                },
                placeholder = {
                    Text(text = "Enter CNIC")
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ), visualTransformation = MaskVisualTransformation("#####-#######-#")
            )
            if (isErrorCNIC) {
                Text(
                    text = "Please provide a valid CNIC",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Start),
                    fontSize = 11.sp
                )
            }



            OutlinedTextField(
                shape = RoundedCornerShape(5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray,
                    errorBorderColor = Color.Red,
                    placeholderColor = Color.Gray,
                    disabledPlaceholderColor = Color.Gray
                ),
                value = PhoneNumber.value,
                onValueChange = {
                    if (it.length <= 11)
                    PhoneNumber.value=it
//                    isErrorPhoneNumber = isValidPhoneNumber(PhoneNumber.value)
                },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.phone),contentDescription = null)
                },
                label = {
                    Text(text = "Phone Number", color = Color.Gray)
                },
                placeholder = {
                    Text(text = "Enter Phone Number")
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),visualTransformation = MaskVisualTransformation("####-#######")
            )
            if (isErrorPhoneNumber) {
                Text(
                    text = "Please provide a valid Phone Number",
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
                    disabledPlaceholderColor = Color.Gray
                ),
                value = password.value,
                onValueChange = {
                    if (it.length <= 15)
                    password.value=it
//                    isErrorPassword = !isValidPassword(password.value)
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = "password")
                },
                trailingIcon = { IconButton(onClick = { secure= !secure }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),contentDescription = null)
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
            )
            if (isErrorPassword) {
                Text(
                    text = "Password must contain at least one uppercase, one lowercase, one number, one special character  and must be 8 charactors long.",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Start),
                    fontSize = 11.sp, lineHeight = 12.sp
                )
            }

            OutlinedTextField(
                visualTransformation = if(secureConfirm){
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
                    disabledPlaceholderColor = Color.Gray
                ),
                value = confirmPassword.value,
                onValueChange = {
                    if (it.length <= 15)
                    confirmPassword.value=it
//                    isErrorConfirmPassword = !isConfirmPassword(password.value,confirmPassword.value)
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = "password")
                },
                trailingIcon = { IconButton(onClick = { secureConfirm= !secureConfirm }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),contentDescription = null)
                }},
                label = {
                    Text(text = "Confirm Password",color = Color.Gray)
                },
                placeholder = {
                    Text(text = "Enter Confirm Password")
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
            )
            if (isErrorConfirmPassword) {
                Text(
                    text = "Password does not match",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Start),
                    fontSize = 11.sp
                )
            }
                Spacer(Modifier.size(10.dp))
            Box(modifier = Modifier
                .background(GlobalStrings.CustomerColorMain, RoundedCornerShape(15.dp))
                .clickable {
                    Log.d("Logg","heheheheh toddddken")
                    isErrorPassword = !isValidPassword(password.value)
                    isErrorEmail = !isValidEmail(email.value)
                    isErrorConfirmPassword = !isConfirmPassword(password.value,confirmPassword.value)
                    isErrorPhoneNumber = !isValidPhoneNumber(PhoneNumber.value)
                    isErrorCNIC = !isValidCNIC(CNIC.value)
                    isErrorFirstName = !isValidFirstName(FirstName.value)
                    isErrorLastName = !isValidLastName(LastName.value)
                    if(!isErrorPassword && !isErrorEmail && !isErrorCNIC
                        && !isErrorConfirmPassword && !isErrorFirstName
                        && !isErrorLastName && !isErrorPhoneNumber){
                        Log.d("Logg","hehehehehsssss token")
                        registerUser(context, email.value, password.value,PhoneNumber.value,CNIC.value,FirstName.value,LastName.value) { isSuccess ->
                            if(isSuccess){
                                Log.d("Logg","heheheheh token")
                                navigator.replace(Login)
                            }
                        }
                    }
                }
                .fillMaxWidth()
                .padding(0.dp, 10.dp)
                .size(400.dp, 30.dp), contentAlignment = Alignment.Center){
                Text(text = "Register", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.Black)
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Already a user?", color = Color.Gray)
                Box(Modifier.padding(horizontal=3.dp).clickable { navigator.replace(Login) }){
                    Text(text = "Log in", color = Color.Blue)
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
        }
    }
    // Login Function
    fun registerUser(context: Context, email: String, password: String, contactNo:String, cnic:String, firstName: String, lastName: String, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}auth/register"

        // Request parameters
        val params = JSONObject()
        params.put("email", email)
        params.put("password", password)
        params.put("contactNo", contactNo)
        params.put("cnic", cnic)
        params.put("category", "customer")
        params.put("role", "customer")
        params.put("profilePicture", "user")
        params.put("firstName", firstName)
        params.put("lastName", lastName)

        val request = object : JsonObjectRequest(
            Request.Method.POST, url, params,
            { response ->
                // Handle successful login response
                Log.d("Register Response", response.toString())
                // You can check the response here to verify if the user is valid
                message = response.getString("message")


                callback(true)
            },
            { error ->
                // Handle error response
                Log.e("Login Error", error.toString())
                callback(false)
            }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "${getTokenFromLocalStorage(context)}"
                return headers
            }
        }


        // Add the request to the RequestQueue.
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
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

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneRegex = Regex("\\d{4}\\d{7}")
        return phoneRegex.matches(phoneNumber)
    }

    fun isValidCNIC(cnic: String): Boolean {
        val cnicRegex = Regex("\\d{5}\\d{7}\\d")
        return cnicRegex.matches(cnic)
    }

    fun isConfirmPassword(password: String, confirmPassword: String): Boolean {
        if (password == confirmPassword) {
            return true
        } else {
            return false
        }
    }

    fun isValidFirstName(firstName: String): Boolean {
        val nameRegex = Regex("^[a-zA-Z]+\$")
        return nameRegex.matches(firstName)
    }

    fun isValidLastName(lastName: String): Boolean {
        val nameRegex = Regex("^[a-zA-Z]+\$")
        return nameRegex.matches(lastName)
    }


}