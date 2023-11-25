package com.example.adminoffice.ui.theme.Customer.Bookings

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
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
import com.example.adminoffice.ui.theme.Customer.CartFunctions.Services
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Customer.Wishlist.WishList
import com.example.adminoffice.ui.theme.DabsUser
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Booking
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Customer
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Hotel
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Room
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Userid
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.AddHotel
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import kotlinx.coroutines.launch
import org.json.JSONObject

object ViewBookingsCustomer  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var bookings = mutableStateListOf<Booking>()
    var UserDABS = mutableStateListOf<DabsUser>()
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        var selectedTab = remember {
            mutableStateOf(1)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .height(screenHeightDp - 50.dp)) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(modifier = Modifier
                            .width(120.dp)
                            .height(40.dp)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .clickable { selectedTab.value = 1 }
                            .background(
                                if (selectedTab.value == 1) {
                                    GlobalStrings.CustomerColorMain
                                } else {
                                    Color(
                                        0xFFEEECF1
                                    )
                                },
                                RoundedCornerShape(12.dp)
                            ), contentAlignment = Alignment.Center) {
                            Row {
                                Icon(
                                    painterResource(id = R.drawable.calendar),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                    tint = if (selectedTab.value == 1) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    }
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Bookings",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W300,
                                    color = if (selectedTab.value == 1) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Box(modifier = Modifier
                            .width(120.dp)
                            .height(40.dp)
                            .clickable { selectedTab.value = 2 }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .background(
                                if (selectedTab.value == 2) {
                                    GlobalStrings.CustomerColorMain
                                } else {
                                    Color(
                                        0xFFEEECF1
                                    )
                                },
                                RoundedCornerShape(12.dp)
                            ), contentAlignment = Alignment.Center) {
                            Row {
                                Icon(
                                    Icons.Filled.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                    tint = if (selectedTab.value == 2) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    }
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Coupons",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W300,
                                    color = if (selectedTab.value == 2) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Box(modifier = Modifier
                            .width(120.dp)
                            .height(40.dp)
                            .clickable { selectedTab.value = 3 }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .background(
                                if (selectedTab.value == 3) {
                                    GlobalStrings.CustomerColorMain
                                } else {
                                    Color(
                                        0xFFEEECF1
                                    )
                                },
                                RoundedCornerShape(12.dp)
                            ), contentAlignment = Alignment.Center) {
                            Row {
                                Icon(
                                    painterResource(id = R.drawable.money),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                    tint = if (selectedTab.value == 3) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    }
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Payments",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W300,
                                    color = if (selectedTab.value == 3) {
                                        Color.White
                                    } else {
                                        Color.Black
                                    }
                                )
                            }
                        }
                    }
                }
                if (selectedTab.value == 1) {
                    item {
                       for(booking in bookings){
                           var bookingdropdown = remember {
                               mutableStateOf(false)
                           }
                          Box(modifier = Modifier.padding(10.dp)){
                              Box(
                                  modifier = Modifier
                                      .width(screenWidthDp)
                                      .background(
                                          Color.White,
                                          RoundedCornerShape(10.dp),
                                      )
                              ) {
                                  Column(modifier = Modifier
                                      .fillMaxWidth()
                                      .padding(10.dp)) {

                                      Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                          Row{
                                              Image(
                                                  painter = rememberAsyncImagePainter(booking.rooms[0].images[0]),
                                                  contentDescription = "image",
                                                  modifier = Modifier
                                                      .size(60.dp)
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
                                                      .padding(start = 10.dp).width(screenWidthDp-140.dp),
                                              ) {
                                                  Text(text = booking.hotelName, fontSize = 16.sp, fontWeight = FontWeight.Black)
                                                  Text(text = booking.hotel.city, fontSize = 12.sp, fontWeight = FontWeight.ExtraLight, color = Color.Gray)

//                                           Row{
//                                               Text(text = "Deluxe Room", color = Color.Gray)
//                                           }

                                              }
                                          }
                                          Box(modifier = Modifier.clickable {
                                                bookingdropdown.value=true
                                          }){
                                              Icon(painterResource(id = R.drawable.more),contentDescription = null)
                                              DropdownMenu(
                                                  expanded = bookingdropdown.value,
                                                  onDismissRequest = {bookingdropdown.value=false },
                                                  modifier = Modifier
                                                      .width(screenWidthDp - 140.dp)
                                                      .height(160.dp).background(Color.White)
                                              ) {
                                                  DropdownMenuItem(text = { Text(text = "View Details") } , leadingIcon = {
                                                      Box(modifier = Modifier
                                                          .size(30.dp)
                                                          .background(Color.Black, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center
                                                      ){
                                                          Icon(Icons.Filled.List, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                                                      }
                                                  }, onClick = {
                                                      bookingdropdown.value = false
                                                      navigator.push(BookingDetails(booking))
                                                  })
                                                  DropdownMenuItem(text = { Text(text = "Extend Booking") } , leadingIcon = {
                                                      Box(modifier = Modifier
                                                          .size(30.dp)
                                                          .background(Color.Black, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center
                                                      ){
                                                          Icon(painterResource(id = R.drawable.extend), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                                                      }
                                                  }, onClick = {
                                                      bookingdropdown.value = false
                                                      navigator.push(ExtendBookingCustomer(booking))
                                                  })
                                                      DropdownMenuItem(text = { Text(text = "Cancel Booking") } , leadingIcon = {
                                                          Box(modifier = Modifier
                                                              .size(30.dp)
                                                              .background(Color.Red, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center
                                                              ){
                                                              Icon(painterResource(id = R.drawable.close), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                                                          }
                                                      }, onClick = {
                                                          bookingdropdown.value = false
                                                          navigator.push(CancelBookingCustomer(booking))
                                                      })

                                              }
                                          }
                                      }
                                      Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                          Text(
                                              text = "Rs. "+(booking.roomCharges+booking.serviceCharges).toString(),
                                              textAlign = TextAlign.Center,
                                              fontSize = 18.sp,
                                              fontWeight = FontWeight.Black,
                                              color = GlobalStrings.CustomerColorMain
                                          )
                                      }

                                      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                          Column{
                                              Spacer(modifier = Modifier.size(2.dp))
                                              Text(text = "CHECK IN", fontSize = 12.sp, fontWeight = FontWeight.ExtraLight, color = Color.Gray)
                                              Text(text = booking.checkInDate, fontSize = 18.sp, fontWeight = FontWeight.Black)
                                          }
                                          Column(horizontalAlignment = Alignment.End){
                                              Spacer(modifier = Modifier.size(2.dp))
                                              Text(text = "CHECK OUT", fontSize = 12.sp, fontWeight = FontWeight.ExtraLight, color = Color.Gray)
                                              Text(text = booking.checkOutDate, fontSize = 18.sp, fontWeight = FontWeight.Black)
                                          }
                                      }
                                  }
                              }
                          }
                       }
                    }
                }
                if (selectedTab.value == 2) {
                    item {
                        for(i in 0 until 8){
                            Box(
                                modifier = Modifier
                                    .width(screenWidthDp)
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Divider(
                                        color = Color(0xFFF1F1F1),
                                        thickness = 16.dp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.Bottom,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row {
                                                Image(
                                                    painter = rememberAsyncImagePainter("https://static.independent.co.uk/2023/03/24/09/Best%20New%20York%20boutique%20hotels.jpg?width=1200"),
                                                    contentDescription = "image",
                                                    modifier = Modifier
                                                        .size(100.dp)
                                                        .clip(
                                                            RoundedCornerShape(
                                                                (CornerSize(
                                                                    15.dp
                                                                ))
                                                            )
                                                        ),
                                                    contentScale = ContentScale.FillBounds
                                                )
                                                Spacer(modifier = Modifier.size(15.dp))
                                                Column {
                                                    Text(
                                                        text = "Royal Hotel",
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.W800,
                                                        lineHeight = 17.sp
                                                    )
                                                    Box(modifier = Modifier
                                                        .width(100.dp)
                                                        .height(25.dp)
                                                        .background(
                                                            Color(
                                                                0xFFEEECF1
                                                            ),
                                                            RoundedCornerShape(12.dp)
                                                        ), contentAlignment = Alignment.Center) {
                                                        Row {
                                                            Text(
                                                                text = "asd4dsg7w",
                                                                textAlign = TextAlign.Center,
                                                                fontSize = 12.sp,
                                                                fontWeight = FontWeight.W300,
                                                                color = Color.Black
                                                            )
                                                        }
                                                    }
                                                    Text(
                                                        text = "Valid till 24-12-2023",
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.W800,
                                                        lineHeight = 12.sp
                                                    )
                                                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                                        Box(modifier = Modifier
                                                            .width(120.dp)
                                                            .height(40.dp)
                                                            .padding(
                                                                horizontal = 10.dp,
                                                                vertical = 5.dp
                                                            )
                                                            .background(
                                                                GlobalStrings.CustomerColorMain,
                                                                RoundedCornerShape(12.dp)
                                                            ), contentAlignment = Alignment.Center) {
                                                            Row {
                                                                Text(
                                                                    text = "25% Discount",
                                                                    textAlign = TextAlign.Center,
                                                                    fontSize = 12.sp,
                                                                    fontWeight = FontWeight.W300,
                                                                    color = Color.White
                                                                )
                                                            }
                                                        }
                                                    }
                                                }

                                            }

                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Divider(
                    color = Color(0xFF8A8A8A),
                    thickness = 0.4.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    IconButton(onClick = {
                        navigator.push(MapsCoupon)
                    }, modifier = Modifier.padding(10.dp)) {
                        Icon(painter = rememberVectorPainter(Icons.Outlined.LocationOn), contentDescription = "AR", tint = Color.Black)
                    }
                    IconButton(onClick = {
                        navigator.replace(ViewBookingsCustomer)
                    }, modifier = Modifier.padding(10.dp)) {
                        Icon(painter = rememberVectorPainter(Icons.Outlined.DateRange), contentDescription = "Booking", tint = GlobalStrings.CustomerColorMain)
                    }
                    IconButton(onClick = {navigator.replace(LandingPage)}, modifier = Modifier.padding(12.dp)) {
                        Icon(painter = rememberVectorPainter(Icons.Filled.Home), contentDescription = "Landing", tint = Color.Black)
                    }
                    IconButton(onClick = {
                        navigator.replace(WishList)
                    }, modifier = Modifier.padding(10.dp)) {
                        Icon(painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder), contentDescription = "WishList", tint = Color.Black)
                    }
                    IconButton(onClick = {
                        navigator.replace(ViewProfile)
                    }, modifier = Modifier.padding(10.dp)) {
                        Icon(painter = rememberVectorPainter(Icons.Outlined.Person), contentDescription = "Account", tint = Color.Black)
                    }
                }
            }
        }
        try{
            getBookings(context)
//            AuthorizationDABS(context){
//
//            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun AuthorizationDABS(context: Context, callback: (Boolean) -> Unit) {
        if(UserDABS.isNotEmpty()){
            return
        }
        val url = "${GlobalStrings.baseURL}auth/getAuthroizedUser"
        // Request parameters
        val params = JSONObject()
        params.put("token", getTokenFromLocalStorage(context))
        Log.d("LOOOO",params.toString())
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
                    UserDABS.clear()
                    // Handle successful login response
                    Log.d("LOOOO", response.toString())
                    var user = response.getJSONObject("user")
                    var _id = user.getString("_id")
                    var firstname = user.getString("firstName")
                    var lastname = user.getString("lastName")
                    var email = user.getString("email")
                    var contactNo = user.getString("contactNo")
                    var cnic = user.getString("cnic")
                    var profilePicture = user.getString("profilePicture")
                    var role = user.getString("role")
                    var userD = DabsUser(_id,  firstname, lastname,email, contactNo, cnic, profilePicture, role)
                    UserDABS.add(userD)
                    progressDialog.dismiss()
                    // Assuming the API returns a JSON object with a field "valid" indicating user validity

                    callback(true)
                },
                { error ->
                    UserDABS.clear()
                    // Handle error response
                    Log.e("LOOOO Error", error.toString())
                    Log.e("LOOOO Error", error.networkResponse.data.toString())
                    Log.e("LOOOO Error", error.networkResponse.statusCode.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Connection Error or try with different Credentials",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    callback(false)
                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
//                    headers["Authorization"] = "${getTokenFromLocalStorage(context)}"
                    headers["Authorization"] = ""
                    return headers
                }
            }


            // Add the request to the RequestQueue.
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
        }
    }
    // GET Bookings Function
    fun getBookings(context: Context) {
        val url = "${GlobalStrings.baseURL}customer/bookings/getBookings"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Bookings...")
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
            val request =
            object : JsonObjectRequest(
                Request.Method.GET, url, ViewBookings.params,
                { response ->
                    try{
                        Log.d("HASHDASDAS",response.toString())
                        var categories  = response.getJSONArray("bookings")
                        bookings.clear()
                        for(i in 0 until categories.length()){
                            try{
                                var category = categories.getJSONObject(i)
                                var RefundList = mutableStateListOf<Refund>()
                                var RoomList = mutableStateListOf<Room>()
                                var _id = category.getString("_id")
                                var bookingDetails = category.getString("bookingDetails")
                                var checkIn = category.getString("checkIn")
                                var checkInDate = category.getString("checkInDate")
                                var checkOutDate = category.getString("checkOutDate")
                                var checkOut = category.getString("checkOut")
                                var hotell = category.getJSONObject("hotel")
                                var hotelName = hotell.getString("name")
                                var __v = category.getInt("__v")
                                var adults = category.getInt("adults")
                                var roomCharges = category.getInt("roomCharges")
                                var childern = category.getInt("childern")
//                            var customerName = category.getString("customerName")
                                var serviceCharges = category.getInt("serviceCharges") /// Issue beacuse of Customer Diferent
                                var status = category.getString("status")
                                var rooms = category.getJSONArray("rooms")
                                for(i in 0 until rooms.length()){
                                    var tyu = rooms.getJSONObject(i)
                                    var description = tyu.getString("description")
                                    var roomNumber = tyu.getString("roomNumber")
                                    var price = tyu.getInt("price")
                                    var images = tyu.getJSONArray("images")
                                    var imagesList = mutableStateListOf<String>()
                                    for(i in 0 until images.length()){
                                        var img = images.getString(i)
                                        imagesList.add(img)
                                    }
                                    var type = tyu.getString("type")
                                    RoomList.add(Room(description = description, images = imagesList,roomNumber=roomNumber, type = type,price=price))
                                }
                                var hotel = category.getJSONObject("hotel")
                                var hotel__v = hotel.getDouble("lng")
                                var hotel_id = hotel.getDouble("lat")
                                var hotelcity= hotel.getString("city")
                                var hoteldescription= hotel.getString("description")
                                var hotelname= hotel.getDouble("lng")
                                var hotelrefundPolicy= hotel.getJSONObject("refundPolicy")
                                var hotelrefundPolicy_id= hotelrefundPolicy.getString("_id")
                                var hotelrefundPolicydescription= hotelrefundPolicy.getString("description")
                                var hotelrefundPolicyrefunds= hotelrefundPolicy.getJSONArray("refunds")
                                for (i in 0 until hotelrefundPolicyrefunds.length()){
                                    var rr = hotelrefundPolicyrefunds.getJSONObject(i)
                                    var refunddays= rr.getInt("days")
                                    var refundpercentage= rr.getInt("percentage")
                                    var refund_id= rr.getString("_id")
                                    var refund = Refund(_id = refund_id, days = refunddays, percentage = refundpercentage)
                                    RefundList.add(refund)
                                }
                                var refundPolicy = RefundPolicy(_id = hotelrefundPolicy_id, description =hotelrefundPolicydescription,
                                    refunds = RefundList)
                                var customerName = "Customer could not be loaded"
                                var customeremail = "Customer could not be loaded"
                                var customernumber = "Customer could not be loaded"
                                var customerprofilePicture = ""
                                if(category.has("customer")){
                                    var customer = category.getJSONObject("customer")
//                                   var customeruseridcnic = customer.getString("cnic")
//                                   var customeruseridcontactNo = customer.getString("contactNo")
//                                   var customeruseridemail = customer.getString("email")
                                    var customeruseridfirstName = customer.getString("firstName")
                                    var customeruseridlastName = customer.getString("lastName")
                                    customeremail = customer.getString("email")
                                    customernumber = customer.getString("contactNo")
                                    customerprofilePicture = customer.getString("profilePicture")
//                                   var customeruseridprofilePicture = customer.getString("profilePicture")
//                                   var customeruseridrole = customer.getString("role")
//                                   var customeruseridstatus = customer.getString("status")
//                                   var customer_id = category.getString("_id")
                                    customerName=customeruseridfirstName+" "+customeruseridlastName
                                }
                                else{
                                    var name = category.getString("name")
                                    customeremail = category.getString("email")
                                    customernumber = category.getString("contactNo")
                                    customerName=name
                                }
//                               try{
//                                   var customer = category.getJSONObject("customer")
////                                   var customeruseridcnic = customer.getString("cnic")
////                                   var customeruseridcontactNo = customer.getString("contactNo")
////                                   var customeruseridemail = customer.getString("email")
//                                   var customeruseridfirstName = customer.getString("firstName")
//                                   var customeruseridlastName = customer.getString("lastName")
////                                   var customeruseridprofilePicture = customer.getString("profilePicture")
////                                   var customeruseridrole = customer.getString("role")
////                                   var customeruseridstatus = customer.getString("status")
////                                   var customer_id = category.getString("_id")
//                                   customerName=customeruseridfirstName+" "+customeruseridlastName
//                               }
//                               catch (e:Exception){
//                                   e.printStackTrace()
////                                   var email =category.getString("email")
////                                   var name =category.getString("name")
////                                   var contactNo =category.getString("contactNo")
////                                   customerName=email
//                               }



                                var userid= Userid(_id ="Customer could not be loaded", cnic =  "Customer could not be loaded", contactNo =customernumber,
                                    email = customeremail, firstName =customerName, lastName = "",
                                    profilePicture = customerprofilePicture, role = "Customer could not be loaded", status = "Customer could not be loaded")
                                var customerOBJ = Customer(_id="customer_id", userid = userid)


                                var hotelOBJ = Hotel(__v =hotel__v.toInt(), _id =  hotel_id.toString(), city =hotelcity, description = hoteldescription,
                                    name = hotelname.toString(), refundPolicy = refundPolicy)
                                Log.d("HAPP",customerprofilePicture)
                                bookings.add(
                                    Booking(__v = __v,_id=_id, adults =adults, bookingDetails =bookingDetails,
                                        checkIn = checkIn, checkInDate = checkInDate, checkOut = checkOut, checkOutDate =checkOutDate,
                                        childern =childern, customer = customerOBJ, customerName = customerName,
                                        hotel = hotelOBJ, hotelName = hotelName, roomCharges =roomCharges, rooms =RoomList ,
                                        serviceCharges = serviceCharges, status =status )
                                )

                            }
                            catch(e:Exception){
                                e.printStackTrace()
                            }
                        }
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    bookings.clear()
                    Log.d("HAPP",error.toString())
                    Log.d("HAPP",error.networkResponse.statusCode.toString())
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