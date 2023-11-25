package com.example.adminoffice.ui.theme.Utils.Screens.Hotels


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.example.adminoffice.ui.theme.Utils.DataClasses.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.ServiceCategory
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
import com.example.adminoffice.ui.theme.Utils.isValidDescription
import com.example.adminoffice.ui.theme.Utils.isValidName
import com.example.adminoffice.ui.theme.Utils.isValidPhoneNumber
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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
import com.google.gson.Gson
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.runBlocking

data class EditHotel(
    val Hotel : Hotel
)  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var imageURL =mutableStateListOf<String>()
    var message = ""
    var refundList = listOf<Refund>()
    var refundPolicy = RefundPolicy(description = "", refunds = refundList)
    var modalopenService = mutableStateOf(false)
    var serviceCategoryObject = ServiceCategory(_id="", title = "", image = "", isDeleted = false, deletedAt = "", createdAt = "", updatedAt = "", __v = 0)
    var currentService =  Service(_id="", serviceCategory = serviceCategoryObject, type = "", name = "", description = "", image = "", priceRate = "", price = 0, addedByRole = "",visible = true, isDeleted = true, deletedAt = "", createdAt = "", updatedAt = "", __v = 0)
    data class Floor(val label: String, val value: String)
    data class City(
        val Name:String,
        val Image: String
    )
    private var userLocation = LatLng(0.0, 0.0)
    var services = mutableStateListOf<Service>()
    var servicesSelected = mutableStateListOf<Service>()
    var floorsSent = mutableStateListOf<String>()
    var usersJsonArrayErrorForServices = mutableStateOf("")
    data class Owner(
        val _id: String,
        val id: Int,
        val cnic: String,
        val contactNo: String,
        val email: String,
        val fullName: String,
        val profilePicture: String,
        val role: String,)

    val floors = listOf(
        Floor("Ground Floor", "Ground Floor"),
        Floor("First Floor", "First Floor"),
        Floor("Second Floor", "Second Floor"),
        Floor("Third Floor", "Third Floor"),
        Floor("Fourth Floor", "Fourth Floor"),
        Floor("Fifth Floor", "Fifth Floor"),
        Floor("Sixth Floor", "Sixth Floor"),
        Floor("Seventh Floor", "Seventh Floor"),
        Floor("Eighth Floor", "Eighth Floor"),
        Floor("Ninth Floor", "Ninth Floor"),
        Floor("Tenth Floor", "Tenth Floor"),
        Floor("Eleventh Floor", "Eleventh Floor"),
        Floor("Twelfth Floor", "Twelfth Floor"),
        Floor("Thirteenth Floor", "Thirteenth Floor"),
        Floor("Fourteenth Floor", "Fourteenth Floor"),
        Floor("Fifteenth Floor", "Fifteenth Floor"),
        Floor("Sixteenth Floor", "Sixteenth Floor"),
        Floor("Seventeenth Floor", "Seventeenth Floor"),
        Floor("Eighteenth Floor", "Eighteenth Floor"),
        Floor("Nineteenth Floor", "Nineteenth Floor"),
        Floor("Twentieth Floor", "Twentieth Floor"),
        Floor("Twenty-First Floor", "Twenty-First Floor"),
        Floor("Twenty-Second Floor", "Twenty-Second Floor"),
        Floor("Twenty-Third Floor", "Twenty-Third Floor"),
        Floor("Twenty-Fourth Floor", "Twenty-Fourth Floor"),
        Floor("Twenty-Fifth Floor", "Twenty-Fifth Floor"),
        Floor("Twenty-Sixth Floor", "Twenty-Sixth Floor"),
        Floor("Twenty-Seventh Floor", "Twenty-Seventh Floor"),
        Floor("Twenty-Eighth Floor", "Twenty-Eighth Floor"),
        Floor("Twenty-Ninth Floor", "Twenty-Ninth Floor"),
        Floor("Thirtieth Floor", "Thirtieth Floor"),
        Floor("Thirty-First Floor", "Thirty-First Floor"),
        Floor("Thirty-Second Floor", "Thirty-Second Floor"),
        Floor("Thirty-Third Floor", "Thirty-Third Floor"),
        Floor("Thirty-Fourth Floor", "Thirty-Fourth Floor"),
        Floor("Thirty-Fifth Floor", "Thirty-Fifth Floor"),
        Floor("Thirty-Sixth Floor", "Thirty-Sixth Floor"),
        Floor("Thirty-Seventh Floor", "Thirty-Seventh Floor"),
        Floor("Thirty-Eighth Floor", "Thirty-Eighth Floor"),
        Floor("Thirty-Ninth Floor", "Thirty-Ninth Floor"),
        Floor("Fortieth Floor", "Fortieth Floor"),
        Floor("Forty-First Floor", "Forty-First Floor"),
        Floor("Forty-Second Floor", "Forty-Second Floor"),
        Floor("Forty-Third Floor", "Forty-Third Floor"),
        Floor("Forty-Fourth Floor", "Forty-Fourth Floor"),
        Floor("Forty-Fifth Floor", "Forty-Fifth Floor"),
        Floor("Forty-Sixth Floor", "Forty-Sixth Floor"),
        Floor("Forty-Seventh Floor", "Forty-Seventh Floor"),
        Floor("Forty-Eighth Floor", "Forty-Eighth Floor"),
        Floor("Forty-Ninth Floor", "Forty-Ninth Floor"),
        Floor("Fiftieth Floor", "Fiftieth Floor"),
        Floor("Fifty-First Floor", "Fifty-First Floor"),
        Floor("Fifty-Second Floor", "Fifty-Second Floor"),
        Floor("Fifty-Third Floor", "Fifty-Third Floor"),
        Floor("Fifty-Fourth Floor", "Fifty-Fourth Floor"),
        Floor("Fifty-Fifth Floor", "Fifty-Fifth Floor"),
        Floor("Fifty-Sixth Floor", "Fifty-Sixth Floor"),
        Floor("Fifty-Seventh Floor", "Fifty-Seventh Floor"),
        Floor("Fifty-Eighth Floor", "Fifty-Eighth Floor"),
        Floor("Fifty-Ninth Floor", "Fifty-Ninth Floor"),
        Floor("Sixtieth Floor", "Sixtieth Floor"),
        Floor("Sixty-First Floor", "Sixty-First Floor"),
        Floor("Sixty-Second Floor", "Sixty-Second Floor"),
        Floor("Sixty-Third Floor", "Sixty-Third Floor"),
        Floor("Sixty-Fourth Floor", "Sixty-Fourth Floor"),
        Floor("Sixty-Fifth Floor", "Sixty-Fifth Floor"),
        Floor("Sixty-Sixth Floor", "Sixty-Sixth Floor"),
        Floor("Sixty-Seventh Floor", "Sixty-Seventh Floor"),
        Floor("Sixty-Eighth Floor", "Sixty-Eighth Floor"),
        Floor("Sixty-Ninth Floor", "Sixty-Ninth Floor"),
        Floor("Seventieth Floor", "Seventieth Floor"),
        Floor("Seventy-First Floor", "Seventy-First Floor"),
        Floor("Seventy-Second Floor", "Seventy-Second Floor"),
        Floor("Seventy-Third Floor", "Seventy-Third Floor"),
        Floor("Seventy-Fourth Floor", "Seventy-Fourth Floor"),
        Floor("Seventy-Fifth Floor", "Seventy-Fifth Floor"),
        Floor("Seventy-Sixth Floor", "Seventy-Sixth Floor"),
        Floor("Seventy-Seventh Floor", "Seventy-Seventh Floor"),
        Floor("Seventy-Eighth Floor", "Seventy-Eighth Floor"),
        Floor("Seventy-Ninth Floor", "Seventy-Ninth Floor"),
        Floor("Eightieth Floor", "Eightieth Floor"),
        Floor("Eighty-First Floor", "Eighty-First Floor"),
        Floor("Eighty-Second Floor", "Eighty-Second Floor"),
        Floor("Eighty-Third Floor", "Eighty-Third Floor"),
        Floor("Eighty-Fourth Floor", "Eighty-Fourth Floor"),
        Floor("Eighty-Fifth Floor", "Eighty-Fifth Floor"),
        Floor("Eighty-Sixth Floor", "Eighty-Sixth Floor"),
        Floor("Eighty-Seventh Floor", "Eighty-Seventh Floor"),
        Floor("Eighty-Eighth Floor", "Eighty-Eighth Floor"),
        Floor("Eighty-Ninth Floor", "Eighty-Ninth Floor"),
        Floor("Ninetieth Floor", "Ninetieth Floor"),
        Floor("Ninety-First Floor", "Ninety-First Floor"),
        Floor("Ninety-Second Floor", "Ninety-Second Floor"),
        Floor("Ninety-Third Floor", "Ninety-Third Floor"),
        Floor("Ninety-Fourth Floor", "Ninety-Fourth Floor"),
        Floor("Ninety-Fifth Floor", "Ninety-Fifth Floor"),
        Floor("Ninety-Sixth Floor", "Ninety-Sixth Floor"),
        Floor("Ninety-Seventh Floor", "Ninety-Seventh Floor"),
        Floor("Ninety-Eighth Floor", "Ninety-Eighth Floor"),
        Floor("Ninety-Ninth Floor", "Ninety-Ninth Floor"),
        Floor("Hundredth Floor", "Hundredth Floor")
    )
    var FloorsAdded = mutableStateListOf<Floor>()
    var FloorsSelected = mutableStateListOf<Floor>()
    var OwnersAvailable = mutableStateListOf<Owner>()
    var usersJsonArrayError = mutableStateOf("")
    var onwerID = Owner(_id = Hotel.hotelOwner.userid._id,id=Hotel.hotelOwner.__v, cnic = Hotel.hotelOwner.userid.cnic, contactNo = Hotel.hotelOwner.userid.contactNo, email =Hotel.hotelOwner.userid.email, fullName = Hotel.hotelOwner.userid.firstName+" "+ Hotel.hotelOwner.userid.lastName, profilePicture = Hotel.hotelOwner.userid.profilePicture, role =Hotel.hotelOwner.userid.userCategory)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)

    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        for(i in 0  until Hotel.images.size){
            imageURL.add(Hotel.images[i])
        }

        for(i in 0  until Hotel.floors.size){
            floorsSent.add(Hotel.floors[i])
            FloorsAdded.add(Floor(label = Hotel.floors[i], value = Hotel.floors[i]))
            FloorsSelected.add(Floor(label = Hotel.floors[i], value = Hotel.floors[i]))
        }
        for(i in 0  until Hotel.floors.size){
            floorsSent.add(Hotel.floors[i])
        }
        var step = remember {
            mutableStateOf(1)
        }
        var floorindex = remember {
            mutableStateOf(1)
        }
        var sortOrder by remember { mutableStateOf(SortOrder.Ascending) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        var selectedImageUris by remember {
            mutableStateOf<List<Uri>>(emptyList())
        }
        val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris -> selectedImageUris = uris }
        )
        var hundredpercent = remember {
            mutableStateOf(Hotel.refundPolicy.refunds[0].days.toString())
        }
        var isErrorhundredpercent = remember {
            mutableStateOf(false)
        }
        var seventyfivepercent = remember {
            mutableStateOf(Hotel.refundPolicy.refunds[1].days.toString())
        }
        var isErrorseventyfivepercent = remember {
            mutableStateOf(false)
        }
        var fiftypercent = remember {
            mutableStateOf(Hotel.refundPolicy.refunds[2].days.toString())
        }
        var isErrorfiftypercent = remember {
            mutableStateOf(false)
        }
        var twentyfivepercent = remember {
            mutableStateOf(Hotel.refundPolicy.refunds[3].days.toString())
        }
        var isErrortwentyfivepercent = remember {
            mutableStateOf(false)
        }
        var refundPolicyDescription = remember {
            mutableStateOf(Hotel.refundPolicy.description)
        }
        var isErrorrefundPolicyDescription = remember {
            mutableStateOf(false)
        }
        var isExpandedOwner = remember {
            mutableStateOf(false)
        }
        var isExpandedCity = remember {
            mutableStateOf(false)
        }
        var isErrorHotelName by remember { mutableStateOf(false) }
        var isErrorOwner by remember { mutableStateOf(false) }
        var isErrorCity by remember { mutableStateOf(false) }
        var isErrorDescription by remember { mutableStateOf(false) }
        var isErrorLocation by remember { mutableStateOf(false) }
        var isErrorTelephone by remember { mutableStateOf(false) }
        var isErrorPhoneNumber by remember { mutableStateOf(false) }
        var isErrorPhotosTaken by remember { mutableStateOf(false) }
        var onwer by remember { mutableStateOf(Hotel.hotelOwner.userid.firstName+" "+Hotel.hotelOwner.userid.lastName) }
        var city by remember { mutableStateOf(Hotel.city) }
        val hotelName = remember {
            mutableStateOf(Hotel.name)
        }
        val hotelDescription = remember {
            mutableStateOf(Hotel.description)
        }
        val location = remember {
            mutableStateOf(Hotel.location)
        }
        val latitude = remember {
            mutableStateOf(Hotel.lat)
        }
        val longitude = remember {
            mutableStateOf(Hotel.lng)
        }
        val telephone = remember {
            mutableStateOf(Hotel.telephoneNo)
        }
        val facebook = remember {
            mutableStateOf(Hotel.facebookURL)
        }
        val instagram = remember {
            mutableStateOf(Hotel.instagramURL)
        }
        val website = remember {
            mutableStateOf(Hotel.websiteURL)
        }
        val PhoneNumber = remember {
            mutableStateOf(Hotel.phoneNo)
        }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(33.6844, 73.0479), 10f)

        }
        val mapProperties = MapProperties(
            isMyLocationEnabled = userLocation != null,
        )
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
        )
        {
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
                Box(modifier = Modifier.padding(start = 10.dp, top = 70.dp, end = 10.dp, bottom = 0.dp)){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Update Hotel",
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            fontWeight = FontWeight.Bold,

                            )

                        if(step.value==1){
                            Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                                Box(modifier = Modifier.fillMaxWidth()){
                                    Row(modifier = Modifier
                                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .padding(10.dp)
                                            .border(
                                                2.dp,
                                                MaterialTheme.colorScheme.primary,
                                                shape = RoundedCornerShape(45.dp)
                                            )){
                                            Icon(painterResource(id= R.drawable.house ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                        }
                                        Column {
                                            Text(text = "1.Hotel Details", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "About Hotel", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                        }
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                        .padding(16.dp)
                                        .clickable { isExpandedOwner.value = true }
                                ) {
                                    Row {
                                        Icon(painterResource(id = R.drawable.role), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= MaterialTheme.colorScheme.primary)
                                        Text(text = if(onwer==""){
                                            "Select Hotel Owner"
                                        }
                                        else{
                                            onwer
                                        },
                                            color =  if(onwer==""){
                                                Color.Gray
                                            }
                                            else{
                                                Color.Black
                                            }
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = isExpandedOwner.value,
                                        onDismissRequest = {isExpandedOwner.value=false },
                                        modifier = Modifier
                                            .size(300.dp, 120.dp)
                                            .fillMaxWidth()
                                    ) {
                                        OwnersAvailable.forEach { option ->
                                            DropdownMenuItem(text = { Column {
                                                Text(text = option.fullName)
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
                                                onwer = option.fullName
                                                onwerID = option
                                                isExpandedOwner.value = false
                                            }, modifier = Modifier.padding(5.dp))
                                        }
                                    }
                                }
                                if (isErrorOwner) {
                                    Text(
                                        text = "Please select a Hotel Owner",
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
                                    value = hotelName.value,
                                    onValueChange = {
                                        if (it.length <= 70)
                                            hotelName.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.house), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                    },
                                    label = {
                                        Text(text = "Hotel Name",color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Hotel Name")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true
                                )
                                if (isErrorHotelName) {
                                    Text(
                                        text = "Please enter a valid Hotel Name",
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
                                        .clickable { isExpandedCity.value = true }
                                ) {
                                    Row {
                                        Icon(painterResource(id = R.drawable.role), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= MaterialTheme.colorScheme.primary)
                                        Text(text = if(city==""){
                                            "Select Hotel City"
                                        }
                                        else{
                                            city
                                        },
                                            color =  if(city==""){
                                                Color.Gray
                                            }
                                            else{
                                                Color.Black
                                            }
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = isExpandedCity.value,
                                        onDismissRequest = {isExpandedCity.value=false },
                                        modifier = Modifier
                                            .size(300.dp, 120.dp)
                                            .fillMaxWidth()
                                    ) {

                                        val Cities = listOf(City(Name = "Islamabad", Image = "https://th.bing.com/th/id/OIP.zmWHViTrLiGswUseryASZwHaEr?pid=ImgDet&rs=1"),City(Name = "Karachi", Image = "https://th.bing.com/th/id/R.544ce936798e4de667bd8b58322c200b?rik=szhxqGw6LGn2UA&pid=ImgRaw&r=0"),City(Name = "Lahore", Image = "https://th.bing.com/th/id/R.9c40a75ee7de2a8105e3abdc5b1bf31b?rik=U7P1rfXkQ8a8rw&pid=ImgRaw&r=0"))
                                        Cities.forEach { option ->
                                            DropdownMenuItem(text = { Text(text = option.Name) } , leadingIcon = {
                                                Box(){
                                                    Image(
                                                        painter = rememberAsyncImagePainter(option.Image, contentScale = ContentScale.Inside),
                                                        contentDescription = "image",
                                                        modifier = Modifier
                                                            .size(50.dp)
                                                            .clip(RoundedCornerShape((CornerSize(20.dp))))
                                                    )
                                                }
                                            }, onClick = {
                                                city = option.Name
                                                isExpandedCity.value = false
                                            })
                                        }
                                    }
                                }
                                if (isErrorCity) {
                                    Text(
                                        text = "Please select a City",
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
                                    value = hotelDescription.value,
                                    onValueChange = {
                                        if (it.length <= 300)
                                            hotelDescription.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                    },
                                    label = {
                                        Text(text = "Hotel Description",color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Hotel Description")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp),
                                )
                                if (isErrorDescription) {
                                    Text(
                                        text = "Description should be more than 30 characters.",
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
                                    value = location.value,
                                    onValueChange = {
                                        if (it.length <= 100)
                                            location.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.location), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                    },
                                    label = {
                                        Text(text = "Hotel Location",color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Hotel Location")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true
                                )
                                if (isErrorLocation) {
                                    Text(
                                        text = "Please select a Hotel Location",
                                        color = Color.Red,
                                        modifier = Modifier.align(Alignment.Start),
                                        fontSize = 11.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                                    GoogleMap(
                                        modifier = Modifier
                                            .width(350.dp).height(200.dp)
//                                        .layout { measurable, constraints ->
//                                            val placeable = measurable.measure(constraints)
//                                            layout(placeable.width, (placeable.height * 0.9).toInt()) {
//                                                placeable.placeRelative(0, 0)
//                                            }
//                                        },
                                        ,
                                        cameraPositionState = cameraPositionState,
                                        properties = mapProperties,
                                    ){

                                        MapEffect(userLocation) { map ->
                                            map.setOnMapLoadedCallback {
                                                Log.d("HHHHHASDASdasdas","enter")
                                                val markerOptions = MarkerOptions()
                                                    .position(LatLng(latitude.value, longitude.value))
                                                map.addMarker(markerOptions)
                                            }
                                            map.setOnMapClickListener { lo->
                                                map.clear()
                                                Log.d("HHHHHASDASdasdas",lo.toString())
                                                Log.d("HHHHHASDASdasdas",lo.toString())
                                                longitude.value=lo.longitude
                                                latitude.value=lo.latitude
                                                val markerOptions = MarkerOptions()
                                                    .position(LatLng(lo.latitude, lo.longitude))
                                                map.addMarker(markerOptions)
                                            }

                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                                Row(modifier = Modifier.fillMaxWidth()
                                    ,horizontalArrangement = Arrangement.End){
                                    OutlinedButton(onClick = {
                                        isErrorHotelName= !isValidName(hotelName.value)
                                        isErrorDescription= !isValidDescription(hotelDescription.value)
                                        isErrorOwner = onwer == ""
                                        isErrorCity = city == ""
                                        isErrorLocation = location.value == ""
                                        if(!isErrorHotelName && !isErrorDescription && !isErrorOwner && !isErrorCity && !isErrorLocation){
                                            step.value++
                                        }
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = MaterialTheme.colorScheme.primary, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,MaterialTheme.colorScheme.primary,),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Next")
                                    }
                                }
                            }
                        }
                        else if(step.value==2){
                            Column {
                                Box(modifier = Modifier.fillMaxWidth()){
                                    Row(modifier = Modifier
                                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .padding(10.dp)
                                            .border(
                                                2.dp,
                                                MaterialTheme.colorScheme.primary,
                                                shape = RoundedCornerShape(45.dp)
                                            )){
                                            Icon(painterResource(id= R.drawable.sms ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                        }
                                        Column {
                                            Text(text = "2.Contact", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "Contacts and Social Media", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
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
                                    value = PhoneNumber.value,
                                    onValueChange = {
                                        if (it.length <= 11) PhoneNumber.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.mobile), contentDescription = "person", tint = MaterialTheme.colorScheme.primary)
                                    },
                                    label = {
                                        Text(text = "Phone Number", color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter 11 digit Phone Number")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    singleLine = true,
                                    visualTransformation = MaskVisualTransformation("####-#######")
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
                                    shape = RoundedCornerShape(5.dp),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = Color.Black,
                                        unfocusedBorderColor = Color.Gray,
                                        errorBorderColor = Color.Red,
                                        placeholderColor = Color.Gray,
                                        disabledPlaceholderColor = Color.Gray
                                    ),
                                    value = telephone.value,
                                    onValueChange = {
                                        if (it.length <= 11) telephone.value=it
                                    },
                                    leadingIcon = {
                                        Icon(Icons.Default.Phone, contentDescription = "person", tint = MaterialTheme.colorScheme.primary)
                                    },
                                    label = {
                                        Text(text = "Telephone Number", color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter 11 digit Telephone Number")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    singleLine = true,
                                    visualTransformation = MaskVisualTransformation("####-#######")
                                )
                                if (isErrorTelephone) {
                                    Text(
                                        text = "Please provide a valid Telephone Number",
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
                                    value = facebook.value,
                                    onValueChange = {
                                        if (it.length <= 45)  facebook.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.baseline_web_24), contentDescription = "password", tint = MaterialTheme.colorScheme.primary)
                                    },
                                    label = {
                                        Text(text = "Facebook Account",color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Facebook Account")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true

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
                                    value = instagram.value,
                                    onValueChange = {
                                        if (it.length <= 45)  instagram.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.baseline_web_24), contentDescription = "password", tint = MaterialTheme.colorScheme.primary)
                                    },
                                    label = {
                                        Text(text = "Instagram Account",color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Instagram Account")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true

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
                                    value = website.value,
                                    onValueChange = {
                                        if (it.length <= 45)  website.value=it
                                    },
                                    leadingIcon = {
                                        Icon(painterResource(id = R.drawable.baseline_web_24), contentDescription = "password", tint = MaterialTheme.colorScheme.primary)
                                    },
                                    label = {
                                        Text(text = "Website",color = Color.Gray)
                                    },
                                    placeholder = {
                                        Text(text = "Enter Website URL")
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true

                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){
                                    OutlinedButton(onClick = { step.value--},
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = Color.Black, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,Color.Black),
                                        shape = RoundedCornerShape(CornerSize(3.dp))
                                    ) {
                                        Text(text = "Previous")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {
                                        isErrorPhoneNumber = !isValidPhoneNumber(phoneNumber = PhoneNumber.value)
                                        isErrorTelephone = !isValidPhoneNumber(phoneNumber = telephone.value)
                                        if(!isErrorTelephone && !isErrorPhoneNumber){
                                            step.value++
                                        }
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = MaterialTheme.colorScheme.primary, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,MaterialTheme.colorScheme.primary,),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Next")
                                    }
                                }
                            }
                        }
                        else if(step.value==3){
                            Box(modifier = Modifier.fillMaxWidth()){
                                Row(modifier = Modifier
                                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier
                                        .padding(10.dp)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(45.dp)
                                        )){
                                        Icon(painterResource(id= R.drawable.sms ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                    }
                                    Column {
                                        Text(text = "3.Services", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                        Text(text = "Hotel Services", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                    }
                                }
                            }
                            Box{
                                SortableTable(
                                    context = context,
                                    rows = services,
                                    sortOrder = sortOrder,
                                    onSortOrderChange = { newSortOrder ->
                                        sortOrder = newSortOrder
                                    })
                                if(modalopenService.value){

                                    CustomProgressDialog(currentService)
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier= Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End){
                                OutlinedButton(onClick = { step.value--},
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.White, // Text color
                                        containerColor = Color.Black, // Border color
                                        // You can customize other colors here
                                    ),
                                    border= BorderStroke(1.dp,Color.Black),
                                    shape = RoundedCornerShape(CornerSize(3.dp))
                                ) {
                                    Text(text = "Previous")
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                OutlinedButton(onClick = {
                                    step.value++
                                },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color.White, // Text color
                                        containerColor = MaterialTheme.colorScheme.primary, // Border color
                                        // You can customize other colors here
                                    ),
                                    border= BorderStroke(1.dp,MaterialTheme.colorScheme.primary,),
                                    shape = RoundedCornerShape(CornerSize(3.dp))) {
                                    Text(text = "Next")
                                }
                            }
                        }
                        else if(step.value==4){
                            Box(modifier = Modifier.fillMaxWidth()){
                                Row(modifier = Modifier
                                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier
                                        .padding(10.dp)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(45.dp)
                                        )){
                                        Icon(painterResource(id= R.drawable.video ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                    }
                                    Column {
                                        Text(text = "4.Media", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                        Text(text = "Photos and Media", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                    }
                                }
                            }
                            Column(Modifier.verticalScroll(rememberScrollState())){
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                                    .height(60.dp)
                                    .clickable {
                                        multiplePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                    .padding(0.dp, 5.dp),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(text = "Select Image", fontSize = 12.sp)
                                }
                                if (isErrorPhotosTaken) {
                                    Text(
                                        text = "Please select atleast one photo.",
                                        color = Color.Red,
                                        modifier = Modifier.align(Alignment.Start),
                                        fontSize = 11.sp
                                    )
                                }
                                selectedImageUris?.let {
                                    val rowSize = 3
                                    val chunkedImageUris = selectedImageUris.chunked(rowSize)

                                    Column {
                                        for (row in chunkedImageUris) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                for (imageUri in row) {
                                                    val bitmap: ImageBitmap? = remember(imageUri) {
                                                        val bitmap = if (Build.VERSION.SDK_INT < 28) {
                                                            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                                                        } else {
                                                            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                                                            ImageDecoder.decodeBitmap(source)
                                                        }
                                                        bitmap.asImageBitmap()
                                                    }

                                                    Image(
                                                        bitmap = bitmap!!,
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .size(100.dp)
                                                            .clip(RoundedCornerShape(20.dp)),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                if(selectedImageUris.isEmpty()){
                                    val rowSize = 3
                                    val chunkedImageUris = imageURL.chunked(rowSize)

                                    Column {
                                        for (row in chunkedImageUris) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                for (imageUri in row) {

                                                    Image(
                                                        rememberAsyncImagePainter(model = imageUri),
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .size(100.dp)
                                                            .clip(RoundedCornerShape(20.dp)),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){
                                    OutlinedButton(onClick = { step.value--},
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White,
                                            containerColor = Color.Black,
                                        ),
                                        border= BorderStroke(1.dp,Color.Black),
                                        shape = RoundedCornerShape(CornerSize(3.dp))
                                    ) {
                                        Text(text = "Previous")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {
                                        scope.launch {
                                            Log.d("HAPP","DONE HOGYA BOSS")
                                            isErrorPhotosTaken = selectedImageUris.isEmpty() && imageURL.isEmpty()
                                            if(!isErrorPhotosTaken){
                                                if(selectedImageUris.isNotEmpty()){
                                                    uploadImageToFireBase(context = context, filePath = selectedImageUris)
                                                }
                                                step.value++
                                            }

                                        }
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = MaterialTheme.colorScheme.primary, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,MaterialTheme.colorScheme.primary,),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Next")
                                    }
                                }
                            }



                        }
                        else if(step.value==5){

                            Box(modifier = Modifier.fillMaxWidth()){
                                Row(modifier = Modifier
                                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier
                                        .padding(10.dp)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(45.dp)
                                        )){
                                        Icon(painterResource(id= R.drawable.money ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                    }
                                    Column {
                                        Text(text = "5.Refunds", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                        Text(text = "Refund Policy", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                    }
                                }
                            }

                            Column(Modifier.verticalScroll(rememberScrollState())){
                                var selectedTabIndex by remember { mutableStateOf(0) }
                                TabRow(
                                    selectedTabIndex = selectedTabIndex,
                                    containerColor = Color.White,
                                    contentColor = Color.Black,
                                ) {
                                    Tab(
                                        selected = selectedTabIndex == 0,
                                        onClick = { selectedTabIndex = 0 },
                                        text = { Text("Full Refund") },
                                        selectedContentColor = MaterialTheme.colorScheme.primary,
                                        unselectedContentColor = Color.Black,
                                    )
                                    Tab(
                                        selected = selectedTabIndex == 1,
                                        onClick = {
                                            selectedTabIndex = 1
                                            hundredpercent.value = 1.toString()
                                            seventyfivepercent.value = 2.toString()
                                        },
                                        text = { Text("Partial Refund") },
                                        selectedContentColor = MaterialTheme.colorScheme.primary,
                                        unselectedContentColor = Color.Black,
                                    )
                                    Tab(
                                        selected = selectedTabIndex == 2,
                                        onClick = {
                                            selectedTabIndex = 2
                                            hundredpercent.value = 1.toString()
                                            seventyfivepercent.value = 2.toString()
                                            fiftypercent.value = 3.toString()
                                            twentyfivepercent.value = 4.toString()
                                        },
                                        text = { Text("No Refund") },
                                        selectedContentColor = MaterialTheme.colorScheme.primary,
                                        unselectedContentColor = Color.Black,
                                    )
                                }

                                when (selectedTabIndex) {
                                    0 ->  Column {
                                        OutlinedTextField(
                                            shape = RoundedCornerShape(5.dp),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                focusedBorderColor = Color.Black,
                                                unfocusedBorderColor = Color.Gray,
                                                errorBorderColor = Color.Red,
                                                placeholderColor = Color.Gray,
                                                disabledPlaceholderColor = Color.Gray
                                            ),
                                            value = hundredpercent.value,
                                            onValueChange = {
                                                hundredpercent.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.money), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "100% Booking Refund Till (Days)",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Select Days")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            singleLine = true
                                        )
                                        if (isErrorhundredpercent.value) {
                                            Text(
                                                text = "Please select days for 100% Refund.",
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
                                            value = seventyfivepercent.value,
                                            onValueChange = {
                                                seventyfivepercent.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.money), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "75% Booking Refund Till (Days)",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Select Days")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            singleLine = true
                                        )
                                        if (isErrorseventyfivepercent.value) {
                                            Text(
                                                text = "Please select days for 75% Refund.",
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
                                            value = fiftypercent.value,
                                            onValueChange = {
                                                fiftypercent.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.money), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "50% Booking Refund Till (Days)",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Select Days")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            singleLine = true
                                        )
                                        if (isErrorfiftypercent.value) {
                                            Text(
                                                text = "Please select days for 50% Refund.",
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
                                            value = twentyfivepercent.value,
                                            onValueChange = {
                                                twentyfivepercent.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.money), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "25% Booking Refund Till (Days)",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Select Days")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            singleLine = true
                                        )
                                        if (isErrortwentyfivepercent.value) {
                                            Text(
                                                text = "Please select days for 25% Refund.",
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
                                            value = refundPolicyDescription.value,
                                            onValueChange = {
                                                refundPolicyDescription.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "Refund Policy Description",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Enter Refund Description")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(150.dp),
                                        )
                                        if (isErrorrefundPolicyDescription.value) {
                                            Text(
                                                text = "Description should be more than 30 characters.",
                                                color = Color.Red,
                                                modifier = Modifier.align(Alignment.Start),
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                    1 -> Column {
                                        OutlinedTextField(
                                            shape = RoundedCornerShape(5.dp),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                focusedBorderColor = Color.Black,
                                                unfocusedBorderColor = Color.Gray,
                                                errorBorderColor = Color.Red,
                                                placeholderColor = Color.Gray,
                                                disabledPlaceholderColor = Color.Gray
                                            ),
                                            value = fiftypercent.value,
                                            onValueChange = {
                                                fiftypercent.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.money), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "50% Booking Refund Till (Days)",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Select Days")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            singleLine = true
                                        )
                                        if (isErrorfiftypercent.value) {
                                            Text(
                                                text = "Please select days for 50% Refund.",
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
                                            value = twentyfivepercent.value,
                                            onValueChange = {
                                                twentyfivepercent.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.money), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "25% Booking Refund Till (Days)",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Select Days")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            singleLine = true
                                        )
                                        if (isErrortwentyfivepercent.value) {
                                            Text(
                                                text = "Please select days for 25% Refund.",
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
                                            value = refundPolicyDescription.value,
                                            onValueChange = {
                                                refundPolicyDescription.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "Refund Policy Description",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Enter Refund Description")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(150.dp),
                                        )
                                        if (isErrorrefundPolicyDescription.value) {
                                            Text(
                                                text = "Description should be more than 30 characters.",
                                                color = Color.Red,
                                                modifier = Modifier.align(Alignment.Start),
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                    2 -> Column {
                                        OutlinedTextField(
                                            shape = RoundedCornerShape(5.dp),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                focusedBorderColor = Color.Black,
                                                unfocusedBorderColor = Color.Gray,
                                                errorBorderColor = Color.Red,
                                                placeholderColor = Color.Gray,
                                                disabledPlaceholderColor = Color.Gray
                                            ),
                                            value = refundPolicyDescription.value,
                                            onValueChange = {
                                                refundPolicyDescription.value=it
                                            },
                                            leadingIcon = {
                                                Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                            },
                                            label = {
                                                Text(text = "Refund Policy Description",color = Color.Gray)
                                            },
                                            placeholder = {
                                                Text(text = "Enter Refund Description")
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(150.dp),
                                        )
                                        if (isErrorrefundPolicyDescription.value) {
                                            Text(
                                                text = "Description should be more than 30 characters.",
                                                color = Color.Red,
                                                modifier = Modifier.align(Alignment.Start),
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){
                                    OutlinedButton(onClick = { step.value--},
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = Color.Black, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,Color.Black),
                                        shape = RoundedCornerShape(CornerSize(3.dp))
                                    ) {
                                        Text(text = "Previous")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {
                                        isErrorhundredpercent.value = hundredpercent.value == ""
                                        isErrorseventyfivepercent.value = seventyfivepercent.value == ""
                                        isErrorfiftypercent.value = fiftypercent.value == ""
                                        isErrortwentyfivepercent.value = twentyfivepercent.value == ""
                                        isErrorrefundPolicyDescription.value = refundPolicyDescription.value == ""
                                        if(!isErrorhundredpercent.value && !isErrorseventyfivepercent.value && !isErrorfiftypercent.value &&
                                            !isErrortwentyfivepercent.value && !isErrorrefundPolicyDescription.value ){
                                            scope.launch {
                                                var refundList = listOf(
                                                    Refund(percentage = 100, days = hundredpercent.value.toInt()),
                                                    Refund(percentage = 75, days = seventyfivepercent.value.toInt()),
                                                    Refund(percentage = 50, days = fiftypercent.value.toInt()),
                                                    Refund(percentage = 25, days = twentyfivepercent.value.toInt()),
                                                )
                                                refundPolicy = RefundPolicy(description = refundPolicyDescription.value, refunds = refundList)
                                                step.value++
                                            }

                                        }
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = MaterialTheme.colorScheme.primary, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,MaterialTheme.colorScheme.primary,),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Next")
                                    }
                                }
                            }



                        }
                        else if(step.value==6){
                            Box(modifier = Modifier.fillMaxWidth()){
                                Row(modifier = Modifier
                                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier
                                        .padding(10.dp)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(45.dp)
                                        )){
                                        Icon(painterResource(id= R.drawable.elevator ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                    }
                                    Column {
                                        Text(text = "6.Floors", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                        Text(text = "Add Floors", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                    }
                                }
                            }
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Text("Add Floors to Hotel", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(10.dp))
                                Box(modifier = Modifier
                                    .size(25.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            CornerSize(15.dp)
                                        )
                                    )
                                    .background(Color(0x901B7C00))
                                    .clickable {
                                        if (floors.size != floorindex.value) {
                                            FloorsAdded.add(floors[floorindex.value])
                                            floorindex.value++
                                        }
                                    }){
                                    Icon(Icons.Default.Add, contentDescription =null, tint = Color.White)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Box(modifier = Modifier
                                    .size(25.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            CornerSize(15.dp)
                                        )
                                    )
                                    .clickable {
                                        if (FloorsAdded.size > 1) {
                                            if (FloorsSelected.contains(FloorsAdded.get(FloorsAdded.lastIndex))) {
                                                FloorsSelected.remove(FloorsAdded.get(FloorsAdded.lastIndex))

                                            }
                                            FloorsAdded.remove(FloorsAdded.get(FloorsAdded.lastIndex))
                                            floorindex.value--
                                        }
                                    }
                                    .background(Color(0x90FF0000))){
                                    Icon(Icons.Default.Clear, contentDescription =null, tint = Color.White)
                                }
                            }
                            Column(Modifier.verticalScroll(rememberScrollState())){


                                for(i in 0 until FloorsAdded.size){
                                    var flooor = FloorsAdded[i]
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                                        if (FloorsSelected.contains(flooor)) {
                                            FloorsSelected.remove(flooor)
                                        } else {
                                            FloorsSelected.add(flooor)
                                        }
                                    }){
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Box(modifier = Modifier
                                            .size(25.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    CornerSize(15.dp)
                                                )
                                            )

                                            .background(
                                                if (FloorsSelected.contains(flooor)) {
                                                    Color(0x901C9600)
                                                } else {
                                                    Color(0x90FF001F)
                                                }
                                            ), contentAlignment = Alignment.Center){
                                            Text(text = i.toString(), color = Color.White)
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(text = flooor.label)
                                    }
                                }






                                Spacer(modifier = Modifier.height(10.dp))
                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){
                                    OutlinedButton(onClick = { step.value--},
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White,
                                            containerColor = Color.Black,
                                        ),
                                        border= BorderStroke(1.dp,Color.Black),
                                        shape = RoundedCornerShape(CornerSize(3.dp))
                                    ) {
                                        Text(text = "Previous")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {
                                        // uploadImageToFireBase(context = context, filePath = selectedImageUris)
                                        Log.d("HAPP","DONE HOGYA BOSS")
                                        floorsSent.clear()
                                        if(FloorsAdded.size == FloorsSelected.size){
                                            Log.d("HAPP","DONE HOGYA LL")
                                            for(i in 0 until FloorsSelected.size){
                                                var obj = FloorsSelected[i]
                                                Log.d("HAPP",obj.label)
                                                floorsSent.add(obj.label)

                                            }
                                            addHotelToSystem(context = context,city=city, description = hotelDescription.value, facebookURL = facebook.value,
                                                floors = floorsSent, images = imageURL, lat = latitude.value,lng=longitude.value, location = location.value,
                                                instagramURL = instagram.value, name = hotelName.value, ownerid = onwerID._id, phoneNo = PhoneNumber.value,
                                                refundPolicy = refundPolicy, services = servicesSelected, id=Hotel._id,telephoneNo = telephone.value, websiteURL = website.value,
                                            ){Success ->
                                                if(Success==true){
                                                    navigator.replace(ViewHotel)
                                                }
                                            }
                                        }
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = MaterialTheme.colorScheme.primary, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,MaterialTheme.colorScheme.primary,),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Update Hotel")
                                    }
                                }
                            }



                        }
                    }
                }
            }
        }













        getOwners(context)
        getServices(context)

    }
    // Add Hotel Function
    fun addHotelToSystem(context: Context,id: String,
                         city: String,
                         description: String,
                         facebookURL:String,
                         instagramURL:String,
                         lat: Double,
                         lng: Double,
                         location:String,
                         name:String,
                         ownerid:String,
                         phoneNo:String,
                         telephoneNo:String,
                         websiteURL:String,
                         services:List<Service>,
                         floors:List<String>,
                         images:List<String>,
                         refundPolicy:RefundPolicy,
                         callback: (Boolean) -> Unit){
        Log.d("HAPP","AAASDASDASDASDAS")
        val url = "${GlobalStrings.baseURL}admin/hotels/updateHotel/${id}"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()
        val gson = Gson()
        val jsonArrayService = JSONArray()
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
            CategoryJSON.put("as",item.serviceCategory.__v)
            CategoryJSON.put("__v",item.serviceCategory._id)
            CategoryJSON.put("image",item.serviceCategory.image)
            CategoryJSON.put("createdAt",item.serviceCategory.createdAt)
            CategoryJSON.put("deletedAt",item.serviceCategory.deletedAt)
            CategoryJSON.put("isDeleted",item.serviceCategory.isDeleted)
            CategoryJSON.put("title",item.serviceCategory.title)
            CategoryJSON.put("updatedAt",item.serviceCategory.updatedAt)
            jsonElement.put("serviceCategory",CategoryJSON)
            jsonArrayService.put(jsonElement)
        }
        val jsonArrayimages = JSONArray()
        images.forEach { item ->
            jsonArrayimages.put(item)
        }
        val jsonArrayfloors = JSONArray()
        floors.forEach { item ->
            jsonArrayfloors.put(item)
        }
        var jrefunds = JSONArray()
        refundPolicy.refunds.forEach { item ->
            val jsonElement = JSONObject()
            jsonElement.put("days",item.days)
            jsonElement.put("percentage",item.percentage)
            jrefunds.put(jsonElement)
        }
        var jsonObject = JSONObject()
        jsonObject.put("description",refundPolicy.description)
        jsonObject.put("refunds",jrefunds)
        val jsonServices = gson.toJson(services)
        Log.d("HAPP",jsonServices)
        val jsonImages = gson.toJson(images)
        Log.d("HAPP",jsonImages)
        val jsonFloors = gson.toJson(floors)
        Log.d("HAPP",jsonFloors)
        val jsonRefund = gson.toJson(refundPolicy)
        Log.d("HAPP",jsonRefund)
        // Request parameters
        val params = JSONObject()
        params.put("city", city)
        params.put("description", description)
        params.put("facebookURL", facebookURL)
        params.put("instagramURL", instagramURL)
        params.put("lat", lat)
        params.put("lng", lng)
        params.put("location", location)
        params.put("name", name)
        params.put("ownerid", ownerid)
        params.put("phoneNo", phoneNo)
        params.put("telephoneNo", telephoneNo)
        params.put("websiteURL", websiteURL)
        params.put("services", jsonArrayService)
        params.put("floors", jsonArrayfloors)
        params.put("images", jsonArrayimages)
        params.put("videos", jsonArrayimages)
        params.put("refundPolicy", jsonObject)
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
                Request.Method.PUT, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("HAPP", response.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Hotel Updated",
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


    @OptIn(ExperimentalMaterial3Api::class)
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
                OutlinedTextField(
                    value = searchFilter,
                    onValueChange = { filter ->
                        searchFilter = filter

                    },
                    modifier = Modifier
                        .padding(5.dp),
                    placeholder = {
                        Text(text = "Search")
                    },
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
                    .background(MaterialTheme.colorScheme.primary)
                ){
                    Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                        Text(text = "", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 10.dp))
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
                            SortOrder.Ascending -> rows.sortedBy { it.priceRate }
                            SortOrder.Descending -> rows.sortedByDescending { it.priceRate }
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

                            SortOrder.Ascending -> rows.sortedBy { it.price }
                            SortOrder.Descending -> rows.sortedByDescending { it.price }
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
    fun MyRow(row: Service, context: Context){
        val navigatorOut = LocalNavigator.currentOrThrow
        Box(modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable {
                if (servicesSelected.contains(row)) {
                    servicesSelected.remove(row)
                } else {
                    servicesSelected.add(row)
                }
            }){
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ){
                Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){
                    Checkbox(checked = servicesSelected.contains(row), onCheckedChange = null)
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
                            currentService = Service(_id = row._id, serviceCategory = row.serviceCategory, type = row.type, name = row.name,
                                description = row.description, image = row.image, priceRate = row.priceRate, price = row.price,
                                addedByRole = row.addedByRole, visible = row.visible, isDeleted = row.isDeleted, deletedAt = row.deletedAt,
                                createdAt = row.createdAt, updatedAt = row.updatedAt, __v = row.__v)
                            modalopenService.value = true
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.eye),contentDescription = "View")
                        }
                    }
                }
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


    // UploadImage method
    suspend fun uploadImageToFireBase(context : Context, filePath: List<Uri?>) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()
        // get the Firebase  storage reference
        var storage = FirebaseStorage.getInstance();
        var storageReference = storage.getReference();
        var message = ""
        val jobs = mutableListOf<Job>()
        if (filePath != null) {


            // Defining the child of storageReference


            for (i in 0 until filePath.size) {
                val job = CoroutineScope(Dispatchers.IO).async {
                    try {
                        val imageOne = filePath[i] // Get the current image path
                        if (imageOne != null) {
                            val ref: StorageReference = storageReference
                                .child(
                                    "images/"
                                            + UUID.randomUUID().toString()
                                )
                            ref.putFile(imageOne).await()
                            val downloadUrl = ref.downloadUrl.await()
                            AddHotel.imageURL.add(downloadUrl.toString())
                            Log.e("HAPP", downloadUrl.toString())
                        }
                        else{
                            Log.e("HAPP", "Image could not load.")
                        }
                    } catch (e: Exception) {
                        Log.e("HAPP", "Error uploading image: ${e.message}")
                    }
                }
                jobs.add(job)
            }

            runBlocking {
                jobs.joinAll()
            }

            Log.d("HAPP","HERE WE GO")
        }
        progressDialog.dismiss()
    }

    @Composable
    fun CustomProgressDialog(current: Service) {
        Dialog(
            onDismissRequest = { modalopenService.value =false}
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
    // GET Categories Function
    fun getOwners(context: Context) {
        val url = "${GlobalStrings.baseURL}admin/users/getUsers/owner"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Owners...")
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
                Request.Method.GET, url, ViewServiceCategory.params,
                { response ->
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("users")
                    OwnersAvailable.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)
                        var _id = category.getString("_id")
                        var id = category.getInt("id")
                        var ownerCnic = category.getString("cnic")
                        var ownerName = category.getString("fullName")
                        var ownerEmail = category.getString("email")
                        var ownerContactNo = category.getString("contactNo")
                        var ownerProfilePicture = category.getString("profilePicture")
                        var ownerRole = category.getString("role")
                        OwnersAvailable.add(
                            Owner(_id=_id,id=id, cnic = ownerCnic, contactNo = ownerContactNo, email = ownerEmail, fullName = ownerName,
                                role = ownerRole, profilePicture = ownerProfilePicture)
                        )
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    OwnersAvailable.clear()
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
    // GET Categories Function
    fun getServices(context: Context) {
        val url = "${GlobalStrings.baseURL}admin/services/getHotelServices"
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

                        var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var serviceName = category.getString("name")
                        var servicecategory = category.getString("category")
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
                    for(serv in services){
                        for(ser in Hotel.services){
                            if(serv._id==ser._id){
                                servicesSelected.add(serv)
                            }
                        }
                    }
                },
                { error ->
                    services.clear()
                    usersJsonArrayErrorForServices = mutableStateOf(error.toString())
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