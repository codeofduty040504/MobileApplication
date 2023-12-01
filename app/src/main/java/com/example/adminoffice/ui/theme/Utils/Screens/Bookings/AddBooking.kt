package com.example.adminoffice.ui.theme.Utils.Screens.Bookings

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Hotel
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.HotelOwner
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.ServiceCategory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Userid
import com.example.adminoffice.ui.theme.Utils.DataClasses.Inventories.Inventory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Inventories.InventoryCategory
import com.example.adminoffice.ui.theme.Utils.DataClasses.RoomBook
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.MaskVisualTransformation
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.AddHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.ViewHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.AddRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.EditServce
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.isValidDescription
import com.example.adminoffice.ui.theme.Utils.isValidName
import com.example.adminoffice.ui.theme.Utils.isValidPhoneNumber
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID
import android.app.DatePickerDialog
import android.service.autofill.UserData
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.ui.text.style.TextAlign
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Room
import com.example.adminoffice.ui.theme.Utils.DrawerUni
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.AddExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewProfit
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
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.ViewRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.convertDateFormat
import com.example.adminoffice.ui.theme.Utils.getRoleFromLocalStorage
import java.util.Calendar
import java.util.Date
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.createPaymentMethod
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import java.text.SimpleDateFormat
import java.util.TimeZone


