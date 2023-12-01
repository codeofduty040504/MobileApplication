package com.example.adminoffice.ui.theme.Customer.CartFunctions

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
import com.example.adminoffice.ui.theme.Customer.Bookings.BookingDetails
import com.example.adminoffice.ui.theme.Customer.Bookings.CancelBookingCustomer
import com.example.adminoffice.ui.theme.Customer.Bookings.ExtendBookingCustomer
import com.example.adminoffice.ui.theme.Customer.Bookings.ViewBookingsCustomer
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.Functions.RoomCustomer
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
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
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import kotlinx.coroutines.launch

object CartView  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var rooms = mutableStateListOf<RoomCustomer>()
    var roomsinCart = mutableStateListOf<RoomCustomer>()
    var check = mutableStateOf(1)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerContent = {},
            drawerState= drawerState,
            gesturesEnabled = false
        ) {
            Scaffold(
                topBar = { }
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                ) {
                    Column{
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
                            Box(modifier = Modifier
                                .size(40.dp)
                                .background(GlobalStrings.CustomerColorMain, RoundedCornerShape(10.dp))
                                .padding(10.dp)
                                .clickable {
                                    navigator.pop()
                                }){
                                Icon(Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(30.dp), tint = Color.White)
                            }
                            Spacer(modifier = Modifier.width((screenWidthDp/4)-5.dp))
                            Text(text = "Rooms Booked", fontSize = 18.sp, fontWeight = FontWeight.Black)
                        }

                        Column(modifier = Modifier.height(screenHeightDp-50.dp)
                            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.SpaceBetween) {
                            Column{
                                for(room in roomsinCart){
                                    Box(modifier = Modifier.padding(vertical=5.dp)){
                                        Box(modifier = Modifier.border(0.4.dp,Color(0xFFD8D8D8), RoundedCornerShape(15.dp))){
                                            Row(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
                                                Row(verticalAlignment = Alignment.CenterVertically){
                                                    Image(
                                                        painter = rememberAsyncImagePainter(room.images[0]),
                                                        contentDescription = "image",
                                                        modifier = Modifier
                                                            .size(80.dp)
                                                            .clip(
                                                                RoundedCornerShape(
                                                                    (CornerSize(
                                                                        15.dp
                                                                    ))
                                                                )
                                                            ),
                                                        contentScale = ContentScale.FillBounds
                                                    )
                                                    Column(
                                                        modifier = Modifier
                                                            .padding(start = 10.dp)
                                                            .width(screenWidthDp - 140.dp),
                                                    ) {
                                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                                            Text(text = room.type, fontSize = 16.sp, fontWeight = FontWeight.Black)
                                                            Box(modifier = Modifier
                                                                .size(25.dp)
                                                                .background(Color.Red, RoundedCornerShape(10.dp))
                                                                .padding(5.dp)
                                                                .clickable {
                                                                    removeRoom(context,room._id)
                                                                    getRoomByHotel(context, getHotelID(context))

                                                                }){
                                                                Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                                                            }
                                                        }
                                                        Text(text = room.description, fontSize = 12.sp, fontWeight = FontWeight.ExtraLight, color = Color.Gray)
                                                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                                                            Row(verticalAlignment = Alignment.Top){
                                                                Icon(
                                                                    Icons.Outlined.Home, contentDescription =null, tint = GlobalStrings.CustomerColorMain, modifier = Modifier.size(18.dp)
                                                                )
                                                                Text(text = room.floor, fontSize = 12.sp)
                                                                //  Text(text = "("+"141"+") reviews", fontSize = 8.sp)
                                                            }
                                                            Row(verticalAlignment = Alignment.Top){
                                                                Icon(
                                                                    painterResource(id = R.drawable.bed), contentDescription =null, tint = GlobalStrings.CustomerColorMain, modifier = Modifier.size(18.dp)
                                                                )
                                                                Text(text = "${(room.adults+room.children).toString()} Guests", fontSize = 12.sp)
                                                                //  Text(text = "("+"141"+") reviews", fontSize = 8.sp)
                                                            }
                                                            Row(verticalAlignment = Alignment.Top){
                                                                Icon(
                                                                    painterResource(id = R.drawable.size), contentDescription =null, tint = GlobalStrings.CustomerColorMain, modifier = Modifier.size(18.dp)
                                                                )
                                                                Text(text = "${(room.size).toString()} sqft", fontSize = 12.sp)
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                                Box(modifier = Modifier
                                    .clickable {
                                        ClearCart(context)
                                        roomsinCart.clear()
                                        rooms.clear()
                                    }
                                    .padding(0.dp, 5.dp)
                                    .background(Color.Black, RoundedCornerShape(10.dp))
                                    .size(((screenWidthDp - 140.dp) / 2) - 10.dp, 35.dp), contentAlignment = Alignment.Center){
                                    Text(text = "Clear Cart", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.Bold)

                                }
                                Spacer(modifier = Modifier.size(5.dp))
                                Box(modifier = Modifier
                                    .clickable {
                                        navigator.push(Services)
                                    }
                                    .padding(0.dp, 5.dp)
                                    .background(
                                        GlobalStrings.CustomerColorMain,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .size(((screenWidthDp - 140.dp) / 2) - 10.dp, 35.dp), contentAlignment = Alignment.Center){
                                    Row{
                                        Text(text = "Next", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.Bold)
                                        Icon(Icons.Filled.ArrowForward, contentDescription = null,tint=Color.White)
                                    }

                                }
                                Spacer(modifier = Modifier.size(5.dp))
                            }
                        }
                    }


                }

            }
        }

        getRoomByHotel(context = context, hotelid = getHotelID(context))
    }
    // GET Categories Function
    fun getRoomByHotel(context: Context, hotelid:String) {
//        if(rooms.isNotEmpty()){
//            return
//        }
        val url = "${GlobalStrings.baseURL}customer/rooms/getHotelRooms/${hotelid}"
        Log.d("HASHDASDAS555",hotelid)
        Log.d("HASHDASDAS555",url)
        var roomservices= mutableStateListOf<String>()
        var roomsfromLocalStorage= getRoomsIdinCart(context)
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
                Request.Method.GET, url, ViewService.params,
                { response ->
                    Log.d("HASHDASDAS555",response.toString())
                    var categories  = response.getJSONArray("rooms")
                    rooms.clear()
                    roomsinCart.clear()
                    for(i in 0 until categories.length()){
                        var dd = categories.getJSONObject(i)
                        roomservices.clear()

                        var _id = dd.getString("_id")
                        var roomNumber = dd.getString("roomNumber")
                    //    var description = dd.getString("description")
                        var floor = dd.getString("floor")
                        var images = dd.getJSONArray("images")
                        var imagesroom = mutableStateListOf<String>()
                        for(i in 0 until images.length()){
                            imagesroom.add(images.get(i).toString())
                        }
                        var inventory = dd.getJSONArray("inventories")
                        var inventories = mutableStateListOf<String>()
                        for(i in 0 until inventory.length()){
                            var inventoryyy = inventory.get(i)
                            inventories.add(inventoryyy.toString())
                        }
                        var adults = dd.getInt("adults")
                        var children = dd.getInt("children")
                        //var roomNumber = dd.getString("roomNumber")
                        var hotel = dd.getJSONObject("hotel")
                        var hotelname = hotel.getString("name")
//
                        var price = dd.getInt("price")
                        var size = dd.getString("size")
                        // var deletedAt = category.get("deletedAt")
                        var type = dd.getString("type")
                        var services = dd.getJSONArray("services")
                        for(i in 0 until services.length()){
                            var iij = services.getJSONObject(i)
                            var iii = iij.getString("_id")
                            roomservices.add(iii)
                        }

                        rooms.add(
                            RoomCustomer(_id=_id,adults=adults,children=children,description=hotelname, floor = floor,images=imagesroom, inventories = inventories,price=price,roomNumber=roomNumber, services = roomservices,size=size,type=type, videos = imagesroom
                            )
                        )
                    }
                    for(room in rooms){
                        for(cart in roomsfromLocalStorage){
                            if(room._id == cart){
                                roomsinCart.add(room)
                            }
                        }
                    }
                    if(roomsfromLocalStorage.isEmpty()){
                        saveHotelIDInToken(context,"")
                    }
                    progressDialog.dismiss()
                    check.value=2
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

    // GET Categories Function
    fun getRoomByHotelwithoutCheck(context: Context, hotelid:String) {
        val url = "${GlobalStrings.baseURL}customer/rooms/getHotelRooms/${hotelid}"
        var roomservices= mutableStateListOf<String>()

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
                Request.Method.GET, url, ViewService.params,
                { response ->
                    Log.d("HASHDASDAS555",response.toString())
                    var categories  = response.getJSONArray("rooms")
                    rooms.clear()
                    for(i in 0 until categories.length()){
                        var dd = categories.getJSONObject(i)
                        roomservices.clear()

                        var _id = dd.getString("_id")
                        var roomNumber = dd.getString("roomNumber")
                        var description = dd.getString("description")
                        var floor = dd.getString("floor")
                        var images = dd.getJSONArray("images")
                        var imagesroom = mutableStateListOf<String>()
                        for(i in 0 until images.length()){
                            imagesroom.add(images.get(i).toString())
                        }
                        var inventory = dd.getJSONArray("inventories")
                        var inventories = mutableStateListOf<String>()
                        for(i in 0 until inventory.length()){
                            inventories.add(images.get(i).toString())
                        }
                        var adults = dd.getInt("adults")
                        var children = dd.getInt("children")
                        //var roomNumber = dd.getString("roomNumber")
                        var hotel = dd.getJSONObject("hotel")
                        var hotelname = hotel.getString("name")
//
                        var price = dd.getInt("price")
                        var size = dd.getString("size")
                        // var deletedAt = category.get("deletedAt")
                        var type = dd.getString("type")
                        var services = dd.getJSONArray("services")
                        for(i in 0 until services.length()){
                            var iij = services.getJSONObject(i)
                            var iii = iij.getString("_id")
                            roomservices.add(iii)
                        }

                        rooms.add(
                            RoomCustomer(_id=_id,adults=adults,children=children,description=hotelname, floor = floor,images=imagesroom, inventories = inventories,price=price,roomNumber=roomNumber, services = roomservices,size=size,type=type, videos = imagesroom
                            )
                        )
                    }
                    var roomsfromLocalStorage= getRoomsIdinCart(context)
                    for(room in rooms){
                        for(cart in roomsfromLocalStorage){
                            if(room._id == cart){
                                roomsinCart.add(room)
                            }
                        }
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
}