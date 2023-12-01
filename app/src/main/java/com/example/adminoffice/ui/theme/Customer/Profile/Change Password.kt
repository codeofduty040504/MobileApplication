package com.example.adminoffice.ui.theme.Customer.Profile

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.MaskVisualTransformation
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
import com.example.adminoffice.ui.theme.Utils.Screens.Dashboard
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.AddExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewProfit
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.AddHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.ViewHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.AddInventory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.AddInventoryCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.ViewInventory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.ViewInventoryCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.AddDish
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.AddDishCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.AddMenu
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.ViewDish
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.ViewDishCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.ViewMenu
import com.example.adminoffice.ui.theme.Utils.Screens.Payments.ViewPayments
import com.example.adminoffice.ui.theme.Utils.Screens.Payments.ViewRefunds
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.AddRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.ViewRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.AddRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.ViewRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.util.UUID

object ChangePassword  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var imageURL = ""
    var message = ""
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        ModalNavigationDrawer(
            drawerContent = {},
            drawerState= drawerState, gesturesEnabled = false
        ) {
            Scaffold(
            )
            {

                Box(modifier = Modifier.fillMaxWidth()){
                    val navigator = LocalNavigator.currentOrThrow
                    val context = LocalContext.current
                    var isExpandedRole = remember {
                        mutableStateOf(false)
                    }
                    var isErrorPassword by remember { mutableStateOf(false) }
                    var isErrorRole by remember { mutableStateOf(false) }
                    var isErrorConfirmPassword by remember { mutableStateOf(false) }
                    var isErrorCNIC by remember { mutableStateOf(false) }
                    var isErrorLastName by remember { mutableStateOf(false) }
                    var isErrorFirstName by remember { mutableStateOf(false) }
                    var isErrorPhoneNumber by remember { mutableStateOf(false) }
                    var isErrorEmail by remember { mutableStateOf(false) }
                    var secure by remember{ mutableStateOf(true) }
                    var secureConfirm by remember{ mutableStateOf(true) }
                    var role by remember { mutableStateOf("") }
                    val rolesAvailable = listOf("Customer", "Hotel Owner", "Staff")
                    val firstName = remember {
                        mutableStateOf("")
                    }
                    val lastName = remember {
                        mutableStateOf("")
                    }
                    var imageuri by remember {
                        mutableStateOf<Uri?>(null)
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
                    val email = remember {
                        mutableStateOf("")
                    }
                    val PhoneNumber = remember {
                        mutableStateOf("")
                    }
                    var bitmap by remember { mutableStateOf<Bitmap?>(null) }


                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia(),
                        onResult = { uri ->
                            imageURL=""
                            imageuri = uri }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 60.dp, horizontal = 30.dp),
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            Text(text = "Change your Password", color = GlobalStrings.CustomerColorMain,
                                fontSize = 24.sp
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
                            },
                            leadingIcon = {
                                Icon(Icons.Outlined.Lock, contentDescription = "password", tint = GlobalStrings.CustomerColorMain)
                            },
                            label = {
                                Text(text = "Old Password", color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter Old Password")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            singleLine = true,
                        )
                        if (isErrorCNIC) {
                            Text(
                                text = "Please provide a valid Old Password",
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
                                if (it.length <= 15) password.value=it
//                    isErrorPassword = !isValidPassword(password.value)
                            },
                            leadingIcon = {
                                Icon(Icons.Outlined.Lock, contentDescription = "password", tint = GlobalStrings.CustomerColorMain)
                            },
                            trailingIcon = { IconButton(onClick = { secure= !secure }) {
                                Icon(painter = painterResource(id = R.drawable.eye),contentDescription = null, tint = GlobalStrings.CustomerColorMain)
                            }
                            },
                            label = {
                                Text(text = "New Password",color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter New Password")
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
                                text = "Password must contain at least one uppercase, one lowercase, one number, one special character  and must be 8 charactors long.",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp,
                                lineHeight = 11.sp
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
                                if (it.length <= 15)  confirmPassword.value=it
                            },
                            leadingIcon = {
                                Icon(Icons.Outlined.Lock, contentDescription = "password", tint = GlobalStrings.CustomerColorMain)
                            },
                            trailingIcon = { IconButton(onClick = { secureConfirm= !secureConfirm }) {
                                Icon(painter = painterResource(id = R.drawable.eye),contentDescription = null, tint = GlobalStrings.CustomerColorMain)
                            }
                            },
                            label = {
                                Text(text = "Confirm New Password",color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter Confirm New Password")
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true

                        )
                        if (isErrorConfirmPassword) {
                            Text(
                                text = "Password does not match",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Box(modifier = Modifier.fillMaxWidth().clickable {
                            isErrorPassword = !Home.isValidPassword(password.value)
                            isErrorConfirmPassword = !Home.isConfirmPassword(
                                password.value,
                                confirmPassword.value
                            )
                            isErrorCNIC = !Home.isValidPassword(CNIC.value)
                            if(!isErrorPassword &&  !isErrorCNIC
                                && !isErrorConfirmPassword){
                                scope.launch {
                                    ChangePassword(context = context, oldPassword = CNIC.value, newPassword = password.value){
                                        if(it){
                                            navigator.pop()
                                        }
                                    }
                                }
                            }

                        }.height(50.dp).background(GlobalStrings.CustomerColorMain,
                            RoundedCornerShape(10.dp)
                        ), contentAlignment = Alignment.Center){
                            Text(text = "Change Password", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

    }

    fun ChangePassword(context: Context,
                     oldPassword: String,
                       newPassword: String,
                     callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}customer/users/changePassword"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()

        // Request parameters
        val params = JSONObject()
        params.put("newPassword", newPassword)
        params.put("oldPassword", oldPassword)
        Log.e("Register", params.toString())
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
                Request.Method.PUT, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("Register", response.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Password Updated",
                            Toast.LENGTH_SHORT
                        )
                        .show()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("Register", error.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "An Error Occurred while updating Password. Please check your Parameters.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
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
    }
}