object AddBooking  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    data class RoomTypeClass(
        val name: String,
        val adults: Int,
        val children: Int
    )
    var usersData = mutableStateListOf<ViewUsers.TableRow>()
    val params = JSONObject()
    var usersJsonArrayError = mutableStateOf("")
    var ClientSecret = mutableStateOf("")
    var BookingAmount =0

    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var imageURL = mutableStateListOf<String>()
    var Hotels = mutableStateListOf<Hotel>()

    var services = mutableStateListOf<Service>()
    var servicesRoomIn = mutableStateListOf<Service>()
    var servicesHotel = mutableStateListOf<Service>()
    var roomservices= mutableStateListOf<String>()
    var servicesSelected = mutableStateListOf<Service>()
    var servicesSelectedHotel = mutableStateListOf<Service>()
    var inventories = mutableStateListOf<Inventory>()
    var rooms = mutableStateListOf<RoomBook>()
    var customerType = mutableStateListOf<String>("Existing Customer","New Customer")
    var inventoriesSelected = mutableStateListOf<Inventory>()
    var Floors = mutableStateListOf<String>()
    var HotelJsonArrayError = mutableStateOf("")
    var message = ""
    var hotelID = ""
    var modalopen = mutableStateOf(false)
    var modalopenInventory = mutableStateOf(false)
    var current = Service(__v = 0, _id = "", addedByRole = "", createdAt = "", deletedAt = "", description = "", image = "", isDeleted = true, name = "", price = 0, priceRate = "",
        serviceCategory = ServiceCategory(_id = "", __v = 0, createdAt = "", deletedAt = "", image = "", isDeleted = false, title = "", updatedAt = ""), type = "", updatedAt = "",visible = false)
    lateinit var roomselected : RoomBook
    lateinit var customer : ViewUsers.TableRow
    var currentInventory = Inventory(
        __v = 0, _id = "", addedByRole = "", category = "", color = "", createdAt = "", deletedAt = "", description = "", id = 0, image = "", inventoryCategory = InventoryCategory(_id = "", __v = 0, createdAt = "", deletedAt = "", image = "", isDeleted = true, title = "", updatedAt = ""), isDeleted = false, name = "", updatedAt = ""
    )
    var roomCharges =0
    var servicesCharges =0

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        // Initialize Stripe with your publishable key
        PaymentConfiguration.init(context, "pk_test_51NAwR3IoGI30N9jFX4aSt1YTus7cMnAgtRDxZJjI9zsboH5zlyyhkm2ggxJolNaaqX2FwYAJw3lboYtjnNiEPZ3G00b7f4qQ0X")

        var step = remember {
            mutableStateOf(1)
        }
        var selectedImageUris by remember {
            mutableStateOf<List<Uri>>(emptyList())
        }

        var sortOrder by remember { mutableStateOf(SortOrder.Ascending) }

        var isExpandedHotel = remember {
            mutableStateOf(false)
        }
        var isExpandedCustomerType = remember {
            mutableStateOf(false)
        }
        var isExpandedCustomer = remember {
            mutableStateOf(false)
        }
        var isExpandedFloor = remember {
            mutableStateOf(false)
        }
        var isErrorHotel by remember { mutableStateOf(false) }
        var isErrorFloor by remember { mutableStateOf(false) }
        var isErrorAdults by remember { mutableStateOf(false) }
        var isErrorChildren by remember { mutableStateOf(false) }
        var isErrorSize by remember { mutableStateOf(false) }
        var isErrorType by remember { mutableStateOf(false) }
        var isErrorRoomNumber by remember { mutableStateOf(false) }
        var isErrorRoomType by remember { mutableStateOf(false) }
        var isErrorRoomDescription by remember { mutableStateOf(false) }
        var hotelSelect by remember { mutableStateOf("") }
        var roomTypeSelect by remember { mutableStateOf("") }
        var floorSelect by remember { mutableStateOf("") }
        var customerSelect by remember { mutableStateOf("Existing Customer") }
        var customerAlreadySelect by remember { mutableStateOf("") }
        var roomDescription by remember { mutableStateOf("") }


        val adults = remember {
            mutableStateOf("")
        }
        val children = remember {
            mutableStateOf("")
        }

        val email = remember {
            mutableStateOf("")
        }
        val customername = remember {
            mutableStateOf("")
        }
        val customerPhone = remember {
            mutableStateOf("")
        }
        val year = remember {
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
        val mDate = remember { mutableStateOf("") }
        val eDate = remember { mutableStateOf("") }
        val mDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
            }, mYear, mMonth, mDay
        )
        val eDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                eDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
            }, mYear, mMonth, mDay
        )

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
                Box(modifier = Modifier.padding(start = 0.dp, top = 70.dp, end = 0.dp, bottom = 0.dp)){
                    Column {
                        if(step.value==1){
                            Column (Modifier.verticalScroll(rememberScrollState())){
                                Box(modifier = Modifier.fillMaxWidth()){
                                    Row(modifier = Modifier
                                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .padding(10.dp)
                                            .border(
                                                2.dp,
                                                GlobalStrings.AdminColorMain,
                                                shape = RoundedCornerShape(45.dp)
                                            )){
                                            Icon(painterResource(id= R.drawable.house ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                        }
                                        Column {
                                            Text(text = "1.Booking Details", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "General Booking Details", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                        }
                                    }
                                }
                                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                            .padding(16.dp)
                                            .clickable { isExpandedHotel.value = true }
                                    ) {
                                        Row {
                                            Icon(painterResource(id = R.drawable.house), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= GlobalStrings.AdminColorMain)
                                            Text(text = if(hotelSelect==""){
                                                "Select Hotel"
                                            }
                                            else{
                                                hotelSelect
                                            },
                                                color =  if(hotelSelect==""){
                                                    Color.Gray
                                                }
                                                else{
                                                    Color.Black
                                                }
                                            )
                                        }

                                        DropdownMenu(
                                            expanded = isExpandedHotel.value,
                                            onDismissRequest = {isExpandedHotel.value=false },
                                            modifier = Modifier
                                                .size(300.dp, 120.dp)
                                                .fillMaxWidth()
                                        ) {
                                            Hotels.forEach { option ->
                                                DropdownMenuItem(text = { Column{
                                                    Text(text = option.name)
                                                    Text(text = option.location, fontSize = 10.sp)
                                                } } , leadingIcon = {
                                                    Box(){
                                                        Image(
                                                            painter = rememberAsyncImagePainter(option.images[0], contentScale = ContentScale.FillBounds),
                                                            contentDescription = "image",
                                                            modifier = Modifier
                                                                .size(50.dp)
                                                                .clip(
                                                                    RoundedCornerShape(
                                                                        (CornerSize(
                                                                            20.dp
                                                                        ))
                                                                    )
                                                                )
                                                        )
                                                    }
                                                }, onClick = {
                                                    hotelSelect = option.name
                                                    hotelID = option._id
                                                    getRoomByHotel(context= context,hotelid=hotelID)

                                                    isExpandedHotel.value = false
                                                }, modifier = Modifier.padding(5.dp))
                                            }
                                        }
                                    }
                                    if (isErrorHotel) {
                                        Text(
                                            text = "Please select a Hotel",
                                            color = Color.Red,
                                            modifier = Modifier.align(Alignment.Start),
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                            .padding(16.dp)
                                            .clickable { isExpandedFloor.value = true }
                                    ) {
                                        Row {
                                            Icon(painterResource(id = R.drawable.bed), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= GlobalStrings.AdminColorMain)
                                            Text(text = if(floorSelect==""){
                                                "Select Room"
                                            }
                                            else{
                                                floorSelect
                                            },
                                                color =  if(floorSelect==""){
                                                    Color.Gray
                                                }
                                                else{
                                                    Color.Black
                                                }
                                            )
                                        }

                                        DropdownMenu(
                                            expanded = isExpandedFloor.value,
                                            onDismissRequest = {isExpandedFloor.value=false },
                                            modifier = Modifier
                                                .size(300.dp, 120.dp)
                                                .fillMaxWidth()
                                        ) {
                                            rooms.forEach { option ->
                                                DropdownMenuItem(text = { Text(text = option.roomType) }, onClick = {
                                                    floorSelect = option.type
                                                    roomselected= option
                                                    isExpandedFloor.value = false
                                                    servicesRoomIn.clear()
                                                    for(s in services){
                                                        for(rm in roomselected.services){
                                                            if(rm== s._id){
                                                                servicesRoomIn.add(s)
                                                            }
                                                        }
                                                    }
                                                })
                                            }
                                        }
                                    }
                                    if (isErrorFloor) {
                                        Text(
                                            text = "Please select a Room",
                                            color = Color.Red,
                                            modifier = Modifier.align(Alignment.Start),
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                            .padding(16.dp)
                                            .clickable { mDatePickerDialog.show() }
                                    ) {
                                        Row {
                                            Icon(painterResource(id = R.drawable.calendar), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= GlobalStrings.AdminColorMain)
                                            Text(text = if(mDate.value==""){
                                                "Select Check In Date"
                                            }
                                            else{
                                                mDate.value
                                            },
                                                color =  if(mDate.value==""){
                                                    Color.Gray
                                                }
                                                else{
                                                    Color.Black
                                                }
                                            )
                                        }
                                    }
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
                                                "Select Check Out Date"
                                            }
                                            else{
                                                eDate.value
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
                                    Spacer(modifier = Modifier.height(5.dp))
                                    OutlinedTextField(
                                        shape = RoundedCornerShape(5.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = Color.Black,
                                            unfocusedBorderColor = Color.Gray,
                                            errorBorderColor = Color.Red,
                                            placeholderColor = Color.Gray,
                                            disabledPlaceholderColor = Color.Gray
                                        ),
                                        value = adults.value,
                                        onValueChange = {
                                            if(it==""){
                                                adults.value=it
                                            }
                                            else{
                                                if (it.toInt() < roomselected.adults){
                                                    adults.value=it
                                                }
                                                else{
                                                    adults.value= roomselected.adults.toString()
                                                }
                                            }
                                        },
                                        leadingIcon = {
                                            Icon(painterResource(id = R.drawable.man), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                        },
                                        label = {
                                            Text(text = "Adults", color = Color.Gray)
                                        },
                                        placeholder = {
                                            Text(text = "Enter number of Adults")
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Phone
                                        ),
                                        singleLine = true,
                                    )
                                    if (isErrorAdults) {
                                        Text(
                                            text = "Please provide the number of Adults in the Room",
                                            color = Color.Red,
                                            modifier = Modifier.align(Alignment.Start),
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    OutlinedTextField(
                                        shape = RoundedCornerShape(5.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = Color.Black,
                                            unfocusedBorderColor = Color.Gray,
                                            errorBorderColor = Color.Red,
                                            placeholderColor = Color.Gray,
                                            disabledPlaceholderColor = Color.Gray
                                        ),
                                        value = children.value,
                                        onValueChange = {

                                            if(it==""){
                                                children.value=it
                                            }
                                            else{
                                                if (it.toInt() < roomselected.children){
                                                    children.value=it
                                                }
                                                else{
                                                    children.value= roomselected.children.toString()
                                                }
                                            }
                                        },
                                        leadingIcon = {
                                            Icon(painterResource(id = R.drawable.man), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                        },
                                        label = {
                                            Text(text = "Children", color = Color.Gray)
                                        },
                                        placeholder = {
                                            Text(text = "Enter Children")
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Phone
                                        ),
                                        singleLine = true,
                                    )
                                    if (isErrorChildren) {
                                        Text(
                                            text = "Please provide the number of Children in the Room",
                                            color = Color.Red,
                                            modifier = Modifier.align(Alignment.Start),
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                            .padding(16.dp)
                                            .clickable { isExpandedCustomerType.value = true }
                                    ) {
                                        Row {
                                            Icon(painterResource(id = R.drawable.bed), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= GlobalStrings.AdminColorMain)
                                            Text(text = if(customerSelect==""){
                                                "Select Customer Type"
                                            }
                                            else{
                                                customerSelect
                                            },
                                                color =  if(customerSelect==""){
                                                    Color.Gray
                                                }
                                                else{
                                                    Color.Black
                                                }
                                            )
                                        }

                                        DropdownMenu(
                                            expanded = isExpandedCustomerType.value,
                                            onDismissRequest = {isExpandedCustomerType.value=false },
                                            modifier = Modifier
                                                .size(300.dp, 120.dp)
                                                .fillMaxWidth()
                                        ) {
                                            customerType.forEach { option ->
                                                DropdownMenuItem(text = { Text(text = option) }, onClick = {
                                                    customerSelect = option
                                                    isExpandedCustomerType.value = false
                                                })
                                            }
                                        }
                                    }
                                    if(customerSelect=="New Customer"){
                                        Spacer(modifier = Modifier.height(5.dp))
                                        OutlinedTextField(
                                            shape = RoundedCornerShape(5.dp),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                focusedBorderColor = Color.Black,
                                                unfocusedBorderColor = Color.Gray,
                                                errorBorderColor = Color.Red,
                                                placeholderColor = Color.Gray,
                                                disabledPlaceholderColor = Color.Gray
                                            ),
                                            value = customername.value,
                                            onValueChange = {
                                                customername.value = it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.man), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                            },
                                            label = {
                                                Text(text = "Customer Name", color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Enter Customer Name")
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            singleLine = true,
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
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
                                                email.value = it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.email), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                            },
                                            label = {
                                                Text(text = "Customer Email", color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Enter Customer Email")
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Email
                                            ),
                                            singleLine = true,
                                        )

                                        Spacer(modifier = Modifier.height(10.dp))
                                        OutlinedTextField(
                                            shape = RoundedCornerShape(5.dp),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                focusedBorderColor = Color.Black,
                                                unfocusedBorderColor = Color.Gray,
                                                errorBorderColor = Color.Red,
                                                placeholderColor = Color.Gray,
                                                disabledPlaceholderColor = Color.Gray
                                            ),
                                            value = customerPhone.value,
                                            onValueChange = {
                                                if(customerPhone.value.length<11){
                                                    customerPhone.value = it
                                                }
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.mobile), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                            },
                                            label = {
                                                Text(text = "Customer Phone", color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Enter Customer Phone")
                                            },
                                            modifier = Modifier.fillMaxWidth(),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Phone
                                            ),
                                            singleLine = true,
                                        )
                                    }
                                    else{
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(
                                                    0.6.dp,
                                                    Color.Black,
                                                    MaterialTheme.shapes.small
                                                )
                                                .padding(16.dp)
                                                .clickable { isExpandedCustomer.value = true }
                                        ) {
                                            Row {
                                                Icon(painterResource(id = R.drawable.house), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= GlobalStrings.AdminColorMain)
                                                Text(text = if(customerAlreadySelect==""){
                                                    "Select Customer"
                                                }
                                                else{
                                                    customerAlreadySelect
                                                },
                                                    color =  if(customerAlreadySelect==""){
                                                        Color.Gray
                                                    }
                                                    else{
                                                        Color.Black
                                                    }
                                                )
                                            }

                                            DropdownMenu(
                                                expanded = isExpandedCustomer.value,
                                                onDismissRequest = {isExpandedCustomer.value=false },
                                                modifier = Modifier
                                                    .size(300.dp, 120.dp)
                                                    .fillMaxWidth()
                                            ) {
                                                usersData.forEach { option ->
                                                    DropdownMenuItem(text = {Column{
                                                        Text(text = option.firstName+" "+option.lastName)
                                                        Text(text = option.email, fontSize = 10.sp)
                                                    } } , leadingIcon = {
                                                        Box(){
                                                            Image(
                                                                painter = rememberAsyncImagePainter(option.profilePicture, contentScale = ContentScale.FillBounds),
                                                                contentDescription = "image",
                                                                modifier = Modifier
                                                                    .size(50.dp)
                                                                    .clip(
                                                                        RoundedCornerShape(
                                                                            (CornerSize(
                                                                                20.dp
                                                                            ))
                                                                        )
                                                                    )
                                                            )
                                                        }
                                                    }, onClick = {
                                                        customerAlreadySelect =option.firstName+" "+option.lastName
                                                        customer= option

                                                        isExpandedCustomer.value = false
                                                    },modifier=Modifier.padding(5.dp))
                                                }
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
                                        value = roomDescription,
                                        onValueChange = {
                                            if (it.length <= 300)
                                                roomDescription=it
                                        },
                                        leadingIcon = {
                                            Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = GlobalStrings.AdminColorMain)
                                        },
                                        label = {
                                            Text(text = "Booking Description",color = Color.Gray)
                                        },
                                        placeholder = {
                                            Text(text = "Enter Booking Description")
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp),
                                    )
                                    if (isErrorRoomDescription) {
                                        Text(
                                            text = "Description should be more than 30 characters.",
                                            color = Color.Red,
                                            modifier = Modifier.align(Alignment.Start),
                                            fontSize = 11.sp
                                        )
                                    }
                                    Row(modifier= Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End){
                                        Spacer(modifier = Modifier.width(10.dp))
                                        OutlinedButton(onClick = {
//                                            isErrorRoomDescription = !isValidDescription(roomDescription)
//                                            isErrorAdults = adults.value == ""
//                                            isErrorChildren = children.value == ""
//                                            isErrorRoomType = roomTypeSelect == ""
//                                            isErrorPrice = price.value == ""
//                                            isErrorHotel = hotelID == ""
//                                            isErrorFloor = floorSelect == ""
//                                            isErrorRoomNumber = RoomNumber.value == ""
//                                            isErrorSize = size.value == ""

//                                            if(!isErrorRoomDescription
//                                                && !isErrorAdults
//                                                && !isErrorChildren
//                                                && !isErrorRoomType
//                                                && !isErrorPrice
//                                                && !isErrorHotel
//                                                && !isErrorFloor
//                                                && !isErrorRoomNumber
//                                                && !isErrorSize
//                                            ){
//                                                step.value++
//                                            }
                                                                 step.value++
                                        },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color.White,
                                                containerColor = GlobalStrings.AdminColorMain,
                                            ),
                                            border= BorderStroke(1.dp,GlobalStrings.AdminColorMain),
                                            shape = RoundedCornerShape(CornerSize(3.dp))) {
                                            Text(text = "Next")
                                        }
                                    }
                                }
                            }
                        }
                        if(step.value==2){
                            Column(Modifier.verticalScroll(rememberScrollState())){
                                Box(modifier = Modifier.fillMaxWidth()){
                                    Row(modifier = Modifier
                                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .padding(10.dp)
                                            .border(
                                                2.dp,
                                                GlobalStrings.AdminColorMain,
                                                shape = RoundedCornerShape(45.dp)
                                            )){
                                            Icon(painterResource(id= R.drawable.house ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                        }
                                        Column {
                                            Text(text = "2.Service Details", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "Manage Booking Services", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                        }
                                    }
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                    Text(text = "Room Services", fontSize = 16.sp, fontWeight = FontWeight.SemiBold,)
                                }

                                SortableTableRoomServices(
                                    context = context,
                                    rows = servicesRoomIn,
                                    sortOrder = sortOrder,
                                    onSortOrderChange = { newSortOrder ->
                                        sortOrder = newSortOrder
                                    })
                                if(modalopen.value){

                                    CustomProgressDialog(current)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                    Text(text = "Hotel Services", fontSize = 16.sp, fontWeight = FontWeight.SemiBold,)
                                }
                                var servi by remember {
                                    mutableStateOf<List<Service>>(emptyList())
                                }
                                for(h in Hotels){
                                    if(hotelID== h._id){
                                        servi = h.services
                                    }
                                }
                                SortableTable(
                                    context = context,
                                    rows = servicesHotel,
                                    sortOrder = sortOrder,
                                    onSortOrderChange = { newSortOrder ->
                                        sortOrder = newSortOrder
                                    })
                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {

                                        step.value--
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.Black, // Text color
                                            containerColor = Color.White, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,Color.Black),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Previous")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {
                                        var amount=0
                                        roomCharges=0
                                        servicesCharges=0
//                                        for(serv in roomselected){
//                                            roomCharges= roomCharges+serv.
//                                        }
                                        // Replace '/' with '-'
                                        val formattedDate = eDate.value.replace("/", "-")
                                        val formattedDatee = mDate.value.replace("/", "-")
                                        // Define the two dates as strings
                                        val dateStr1 = convertion(formattedDatee)
                                        val dateStr2 = convertion(formattedDate)


                                        // Define date format
                                        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                                            val date1 = dateFormat.parse(dateStr1)
                                            val date2 = dateFormat.parse(dateStr2)

                                            // Convert Date objects to Calendar objects
                                            val calendar1 = Calendar.getInstance()
                                            calendar1.time = date1
                                            val calendar2 = Calendar.getInstance()
                                            calendar2.time = date2

                                            // Calculate the number of days between the two dates
                                            val millisecondsPerDay = 24 * 60 * 60 * 1000
                                            val daysBetween = (calendar2.timeInMillis - calendar1.timeInMillis) / millisecondsPerDay
                                            Log.d("KKKKKKKK",daysBetween.toString())
                                        roomCharges= roomselected.price*daysBetween.toInt()
                                        for(serv in servicesSelectedHotel){
                                            servicesCharges=servicesCharges+serv.price
                                        }
                                        amount= servicesCharges+ roomCharges
                                        BookingAmount=amount
                                       scope.launch {
                                           getIntentStripe(context = context,amount=amount, hotelid = hotelID){it->

                                           }
                                       }
                                        step.value++
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = GlobalStrings.AdminColorMain, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,GlobalStrings.AdminColorMain),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Next")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                        if(step.value==3){
                            Column{
                                Box(modifier = Modifier.fillMaxWidth()){
                                    Row(modifier = Modifier
                                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .padding(10.dp)
                                            .border(
                                                2.dp,
                                                GlobalStrings.AdminColorMain,
                                                shape = RoundedCornerShape(45.dp)
                                            )){
                                            Icon(painterResource(id= R.drawable.house ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                        }
                                        Column {
                                            Text(text = "3.CheckOut Summary", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "Checkout and Booking Details", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                        }
                                    }
                                }
                                Column(modifier = Modifier.fillMaxWidth().padding(10.dp)){
                                    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                                        Text(text = "Bill", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = GlobalStrings.AdminColorMain)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = "Hotel: ")
                                                Text(text = hotelSelect, fontWeight = FontWeight.SemiBold, color = GlobalStrings.AdminColorMain, fontSize = 16.sp)
                                            }
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(text = "Room: ")
                                            Text(text = roomselected.roomType+" "+roomselected.roomNumber, fontWeight = FontWeight.SemiBold, color = GlobalStrings.AdminColorMain, fontSize = 16.sp)

                                        }
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                        Text(text = "Booking Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = GlobalStrings.AdminColorMain)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                        Text(text = "Booking Date", fontSize = 14.sp, fontWeight = FontWeight.Light)
                                        Text(text = eDate.value, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                        Text(text = "Check In", fontSize = 14.sp, fontWeight = FontWeight.Light)
                                        Text(text = mDate.value, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                        Text(text = "Room", fontSize = 14.sp, fontWeight = FontWeight.Light)
                                        Text(text = roomCharges.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                        Text(text = "Services", fontSize = 14.sp, fontWeight = FontWeight.Light)
                                        Text(text = servicesCharges.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                        Text(text = "Refund Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = GlobalStrings.AdminColorMain)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                        Text(text = "Total Bill", fontSize = 14.sp, fontWeight = FontWeight.Light)
                                        Text(text = BookingAmount.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Divider(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 10.dp))
                                    Row(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp)
                                        .background(
                                            color = GlobalStrings.AdminColorMain,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .height(40.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                        Text(text = "Total Bill", fontSize = 14.sp, fontWeight = FontWeight.Light, color = Color.White, modifier = Modifier.padding(horizontal = 5.dp))


                                        Text(text = "Rs. "+BookingAmount.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 5.dp))
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
                                    value = cardNumber.value,
                                    onValueChange = {
                                        if(cardNumber.value.length<16)
                                            cardNumber.value =it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.card), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
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
                                        Icon(painterResource(id = R.drawable.card), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                    },
                                    label = {
                                        Text(text = "CVC", color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter CVC")
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
                                    value = month.value,
                                    onValueChange = {
                                        if(month.value.length<2)
                                            month.value =it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.calendar), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                    },
                                    label = {
                                        Text(text = "Month", color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Expiry Month")
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
                                    value = year.value,
                                    onValueChange = {
                                        if(year.value.length<4)
                                            year.value =it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.man), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                    },
                                    label = {
                                        Text(text = "Year", color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Expiry Year")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    singleLine = true,
                                )
                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {
                                        step.value=2
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.Black, // Text color
                                            containerColor = Color.White, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,Color.Black),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Previous")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {
                                        // Create a Stripe instance
                                        val stripe = Stripe(context, PaymentConfiguration.getInstance(context).publishableKey)

                                        scope.launch {

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
                                                    ClientSecret.value // ID obtained from your server
                                                ))
                                                Log.d("KKKKKKKpaymentintent",paymentIntentResult.toString())
                                                if(customerSelect=="New Customer"){
                                                    addBookingNewCustomer(context=context,adults=adults.value.toInt(), bookingDetails = roomDescription,
                                                        childern = children.value.toInt(), checkInDate = mDate.value, checkOutDate = eDate.value,
                                                         hotelid = hotelID, roomCharges = roomCharges, serviceCharges = servicesCharges,
                                                        roomids = roomselected._id, services = servicesSelectedHotel, customeremail = email.value, customername = customername.value, customerphone = customerPhone.value){
                                                            it->
                                                        if(it){
                                                            navigator.replace(ViewBookings)
                                                        }
                                                    }
                                                }
                                                else{
                                                    addBooking(context=context,adults=adults.value.toInt(), bookingDetails = roomDescription,
                                                        childern = children.value.toInt(), checkInDate = mDate.value, checkOutDate = eDate.value,
                                                        customerData = customer._id, hotelid = hotelID, roomCharges = roomCharges, serviceCharges = servicesCharges,
                                                        roomids = roomselected._id, services = servicesSelectedHotel){
                                                            it->
                                                        if(it){
                                                            navigator.replace(ViewBookings)
                                                        }
                                                    }
                                                }

//                            when (paymentIntentResult.outcome) {
//                                StripeIntentResult.Outcome.SUCCEEDED -> {
//                                    // Payment was successful
//                                    // You can typically show a success message to the user
//                                }
////                                StripeIntentResult.Outcome.REQUIRES_ACTION -> {
////                                    // Payment requires additional action (e.g., 3D Secure authentication)
////                                    // Handle the action using `handleNextActionForPayment` function
////                                }
//                                StripeIntentResult.Outcome.FAILED -> {
//                                    // Payment failed, handle the error
//                                    val errorMessage = paymentIntentResult.failureMessage
//                                    // Show an error message to the user
//                                }
//                            }
                                                // Now you can use this PaymentMethod for payments
                                            } catch (e: Exception) {
                                                // Handle error
                                                Log.d("KKKKKKK","paymentMethodId".toString())
                                            }
                                        }
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor =GlobalStrings.AdminColorMain, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,GlobalStrings.AdminColorMain),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Next")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
        getHotels(context)
        getCategories(context)
        getInventories(context)
        GetUsers(context)
//        getServicesHotel(context)
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
                            var hotel = hotels.getJSONObject(i)
                            var ServiceList = mutableStateListOf<Service>()
                            var VideoList = mutableStateListOf<String>()
                            var FloorList = mutableStateListOf<String>()
                            var RulesList = mutableStateListOf<String>()
                            var ReviewsList = mutableStateListOf<String>()
                            var RefundList = mutableStateListOf<Refund>()
                            var ImageList = mutableStateListOf<String>()
                        var RoomsList = mutableStateListOf<Room>()
//                            var hotelOwner = hotel.getJSONObject("hotelOwner")
//                            var hotelOwner_id = hotelOwner.getString("_id")
//                            var hotelOwneruserid = hotelOwner.getJSONObject("userid")
//                            var hotelOwneruseriduserCategory= hotelOwneruserid.getString("userCategory")
//                            var hotelOwneruseriduser_id= hotelOwneruserid.getString("_id")
//                            var hotelOwneruseridfirstName = hotelOwneruserid.getString("firstName")
//                            var hotelOwneruseridlastName = hotelOwneruserid.getString("lastName")
//                            var hotelOwneruseridemail = hotelOwneruserid.getString("email")
//                            var hotelOwneruseridpassword = hotelOwneruserid.getString("password")
//                            var hotelOwneruseridcontactNo = hotelOwneruserid.getString("contactNo")
//                            var hotelOwneruseridcnic = hotelOwneruserid.getString("cnic")
//                            var hotelOwneruseridprofilePicture = hotelOwneruserid.getString("profilePicture")
//                            var hotelOwneruseridstatus = hotelOwneruserid.getString("status")
//                            var hotelOwneruseridrole = hotelOwneruserid.getString("role")
//                            var hotelOwneruseridverified = hotelOwneruserid.getBoolean("verified")
//                            var hotelOwneruseridisDeleted = hotelOwneruserid.getBoolean("isDeleted")
//                            var hotelOwneruseriddeletedAt = hotelOwneruserid.get("deletedAt")
//                            var hotelOwneruseridcreatedAt = hotelOwneruserid.getString("createdAt")
//                            var hotelOwneruseridupdatedAt = hotelOwneruserid.getString("updatedAt")
//                            var hotelOwneruserid__v = hotelOwneruserid.getInt("__v")
//                            var hotelOwnerisDeleted = hotelOwner.get("isDeleted")
//                            var hotelOwnerdeletedAt = hotelOwner.get("deletedAt")
//                            var hotelOwnercreatedAt = hotelOwner.getString("createdAt")
//                            var hotelOwnerupdatedAt = hotelOwner.getString("updatedAt")
//                            var hotelOwner__v = hotelOwner.getInt("__v")

                            var userID = Userid(__v = 0, _id = "hotelOwneruseriduser_id", cnic = "hotelOwneruseridcnic",
                                contactNo = "hotelOwneruseridcontactNo", createdAt = "hotelOwneruseridcreatedAt", deletedAt = "hotelOwneruseriddeletedAt",
                                email = "hotelOwneruseridemail", firstName = "hotelOwneruseridfirstName", isDeleted = false,
                                lastName = "hotelOwneruseridlastName", password = "hotelOwneruseridpassword", profilePicture = "hotelOwneruseridprofilePicture",
                                role = "hotelOwneruseridrole", status = "hotelOwneruseridstatus", updatedAt = "hotelOwneruseridupdatedAt", userCategory = "hotelOwneruseriduserCategory",
                                verified = true)
                            var hotelOwnerOBJ = HotelOwner(__v = 0, _id = "hotelOwner_id", createdAt = "hotelOwnercreatedAt",
                                deletedAt = "hotelOwnerdeletedAt", isDeleted = false, updatedAt = "hotelOwnerupdatedAt",
                                userid = userID)


                            var _id = hotel.getString("_id")
                            //var id = hotel.getString("id")
                            var hotelname = hotel.getString("name")
                            var hoteldescription = hotel.getString("description")
                            var hotellocation = hotel.getString("location")
                            var hotellat = hotel.getDouble("lat")
                            var hotellng = hotel.getDouble("lng")
                            var hotelcity = hotel.getString("city")
                            var hotelwebsiteURL = hotel.getString("websiteURL")
                            var hotelstatus = hotel.getString("status")
                            var hotelinstagramURL = hotel.getString("instagramURL")
                            var hotelfacebookURL = hotel.getString("facebookURL")
                            var hotelcreatedAt = hotel.getString("createdAt")
                            var hotelupdatedAt = hotel.getString("updatedAt")
                            var hotelisDeleted = hotel.getBoolean("isDeleted")
                            var hoteldeletedAt = hotel.get("deletedAt")
                            var hotel__v = hotel.getInt("__v")
                            var hotelaverageRating = hotel.getInt("averageRating")
                            var hoteltotalReviews = hotel.getInt("totalReviews")
                            var hotelaveragePrice = hotel.getInt("averagePrice")
                            var hotelid = hotel.getInt("id")
                            var hotelownerName = hotel.getString("ownerName")
                            var hotelphoneNo = hotel.getString("phoneNo")
                            var hoteltelephoneNo = hotel.getString("telephoneNo")

                            var hotelimages = hotel.getJSONArray("images")
                            for(i in 0 until hotelimages.length()){
                                var hotelImage = hotelimages.getString(i)
                                ImageList.add(hotelImage)
                            }

                            var hotelvideos = hotel.getJSONArray("videos")
                            for(i in 0 until hotelvideos.length()){
                                var hotelVideoList = hotelvideos.getString(i)
                                VideoList.add(hotelVideoList)
                            }

                            var hotelrules = hotel.getJSONArray("rules")
                            for(i in 0 until hotelrules.length()){
                                var hotelRulesList = hotelrules.getString(i)
                                RulesList.add(hotelRulesList)
                            }
                            var hotelreviews = hotel.getJSONArray("reviews")
                            for(i in 0 until hotelreviews.length()){
                                var hotelReviewList = hotelreviews.getString(i)
                                ReviewsList.add(hotelReviewList)
                            }
                            var hotelfloors = hotel.getJSONArray("floors")
                            for(i in 0 until hotelfloors.length()){
                                var hotelFloorList = hotelfloors.getString(i)
                                FloorList.add(hotelFloorList)
                            }

                            var hotelservices = hotel.getJSONArray("services")
                            for(i in 0 until hotelservices.length()){
                                var serviceOBJ = hotelservices.getJSONObject(i)
                                var hotelservices_id = serviceOBJ.getString("_id")
                                var hotelservicestype = serviceOBJ.getString("type")
                                var hotelservicesname = serviceOBJ.getString("name")
                                var hotelservicesdescription = serviceOBJ.getString("description")
                                var hotelservicesimage = serviceOBJ.getString("image")
                                var hotelservicespriceRate = serviceOBJ.getString("priceRate")
                                var hotelservicesprice = serviceOBJ.getInt("price")
                                var hotelservicesaddedByRole = serviceOBJ.getString("addedByRole")
                                var hotelservicesdeletedAt = serviceOBJ.get("deletedAt")
                                var hotelservicesvisible = serviceOBJ.getBoolean("visible")
                                var hotelservicesisDeleted = serviceOBJ.getBoolean("isDeleted")
                                var hotelservicescreatedAt = serviceOBJ.getString("createdAt")
                                var hotelservicesupdatedAt = serviceOBJ.getString("updatedAt")
                                var hotelservices__v = serviceOBJ.getInt("__v")
                                var hotelservicesserviceCategory = serviceOBJ.getJSONObject("serviceCategory")
                                var hotelservicesserviceCategory_id = hotelservicesserviceCategory.getString("_id")
                                var hotelservicesserviceCategorytitle = hotelservicesserviceCategory.getString("title")
                                var hotelservicesserviceCategoryimage = hotelservicesserviceCategory.getString("image")
                                var hotelservicesserviceCategoryisDeleted = hotelservicesserviceCategory.getBoolean("isDeleted")
                                var hotelservicesserviceCategorydeletedAt = hotelservicesserviceCategory.get("deletedAt")
                                var hotelservicesserviceCategorycreatedAt = hotelservicesserviceCategory.getString("createdAt")
                                var hotelservicesserviceCategoryupdatedAt = hotelservicesserviceCategory.getString("updatedAt")
                                var hotelservicesserviceCategory__v = hotelservicesserviceCategory.getInt("__v")
                                var cat = ServiceCategory(__v = hotelservicesserviceCategory__v,_id=hotelservicesserviceCategory_id, createdAt = hotelservicesserviceCategorycreatedAt,
                                    deletedAt =hotelservicesserviceCategorydeletedAt, image = hotelservicesserviceCategoryimage, isDeleted = hotelservicesserviceCategoryisDeleted,
                                    title = hotelservicesserviceCategorytitle, updatedAt = hotelservicesserviceCategoryupdatedAt)
                                var ser = Service(__v = hotelservices__v,_id=hotelservices_id, addedByRole = hotelservicesaddedByRole,
                                    createdAt = hotelservicescreatedAt, deletedAt = hotelservicesdeletedAt, description = hotelservicesdescription,
                                    image = hotelservicesimage,isDeleted = hotelservicesisDeleted, name = hotelservicesname,
                                    price = hotelservicesprice, priceRate = hotelservicespriceRate, type = hotelservicestype,
                                    updatedAt = hotelservicesupdatedAt, visible = hotelservicesvisible, serviceCategory = cat)
                                ServiceList.add(ser)
                            }


                            var hotelrefundPolicy = hotel.getJSONObject("refundPolicy")
                            var hotelrefundPolicy_id = hotelrefundPolicy.getString("_id")
                            var hotelrefundPolicydescription = hotelrefundPolicy.getString("description")
                            var hotelrefundPolicyrefunds = hotelrefundPolicy.getJSONArray("refunds")
                            for(i in 0 until hotelrefundPolicyrefunds.length()){
                                var refundsOBJ = hotelrefundPolicyrefunds.getJSONObject(i)
                                var refundsOBJdays = refundsOBJ.getInt("days")
                                var refundsOBJpercentage = refundsOBJ.getInt("percentage")
                                var refundsOBJ_id = refundsOBJ.getString("_id")
                                var refundsOBJisDeleted = refundsOBJ.getBoolean("isDeleted")
                                var refundsOBJdeletedAt = refundsOBJ.get("deletedAt")
                                var refundOBJ = Refund(_id=refundsOBJ_id, days = refundsOBJdays, deletedAt = refundsOBJdeletedAt,
                                    isDeleted = refundsOBJisDeleted, percentage = refundsOBJpercentage)
                                RefundList.add(refundOBJ)

                            }

                            var hotelrefundPolicyisDeleted = hotelrefundPolicy.getBoolean("isDeleted")
                            var hotelrefundPolicydeletedAt = hotelrefundPolicy.get("deletedAt")
                            var hotelrefundPolicycreatedAt = hotelrefundPolicy.getString("createdAt")
                            var hotelrefundPolicyupdatedAt = hotelrefundPolicy.getString("updatedAt")
                            var hotelrefundPolicy__v = hotelrefundPolicy.getInt("__v")

                            var RefundPolicy = RefundPolicy(__v = hotelrefundPolicy__v,_id=hotelrefundPolicy_id, createdAt = hotelrefundPolicycreatedAt,
                                deletedAt = hotelrefundPolicydeletedAt, description = hotelrefundPolicydescription, isDeleted = hotelrefundPolicyisDeleted,
                                refunds = RefundList, updatedAt = hotelrefundPolicyupdatedAt)


                            var Hotel = Hotel(__v = hotel__v,_id=_id, averagePrice = hotelaveragePrice, averageRating = hotelaverageRating,
                                city = hotelcity, createdAt = hotelcreatedAt, deletedAt = hoteldeletedAt, description = hoteldescription,
                                facebookURL = hotelfacebookURL, floors = FloorList, hotelOwner = hotelOwnerOBJ, id = hotelid, images = ImageList,
                                instagramURL = hotelinstagramURL, isDeleted = hotelisDeleted, lat = hotellat, lng = hotellng, location = hotellocation,
                                name = hotelname, ownerName = hotelownerName, phoneNo = hotelphoneNo, refundPolicy =RefundPolicy, reviews = ReviewsList,
                                rules = RulesList, services = ServiceList, status = hotelstatus, telephoneNo = hoteltelephoneNo, totalReviews = hoteltotalReviews,
                                updatedAt = hotelupdatedAt, videos = VideoList, websiteURL = hotelwebsiteURL, rooms = RoomsList)

                            Hotels.add(Hotel)

                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS",error.toString())
                    Hotels.clear()
                    HotelJsonArrayError = mutableStateOf(error.toString())
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
    // Add Room Function
    fun addBooking(context: Context,
                adults: Int,
                   childern: Int,
                   bookingDetails:String,
                   checkInDate:String,
                hotelid: String,
                   checkOutDate: String,
                   customerData:String,
                   roomCharges:Int,
                   serviceCharges:Int,
                   roomids:String,
                   services
                   :List<Service>,callback: (Boolean) -> Unit){
        Log.d("HAPP","AAASDASDASDASDAS")
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/bookings/addBooking"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()
        val jsonArrayService = JSONArray()
        val jsonArrayRooms = JSONArray()
        jsonArrayRooms.put(roomids)
        services.forEach { item ->
            val jsonElement = JSONObject()
            jsonElement.put("__v",item.__v)
            jsonElement.put("_id",item._id)
            jsonElement.put("addedByRole",item.addedByRole)
            jsonElement.put("image",item.image)
            jsonElement.put("createdAt",item.createdAt)
            jsonElement.put("deletedAt",item.deletedAt)
            jsonElement.put("description",item.description)
            jsonElement.put("isDeleted",item.isDeleted)
            jsonElement.put("name",item.name)
            jsonElement.put("price",item.price)
            jsonElement.put("priceRate",item.priceRate)
            jsonElement.put("type",item.type)
            jsonElement.put("updatedAt",item.updatedAt)
            jsonElement.put("visible",item.visible)
            val CategoryJSON = JSONObject()
            CategoryJSON.put("__v",item.serviceCategory.__v)
            CategoryJSON.put("_id",item.serviceCategory._id)
            CategoryJSON.put("image",item.serviceCategory.image)
            CategoryJSON.put("createdAt",item.serviceCategory.createdAt)
            CategoryJSON.put("deletedAt",item.serviceCategory.deletedAt)
            CategoryJSON.put("isDeleted",item.serviceCategory.isDeleted)
            CategoryJSON.put("title",item.serviceCategory.title)
            CategoryJSON.put("updatedAt",item.serviceCategory.updatedAt)
            jsonElement.put("serviceCategory",CategoryJSON)
            jsonArrayService.put(jsonElement)
        }
        // Define the input date format
        val inputDateFormat = SimpleDateFormat("dd/M/yyyy")

        // Parse the input date
        val inputDate = inputDateFormat.parse(checkInDate)

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

        val inputDateOut = inputDateFormat.parse(checkOutDate)
        // Add two days to the input date
        val calendarOut = Calendar.getInstance()
        calendarOut.time = inputDateOut
        calendarOut.add(Calendar.DAY_OF_MONTH, 2)
        val modifiedDateOut = calendarOut.time

        // Define the output date format
        val outputDateFormatOut = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        outputDateFormatOut.timeZone = TimeZone.getTimeZone("UTC")

        // Format the modified date to ISO 8601 format
        val isoDateStringOut = outputDateFormat.format(modifiedDateOut)

        // Request parameters
        val params = JSONObject()
        params.put("adults", adults)
        params.put("childern", childern)
        params.put("bookingDetails", bookingDetails)
        params.put("checkInDate", isoDateString)
        params.put("hotelid", hotelid)
        params.put("checkOutDate", isoDateStringOut)
        params.put("customerData", customerData)
        params.put("roomCharges", roomCharges)
        params.put("serviceCharges", serviceCharges)
        params.put("roomids", jsonArrayRooms)
        params.put("services", jsonArrayService)
        Log.e("HAPP", params.toString())
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
                    Log.d("HAPP", response.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Booking Added",
                            Toast.LENGTH_SHORT
                        )
                        .show()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("HAPP", error.toString())
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

    // Add Room Function
    fun addBookingNewCustomer(context: Context,
                   adults: Int,
                   childern: Int,
                   bookingDetails:String,
                   checkInDate:String,
                   hotelid: String,
                   checkOutDate: String,
                   customeremail:String,
                   customername:String,
                   customerphone:String,
                   roomCharges:Int,
                   serviceCharges:Int,
                   roomids:String,
                   services
                   :List<Service>,callback: (Boolean) -> Unit){
        Log.d("HAPP","AAASDASDASDASDAS")
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/bookings/addBooking"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()
        val jsonArrayService = JSONArray()
        val jsonArrayRooms = JSONArray()
        val jsonCustomer = JSONObject()
        jsonCustomer.put("contactNo",customerphone)
        jsonCustomer.put("name",customername)
        jsonCustomer.put("email",customeremail)
        jsonArrayRooms.put(roomids)
        services.forEach { item ->
            val jsonElement = JSONObject()
            jsonElement.put("__v",item.__v)
            jsonElement.put("_id",item._id)
            jsonElement.put("addedByRole",item.addedByRole)
            jsonElement.put("image",item.image)
            jsonElement.put("createdAt",item.createdAt)
            jsonElement.put("deletedAt",item.deletedAt)
            jsonElement.put("description",item.description)
            jsonElement.put("isDeleted",item.isDeleted)
            jsonElement.put("name",item.name)
            jsonElement.put("price",item.price)
            jsonElement.put("priceRate",item.priceRate)
            jsonElement.put("type",item.type)
            jsonElement.put("updatedAt",item.updatedAt)
            jsonElement.put("visible",item.visible)
            val CategoryJSON = JSONObject()
            CategoryJSON.put("__v",item.serviceCategory.__v)
            CategoryJSON.put("_id",item.serviceCategory._id)
            CategoryJSON.put("image",item.serviceCategory.image)
            CategoryJSON.put("createdAt",item.serviceCategory.createdAt)
            CategoryJSON.put("deletedAt",item.serviceCategory.deletedAt)
            CategoryJSON.put("isDeleted",item.serviceCategory.isDeleted)
            CategoryJSON.put("title",item.serviceCategory.title)
            CategoryJSON.put("updatedAt",item.serviceCategory.updatedAt)
            jsonElement.put("serviceCategory",CategoryJSON)
            jsonArrayService.put(jsonElement)
        }
        // Define the input date format
        val inputDateFormat = SimpleDateFormat("dd/M/yyyy")

        // Parse the input date
        val inputDate = inputDateFormat.parse(checkInDate)

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

        val inputDateOut = inputDateFormat.parse(checkOutDate)
        // Add two days to the input date
        val calendarOut = Calendar.getInstance()
        calendarOut.time = inputDateOut
        calendarOut.add(Calendar.DAY_OF_MONTH, 2)
        val modifiedDateOut = calendarOut.time

        // Define the output date format
        val outputDateFormatOut = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        outputDateFormatOut.timeZone = TimeZone.getTimeZone("UTC")

        // Format the modified date to ISO 8601 format
        val isoDateStringOut = outputDateFormat.format(modifiedDateOut)

        // Request parameters
        val params = JSONObject()
        params.put("adults", adults)
        params.put("childern", childern)
        params.put("bookingDetails", bookingDetails)
        params.put("checkInDate", isoDateString)
        params.put("hotelid", hotelid)
        params.put("checkOutDate", isoDateStringOut)
        params.put("customerData", jsonCustomer)
        params.put("roomCharges", roomCharges)
        params.put("serviceCharges", serviceCharges)
        params.put("roomids", jsonArrayRooms)
        params.put("services", jsonArrayService)
        Log.e("HAPP", params.toString())
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
                    Log.d("HAPP", response.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Booking Added",
                            Toast.LENGTH_SHORT
                        )
                        .show()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("HAPP", error.toString())
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
    @Composable
    fun SortableTable(
        context: Context,
        rows: List<Service>,
        sortOrder: SortOrder,
        onSortOrderChange: (SortOrder) -> Unit
    ) {
        var currentPage by remember { mutableStateOf(0) }
        val itemsPerPage = 5
        val startIndex = currentPage * itemsPerPage
        val endIndex = minOf((currentPage + 1) * itemsPerPage, rows.size)
        var sortHeader by remember {
            mutableStateOf("")
        }
        var searchFilter by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()){
                BasicTextField(
                    value = searchFilter,
                    onValueChange = { filter ->
                        searchFilter = filter
                    },
                    cursorBrush = SolidColor(Color.Black), // Change cursor color here
                    textStyle = TextStyle(fontSize = 12.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .height(30.dp)
                        .width(200.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp)),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Column(modifier = Modifier.padding(7.dp)) {
                            if(searchFilter==""){
                                Text("Search", fontSize = 12.sp, color = Color.Gray)
                            }
                            else{
                                innerTextField()
                            }
                        }
                    }
                )

            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )) {

                // Table header
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(GlobalStrings.AdminColorMain)
                ){
                    Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){
                        Text(text = "ID", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                        Text(text = "Image", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "name"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Service Name", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "category"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Service Category", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "type"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Service Type", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "description"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Description", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "priceR"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Price Rate", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "price"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Service Price", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                        Text(text = "Action", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }

                }

                if(rows.isEmpty()){
                    Text(text = "NO Records Found.", fontSize = 12.sp, fontWeight = FontWeight.W800, modifier = Modifier.padding(20.dp))
                }
                else{
                    if(sortHeader=="name"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.name }
                            SortOrder.Descending -> rows.sortedByDescending { it.name }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceName.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="category"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.serviceCategory.title }
                            SortOrder.Descending -> rows.sortedByDescending { it.serviceCategory.title }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceName.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="description"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.description }
                            SortOrder.Descending -> rows.sortedByDescending { it.description }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceDescription.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.description.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="priceR"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.priceRate.toString() }
                            SortOrder.Descending -> rows.sortedByDescending { it.priceRate.toString() }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceDescription.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.description.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="price"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.price.toString() }
                            SortOrder.Descending -> rows.sortedByDescending { it.price.toString() }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.price.toString().contains(searchFilter, ignoreCase = true)
                            }
                            .filter {
                                it.price.toString().contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)||
                                        it.price.toString().contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="type"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.type }
                            SortOrder.Descending -> rows.sortedByDescending { it.type }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceType.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.type.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else{
                        val sortedRows = when (sortOrder) {

                            SortOrder.Ascending -> rows.sortedBy { it.name }
                            SortOrder.Descending -> rows.sortedByDescending { it.name }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }

                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))){
                    IconButton(
                        onClick = {
                            if (currentPage>0) {
                                currentPage--
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.arrowleft), contentDescription = "Arrow Right")
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))){
                    IconButton(
                        onClick = {
                            if ((currentPage + 1) * itemsPerPage < rows.size) {
                                currentPage++
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.arrowright), contentDescription = "Arrow Right")
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
    @Composable
    fun SortableTableRoomServices(
        context: Context,
        rows: List<Service>,
        sortOrder: SortOrder,
        onSortOrderChange: (SortOrder) -> Unit
    ) {
        var currentPage by remember { mutableStateOf(0) }
        val itemsPerPage = 5
        val startIndex = currentPage * itemsPerPage
        val endIndex = minOf((currentPage + 1) * itemsPerPage, rows.size)
        var sortHeader by remember {
            mutableStateOf("")
        }
        var searchFilter by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()){
                BasicTextField(
                    value = searchFilter,
                    onValueChange = { filter ->
                        searchFilter = filter
                    },
                    cursorBrush = SolidColor(Color.Black), // Change cursor color here
                    textStyle = TextStyle(fontSize = 12.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .height(30.dp)
                        .width(200.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp)),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Column(modifier = Modifier.padding(7.dp)) {
                            if(searchFilter==""){
                                Text("Search", fontSize = 12.sp, color = Color.Gray)
                            }
                            else{
                                innerTextField()
                            }
                        }
                    }
                )

            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )) {

                // Table header
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(GlobalStrings.AdminColorMain)
                ){
                    Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){
                        Text(text = "ID", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                        Text(text = "Image", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "name"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Service Name", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "category"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Service Category", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "type"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Service Type", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "description"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Description", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "priceR"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Price Rate", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "price"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Service Price", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                        Text(text = "Action", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }

                }

                if(rows.isEmpty()){
                    Text(text = "NO Records Found.", fontSize = 12.sp, fontWeight = FontWeight.W800, modifier = Modifier.padding(20.dp))
                }
                else{
                    if(sortHeader=="name"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.name }
                            SortOrder.Descending -> rows.sortedByDescending { it.name }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceName.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowRoomServices(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="category"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.serviceCategory.title }
                            SortOrder.Descending -> rows.sortedByDescending { it.serviceCategory.title }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceName.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowRoomServices(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="description"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.description }
                            SortOrder.Descending -> rows.sortedByDescending { it.description }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceDescription.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.description.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowRoomServices(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="priceR"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.priceRate.toString() }
                            SortOrder.Descending -> rows.sortedByDescending { it.priceRate.toString() }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceDescription.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.description.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowRoomServices(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="price"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.price.toString() }
                            SortOrder.Descending -> rows.sortedByDescending { it.price.toString() }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.price.toString().contains(searchFilter, ignoreCase = true)
                            }
                            .filter {
                                it.price.toString().contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)||
                                        it.price.toString().contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowRoomServices(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="type"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.type }
                            SortOrder.Descending -> rows.sortedByDescending { it.type }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceType.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.type.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.serviceCategory.title.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowRoomServices(row = row, context = context)
                            }
                    }
                    else{
                        val sortedRows = when (sortOrder) {

                            SortOrder.Ascending -> rows.sortedBy { it.name }
                            SortOrder.Descending -> rows.sortedByDescending { it.name }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowRoomServices(row = row, context = context)
                            }
                    }

                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))){
                    IconButton(
                        onClick = {
                            if (currentPage>0) {
                                currentPage--
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.arrowleft), contentDescription = "Arrow Right")
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))){
                    IconButton(
                        onClick = {
                            if ((currentPage + 1) * itemsPerPage < rows.size) {
                                currentPage++
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.arrowright), contentDescription = "Arrow Right")
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }

    enum class SortOrder {
        Ascending,
        Descending;

        fun toggle(): SortOrder {
            return if (this == Ascending) Descending else Ascending
        }
    }

    @Composable
    fun MyRow(row: Service, context: Context){
        val navigatorOut = LocalNavigator.currentOrThrow
        Box(modifier = Modifier
            .padding(vertical = 10.dp)
//            .background(
//                if (servicesSelected.contains(row)) {
//                    Color(0x95B3DCFC)
//                } else {
//                    Color(0x90FFFFFF)
//                }
//            )
//            .border(
//                0.3.dp, if (servicesSelected.contains(row)) {
//                    Color(0x95B3DCFC)
//                } else {
//                    Color(0x90FFFFFF)
//                }, RoundedCornerShape(5.dp)
//            )
            .clickable {
                if (servicesSelected.contains(row)) {
                    servicesSelected.remove(row)
                } else {
                    servicesSelected.add(row)
                }
            }
            .border(0.3.dp, Color.Gray, RoundedCornerShape(5.dp))){
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ){
                Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){
//                    Text(text = row.id.toString())
                    Checkbox(checked =  if (servicesSelected.contains(row)) {
                        true
                    } else {
                        false
                    }, onCheckedChange = null)
                }

                Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                    if(row.image==""){
                        Image(
                            painter = rememberAsyncImagePainter("https://cdn4.iconfinder.com/data/icons/social-messaging-ui-color-squares-01/3/30-512.png"),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(
                                    RoundedCornerShape(
                                        (CornerSize(
                                            10.dp
                                        ))
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    else{
                        Image(
                            painter = rememberAsyncImagePainter(row.image),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(
                                    RoundedCornerShape(
                                        (CornerSize(
                                            10.dp
                                        ))
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                }

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.name)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.serviceCategory.title)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.type)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.description)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.priceRate)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.price.toString())
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Row{
                        IconButton(onClick = {
                            current = row
                            modalopen.value = true
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.eye),contentDescription = "View")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MyRowRoomServices(row: Service, context: Context){
        val navigatorOut = LocalNavigator.currentOrThrow
        Box(modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable {
                if (servicesSelectedHotel.contains(row)) {
                    servicesSelectedHotel.remove(row)
                } else {
                    servicesSelectedHotel.add(row)
                }
            }
            .border(0.3.dp, Color.Gray, RoundedCornerShape(5.dp))){
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ){
                Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){
//                    Text(text = row.id.toString())
                    Checkbox(checked =  if (servicesSelectedHotel.contains(row)) {
                        true
                    } else {
                        false
                    }, onCheckedChange = null)
                }

                Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                    if(row.image==""){
                        Image(
                            painter = rememberAsyncImagePainter("https://cdn4.iconfinder.com/data/icons/social-messaging-ui-color-squares-01/3/30-512.png"),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(
                                    RoundedCornerShape(
                                        (CornerSize(
                                            10.dp
                                        ))
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    else{
                        Image(
                            painter = rememberAsyncImagePainter(row.image),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(
                                    RoundedCornerShape(
                                        (CornerSize(
                                            10.dp
                                        ))
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                }

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.name)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.serviceCategory.title)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.type)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.description)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.priceRate)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.price.toString())
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Row{
                        IconButton(onClick = {
                            current = row
                            modalopen.value = true
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.eye),contentDescription = "View")
                        }
                    }
                }
            }
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
                    Log.d("HASHDASDAS",response.toString())
                    var users = response.getJSONArray("users")
                    usersData.clear()
                    for (i in 0 until users.length()) {
                        var user = users.getJSONObject(i)

                        var id = user.getInt("id")
                        var _id = user.getString("_id")
                        var profilePicture=""
                        if (user.has("profilePicture")){
                             profilePicture = user.getString("profilePicture")
                        }
                        var firstName = user.getString("firstName")
                        var lastName = user.getString("lastName")
                        var email = user.getString("email")
                        var userCategory = user.getString("userCategory").toString()
                        var cnic = user.getString("cnic")
                        var contactNo = user.getString("contactNo")
                        var status = user.getString("status")

                        usersData.add(
                            ViewUsers.TableRow(
                                _id = _id,
                                id = id,
                                profilePicture = profilePicture,
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                role = userCategory,
                                cnic = cnic,
                                phoneNumber = contactNo,
                                status = status
                            )
                        )
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    usersData.clear()
                    usersJsonArrayError = mutableStateOf(error.toString())
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
    @Composable
    fun SortableTableInventory(
        context: Context,
        rows: List<Inventory>,
        sortOrder: SortOrder,
        onSortOrderChange: (SortOrder) -> Unit
    ) {
        var currentPage by remember { mutableStateOf(0) }
        val itemsPerPage = 5
        val startIndex = currentPage * itemsPerPage
        val endIndex = minOf((currentPage + 1) * itemsPerPage, rows.size)
        var sortHeader by remember {
            mutableStateOf("")
        }
        var searchFilter by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()){
                BasicTextField(
                    value = searchFilter,
                    onValueChange = { filter ->
                        searchFilter = filter
                    },
                    cursorBrush = SolidColor(Color.Black), // Change cursor color here
                    textStyle = TextStyle(fontSize = 12.sp),
                    modifier = Modifier
                        .padding(16.dp)
                        .height(30.dp)
                        .width(200.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp)),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Column(modifier = Modifier.padding(7.dp)) {
                            if(searchFilter==""){
                                Text("Search", fontSize = 12.sp, color = Color.Gray)
                            }
                            else{
                                innerTextField()
                            }
                        }
                    }
                )

            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )) {

                // Table header
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(GlobalStrings.AdminColorMain)
                ){
                    Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){
                        Text(text = "ID", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                        Text(text = "Image", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "name"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Inventory Name", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "category"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Category", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "color"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Inventory Color", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                        Text(text = "Action", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }

                }

                if(rows.isEmpty()){
                    Text(text = "NO Records Found.", fontSize = 12.sp, fontWeight = FontWeight.W800, modifier = Modifier.padding(20.dp))
                }
                else{
                    if(sortHeader=="name"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.name }
                            SortOrder.Descending -> rows.sortedByDescending { it.name }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceName.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.category.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowInventory(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="category"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.category }
                            SortOrder.Descending -> rows.sortedByDescending { it.category }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
//                            .filter {
//                                it.serviceName.contains(searchFilter, ignoreCase = true)
//                            }
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                                it.name.contains(searchFilter, ignoreCase = true) ||
                                        it.category.contains(searchFilter, ignoreCase = true) ||
                                        it.description.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowInventory(row = row, context = context)
                            }
                    }
                    else{
                        val sortedRows = when (sortOrder) {

                            SortOrder.Ascending -> rows.sortedBy { it.id }
                            SortOrder.Descending -> rows.sortedByDescending { it.id }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.name.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRowInventory(row = row, context = context)
                            }
                    }

                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))){
                    IconButton(
                        onClick = {
                            if (currentPage>0) {
                                currentPage--
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.arrowleft), contentDescription = "Arrow Right")
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))){
                    IconButton(
                        onClick = {
                            if ((currentPage + 1) * itemsPerPage < rows.size) {
                                currentPage++
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.arrowright), contentDescription = "Arrow Right")
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }

    enum class SortOrderInventory {
        Ascending,
        Descending;

        fun toggle(): SortOrderInventory {
            return if (this == Ascending) Descending else Ascending
        }
    }

    @Composable
    fun MyRowInventory(row: Inventory, context: Context){
        val navigatorOut = LocalNavigator.currentOrThrow
        Box(modifier = Modifier
            .padding(vertical = 10.dp)
//            .background(
//                if (servicesSelected.contains(row)) {
//                    Color(0x95B3DCFC)
//                } else {
//                    Color(0x90FFFFFF)
//                }
//            )
//            .border(
//                0.3.dp, if (servicesSelected.contains(row)) {
//                    Color(0x95B3DCFC)
//                } else {
//                    Color(0x90FFFFFF)
//                }, RoundedCornerShape(5.dp)
//            )
            .clickable {
                if (inventoriesSelected.contains(row)) {
                    inventoriesSelected.remove(row)
                } else {
                    inventoriesSelected.add(row)
                }
            }
            .border(0.3.dp, Color.Gray, RoundedCornerShape(5.dp))){
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ){
                Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){
//                    Text(text = row.id.toString())
                    Checkbox(checked =  if (inventoriesSelected.contains(row)) {
                        true
                    } else {
                        false
                    }, onCheckedChange = null)
                }

                Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                    if(row.image==""){
                        Image(
                            painter = rememberAsyncImagePainter("https://cdn4.iconfinder.com/data/icons/social-messaging-ui-color-squares-01/3/30-512.png"),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(
                                    RoundedCornerShape(
                                        (CornerSize(
                                            10.dp
                                        ))
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    else{
                        Image(
                            painter = rememberAsyncImagePainter(row.image),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(
                                    RoundedCornerShape(
                                        (CornerSize(
                                            10.dp
                                        ))
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                }

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.name)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.category)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.color)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Row{
                        IconButton(onClick = {
                            currentInventory= row
                            modalopenInventory.value = true
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.eye),contentDescription = "View")
                        }
                    }
                }
            }
        }
    }

    // GET Categories Function
    fun getCategories(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/services/getRoomServices"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Services...")
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
                Request.Method.GET, url, ViewService.params,
                { response ->
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("services")
                    services.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)

                        //var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var serviceName = category.getString("name")
                        var servicedescription = category.getString("description")
                        var serviceprice = category.getInt("price")
                        var servicepriceRate = category.getString("priceRate")
                        var serviceaddedByRole = category.getString("addedByRole")
                        var servicevisible = category.getBoolean("visible")
                        var serviceisDeleted = category.getBoolean("isDeleted")
                        var servicedeletedAt = category.getString("deletedAt")
                        var servicecreatedAt = category.getString("createdAt")
                        var serviceupdatedAt = category.getString("updatedAt")
                        var service__v = category.getInt("__v")
                        var serviceType = category.getString("type")
                        var serviceCa = category.getJSONObject("serviceCategory")
                        var serviceCategoryID = serviceCa.getString("_id")
                        var serviceCategoryTitle = serviceCa.getString("title")
                        var serviceCategoryImage = serviceCa.getString("image")
                        var serviceCategoryDeletedAt = serviceCa.getString("deletedAt")
                        var serviceCategoryIsDeleted = serviceCa.getBoolean("isDeleted")
                        var serviceCategoryCreatedAt = serviceCa.getString("createdAt")
                        var serviceCategoryUpdatedAt = serviceCa.getString("updatedAt")
                        var serviceCategory__V = serviceCa.getInt("__v")
                        var serviceCategoryObject =
                            ServiceCategory(
                                _id = serviceCategoryID,
                                title = serviceCategoryTitle,
                                image = serviceCategoryImage,
                                isDeleted = serviceCategoryIsDeleted,
                                deletedAt = serviceCategoryDeletedAt,
                                createdAt = serviceCategoryCreatedAt,
                                updatedAt = serviceCategoryUpdatedAt,
                                __v = serviceCategory__V
                            )
                        services.add(
                            Service(
                                _id = _id,
                                serviceCategory = serviceCategoryObject,
                                type = serviceType,
                                name = serviceName,
                                description = servicedescription,
                                image = serviceImage,
                                priceRate = servicepriceRate,
                                price = serviceprice,
                                addedByRole = serviceaddedByRole,
                                visible = servicevisible,
                                isDeleted = serviceisDeleted,
                                deletedAt = servicedeletedAt,
                                createdAt = servicecreatedAt,
                                updatedAt = serviceupdatedAt,
                                __v = service__v
                            )
                        )
                    }
                    progressDialog.dismiss()
                },
                { error ->
//                    ViewService.serviceCategories.clear()
//                    ViewService.usersJsonArrayError = mutableStateOf(error.toString())
                    Log.d("HASHDASDAS",error.toString())
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
    fun getInventories(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/inventories/getInventories"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Inventories...")
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
                Request.Method.GET, url, ViewService.params,
                { response ->
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("inventories")
                    inventories.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)

                        var _id = category.getString("_id")
                        var image = category.getString("image")
                        var name = category.getString("name")
                        var description = category.getString("description")
                        var color = category.getString("color")
                        var addedByRole = category.getString("addedByRole")
                        var isDeleted = category.getBoolean("isDeleted")
                        var deletedAt = category.get("deletedAt")
                        var createdAt = category.getString("createdAt")
                        var updatedAt = category.getString("updatedAt")
                        var __v = category.getInt("__v")
                        var inventorycategory = category.getString("category")
                        var id = category.getInt("id")


                        var serviceCa = category.getJSONObject("inventoryCategory")
                        var serviceCategoryID = serviceCa.getString("_id")
                        var servicecategory = serviceCa.getString("title")
                        var serviceimage = serviceCa.getString("image")
                        var serviceisDeleted = serviceCa.getBoolean("isDeleted")
                        var servicedeletedAt = serviceCa.getString("deletedAt")
                        var servicecreatedAt = serviceCa.getString("createdAt")
                        var serviceupdatedAt = serviceCa.getString("updatedAt")
                        var service__v= serviceCa.getInt("__v")

                        var InventoryCAtegoryObj = InventoryCategory(__v = service__v, _id = serviceCategoryID,createdAt=servicecreatedAt,deletedAt=servicedeletedAt,
                            image = serviceimage,isDeleted=serviceisDeleted, title = servicecategory,updatedAt=serviceupdatedAt)
                        inventories.add(
                            Inventory(__v = __v, _id = _id,addedByRole=addedByRole,category=inventorycategory, color = color,createdAt=createdAt,
                                deletedAt=deletedAt,description=description,id=id, image = image, inventoryCategory = InventoryCAtegoryObj, isDeleted = isDeleted, name = name,updatedAt=updatedAt))

                    }
                    progressDialog.dismiss()
                },
                { error ->
//                    ViewService.serviceCategories.clear()
//                    ViewService.usersJsonArrayError = mutableStateOf(error.toString())
                    Log.d("HASHDASDAS",error.toString())
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
    fun getRoomByHotel(context: Context,hotelid:String) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/rooms/getHotelRooms/${hotelid}"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Rooms...")
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
                Request.Method.GET, url, ViewService.params,
                { response ->
                    Log.d("HASHDASDAS555",response.toString())
                    var categories  = response.getJSONArray("rooms")
                    rooms.clear()
                    for(i in 0 until categories.length()){
                        var dd = categories.getJSONObject(i)
                        roomservices.clear()
                        var _id = dd.getString("_id")
                       var adults = dd.getInt("adults")
                        var children = dd.getInt("children")
                        //var roomNumber = dd.getString("roomNumber")
                        var roomType = dd.getString("type")
//
                        var price = dd.getInt("price")
                        var size = dd.getString("size")
                        var isDeleted = dd.getBoolean("isDeleted")
                       // var deletedAt = category.get("deletedAt")
                        var type = dd.getString("type")
                        var services = dd.getJSONArray("services")
                        for(i in 0 until services.length()){
                            var iij = services.getJSONObject(i)
                            var iii = iij.getString("_id")
                            roomservices.add(iii)
                        }

                        rooms.add(RoomBook(_id = _id, adults = adults, children = children, deletedAt = "a",isDeleted=isDeleted,
                            price = price, roomNumber = roomType, roomType =roomType, services = roomservices, size = size, type =  type))
                    }
                    progressDialog.dismiss()
                },
                { error ->
//                    ViewService.serviceCategories.clear()
//                    ViewService.usersJsonArrayError = mutableStateOf(error.toString())
                    Log.d("HASHDASDAS555",error.toString())
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
    @Composable
    fun CustomProgressDialog(current: Service) {
        Dialog(
            onDismissRequest = { modalopen.value =false}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "View Service", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = rememberAsyncImagePainter(current.image),

                    contentDescription = "image",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    painterResource(id = R.drawable.man),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color.Black
                                )
                            }
                            Column {
                                Text(text = "Name", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = current.name,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    painterResource(id = R.drawable.money),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color.Black
                                )
                            }
                            Column {
                                Text(text = "Price", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = current.price.toString(),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    painterResource(id = R.drawable.category),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color.Black
                                )
                            }
                            Column {
                                Text(text = "Category", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = current.serviceCategory.title,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    painterResource(id = R.drawable.description),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color.Black
                                )
                            }
                            Column {
                                Text(text = "Description", fontSize = 10.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = current.description,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }

                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @Composable
    fun CustomProgressDialogInventory(current: Inventory) {
        Dialog(
            onDismissRequest = { modalopenInventory.value =false}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "View Inventory", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = rememberAsyncImagePainter(current.image),

                    contentDescription = "image",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    painterResource(id = R.drawable.man),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color.Black
                                )
                            }
                            Column {
                                Text(text = "Name", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = current.name,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    painterResource(id = R.drawable.money),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color.Black
                                )
                            }
                            Column {
                                Text(text = "Color", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = current.color,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // UploadImage method
    suspend fun uploadImageToFireBase(context : Context, filePath: List<Uri?>) {
        // get the Firebase  storage reference
        var storage = FirebaseStorage.getInstance();
        var storageReference = storage.getReference();
        var message = ""
        val jobs = mutableListOf<Job>()
        if (filePath != null) {
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            val ref: StorageReference = storageReference
                .child(
                    "images/"
                            + UUID.randomUUID().toString()
                )

            for(i in 0 until filePath.size){
                val job = CoroutineScope(Dispatchers.IO).async {
                    try {
                        var imageOne = filePath[i]
                        ref.putFile(imageOne!!).await()
                        val downloadUrl = ref.downloadUrl.await()
                        imageURL.add(downloadUrl.toString())
                        Log.e("HAPP", downloadUrl.toString())
                    } catch (e: Exception) {
                        Log.e("HAPP", "Error uploading image: ${e.message}")
                    }

                }
                jobs.add(job)
            }
            jobs.joinAll()
            progressDialog.dismiss()
            Log.d("HAPP","HERE WE GO")
        }
    }
}