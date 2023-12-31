package com.example.adminoffice.ui.theme.Customer.Bookings



import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
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
import com.example.adminoffice.ui.theme.Customer.CartFunctions.Services
import com.example.adminoffice.ui.theme.Customer.CartFunctions.getRoomsIdinCart
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Booking
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
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
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.createPaymentMethod
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import java.util.UUID


import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
data class ExtendBookingCustomer(
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
    var ClientSecret = mutableStateOf("")
    val datecorrect= mutableStateOf(true)

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        PaymentConfiguration.init(context, "pk_test_51NAwR3IoGI30N9jFX4aSt1YTus7cMnAgtRDxZJjI9zsboH5zlyyhkm2ggxJolNaaqX2FwYAJw3lboYtjnNiEPZ3G00b7f4qQ0X")
        val DaysBetween = remember {
            mutableStateOf(0)
        }
        val year = remember {
            mutableStateOf("")
        }
        val datee = remember {
            mutableStateOf("")
        }
        val month = remember {
            mutableStateOf("")
        }
        val cvc = remember {
            mutableStateOf("")
        }
        val cardNumber = remember {
            mutableStateOf("")
        }
        val addi = remember {
            mutableStateOf(0)
        }
        // Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        mCalendar.time = Date()
        val eDate = remember { mutableStateOf("") }
//        // Create mutableState to hold minDate for eDatePickerDialog
//        var minDate by remember { mutableStateOf(mCalendar.timeInMillis) }
//
//        minDate = mCalendar.apply {
//            set(Calendar.YEAR, booking.checkOutDate.substring(6,10).toInt())
//            set(Calendar.MONTH, booking.checkOutDate.substring(0,2).toInt())
//            set(Calendar.DAY_OF_MONTH, booking.checkOutDate.substring(3,5).toInt())
//        }.timeInMillis
        val eDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                // Update minDate for eDatePickerDialog

                eDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
                var abc = ""
                var date = ""
                try{
                    val inputDateStr = eDate.value
                    Log.d("AAAAAAAAAAAEDATE",eDate.value.toString())
                    Log.d("AAAAAAAAAAAEDATE",mYear.toString())
                    Log.d("AAAAAAAAAAAEDATE",mDay.toString())
                    Log.d("AAAAAAAAAAAEDATE",mMonth.toString())
                    // Replace '/' with '-'
                    val formattedDate = inputDateStr.replace("/", "-")
                    Log.d("AAAAAAAAAAAEDATE",formattedDate.toString())
                    Log.d("AAAAAAAAAAAEDATE",formattedDate.length.toString())
                    // Define the two dates as strings
                    abc = booking.checkOut.substring(0,10)
                    if(formattedDate.length==10){
                        date = formattedDate.substring(6,10)+"-"+formattedDate.substring(3,5)+"-"+formattedDate.substring(0,2)
                    }
                    else{
                        val parts = formattedDate.split("-")
                        if(parts[2].length==4){
                            date=parts[2]+"-"
                        }
                        if(parts[1].length==1){
                            date=date+"0"+parts[1]+"-"
                        }
                        else{
                            date=date+parts[1]+"-"
                        }
                        if(parts[0].length==1){
                            date=date+"0"+parts[0]
                        }
                        else{
                            date=date+parts[0]
                        }
                        Log.d("AAAAAAAAAAAEDATE",date.toString())
                        //date = formattedDate.substring(5,9)+"-"+formattedDate.substring(2,4)+"-0"+formattedDate.substring(0,1)
                    }
                    Log.d("AAAAAAAAAAAEDATEggg",daysBetweenDates(abc,date,context).toString())
                    Log.d("AAAAAAAAAAAEDATE1",abc.toString())
                    Log.d("AAAAAAAAAAAEDATE2",date.toString())
                    DaysBetween.value = daysBetweenDates(abc,date,context).toInt()
//                        addi.value = DaysBetween.value*booking.rooms[0].price
                    Log.d("AAAAAAAAAAAEDATE2","HERECode")
                    for(b in booking.rooms){
                        addi.value= addi.value+ b.price*DaysBetween.value

                    }
                    Log.d("AAAAAAAAAAAEDATE2","HERECode22")
                    if(!datecorrect.value){
                        addi.value=0
                    }
                    Log.d("AAAAAAAAAAAEDATE2","HERECode44")
                    datee.value = date
                }
                catch(e:Exception){
                    e.printStackTrace()
                }

            }, mYear, mMonth, mDay
        )
