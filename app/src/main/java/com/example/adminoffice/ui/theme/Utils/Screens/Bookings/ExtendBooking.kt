package com.example.adminoffice.ui.theme.Utils.Screens.Bookings


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
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Booking
import com.example.adminoffice.ui.theme.Utils.DrawerUni
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
import com.example.adminoffice.ui.theme.Utils.getRoleFromLocalStorage
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
import java.util.TimeZone
import java.util.UUID


import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
data class ExtendBooking
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
    var ClientSecret = mutableStateOf("")
    val datecorrect= mutableStateOf(true)
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        val DaysBetween = remember {
            mutableStateOf(0)
        }
        val added = remember {
            mutableStateOf(0)
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
        val eDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                eDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
            }, mYear, mMonth, mDay
        )
        val reason = remember {
            mutableStateOf("")
        }
        var isErrorreason by remember { mutableStateOf(false) }
        var isErrorIBAN by remember { mutableStateOf(false) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        ModalNavigationDrawer(
            drawerContent = {

                DrawerUni(scope,drawerState)
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
                        Text(text = "Old Booking Summary", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = GlobalStrings.AdminColorMain)
                    }
                    Column(modifier = Modifier
                        .padding(10.dp)
                        .background(
                            color = GlobalStrings.AdminColorMain,
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
                                    color = GlobalStrings.AdminColorMain,
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
                            Icon(painterResource(id = R.drawable.calendar), contentDescription = "add", tint = GlobalStrings.AdminColorMain)
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
                            Icon(painterResource(id = R.drawable.calendar), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= GlobalStrings.AdminColorMain)
                            Text(text = if(eDate.value==""){
                                "Select New Check Out Date"
                            }
                            else{
                                val formattedDate = eDate.value.replace("/", "-")
                                formattedDate.substring(6,10)+"-"+formattedDate.substring(3,5)+"-"+formattedDate.substring(0,2)

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
                    try{
                        val inputDateStr = eDate.value
                        Log.d("AAAAAAAAAAAEDATE",eDate.value.toString())
                        Log.d("AAAAAAAAAAAEDATE",mYear.toString())
                        Log.d("AAAAAAAAAAAEDATE",mDay.toString())
                        Log.d("AAAAAAAAAAAEDATE",mMonth.toString())
                        // Replace '/' with '-'
                        val formattedDate = inputDateStr.replace("/", "-")
                        Log.d("AAAAAAAAAAAEDATE",formattedDate.toString())
                        // Define the two dates as strings
                        val abc = booking.checkOut.substring(0,10)
                        val date = formattedDate.substring(6,10)+"-"+formattedDate.substring(3,5)+"-"+formattedDate.substring(0,2)
                        Log.d("AAAAAAAAAAAEDATEggg",daysBetweenDates(abc,date,context).toString())
                        Log.d("AAAAAAAAAAAEDATE1",abc.toString())
                        Log.d("AAAAAAAAAAAEDATE2",date.toString())
                        DaysBetween.value = daysBetweenDates(abc,date,context).toInt()
                        addi.value = DaysBetween.value*booking.rooms[0].price
                        if(!datecorrect.value){
                            addi.value=0
                        }
                    }
                    catch(e:Exception){
                        e.printStackTrace()
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
                            Text(text = "Check In", fontSize = 14.sp, fontWeight = FontWeight.Light, color = GlobalStrings.AdminColorMain)
                            Text(text = convertDateFormat(booking.checkIn), fontSize = 14.sp, fontWeight = FontWeight.Bold, )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Check Out", fontSize = 14.sp, fontWeight = FontWeight.Light,color = GlobalStrings.AdminColorMain)
                            Text(text = eDate.value, fontSize = 14.sp, fontWeight = FontWeight.Bold,)
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(text = booking.hotelName, fontSize = 18.sp, fontWeight = FontWeight.Bold,color = GlobalStrings.AdminColorMain)
                    }
                    Row(modifier = Modifier.fillMaxWidth()){
                        Text(text = "Invoice Details", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                    }
                    Divider(Modifier.fillMaxWidth().padding(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Booked Rooms", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                        Text(text = "Old Price", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                        Text(text = "Additional Price", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                    }
                    for(room in booking.rooms){
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                            Text(text = room.type, fontSize = 14.sp, fontWeight = FontWeight.Light,)
                            Text(text = room.price.toString(), fontSize = 14.sp, fontWeight = FontWeight.Light,)
                            Text(text = added.value.toString(), fontSize = 14.sp, fontWeight = FontWeight.Light,)
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = "Grand Total", fontSize = 14.sp, fontWeight = FontWeight.Light,)
                        Text(text = addi.value.toString(), fontSize = 14.sp, fontWeight = FontWeight.Light,)
                    }
                    OutlinedButton(
                        border = BorderStroke(2.dp, GlobalStrings.AdminColorMain),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GlobalStrings.AdminColorMain,
                            contentColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 10.dp)
                            .size(400.dp, 45.dp),
                        onClick = {
                                if(eDate.value!=""){
                                    scope.launch {
                                        if(datecorrect.value){
                                            getIntentStripe(context = context, amount = addi.value.toInt(), hotelid = booking.hotel._id){

                                                if(it){
                                                    ExtendBookingInSystem(context=context,id=booking._id, roomCharges=addi.value, checkOutDate=eDate.value){
                                                        if(it){
                                                            navigator.replace(ViewBookings)
                                                        }
                                                    }
                                                }
                                            }

                                        }

                                    }
                                }

                        },
                        shape = RoundedCornerShape(15.dp),
                    ) {
                        Text(text = "Proceed to Pay Rs.${addi.value}", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                    }

                }
            }
        }
    }

    suspend fun getIntentStripe(context: Context,amount:Int,hotelid: String,callback: (Boolean) -> Unit) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/payments/create-payment-intent"
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
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/bookings/extendBooking/${id}"
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