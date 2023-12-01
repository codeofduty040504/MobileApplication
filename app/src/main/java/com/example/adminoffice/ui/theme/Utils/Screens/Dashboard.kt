package com.example.adminoffice.ui.theme.Utils.Screens

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import com.stripe.android.model.CardParams
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.stripe.android.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Column
import android.app.Application
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
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
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.stripe.android.view.CardInputWidget
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.UUID
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Utils.DataClasses.Accountings.Expense
import com.example.adminoffice.ui.theme.Utils.DataClasses.Accountings.Revenue
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Booking
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Hotel
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.HotelOwner
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Room
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.ServiceCategory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Userid
import com.example.adminoffice.ui.theme.Utils.DrawerUni
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.getRoleFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import kotlinx.coroutines.launch
object Dashboard  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController

    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var imageURL = ""
    var message = ""
    var Hotels = mutableStateListOf<String>()
    var Users = mutableStateListOf<String>()
    var islamabadHotel=  mutableStateOf(0)
    var karachihotel= mutableStateOf(0)
    var lahoreHotel= mutableStateOf(0)
    var customers= mutableStateOf(0)
    var owners= mutableStateOf(0)
    var staff=  mutableStateOf(0)
    var revenueCal=  mutableStateOf(0)
    var expenseCal=  mutableStateOf(0)
    var accountStripe = true
    var modalopen = mutableStateOf(true)
    var URLStripe = ""
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember { mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        ModalNavigationDrawer(
            drawerContent = {

                DrawerUni(scope,drawerState)
            },
            drawerState = drawerState,
        ) {
            Scaffold(
                topBar = {
                    CustomTopAppBar {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }
            )
            {
                if(!modalopen.value){
                    createStripeAccount(context)
                    CustomProgressDialog(context)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 70.dp, horizontal = 30.dp),
                    verticalArrangement = Arrangement.Center
                ) {
//                   Column(modifier = Modifier
//                       .border(
//                           0.2.dp, Color(0xFFF7F7F7),
//                           RoundedCornerShape(10.dp)
//                       )
//                       .background(
//                           Color(0xFFFFFFFF),
//                           RoundedCornerShape(10.dp)
//                       )
//                       .padding(vertical = 5.dp, horizontal = 15.dp)) {
//                       Text(text = "Cash in Hand", fontWeight = FontWeight.Light, fontSize = 12.sp, color = Color.Gray)
//                       Text(text = "5000", fontWeight = FontWeight.W600, fontSize = 32.sp)
//                   }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Box(modifier = Modifier.clickable { navigator.push(ViewUsers)}){
                            CardDetails(Color(0xFFE0F2FD),"Total Users", Users.size.toString(),{
                                navigator.push(ViewUsers)
                            })
                        }
                        Box(modifier = Modifier.clickable { navigator.push(ViewHotel) }) {
                            CardDetails(Color(0xFFEEFDE0), "Total Hotels", Hotels.size.toString(), {
                                navigator.push(ViewHotel)
                            })
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Box(modifier = Modifier.clickable { navigator.push(ViewBookings)}) {
                            CardDetails(
                                Color(0xFFFDE0E4),
                                "Total Bookings",
                                ViewBookings.serviceCategories.size.toString(),
                                {
                                    navigator.push(ViewBookings)
                                })
                        }
                        Box(modifier = Modifier.clickable { navigator.push(ViewRevenue)}) {
                            CardDetails(
                                Color(0xFFFDF7E0),
                                "Total Revenue",
                                revenueCal.value.toString(),
                                {
                                    navigator.push(ViewRevenue)
                                })
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Box(modifier = Modifier.clickable { navigator.push(ViewExpense)}) {
                            CardDetails(
                                Color(0xFFFBE0FD),
                                "Total Expenses",
                                expenseCal.value.toString(),
                                {
                                    navigator.push(ViewExpense)
                                })
                        }
                        Box(modifier = Modifier.clickable { navigator.push(ViewProfit) }) {
                            CardDetails(
                                Color(0xFFE0FDF5),
                                "Total Profit",
                                (revenueCal.value - expenseCal.value).toString(),
                                {
                                    navigator.push(ViewProfit)
                                })
                        }
                    }
                    var role = getRoleFromLocalStorage(context)
                    if(role =="admin"){
                        PieChart(size = 100.dp)
                    }
                    Spacer(modifier = Modifier.size(5.dp))
                   DoughnutChart(size = 100.dp)
//                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
//                        val cameraPositionState = rememberCameraPositionState {
//                            position = CameraPosition.fromLatLngZoom(LatLng(33.6844, 73.0479), 10f)
//
//                        }
//                        var userLocation = LatLng(0.0, 0.0)
//                        val mapProperties = MapProperties(
//                            isMyLocationEnabled = userLocation != null,
//                        )
//                        GoogleMap(
//                            modifier = Modifier
//                                .width(350.dp)
//                                .height(200.dp)
////                                        .layout { measurable, constraints ->
////                                            val placeable = measurable.measure(constraints)
////                                            layout(placeable.width, (placeable.height * 0.9).toInt()) {
////                                                placeable.placeRelative(0, 0)
////                                            }
////                                        },
//                            ,
//                            cameraPositionState = cameraPositionState,
//                            properties = mapProperties,
//                        ){
//
//                            MapEffect(HotelsLocation) { map ->
////                                map.setOnMapLoadedCallback {
////                                    Log.d("HHHHHASDASdasdas","enter")
////                                    for(hotel in HotelsLocation){
////                                        val markerOptions = MarkerOptions()
////                                            .position(hotel)
////                                        map.addMarker(markerOptions)
////                                    }
////
////                                }
//
//                            }
//                        }
//                    }

                }

            }
        }
        getHotels(context = context)
        GetUsers(context)
        getCategoriesR(context)
        getCategories(context)
        ViewBookings.getCategories(context)
        if(getRoleFromLocalStorage(context) != "admin"){
            verifyStripeAccount(context)
        }
    }
    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
    @Composable
    fun CustomProgressDialog(context: Context) {
        Dialog(
            onDismissRequest = {}
        ) {
                Box(modifier = Modifier.fillMaxWidth().height(45.dp).background(GlobalStrings.CustomerColorMain,
                    RoundedCornerShape(15.dp)
                ).clickable {
                    val url = URLStripe
                    openUrl(context, url)
                }, contentAlignment = Alignment.Center){
                    Text(text = "Create Stripe Account", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                }

           }
    }
    fun verifyStripeAccount(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/payments/verifyStripeAccount"
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if(!isInternetAvailable(context)){
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        else{
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, ViewExpense.params,
                { response ->
                    Log.d("HASHDASDASVeru",response.toString())
                    var accountExists = response.getBoolean("accountExists")
                    accountStripe=accountExists
                    modalopen.value= accountStripe
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDASVeru",error.toString())
                    Log.d("HASHDASDASVeru",error.networkResponse.statusCode.toString())
                    progressDialog.dismiss()
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
    fun createStripeAccount(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/payments/create-connect-account"
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if(!isInternetAvailable(context)){
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        else{
            val request = object : JsonObjectRequest(
                Request.Method.POST, url, ViewExpense.params,
                { response ->
                    Log.d("HASHDASDASVeru",response.toString())
                    var url = response.getString("url")
                    URLStripe=url
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDASVeru",error.toString())
                    Log.d("HASHDASDASVeru",error.networkResponse.statusCode.toString())
                    progressDialog.dismiss()
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
    // GET Categories Function
    fun getCategories(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/expense/getExpenses"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Expense...")
        progressDialog.show()
        if(!isInternetAvailable(context)){
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        else{
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, ViewExpense.params,
                { response ->
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("expenses")
                    ViewExpense.serviceCategories.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)

                        var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var hotelName = category.getString("hotelName")
                        var details = category.getString("details")
                        var amount = category.getInt("amount")
                        var date = category.getString("date")
                        var referenceTitle = category.getString("title")
                        // var type = category.getString("type")
                        var hotel = category.getJSONObject("hotel")
                        var hotelID = hotel.getString("_id")

                        ViewExpense.serviceCategories.add(
                            Expense(_id = _id, amount = amount, date = date, details =details, hotelName = hotelName , id = id,
                                image = serviceImage, referenceTitle = referenceTitle, hotelID = hotelID, type = "type")
                        )
                        expenseCal.value= expenseCal.value+amount
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    ViewExpense.serviceCategories.clear()
                    ViewExpense.usersJsonArrayError = mutableStateOf(error.toString())
                    progressDialog.dismiss()
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
    // GET Categories Function
    fun getCategoriesR(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/revenue/getRevenues"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Revenue...")
        progressDialog.show()
        if(!isInternetAvailable(context)){
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        else{
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, ViewRevenue.params,
                { response ->
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("revenues")
                    ViewProfit.serviceCategoriesR.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)

                        var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var hotelName = category.getString("hotelName")
                        var details = category.getString("details")
                        var amount = category.getInt("amount")
                        var date = category.getString("date")
                        var referenceTitle = category.getString("referenceTitle")
                        var hotel = category.getJSONObject("hotel")
                        var hotelID = hotel.getString("_id")
                        var rrr = Revenue(_id = _id, amount = amount, date = date, details =details, hotelName = hotelName , id = id,
                            image = serviceImage, referenceTitle = referenceTitle, hotelID = hotelID)
                        ViewProfit.serviceCategoriesR.add(rrr)
                        revenueCal.value= revenueCal.value+rrr.amount
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    ViewProfit.serviceCategoriesR.clear()
                    ViewProfit.usersJsonArrayError = mutableStateOf(error.toString())
                    progressDialog.dismiss()
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
    private @Composable
    fun CardDetails(color: Color,heading:String,quantiy:String,callback: (Boolean) -> Unit) {
        Box(modifier = Modifier
            .height(120.dp)
            .width(170.dp)
            .padding(vertical = 5.dp)
            .border(
                0.2.dp, color,
                RoundedCornerShape(10.dp)
            )
            .background(
                color,
                RoundedCornerShape(10.dp)
            )){
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
                Text(text = heading, fontWeight = FontWeight.Light, fontSize = 12.sp, color = Color.Gray, style = TextStyle(shadow = Shadow(Color.Gray,
                    Offset.Zero)))
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = quantiy, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            }

        }
    }

    @Composable
    fun PieChart(
        values: List<Float> = listOf(customers.value.toFloat(), owners.value.toFloat(), staff.value.toFloat()),
        colors: List<Color> = listOf(Color(0xFF58BDFF), Color(0xFF125B7F), Color(0xFF092D40)),
        legend: List<String> = listOf("Customer", "Owners", "Others"),
        size: Dp = 200.dp
    ) {
        // Sum of all the values
        val sumOfValues = values.sum()

        // Calculate each proportion value
        val proportions = values.map {
            it * 100 / sumOfValues
        }

        // Convert each proportions to angle
        val sweepAngles = proportions.map {
            it * 360 / 100
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .border(
                0.6.dp, Color(0xFFF7F7F7),
                RoundedCornerShape(10.dp)
            )
            .background(
                Color(0xFFFFFFFF),
                RoundedCornerShape(10.dp)
            )) {
            Text(text = "Users Breakdown", fontWeight = FontWeight.W400, fontSize = 16.sp, color = GlobalStrings.AdminColorMain)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier

                    .padding(10.dp)){
                Canvas(
                    modifier = Modifier.size(size)
                ) {
                    var startAngle = -90f

                    for (i in sweepAngles.indices) {
                        drawArc(
                            color = colors[i],
                            startAngle = startAngle,
                            sweepAngle = sweepAngles[i],
                            useCenter = true
                        )
                        startAngle += sweepAngles[i]
                    }
                }

                Spacer(modifier = Modifier.size(32.dp))

                Column(modifier = Modifier.size(80.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = customers.value.toString(), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Customer", fontSize = 12.sp)
                }
                Column(modifier = Modifier.size(80.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = owners.value.toString(), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Hotel Owners", fontSize = 12.sp)
                }
                Column(modifier = Modifier.size(80.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = staff.value.toString(), fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Others", fontSize = 12.sp)
                }

            }
        }
    }

    @Composable
    fun DisplayLegend(color: Color, legend: String) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.width(16.dp),
                thickness = 4.dp,
                color = color
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = legend,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }

    @Composable
    fun DoughnutChart(
        values: List<Float> = listOf(islamabadHotel.value.toFloat(), karachihotel.value.toFloat(), lahoreHotel.value.toFloat()),
        colors: List<Color> = listOf(
            Color(0xFFFF6384),
            Color(0xFFFFCE56),
            Color(0xFF448AFF)
        ),
        legend: List<String> = listOf("Islamabad", "Karachi", "Lahore",),
        size: Dp = 200.dp,
        thickness: Dp = 36.dp
    ) {

        // Sum of all the values
        val sumOfValues = values.sum()

        // Calculate each proportion
        val proportions = values.map {
            it * 100 / sumOfValues
        }

        // Convert each proportion to angle
        val sweepAngles = proportions.map {
            360 * it / 100
        }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .border(
                        0.6.dp, Color(0xFFF7F7F7),
                        RoundedCornerShape(10.dp)
                    )
                    .background(
                        Color(0xFFFFFFFF),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(5.dp)
                    .fillMaxWidth()){
                Spacer(modifier = Modifier.size(15.dp))
Column( horizontalAlignment = Alignment.CenterHorizontally){
    Text(text = "Hotels", fontWeight = FontWeight.W400, fontSize = 16.sp, color = GlobalStrings.AdminColorMain)
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()){
        Canvas(
            modifier = Modifier
                .size(size = 60.dp)
        ) {
            var startAngle = -90f

            for (i in values.indices) {
                drawArc(
                    color = colors[i],
                    startAngle = startAngle,
                    sweepAngle = sweepAngles[i],
                    useCenter = false,
                    style = Stroke(width = 30f, cap = StrokeCap.Butt)
                )
                startAngle += sweepAngles[i]
            }
        }
        Column {
            for (i in values.indices) {
                DisplayLegend(color = colors[i], legend = legend[i])
            }
        }
    }



}

        }
    }
    // GET Hotels Function
    fun getHotels(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/hotels/getHotels"
        val progressDialog = ProgressDialog(context)
        val params = JSONObject()

        progressDialog.setTitle("Loading Hotels...")
        progressDialog.show()
        if(!isInternetAvailable(context)){
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        else{
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, params,
                { response ->
                    Hotels.clear()
                    Log.d("HAPP",response.toString())
                    var hotels  = response.getJSONArray("hotels")
                    for(i in 0 until hotels.length()){
                        try{
                            var hotel = hotels.getJSONObject(i)
                            var hotelcity = hotel.getString("city")
                            Hotels.add(hotelcity)
                            if(hotelcity.toLowerCase()=="karachi"){
                                karachihotel.value= karachihotel.value+1
                            }
                            else if(hotelcity.toLowerCase()=="lahore"){
                                lahoreHotel.value= lahoreHotel.value+1
                            }
                            else if(hotelcity.toLowerCase()=="islamabad"){
                                islamabadHotel.value= islamabadHotel.value+1
                            }
                        }
                        catch (e:Exception){
                            e.printStackTrace()
                        }

                    }

                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS",error.toString())
                    Hotels.clear()
                    progressDialog.dismiss()
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

    // GET USERS Function
    fun GetUsers(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/users/getUsers"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Users...")
        progressDialog.show()
        if (!isInternetAvailable(context)) {
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        } else {
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, ViewUsers.params,
                { response ->
                    Log.d("HAPP",response.toString())
                    Users.clear()
                    var users = response.getJSONArray("users")
                    for (i in 0 until users.length()) {
                        var user = users.getJSONObject(i)

                        var userCategory = user.getString("userCategory")

                        Users.add(userCategory)
                        if (userCategory.toLowerCase()=="customer"){
                            customers.value= customers.value+1
                        }
                        else if(userCategory.lowercase()=="owner"){
                            owners.value= owners.value+1
                        }
                        else{
                            staff.value= staff.value+1
                        }
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Users.clear()
                    Toast
                        .makeText(
                            context,
                            "Cannot fetch Users.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    progressDialog.dismiss()
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