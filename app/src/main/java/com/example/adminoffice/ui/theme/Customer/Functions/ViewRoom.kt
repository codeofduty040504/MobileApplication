package com.example.adminoffice.ui.theme.Customer.Functions

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.adminoffice.ui.theme.Customer.Bookings.ViewBookingsCustomer
import com.example.adminoffice.ui.theme.Customer.CartFunctions.AddRoomInCart
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Refund
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy
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

data class ViewRoom(
    val room : RoomCustomerBooking,
    val hotelname: String,
    val hotelId: String
)  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var Hotels = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel>()
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var user = getUserFromLocal(context)
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            CustomSlider(sliderList = room.images)
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .verticalScroll(
                    rememberScrollState()
                ), horizontalAlignment = Alignment.CenterHorizontally){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = room.type,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W800,
                        lineHeight = 17.sp
                    )


                }
                Text(text = room.description.toString(), fontSize = 12.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Gray)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Row(verticalAlignment = Alignment.Bottom){
                        Icon(painterResource(id = R.drawable.size), contentDescription = null, tint = GlobalStrings.CustomerColorMain,modifier=Modifier)

                        Text(text = "Room Size: "+room.size, fontSize = 12.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Gray)
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Row(verticalAlignment = Alignment.Bottom){
                        Icon(painterResource(id = R.drawable.elevator), contentDescription = null, tint = GlobalStrings.CustomerColorMain,modifier=Modifier)

                        Text(text = "Floor: "+room.floor, fontSize = 12.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Gray)
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Row(verticalAlignment = Alignment.Bottom){
                        Icon(painterResource(id = R.drawable.man), contentDescription = null, tint = GlobalStrings.CustomerColorMain,modifier=Modifier)

                        Text(text = "Adults: "+room.adults.toString(), fontSize = 12.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Gray)
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Row(verticalAlignment = Alignment.Bottom){
                        Icon(painterResource(id = R.drawable.man), contentDescription = null, tint = GlobalStrings.CustomerColorMain,modifier=Modifier)

                        Text(text = "Children: "+room.children.toString(), fontSize = 12.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Gray)
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Icon(painterResource(id = R.drawable.money), contentDescription = null, tint = GlobalStrings.CustomerColorMain,modifier=Modifier)

                        Text(text = "Price: "+room.price.toString(), fontSize = 16.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Gray)
                    }
                }
                Text(text = "Room Services", fontSize = 16.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Black)

                Spacer(modifier = Modifier.size(10.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())){
                    for(service in room.services){
                        Box(modifier = Modifier.padding(horizontal=15.dp)){
                            Box(modifier = Modifier
                                .width(280.dp)
//                                    .height(370.dp)
                                // .background(Color(0xFFFEFEFE))
//                                    .border(
//                                        0.2.dp,
//                                        Color(0xFFB6B4BB),
//                                        RoundedCornerShape(10.dp)
//                                    )
                            ){
                                Column(
                                    Modifier
                                        .fillMaxWidth()){
                                    Image(
                                        painter = rememberAsyncImagePainter(service.image),
                                        contentDescription = "image",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(220.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    (CornerSize(
                                                        10.dp
                                                    ))
                                                )
                                            ),
                                        contentScale = ContentScale.FillBounds
                                    )
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(text = service.name, fontSize = 15.sp, fontWeight = FontWeight.W700, letterSpacing = 0.2.sp)
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp)){
                                            Text(text = service.description, fontSize = 14.sp, fontWeight = FontWeight.W300, letterSpacing = 0.2.sp, color = Color.Black, lineHeight = 15.sp)
                                        }
                                        Spacer(modifier = Modifier.size(7.dp))
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                            Text(text = "Rs. "+service.price.toString(), fontSize = 14.sp, fontWeight = FontWeight.W600)
                                            Box(modifier = Modifier
                                                .background(
                                                    GlobalStrings.CustomerColorMain,
                                                    RoundedCornerShape(5.dp)
                                                )
                                                .padding(5.dp)
                                            ){
                                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(20.dp)){
                                                    Text(text = service.priceRate, fontSize = 12.sp, fontWeight = FontWeight.W800,color=Color.White)
//                                                Text(text = "night",fontSize = 12.sp, fontWeight = FontWeight.Thin,color=Color.White)
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(2.dp))
                                    }
                                }

                            }
                        }
                    }
                }
                Box(modifier = Modifier
                    .clickable {
                        AddRoomInCart(
                            context,
                            hotelId,
                            room._id,
                            hotelname
                        )
                    }
                    .fillMaxWidth()
                    .background(
                        GlobalStrings.CustomerColorMain,
                        RoundedCornerShape(15.dp)
                    )
                    .height(45.dp), contentAlignment = Alignment.Center){
                    Text(text = "Book Now", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
        //getWishList(context)

    }
}