//        // Set maxDate for the eDatePickerDialog initially
//        eDatePickerDialog.datePicker.minDate = minDate
        val reason = remember {
            mutableStateOf("")
        }
        var isErrorreason by remember { mutableStateOf(false) }
        var isErrorIBAN by remember { mutableStateOf(false) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
                Column(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            top = 0.dp,
                            end = 10.dp,
                            bottom = 0.dp
                        )
                        .verticalScroll(
                            rememberScrollState()
                        ),
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp)){
                        Image(
                            painter = rememberAsyncImagePainter(if(booking.customer.userid.profilePicture=="user"){"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKSLF2KD0x9KU5CwmXdqJjrphNQNmJgqzjPQ&usqp=CAU"}else{booking.customer.userid.profilePicture}),
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
                        Text(text = "Old Booking Summary", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = GlobalStrings.CustomerColorMain)
                    }
                    Column(modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = GlobalStrings.CustomerColorMain,
                            shape = RoundedCornerShape(10.dp)
                        )) {
                        Column(modifier = Modifier
                            .padding(5.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "Hotel: ", color = Color.White)
                                        Text(text = booking.hotelName, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 16.sp)
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(text = "Room: ", color = Color.White)
                                    for(room in booking.rooms){
                                        Text(text = room.roomNumber+" "+room.type, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 16.sp)
                                    }
                                }
                                Image(
                                    painter = rememberAsyncImagePainter(booking.rooms[0].images[0]),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(horizontal = 10.dp)
                                        .clip(RoundedCornerShape((CornerSize(20.dp))))
                                )
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                Column {
                                    Text(text = "Check In", fontSize = 14.sp, fontWeight = FontWeight.Light, color = Color.White)
                                    Text(text = convertDateFormat(booking.checkIn), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "Check Out", fontSize = 14.sp, fontWeight = FontWeight.Light, color = Color.White)
                                    Text(text = convertDateFormat(booking.checkOut), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }


                            Text(text = "Guests", fontSize = 18.sp, fontWeight = FontWeight.W800, color = Color.White)
                            Text(text = booking.adults.toString()+"x"+" Adults", fontSize = 14.sp, fontWeight = FontWeight.W800, color = Color.White)
                            Text(text = booking.childern.toString()+"x"+" Children", fontSize = 14.sp, fontWeight = FontWeight.W800, color = Color.White)
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "Room Details", fontSize = 18.sp, fontWeight = FontWeight.W800, color = Color.White)
                            for(room in booking.rooms){
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                    Row(verticalAlignment = Alignment.Bottom){
                                        Text(text = room.roomNumber, fontSize = 20.sp, fontWeight = FontWeight.W800, color = Color.White)
                                        Text(text = " ("+room.type+")", fontSize = 12.sp, fontWeight = FontWeight.Light, color = Color.White)
                                    }
                                    Text(text = room.price.toString(), fontSize = 12.sp, fontWeight = FontWeight.Light, color = Color.White)
                                }
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                Text(text = booking.roomCharges.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                Text(text = "Services", fontSize = 14.sp, fontWeight = FontWeight.Light, color = Color.White)
                                Text(text = booking.serviceCharges.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .background(
                                    color = GlobalStrings.CustomerColorMain,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .height(40.dp),
                                horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                Text(text = "Total Bill", fontSize = 14.sp, fontWeight = FontWeight.Light, color = Color.White)
                                Text(text = (booking.roomCharges+booking.serviceCharges).toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
                        value = booking.checkOut.substring(0,10),
                        onValueChange = {},
                        readOnly = true,
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.calendar), contentDescription = "add", tint = GlobalStrings.CustomerColorMain)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                            .padding(16.dp)
                            .clickable { eDatePickerDialog.show() }
                    ) {
                        Row {
                            Icon(painterResource(id = R.drawable.calendar), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint=GlobalStrings.CustomerColorMain)
                            Text(text = if(eDate.value==""){
                                "Select New Check Out Date"
                            }
                            else{
                                val formattedDate = eDate.value.replace("/", "-")
                                if(formattedDate.length==10){
                                    formattedDate.substring(6,10)+"-"+formattedDate.substring(3,5)+"-"+formattedDate.substring(0,2)
                                }
                                else{
                                    datee.value
                                }
//                                formattedDate.substring(6,10)+"-"+formattedDate.substring(3,5)+"-"+formattedDate.substring(0,2)

                            },
                                color =  if(eDate.value==""){
                                    Color.Gray
                                }
                                else{
                                    Color.Black
                                }
                            )
                        }
                    }

                    if (!datecorrect.value) {
                        Text(
                            text = "You have selected a date which is before your last check out",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(text = "Booking ID: ", fontSize = 14.sp, fontWeight = FontWeight.Light)
                        Text(text = booking._id, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Column {
                            Text(text = "Check In", fontSize = 14.sp, fontWeight = FontWeight.Light, color = GlobalStrings.CustomerColorMain)
                            Text(text = convertDateFormat(booking.checkIn), fontSize = 14.sp, fontWeight = FontWeight.Bold, )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Check Out", fontSize = 14.sp, fontWeight = FontWeight.Light,color = GlobalStrings.CustomerColorMain)
                            Text(text = eDate.value, fontSize = 14.sp, fontWeight = FontWeight.Bold,)
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(text = booking.hotelName, fontSize = 18.sp, fontWeight = FontWeight.Bold,color = GlobalStrings.CustomerColorMain)
                    }
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(text = "Invoice Details", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                    }
                    Divider(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Booked Rooms", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                        Text(text = "Old Price", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                        Text(text = "Additional Price", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                    }
                    for(room in booking.rooms){
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                            Text(text = room.type, fontSize = 14.sp, fontWeight = FontWeight.Light,)
                            Text(text = room.price.toString(), fontSize = 14.sp, fontWeight = FontWeight.Light,)
                            Text(text = (room.price*DaysBetween.value).toString(), fontSize = 14.sp, fontWeight = FontWeight.Light,)
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Grand Total", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                        Text(text = addi.value.toString(), fontSize = 14.sp, fontWeight = FontWeight.Light,)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color.Red,
                            placeholderColor = Color.Gray,
                            disabledPlaceholderColor = Color.Gray
                        ),
                        value = cardNumber.value,
                        onValueChange = {
                            if(cardNumber.value.length<16)
                                cardNumber.value =it
                        },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.card), contentDescription = "person", tint = GlobalStrings.CustomerColorMain)
                        },
                        label = {
                            Text(text = "Card Number", color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Enter Card Number")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        singleLine = true,
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
                        value = cvc.value,
                        onValueChange = {
                            if(cvc.value.length<3)
                                cvc.value =it
                        },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.card), contentDescription = "person", tint = GlobalStrings.CustomerColorMain)
                        },
                        label = {
                            Text(text = "CVC", color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Enter CVC")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
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
                        value = month.value,
                        onValueChange = {
                            if(month.value.length<2)
                                month.value =it
                        },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.calendar), contentDescription = "person", tint = GlobalStrings.CustomerColorMain)
                        },
                        label = {
                            Text(text = "Month", color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Enter Expiry Month")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
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
                        value = year.value,
                        onValueChange = {
                            if(year.value.length<4)
                                year.value =it
                        },
                        leadingIcon = {
                            Icon(painterResource(id = R.drawable.calendar), contentDescription = "person", tint = GlobalStrings.CustomerColorMain)
                        },
                        label = {
                            Text(text = "Year", color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Enter Expiry Year")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier.clickable {
                        if(eDate.value!=""){
                            scope.launch {
                                if(datecorrect.value){
                                        getIntentStripe(context = context, amount = addi.value.toInt(), hotelid = booking.hotel.description){
                                            // Create a Stripe instance

                                            if(it){
                                                scope.launch {
                                                    val stripe = Stripe(context, PaymentConfiguration.getInstance(context).publishableKey)
                                                    val params = PaymentMethodCreateParams.create(
                                                        card = PaymentMethodCreateParams.Card(
                                                            number = cardNumber.value,expiryMonth = month.value.toInt(),expiryYear = year.value.toInt(),
                                                            cvc = cvc.value.toString(), token = null,attribution = null)
                                                    )
                                                    try {
                                                        val paymentMethod = stripe.createPaymentMethod(params)
                                                        // Handle successful creation of PaymentMethod
                                                        val paymentMethodId = paymentMethod.id
                                                        Log.d("KKKKKKKpaymentid",paymentMethodId.toString())
                                                        val paymentIntentResult = stripe.confirmPayment(context as ComponentActivity, ConfirmPaymentIntentParams.createWithPaymentMethodId(
                                                            paymentMethodId.toString(),
                                                            Services.ClientSecret.value // ID obtained from your server
                                                        ))
                                                        Log.d("KKKKKKKpaymentintent",paymentIntentResult.toString())
                                                        ExtendBookingInSystem(context=context,id=booking._id, roomCharges=addi.value, checkOutDate=eDate.value){
                                                            navigator.pop()

                                                        }

                                                        // Now you can use this PaymentMethod for payments
                                                    } catch (e: Exception) {
                                                        // Handle error
                                                        Log.d("KKKKKKK","paymentMethodId".toString())
                                                    }
                                                }

                                            }
                                        }

                                }

                            }
                        }
                    }
                        .fillMaxWidth().background(GlobalStrings.CustomerColorMain,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(0.dp, 10.dp)
                        .size(400.dp, 25.dp), contentAlignment = Alignment.Center){
                        Text(text = "Proceed to Pay Rs.${addi.value}", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)

                    }

                }
    }

    suspend fun getIntentStripe(context: Context,amount:Int,hotelid: String,callback: (Boolean) -> Unit) {
        val url = "${GlobalStrings.baseURL}customer/payments/create-payment-intent"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()

        // Request parameters
        val params = JSONObject()
        params.put("hotelid", hotelid)
        params.put("amount", amount)
        Log.d("Register", params.toString())
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
                    // Handle successful login response
                    Log.d("Register", response.toString())
                    progressDialog.dismiss()
                    var ClientS = response.getString("clientSecret")
                    ClientSecret.value = ClientS

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("Register", error.toString())
                    Log.e("Register", error.networkResponse.statusCode.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "An Error Occurred while adding the User. Please check your Parameters.",
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
    fun ExtendBookingInSystem(context: Context,id:String, roomCharges: Int, checkOutDate: String,
                              callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}customer/bookings/extendBooking/${id}"
        // Define the input date format
        val inputDateFormat = SimpleDateFormat("dd/M/yyyy")

        // Parse the input date
        val inputDate = inputDateFormat.parse(checkOutDate)

        // Add two days to the input date
        val calendar = Calendar.getInstance()
        calendar.time = inputDate
        calendar.add(Calendar.DAY_OF_MONTH, 2)
        val modifiedDate = calendar.time

        // Define the output date format
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        outputDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        // Format the modified date to ISO 8601 format
        val isoDateString = outputDateFormat.format(modifiedDate)


        // Request parameters
        val params = JSONObject()
        params.put("roomCharges", roomCharges)
        params.put("checkOutDate", isoDateString)
        Log.d("ASDWFVSA", params.toString())
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
                            "Booking is Extended.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
                { error ->
                    // Handle error response
                    Log.e("ASDWFVSA", error.toString())
                    Log.e("ASDWFVSA", error.networkResponse.statusCode.toString())
                    progressDialog.dismiss()
//                    Toast
//                        .makeText(
//                            context,
//                            "There is some error.",
//                            Toast.LENGTH_SHORT
//                        )
//                        .show()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun daysBetweenDates(startDateStr: String, endDateStr: String,context: Context): Long {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startDate = LocalDate.parse(startDateStr, formatter)
        val endDate = LocalDate.parse(endDateStr, formatter)
        if(startDate.isAfter(endDate)){
//            Toast
//                .makeText(
//                    context,
//                    "You have selected a date which is before your last check out",
//                    Toast.LENGTH_SHORT
//                )
//                .show()
            datecorrect.value = false
        }
        else{
            datecorrect.value=true
        }
        return ChronoUnit.DAYS.between(startDate, endDate)
    }
}