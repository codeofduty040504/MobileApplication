package com.example.adminoffice.ui.theme.Customer.Functions


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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Customer
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Refund
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service
import com.example.adminoffice.ui.theme.Customer.HotelCardWithSlider
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import org.json.JSONObject

data class CompareHotel(
    val firstHotel: Hotel
) : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var Hotels = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel>()
    var notRan = true

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var user = getUserFromLocal(context)
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        var hotelDown = remember {
            mutableStateOf(false)
        }
        var hotelSelect by remember { mutableStateOf("") }
        //lateinit var Hotel : Hotel
       // var Hotel: Hotel? = null
        val Hotel = remember { mutableStateOf(hotelempty()) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
           Box(modifier = Modifier.padding(10.dp)){
               Box(modifier = Modifier
                   .size(40.dp)
                   .background(GlobalStrings.CustomerColorMain, RoundedCornerShape(10.dp))
                   .padding(10.dp)
                   .clickable {
                       navigator.pop()
                   }){
                   Icon(Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(30.dp), tint = Color.White)
               }
           }
            Column (modifier = Modifier.padding(10.dp)){
                Text(text = "Compare ", fontSize = 12.sp, color = Color.Gray)
                Text(text = firstHotel.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = "With ", fontSize = 12.sp, color = Color.Gray)
            }
           Box(modifier = Modifier.padding(10.dp)){
               Box(modifier = Modifier
                   .fillMaxWidth()
                   .border(0.8.dp, Color(0xFFE4E4E4), MaterialTheme.shapes.small)
                   .padding(16.dp)
                   .clickable {
                       hotelDown.value = true
                   }){
                   Row {
                       Icon(painterResource(id = R.drawable.house), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= Color(
                           0xFF000000
                       )
                       )
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
                       expanded = hotelDown.value,
                       onDismissRequest = {hotelDown.value=false },
                       modifier = Modifier
                           .width(screenWidthDp - 140.dp)
                           .height(160.dp)
                           .background(Color.White)
                   ) {
                       for(hotel in LandingPage.Hotels){
                           if(hotel._id != firstHotel._id){
                               DropdownMenuItem(text = { Text(text = hotel.name) } , leadingIcon = {
                                   Image(
                                       painter = rememberAsyncImagePainter(hotel.images[0]),
                                       contentDescription = "image",
                                       modifier = Modifier
                                           .size(40.dp)
                                           .clip(
                                               RoundedCornerShape(
                                                   (CornerSize(
                                                       50.dp
                                                   ))
                                               )
                                           ),
                                       contentScale = ContentScale.FillBounds
                                   )
                               }, onClick = {
                                   hotelSelect=hotel.name
                                   Hotel.value=hotel
                                   Log.d("AAASDASDASD",Hotel!!.value.name.toString())
                                   hotelDown.value = false
                                   //  navigator.push(BookingDetails(booking))
                               })
                           }
                       }


                   }
               }
           }
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(screenHeightDp)
                .verticalScroll(
                    rememberScrollState()
                ), horizontalAlignment = Alignment.CenterHorizontally){
                Row (modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.width(screenWidthDp/2)) {
                        Text(text = firstHotel.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GlobalStrings.CustomerColorMain)
                    }

                    Column(modifier = Modifier.width(screenWidthDp/2), horizontalAlignment = Alignment.End) {
                        if(Hotel !=null){
                            Text(text = Hotel!!.value.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GlobalStrings.CustomerColorMain)
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider(color = Color(0xDAECECEC))
                Spacer(modifier = Modifier.size(10.dp))
//                Row (modifier = Modifier.fillMaxWidth()){
//                    Column(modifier = Modifier.width(screenWidthDp/2)) {
//                        Image(
//                            painter = rememberAsyncImagePainter(firstHotel.images[0]),
//                            contentDescription = "image",
//                            modifier = Modifier
//                                .size(80.dp)
//                                .clip(
//                                    RoundedCornerShape(
//                                        (CornerSize(
//                                            20.dp
//                                        ))
//                                    )
//                                ),
//                            contentScale = ContentScale.FillBounds
//                        )                    }
//
//                    Column(modifier = Modifier.width(screenWidthDp/2), horizontalAlignment = Alignment.End) {
//                        if(Hotel !=null){
//                            Image(
//                                painter = rememberAsyncImagePainter(Hotel!!.value.images[0]),
//                                contentDescription = "image",
//                                modifier = Modifier
//                                    .size(80.dp)
//                                    .clip(
//                                        RoundedCornerShape(
//                                            (CornerSize(
//                                                20.dp
//                                            ))
//                                        )
//                                    ),
//                                contentScale = ContentScale.FillBounds
//                            )                        }
//                    }
//                }
//                Spacer(modifier = Modifier.size(10.dp))
//                Divider(color = Color(0xDAECECEC))
                Spacer(modifier = Modifier.size(10.dp))
                Row (modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.width(screenWidthDp/2)) {
                        Text(text = firstHotel.location, fontSize = 14.sp, fontWeight = FontWeight.Light)
                    }

                    Column(modifier = Modifier.width(screenWidthDp/2), horizontalAlignment = Alignment.End) {
                        Text(text = Hotel!!.value.location, fontSize = 14.sp, fontWeight = FontWeight.Light)
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider(color = Color(0xDAECECEC))
                Spacer(modifier = Modifier.size(10.dp))
                Row (modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.width(screenWidthDp/2)) {
                        Text(text = firstHotel.services.size.toString()+" services", fontSize = 14.sp, fontWeight = FontWeight.Light)
                    }

                    Column(modifier = Modifier.width(screenWidthDp/2), horizontalAlignment = Alignment.End) {
                        Text(text = Hotel!!.value.services.size.toString()+" services", fontSize = 14.sp, fontWeight = FontWeight.Light)
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider(color = Color(0xDAECECEC))
                Spacer(modifier = Modifier.size(10.dp))
                Row (modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.width(screenWidthDp/2)) {
                        for(service in firstHotel.services){
                            Row{
                                Image(
                                    painter = rememberAsyncImagePainter(service.image),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    50.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(3.dp))
                                Column{
                                    Text(text = service.name, fontSize = 14.sp)
                                    Text(text = "Rs. ${service.price}", fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                        }
                    }

                    Column(modifier = Modifier.width(screenWidthDp/2), horizontalAlignment = Alignment.End) {
                        Column(modifier = Modifier, horizontalAlignment = Alignment.Start){
                            for(service in Hotel!!.value.services){
                                Row{
                                    Image(
                                        painter = rememberAsyncImagePainter(service.image),
                                        contentDescription = "image",
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    (CornerSize(
                                                        50.dp
                                                    ))
                                                )
                                            ),
                                        contentScale = ContentScale.FillBounds
                                    )
                                    Spacer(modifier = Modifier.size(3.dp))
                                    Column{
                                        Text(text = service.name, fontSize = 14.sp)
                                        Text(text = "Rs. ${service.price}", fontSize = 12.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider(color = Color(0xDAECECEC))
                Spacer(modifier = Modifier.size(10.dp))
                Row (modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.width(screenWidthDp/2)) {
                        Row{
                            Box(modifier = Modifier
                                .size(20.dp)
                                .background(
                                    Color(0xFF108615),
                                    RoundedCornerShape(10.dp)
                                ), contentAlignment = Alignment.Center
                            ){
                                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(10.dp), tint = Color.White)
                            }
                            Spacer(modifier = Modifier.size(3.dp))
                            Text(text = "Meal Availabilty: Yes", fontSize = 12.sp)
                        }
                    }

                    Column(modifier = Modifier.width(screenWidthDp/2), horizontalAlignment = Alignment.End) {
                        Row{
                            Box(modifier = Modifier
                                .size(20.dp)
                                .background(
                                    Color.Red,
                                    RoundedCornerShape(10.dp)
                                ), contentAlignment = Alignment.Center
                            ){
                                Icon(Icons.Filled.Clear, contentDescription = null, modifier = Modifier.size(10.dp), tint = Color.White)
                            }
                            Spacer(modifier = Modifier.size(3.dp))
                            Text(text = "Meal Availabilty: No", fontSize = 12.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider(color = Color(0xDAECECEC))
                Spacer(modifier = Modifier.size(10.dp))
                Row (modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.width(screenWidthDp/2)) {
                        Row( modifier = Modifier, horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Top){
                            Icon(
                                Icons.Outlined.Star, contentDescription =null, tint = Color(
                                    0xFFFFC107
                                ), modifier = Modifier.size(22.dp)
                            )
                            Text(text = firstHotel.averageRating.toString()+".0", fontSize = 15.sp)
                        }
                    }

                    Column(modifier = Modifier.width(screenWidthDp/2), horizontalAlignment = Alignment.End) {
                        Row( modifier = Modifier, horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Top){
                            Icon(
                                Icons.Outlined.Star, contentDescription =null, tint = Color(
                                    0xFFFFC107
                                ), modifier = Modifier.size(22.dp)
                            )
                            Text(text = Hotel!!.value.averageRating.toString()+".0", fontSize = 15.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider(color = Color(0xDAECECEC))
                Row (modifier = Modifier.fillMaxWidth()){
                    Column(modifier = Modifier.width(screenWidthDp/2)) {
                        Text(text = "Average Price: Rs. ${firstHotel.averagePrice.toString()}", fontSize = 12.sp, fontWeight = FontWeight.Light)
                    }

                    Column(modifier = Modifier.width(screenWidthDp/2), horizontalAlignment = Alignment.End) {
                        Text(text = "Average Price: Rs. ${Hotel!!.value.averagePrice.toString()}", fontSize = 12.sp, fontWeight = FontWeight.Light)
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider(color = Color(0xDAECECEC))
            }
        }

    }

    fun hotelempty(): Hotel {
        var ServiceList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service>()
        var VideoList = mutableStateListOf<String>()
        var FloorList = mutableStateListOf<String>()
        var RulesList = mutableStateListOf<String>()
        var ReviewsList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review>()
        var RefundList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Refund>()
        var ImageList = mutableStateListOf<String>()
        var RoomsList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room>()
            var RefundPolicy = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy(__v = 0,_id="hotelrefundPolicy_id", createdAt = "hotelrefundPolicycreatedAt",
        deletedAt = "hotelrefundPolicydeletedAt", description = "hotelrefundPolicydescription", isDeleted = false,
        refunds = RefundList, updatedAt = "hotelrefundPolicyupdatedAt")
        var userID = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Userid(__v = 0, _id = "hotelOwner_id", cnic = "hotelOwneruseridcnic",
            contactNo = "hotelOwneruseridcontactNo", createdAt = "hotelOwneruseridcreatedAt", deletedAt = "hotelOwneruseriddeletedAt",
            email = "hotelOwneruseridemail", firstName = "hotelOwneruseridfirstName", isDeleted = false,
            lastName = "hotelOwneruseridlastName", password = "hotelOwneruseridpassword", profilePicture = "hotelOwneruseridprofilePicture",
            role = "hotelOwneruseridrole", status = "hotelOwneruseridstatus", updatedAt = "hotelOwneruseridupdatedAt", userCategory = "hotelOwneruseriduserCategory",
            verified = true)
        var hotelOwnerOBJ = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.HotelOwner(__v = 0, _id = "hotelOwner_id", createdAt = "hotelOwnercreatedAt",
            deletedAt = "hotelOwnerdeletedAt", isDeleted = false, updatedAt = "hotelOwnerupdatedAt",
            userid = userID)
        var Hotel = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel(__v = 0,_id="", averagePrice = 0, averageRating = 0,
            city = "", createdAt = "", deletedAt = "", description = "",
            facebookURL = "", floors = FloorList, hotelOwner = hotelOwnerOBJ, id = 0, images = ImageList,
            instagramURL = "", isDeleted = false, lat = 0.0, lng = 0.0, location = "",
            name = "", ownerName = "", phoneNo = "", refundPolicy =RefundPolicy, reviews = ReviewsList,
            rules = RulesList, services = ServiceList, status = "", telephoneNo = "", totalReviews = 7,
            updatedAt = "", videos = VideoList, websiteURL = "", rooms = RoomsList)

        return Hotel


    }
}