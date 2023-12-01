package com.example.adminoffice.ui.theme.Utils.Screens.Expense



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
import android.content.ClipDescription
import android.graphics.Bitmap
import android.service.autofill.UserData
import android.widget.DatePicker
import androidx.compose.material3.Button
import androidx.compose.ui.text.style.TextAlign
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Room
import com.example.adminoffice.ui.theme.Utils.DrawerUni
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
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
import com.example.adminoffice.ui.theme.Utils.getRoleFromLocalStorage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object AddExpense  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    data class RoomTypeClass(
        val name: String,
        val adults: Int,
        val children: Int
    )
    var imageURL =""
    val params = JSONObject()
    var usersJsonArrayError = mutableStateOf("")


    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var Hotels = mutableStateListOf<Hotel>()

    var services = mutableStateListOf<Service>()
    var servicesRoomIn = mutableStateListOf<Service>()
    var servicesHotel = mutableStateListOf<Service>()
    var roomservices= mutableStateListOf<String>()
    var servicesSelected = mutableStateListOf<Service>()
    var inventories = mutableStateListOf<Inventory>()
    var rooms = mutableStateListOf<RoomBook>()
    var Floors = mutableStateListOf<String>()
    var HotelJsonArrayError = mutableStateOf("")
    var message = ""
    var hotelID = ""
    var modalopen = mutableStateOf(false)
    var current = Service(__v = 0, _id = "", addedByRole = "", createdAt = "", deletedAt = "", description = "", image = "", isDeleted = true, name = "", price = 0, priceRate = "",
        serviceCategory = ServiceCategory(_id = "", __v = 0, createdAt = "", deletedAt = "", image = "", isDeleted = false, title = "", updatedAt = ""), type = "", updatedAt = "",visible = false)
    lateinit var customer : ViewUsers.TableRow
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current


        var isExpandedHotel = remember {
            mutableStateOf(false)
        }
        var isErrorHotel by remember { mutableStateOf(false) }
        var isErrorCustomer by remember { mutableStateOf(false) }
        var isErrorStars by remember { mutableStateOf(false) }
        var isErrorAdults by remember { mutableStateOf(false) }
        var isErrorRoomDescription by remember { mutableStateOf(false) }
        var isErrorType by remember { mutableStateOf(false) }
        var hotelSelect by remember { mutableStateOf("") }
        var roomDescription by remember { mutableStateOf("") }

        val title = remember {
            mutableStateOf("")
        }
        val type = remember {
            mutableStateOf("")
        }
        val amount = remember {
            mutableStateOf("")
        }
// Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int
        val mCalendar = Calendar.getInstance()
        var isErrorServiceImage= remember { mutableStateOf(true) }
        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        mCalendar.time = Date()
        val mDate = remember { mutableStateOf("") }
        var bitmap by remember { mutableStateOf<Bitmap?>(null) }
        var imageuri by remember {
            mutableStateOf<Uri?>(null)
        }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                imageURL =""
                imageuri = uri }
        )
        val mDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
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
                        Column (Modifier.verticalScroll(rememberScrollState())){
                            Text(
                                text = "Add Expense",
                                color = GlobalStrings.AdminColorMain,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                fontWeight = FontWeight.Bold,

                                )
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
                                                        painter = rememberAsyncImagePainter(option.images[0], contentScale = ContentScale.Fit),
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
                                        .clickable { mDatePickerDialog.show() }
                                ) {
                                    Row {
                                        Icon(painterResource(id = R.drawable.calendar), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= GlobalStrings.AdminColorMain)
                                        Text(text = if(mDate.value==""){
                                            "Select Date"
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
                                    value = title.value,
                                    onValueChange = {
                                        title.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.man), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                    },
                                    label = {
                                        Text(text = "Title", color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Expense Title")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                )
                                if (isErrorAdults) {
                                    Text(
                                        text = "Please provide a valid Expense title",
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
                                    value = type.value,
                                    onValueChange = {
                                        type.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.man), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                    },
                                    label = {
                                        Text(text = "Type", color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Expense Type")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                )
                                if (isErrorType) {
                                    Text(
                                        text = "Please provide a valid Type",
                                        color = Color.Red,
                                        modifier = Modifier.align(Alignment.Start),
                                        fontSize = 11.sp
                                    )
                                }
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
                                    value = amount.value,
                                    onValueChange = {
                                        amount.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.man), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                                    },
                                    label = {
                                        Text(text = "Amount", color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Expense Amount")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                )
                                if (isErrorStars) {
                                    Text(
                                        text = "Please select Amount of Expense",
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
                                    value = roomDescription,
                                    onValueChange = {
                                        if (it.length <= 300)
                                            roomDescription=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = GlobalStrings.AdminColorMain)
                                    },
                                    label = {
                                        Text(text = "Expense Description",color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Expense Description")
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
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)){
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                                        .height(60.dp)
                                        .clickable {
                                            launcher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        }
                                        .padding(0.dp, 5.dp),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(text = "Select Image", fontSize = 12.sp)
                                    }

                                }
                                imageuri?.let {
                                    bitmap = if(Build.VERSION.SDK_INT < 28){
                                        MediaStore.Images.Media.getBitmap(context.contentResolver,it)
                                    }
                                    else{
                                        val source = ImageDecoder.createSource(context.contentResolver,it)
                                        ImageDecoder.decodeBitmap(source)
                                    }

                                    Row(verticalAlignment = Alignment.Bottom) {
                                        Image(bitmap= bitmap?.asImageBitmap()!!, contentDescription = "", modifier = Modifier
                                            .size(100.dp)
                                            .clip(RoundedCornerShape((CornerSize(20.dp)))),
                                            contentScale = ContentScale.FillBounds)
                                        Spacer(modifier = Modifier
                                            .height(20.dp)
                                            .width(30.dp))
                                    }
                                }
                                if (!isErrorServiceImage.value) {
                                    Text(
                                        text = "Please provide an Image for Expense",
                                        color = Color.Red,
                                        modifier = Modifier.align(Alignment.Start),
                                        fontSize = 11.sp
                                    )
                                }
                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = GlobalStrings.AdminColorMain,
                                            contentColor = Color.White),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(0.dp, 10.dp)
                                            .size(400.dp, 45.dp),
                                        shape = RoundedCornerShape(15.dp),
                                        border = BorderStroke(2.dp,GlobalStrings.AdminColorMain),

                                        onClick = {
                                            isErrorRoomDescription = !isValidDescription(roomDescription)
                                            isErrorAdults = title.value == ""
                                            isErrorHotel = hotelID == ""
                                            isErrorCustomer = mDate.value == ""
                                            isErrorStars = amount.value == ""
                                            isErrorServiceImage.value = imageuri != null
                                            isErrorType = type.value == ""

                                            if(!isErrorRoomDescription
                                                && !isErrorAdults
                                                && !isErrorHotel
                                                && !isErrorCustomer
                                                && !isErrorStars
                                                && !isErrorType
                                            ){
                                                scope.launch {
                                                    if(imageuri!=null){
                                                        uploadImageToFireBase(
                                                            context = context,
                                                            filePath = imageuri
                                                        )
                                                    }
                                                    AddRevenueToSystem(context = context,amount=amount.value.toInt(),
                                                        date = mDate.value, details = roomDescription, title = title.value,
                                                        type=type.value,
                                                        hotelid = hotelID, image = imageURL){
                                                    if(it){
                                                        navigator.replace(ViewRevenue)
                                                    }

                                                    }
                                                }

                                            }
                                        },
                                    ) {
                                        Text(text = "Add Expense", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        getHotels(context)
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
                        var id = hotel.getString("id")
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
    // UploadImage method
    suspend fun uploadImageToFireBase(context : Context, filePath: Uri?) {
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

            val job = CoroutineScope(Dispatchers.IO).async {
                try {
                    var imageOne = filePath
                    ref.putFile(imageOne!!).await()
                    val downloadUrl = ref.downloadUrl.await()
                    imageURL = (downloadUrl.toString())
                    Log.e("HAPP", downloadUrl.toString())
                } catch (e: Exception) {
                    Log.e("HAPP", "Error uploading image: ${e.message}")
                }
            }
            jobs.add(job)
            jobs.joinAll()
            progressDialog.dismiss()
            Log.d("HAPP","HERE WE GO")
        }
    }

    // Add Category Function
    fun AddRevenueToSystem(context: Context, amount : Int,date: String,details: String, hotelid: String,type: String,title:String,image:String, callback: (Boolean) -> Unit){
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/expense/addExpense"
        // Define the input date format
        val inputDateFormat = SimpleDateFormat("dd/M/yyyy")

        // Parse the input date
        val inputDate = inputDateFormat.parse(date)

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
        params.put("amount", amount)
        params.put("type", type)
        params.put("date", isoDateString)
        params.put("hotelid", hotelid)
        params.put("details", details)
        params.put("title", title)
        params.put("image", image)

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
                    // Handle successful login response
                    Log.d("ASDWFVSA", response.toString())
                    Toast
                        .makeText(
                            context,
                            "Expense Added",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    progressDialog.dismiss()

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