package com.example.adminoffice.ui.theme.Utils.Screens.Bookings


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
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Booking
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
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
import com.example.adminoffice.ui.theme.Utils.convertDateFormat
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.isValidDescription
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

data class CancelBooking
    (
    val booking: Booking
)
    : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var imageURL = ""
    var message = ""

    var refundAmount = mutableStateOf(0)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val IBAN = remember {
            mutableStateOf("")
        }
        val reason = remember {
            mutableStateOf("")
        }
        var isErrorreason by remember { mutableStateOf(false) }
        var isErrorIBAN by remember { mutableStateOf(false) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        val inputDateStr = currentDate.toString()
        Log.d("KKKKKKK1",inputDateStr)
        // Replace '/' with '-'
        val formattedDate = inputDateStr.replace("/", "-")
        // Define the two dates as strings
        val dateStr1 = convertion(convertDateFormat(booking.checkOut))
        val dateStr2 = convertion(formattedDate)
        Log.d("KKKKKKK",dateStr1)
        Log.d("KKKKKKK",dateStr2)

        // Define date format
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        try {
            // Define the input date format
            val inputDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX yyyy", Locale.ENGLISH)

            // Parse the input date string
            val date = inputDateFormat.parse(inputDateStr)

            // Define the output date format
            val outputDateFormat = SimpleDateFormat("yyyy-MM-dd")

            // Format the date to the desired format
            val formattedDate = outputDateFormat.format(date)
            // Parse the date strings into Date objects
            val date1 = dateFormat.parse(dateStr1)
            val date2 = dateFormat.parse(formattedDate)

            // Convert Date objects to Calendar objects
            val calendar1 = Calendar.getInstance()
            calendar1.time = date1
            val calendar2 = Calendar.getInstance()
            calendar2.time = date2

            // Calculate the number of days between the two dates
            val millisecondsPerDay = 24 * 60 * 60 * 1000
            val daysBetween =  Math.abs(((calendar2.timeInMillis - calendar1.timeInMillis) / millisecondsPerDay)/100)
            Log.d("KKKKKKKK",daysBetween.toString())
//            if(daysBetween.toInt() < booking.hotel.refundPolicy.refunds[0].days){
//                var charges = booking.roomCharges+booking.serviceCharges
//                refundAmount = charges*1
//            }
//            else if(daysBetween.toInt() < booking.hotel.refundPolicy.refunds[1].days){
//                var charges = booking.roomCharges+booking.serviceCharges
//                refundAmount = charges*0.75.toInt()
//            }
//            else if(daysBetween.toInt() < booking.hotel.refundPolicy.refunds[2].days){
//                var charges = booking.roomCharges+booking.serviceCharges
//                refundAmount = charges*0.5.toInt()
//            }
//            else if(daysBetween.toInt() < booking.hotel.refundPolicy.refunds[3].days){
//                var charges = booking.roomCharges+booking.serviceCharges
//                refundAmount = charges*0.25.toInt()
//            }

        } catch (e: Exception) {
            println("Invalid date format")
        }
        ModalNavigationDrawer(
            drawerContent = {

                ModalDrawerSheet {
                    Logo(scope = scope, drawerState = drawerState)
                    Menu().forEachIndexed{
                            index, data ->
                        NavigationDrawerItem(
                            modifier = Modifier.height(45.dp),
                            label = { Header(first = data.first, second = data.second) },
                            selected = selectedItem==index,
                            onClick = {
                                selectedItem=index
                                selectedSubItem = -1
                                if(selectedItem==0){
                                    scope.launch {
                                        drawerState.close()
                                        navigator.pop()
                                    }

                                }
                                else if(selectedItem==10){
                                    scope.launch {
                                        drawerState.close()
                                        navigator.replace(Chat)
                                    }

                                }
                            })
                        if (selectedItem == index) {
                            val subMenuItems = data.third
                            Column {
                                subMenuItems.forEachIndexed { index, subItem ->
                                    NavigationDrawerItem(
                                        modifier = Modifier.height(45.dp),
                                        label = {
                                            SubHeader(subItem=subItem)
                                        },
                                        selected = selectedSubItem == index,
                                        onClick = {
                                            //onSubItemClick()
                                            scope.launch {
                                                drawerState.close()
                                                navigator.pop()
                                                if (selectedItem == 1) {
                                                    if (index == 1) {
                                                        navigator.replace(ViewUsers)
                                                    }
                                                    if (index == 0) {
                                                        navigator.replace(Home)
                                                    }
                                                } else if (selectedItem == 2) {
                                                    if (index == 1) {
                                                        navigator.replace(ViewServiceCategory)
                                                    }
                                                    if (index == 0) {
                                                        navigator.replace(AddServiceCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddService)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewService)
                                                    }
                                                } else if (selectedItem == 3) {
                                                    if (index == 0) {
                                                        navigator.replace(AddHotel)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewHotel)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddRoom)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewRoom)
                                                    }
                                                } else if (selectedItem == 4) {
                                                    if (index == 0) {
                                                        navigator.replace(AddInventoryCategory)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewInventoryCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddInventory)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewInventory)
                                                    }
                                                }
                                                else if (selectedItem == 5) {
                                                    if (index == 0) {
                                                        navigator.replace(AddCoupon)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewCoupons)
                                                    }
                                                }else if (selectedItem == 6) {
                                                    if (index == 0) {
                                                        navigator.replace(AddBooking)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewBookings)
                                                    }
                                                } else if (selectedItem == 7) {
                                                    if (index == 0) {
                                                        navigator.replace(ViewPayments)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewRefunds)
                                                    }
                                                } else if (selectedItem == 8) {
                                                    if (index == 0) {
                                                        navigator.replace(AddDishCategory)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewDishCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddDish)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewDish)
                                                    }
                                                    if (index == 4) {
                                                        navigator.replace(AddMenu)
                                                    }
                                                    if (index == 5) {
                                                        navigator.replace(ViewMenu)
                                                    }
                                                } else if (selectedItem == 9) {
                                                    if (index == 0) {
                                                        navigator.replace(AddReview)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewReview)
                                                    }
                                                } else if (selectedItem == 11) {
                                                    if (index == 0) {
                                                        navigator.replace(AddRevenue)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewRevenue)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddExpense)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewExpense)
                                                    }
                                                    if (index == 4) {
                                                        navigator.replace(ViewProfit)
                                                    }
                                                }
                                                else if (selectedItem == 12) {
                                                    if (index == 1) {
                                                        navigator.replace(FAQ)
                                                    }
                                                    if (index == 0) {
                                                        navigator.push(AboutUs)
                                                    }
                                                    if (index == 2) {
                                                        navigator.push(Policy)
                                                    }
                                                }
                                            }


                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            },
            drawerState= drawerState,
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

                Column(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            top = 70.dp,
                            end = 10.dp,
                            bottom = 0.dp
                        )
                        .verticalScroll(
                            rememberScrollState()
                        ),
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp)){
                        Image(
                            painter = rememberAsyncImagePainter(booking.customer.userid.profilePicture),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape((CornerSize(20.dp))))
                        )
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = booking.customer.userid.firstName+" "+booking.customer.userid.lastName, fontSize = 18.sp, fontWeight = FontWeight.W800)
                            Text(text = booking.customer.userid.email,fontSize = 12.sp)
                            Text(text = booking.customer.userid.contactNo,fontSize = 12.sp)

                        }
                    }
                    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                        Text(text = "Refund", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Hotel: ")
                                Text(text = booking.hotelName, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Room: ")
                            for(room in booking.rooms){
                                Text(text = room.roomNumber+" "+room.type, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp)
                            }
                        }
                        Image(
                            painter = rememberAsyncImagePainter(booking.rooms[0].images[0]),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape((CornerSize(20.dp))))
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                        Text(text = "Booking Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Booking Date", fontSize = 14.sp, fontWeight = FontWeight.Light)
                        Text(text = convertDateFormat(booking.checkIn), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Check In", fontSize = 14.sp, fontWeight = FontWeight.Light)
                        Text(text = convertDateFormat(booking.checkIn), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Room", fontSize = 14.sp, fontWeight = FontWeight.Light)
                        Text(text = booking.roomCharges.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Services", fontSize = 14.sp, fontWeight = FontWeight.Light)
                        Text(text = booking.serviceCharges.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                        Text(text = "Refund Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Total Bill", fontSize = 14.sp, fontWeight = FontWeight.Light)
                        Text(text = (booking.roomCharges+booking.serviceCharges).toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Cancellation Fee", fontSize = 14.sp, fontWeight = FontWeight.Light)
                        Text(text = 50.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Divider(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .height(40.dp),
                        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Refund Amount", fontSize = 14.sp, fontWeight = FontWeight.Light, color = Color.White, modifier = Modifier.padding(horizontal = 5.dp))


                        Text(text = "Rs. "+refundAmount.value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 5.dp))
                    }
                    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                        Text(text = "Refund Policy", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Column {
                            Text(text = "Refund Policy Description", fontSize = 10.sp, color = Color.Gray)
                            Text(
                                text = booking.hotel.refundPolicy.description,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                    }
                    for(refu in booking.hotel.refundPolicy.refunds){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/money-removebg-preview.png?alt=media&token=f299db3a-84e0-48a3-9ee2-90d6db603b01"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(30.dp)

                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Column {
                                Text(text = "${refu.percentage}% Refund", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = refu.days.toString()+" "+"Days",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
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
                        value = IBAN.value,
                        onValueChange = {
                            if (it.length <= 34)
                                IBAN.value=it
                        },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.card), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                        },
                        label = {
                            Text(text = "IBAN Number",color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Enter IBAN Number")
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = true
                    )
                    if (isErrorIBAN) {
                        Text(
                            text = "Please provide a valid IBAN Number",
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
                        value = reason.value,
                        onValueChange = {
                            if (it.length <= 300)
                                reason.value=it
                        },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                        },
                        label = {
                            Text(text = "Cancellation Reason",color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Enter Cancellation Reason")
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = true
                    )
                    if (isErrorreason) {
                        Text(
                            text = "Please provide a Cancellation Reason which is more than 30 characters",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                    OutlinedButton(
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp)
                            .size(400.dp, 45.dp),
                        onClick = {
                            isErrorIBAN = IBAN.value==""
                            isErrorreason = !isValidDescription(reason.value)
                            if(!isErrorIBAN && !isErrorreason){
                                Log.d("ASDWFVSA","Aaasdasdas")
                                CancelBookingInSystem(context= context,id=booking._id, status = "cancelled", cancellationReason= reason.value,iban=IBAN.value,amount=refundAmount.value.toString()){
                                    if(it){
                                        navigator.replace(ViewBookings)
                                    }
                                }
                            }

                        },
                        shape = RoundedCornerShape(15.dp),
                    ) {
                        Text(text = "Cancel Booking", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                    }

                }
            }
        }
        getRefund(context,booking._id,{

        })
    }
    fun convertion(date:String): String {
        val inputDateStr = date

        // Split the input date using the hyphen separator
        val parts = inputDateStr.split("-")

        if (parts.size == 3) {
            // Reconstruct the date in the desired format
            val formattedDate = "${parts[2]}-${parts[1].padStart(2, '0')}-${parts[0].padStart(2, '0')}"
            println(formattedDate)
            return formattedDate
        } else {
            println("Invalid date format: $inputDateStr")
            return inputDateStr
        }

    }

    // Add Category Function
    fun CancelBookingInSystem(context: Context,id:String, status: String, cancellationReason: String,iban: String,amount: String, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}admin/bookings/updateStatus/${id}"
        // Request parameters
        val params = JSONObject()
        params.put("status", status)
        params.put("cancellationReason", cancellationReason)
        params.put("iban", iban)
        params.put("amount", amount.toInt())
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
                Request.Method.PUT, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("ASDWFVSA", response.toString())

                    progressDialog.dismiss()

                    // Assuming the API returns a JSON object with a field "valid" indicating user validity
                    callback(true)
                    Toast
                        .makeText(
                            context,
                            "Booking is Cancelled.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
                { error ->
                    // Handle error response
                    Log.e("ASDWFVSA", error.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "There is some error.",
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

    // Add Category Function
    fun getRefund(context: Context,id:String, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}admin/bookings/refundBooking/${id}"
        // Request parameters
        val params = JSONObject()
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
                Request.Method.GET, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("ASDWFVSA", response.toString())
                    refundAmount.value = response.getJSONObject("refund").getInt("amount")
                    progressDialog.dismiss()

                    // Assuming the API returns a JSON object with a field "valid" indicating user validity
                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("ASDWFVSA", error.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "There is some error.",
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