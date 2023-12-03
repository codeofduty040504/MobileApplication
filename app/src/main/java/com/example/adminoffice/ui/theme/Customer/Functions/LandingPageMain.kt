package com.example.adminoffice.ui.theme.Customer.Functions

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.example.adminoffice.ui.theme.Customer.CartFunctions.AddRoomInCart
import com.example.adminoffice.ui.theme.Customer.CartFunctions.Services
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Hotel
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.HotelOwner
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Room
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.ServiceCategory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Userid
import com.example.adminoffice.ui.theme.Utils.DataClasses.RoomBook
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.getRoleFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import org.json.JSONObject


data class HotelScreen(
    val hotel:com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel
)  : Screen {
    var rooms = mutableStateListOf<RoomCustomerBooking>()
    var imageURL = ""
    var HotelJsonArrayError = mutableStateOf("")
    var Hotel = mutableStateOf("")
    var Hotels = mutableStateListOf<Hotel>()
    var roomservices= mutableStateListOf<String>()
    var services = mutableStateListOf<Service>()
    private var userLocation = LatLng(0.0, 0.0)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        var selectedTab = remember {
            mutableStateOf(1)
        }
        var user = getUserFromLocal(context)
        var descshow = remember {
            mutableStateOf(false)
        }
        var fav = remember {
            mutableStateOf(false)
        }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(33.6844, 73.0479), 10f)

        }
        val mapProperties = MapProperties(
            isMyLocationEnabled = userLocation != null,
        )
        LazyColumn(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .height(screenHeightDp)
        ) {
            item{
               Box{

                   CustomSlider(sliderList = hotel.images)
                               IconButton(onClick = { navigator.pop() }, modifier = Modifier.padding(10.dp)) {
                                   Icon(Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(30.dp), tint = Color.White)
                               }




               }
            }
            item{
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = hotel.name, fontSize = 18.sp, fontWeight = FontWeight.W800, lineHeight = 17.sp)
                        Box(modifier = Modifier.clickable {
                            if(fav.value==false){
                                AddToWishlist(context=context,hotelid= hotel._id, customerid = user._id){
                                    if(it){
                                        fav.value=true
                                    }
                                }
                            }
                            else{
                                RemoveToWishlist(context=context,hotelid= hotel._id, customerid = user._id){
                                    if(it){
                                        fav.value=false
                                    }
                                }
                            }
                        }){
                            Icon(Icons.Filled.Favorite, contentDescription = null, modifier = Modifier.size(30.dp), tint = if(fav.value){Color.Red}else{Color.Gray})
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Row(verticalAlignment = Alignment.Bottom){
//                            Image(
//                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Flocation.png?alt=media&token=a6475cc6-a856-40ba-8340-08bf9399c610&_gl=1*16269ov*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDQ2MDEuMzEuMC4w"),
//                                contentDescription = "image",
//                                modifier = Modifier
//                                    .size(20.dp),
//                                contentScale = ContentScale.FillBounds
//                            )
                            Icon(painterResource(id = R.drawable.location), contentDescription = null, tint = GlobalStrings.CustomerColorMain,modifier=Modifier)

                            Text(text = hotel.location, fontSize = 12.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Gray)
                        }
                        Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                            Icon(
                                Icons.Outlined.Star, contentDescription =null, tint = Color(
                                    0xFFFFC107
                                ), modifier = Modifier.size(22.dp)
                            )
                            Text(text = hotel.averageRating.toString(), fontSize = 15.sp)
                            Text(text = "("+hotel.totalReviews+") reviews", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                        Spacer(modifier = Modifier.size(10.dp))

                }
                Box(modifier = Modifier.padding(horizontal = 10.dp)){
                    Box(modifier = Modifier
                        .clickable {
                            navigator.push(CompareHotel(hotel))
                        }
                        .background(
                            Color.Black,
                            RoundedCornerShape(15.dp)
                        )
                        .width(120.dp)
                        .height(25.dp), contentAlignment = Alignment.Center){
                        Text(text = "Compare", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            item{
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                ){
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
                        ), contentAlignment = Alignment.Center){
                        Row {
                            Icon(painterResource(id = R.drawable.home), contentDescription = null, modifier = Modifier.size(15.dp), tint = if(selectedTab.value==1){
                                Color.White
                            }else{
                                Color.Black
                            })
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "About", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.W300, color = if(selectedTab.value==1){
                                Color.White
                            }else{
                                Color.Black
                            })
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
                        ), contentAlignment = Alignment.Center){
                        Row {
                            Icon(painterResource(id = R.drawable.hotel), contentDescription = null, modifier = Modifier.size(15.dp), tint = if(selectedTab.value==2){
                                Color.White
                            }else{
                                Color.Black
                            })
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Rooms", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.W300, color = if(selectedTab.value==2){
                                Color.White
                            }else{
                                Color.Black
                            })
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
                        ), contentAlignment = Alignment.Center){
                        Row {
                            Icon(painterResource(id = R.drawable.category), contentDescription = null, modifier = Modifier.size(15.dp), tint = if(selectedTab.value==3){
                                Color.White
                            }else{
                                Color.Black
                            })
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Services", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.W300, color = if(selectedTab.value==3){
                                Color.White
                            }else{
                                Color.Black
                            })
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .clickable { selectedTab.value = 4 }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .background(
                            if (selectedTab.value == 4) {
                                GlobalStrings.CustomerColorMain
                            } else {
                                Color(
                                    0xFFEEECF1
                                )
                            },
                            RoundedCornerShape(12.dp)
                        ), contentAlignment = Alignment.Center){
                        Row {
                            Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.size(15.dp), tint = if(selectedTab.value==4){
                                Color.White
                            }else{
                                Color.Black
                            })
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Reviews", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.W300, color = if(selectedTab.value==4){
                                Color.White
                            }else{
                                Color.Black
                            })
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .clickable { selectedTab.value = 5 }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .background(
                            if (selectedTab.value == 5) {
                                GlobalStrings.CustomerColorMain
                            } else {
                                Color(
                                    0xFFEEECF1
                                )
                            },
                            RoundedCornerShape(12.dp)
                        ), contentAlignment = Alignment.Center){
                        Row {
                            Icon(painterResource(id = R.drawable.map), contentDescription = null, modifier = Modifier.size(15.dp),
                                tint = if(selectedTab.value==5){
                                Color.White
                            }else{
                                Color.Black
                            })
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Maps", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.W300, color = if(selectedTab.value==5){
                                Color.White
                            }else{
                                Color.Black
                            })
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .clickable { selectedTab.value = 6 }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .background(
                            if (selectedTab.value == 6) {
                                GlobalStrings.CustomerColorMain
                            } else {
                                Color(
                                    0xFFEEECF1
                                )
                            },
                            RoundedCornerShape(12.dp)
                        ), contentAlignment = Alignment.Center){
                        Row {
                            Icon(painterResource(id = R.drawable.menu), contentDescription = null, modifier = Modifier.size(15.dp), tint = if(selectedTab.value==6){
                                Color.White
                            }else{
                                Color.Black
                            })
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Menus", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.W300, color = if(selectedTab.value==6){
                                Color.White
                            }else{
                                Color.Black
                            })
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .width(150.dp)
                        .height(40.dp)
                        .clickable { selectedTab.value = 7 }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .background(
                            if (selectedTab.value == 7) {
                                GlobalStrings.CustomerColorMain
                            } else {
                                Color(
                                    0xFFEEECF1
                                )
                            },
                            RoundedCornerShape(12.dp)
                        ), contentAlignment = Alignment.Center){
                        Row {
                            Icon(painterResource(id = R.drawable.description), contentDescription = null, modifier = Modifier.size(15.dp),
                                tint = if(selectedTab.value==7){
                                    Color.White
                                }else{
                                    Color.Black
                                }
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Refunds Policy", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.W300, color = if(selectedTab.value==7){
                                Color.White
                            }else{
                                Color.Black
                            })
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            if(selectedTab.value==1){
                item{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                        Text(text = if(descshow.value==true){hotel.description}else{hotel.description.substring(0,(29))}, fontSize = 14.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Black)
                        Box(modifier = Modifier.clickable { descshow.value=!descshow.value }){
                            Text(text = if(descshow.value==true){"Show Less"}else{"Show More"}, color = GlobalStrings.CustomerColorMain,fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.size(15.dp))
                        Text(text = "Social Links", fontSize = 16.sp, fontWeight = FontWeight.W600)
                        Spacer(modifier = Modifier.size(15.dp))
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Femail.png?alt=media&token=389e7cff-3268-4c97-a115-29b9234a5e0d&_gl=1*1kcjhwe*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDU5MzguNjAuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(25.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.size(15.dp))
                            Column {
                                Text(text = "Email", color = Color.Gray, fontSize = 12.sp)
                                Text(text = hotel.facebookURL, color = Color.Black, fontSize = 14.sp, lineHeight = 15.sp)
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Ftelephone.png?alt=media&token=40116725-d680-4382-bc01-5fcb28e65b98&_gl=1*w5rdiy*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDYyOTcuNjAuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(25.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.size(15.dp))
                            Column {
                                Text(text = "Telephone", color = Color.Gray, fontSize = 12.sp)
                                Text(text = hotel.telephoneNo, color = Color.Black, fontSize = 14.sp, lineHeight = 15.sp)
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Fphone.png?alt=media&token=6a600beb-78ae-496a-9743-7d42d038e623&_gl=1*flafsv*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDY0MjAuNjAuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(25.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.size(15.dp))
                            Column {
                                Text(text = "Phone", color = Color.Gray, fontSize = 12.sp)
                                Text(text = hotel.phoneNo, color = Color.Black, fontSize = 14.sp, lineHeight = 15.sp)
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Ffacebook.png?alt=media&token=dc207d4f-a1fa-44b9-8193-086d2fe2f4fc&_gl=1*1hktox3*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDY1MTUuNjAuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(25.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.size(15.dp))
                            Column {
                                Text(text = "Facebook", color = Color.Gray, fontSize = 12.sp)
                                Text(text = hotel.facebookURL, color = Color.Black, fontSize = 14.sp, lineHeight = 15.sp)
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Finstagram.png?alt=media&token=d26fc3ab-76b8-4a6c-a4ca-ef0dcb843c5b&_gl=1*1pyemye*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDY1NDIuMzMuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(25.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.size(15.dp))
                            Column {
                                Text(text = "Instagram", color = Color.Gray, fontSize = 12.sp)
                                Text(text = hotel.instagramURL, color = Color.Black, fontSize = 14.sp, lineHeight = 15.sp)
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Ftwitter.png?alt=media&token=fd39f1b5-cb24-4f88-8180-d27823595b57&_gl=1*3nuhvy*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDY2MDAuNjAuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(25.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.size(15.dp))
                            Column {
                                Text(text = "Twitter", color = Color.Gray, fontSize = 12.sp)
                                Text(text = "twitter.com/asd", color = Color.Black, fontSize = 14.sp, lineHeight = 15.sp)
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Fyoutube.png?alt=media&token=6ca7e4b5-aecd-4db0-a176-5a68cd6c1dd9&_gl=1*1rzbm*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDY2MTcuNDMuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(25.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.size(15.dp))
                            Column {
                                Text(text = "Youtube", color = Color.Gray, fontSize = 12.sp)
                                Text(text = "www.youtubr.com/asdasd", color = Color.Black, fontSize = 14.sp, lineHeight = 15.sp)
                            }
                        }
                    }
                }
            }
            if(selectedTab.value==2){
                item{
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())){
                            for(room in rooms){
                                Box(modifier = Modifier.padding(horizontal=15.dp)){
                                    Box(modifier = Modifier
                                        .clickable {
                                            navigator.push(ViewRoom(room, hotel.name, hotel._id))
                                        }
                                        .width(300.dp)
                                        // .background(Color(0xFFFEFEFE))
                                        .border(
                                            0.2.dp,
                                            Color(0xFFB6B4BB),
                                            RoundedCornerShape(15.dp)
                                        )
                                    ){
                                        Column(
                                            Modifier
                                                .fillMaxWidth()){
                                            CustomSlider(sliderList = room.images, imageModifier = Modifier
                                                .height(220.dp)
                                                .clip(
                                                    RoundedCornerShape(
                                                        (CornerSize(
                                                            10.dp
                                                        ))
                                                    )
                                                ))
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Text(text = room.type, fontSize = 15.sp, fontWeight = FontWeight.W700, letterSpacing = 0.2.sp)
                                                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                                                    Row(verticalAlignment = Alignment.Bottom){
                                                        Icon(
                                                            Icons.Outlined.Home, contentDescription =null, tint = GlobalStrings.CustomerColorMain, modifier = Modifier.size(18.dp)
                                                        )
                                                        Text(text = room.floor, fontSize = 12.sp)
                                                        //  Text(text = "("+"141"+") reviews", fontSize = 8.sp)
                                                    }
                                                    Row(verticalAlignment = Alignment.Bottom){
                                                        Icon(
                                                            painterResource(id = R.drawable.bed), contentDescription =null, tint = GlobalStrings.CustomerColorMain, modifier = Modifier.size(18.dp)
                                                        )
                                                        Text(text = (room.adults+room.children).toString()+" Guests", fontSize = 12.sp)
                                                        //  Text(text = "("+"141"+") reviews", fontSize = 8.sp)
                                                    }
                                                    Row(verticalAlignment = Alignment.Bottom){
                                                        Icon(
                                                            painterResource(id = R.drawable.size), contentDescription =null, tint = GlobalStrings.CustomerColorMain, modifier = Modifier.size(18.dp)
                                                        )
                                                        Text(text = room.size.toString()+" sqft", fontSize = 12.sp)
                                                        //  Text(text = "("+"141"+") reviews", fontSize = 8.sp)
                                                    }
                                                }
                                                Spacer(modifier = Modifier.size(10.dp))
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                                    Text(text = "Rs. "+room.price+"/Night", fontSize = 14.sp, fontWeight = FontWeight.W600)
                                                    Box(modifier = Modifier
                                                        .clickable {
                                                            AddRoomInCart(
                                                                context,
                                                                hotel._id,
                                                                room._id,
                                                                hotel.name
                                                            )
                                                        }
                                                        .background(
                                                            GlobalStrings.CustomerColorMain,
                                                            RoundedCornerShape(5.dp)
                                                        )
                                                        .padding(5.dp)
                                                    ){
                                                        Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.height(20.dp)){
                                                            Text(text = "Add To Cart", fontSize = 14.sp, fontWeight = FontWeight.W500,color=Color.White)
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
                }
            }
            if(selectedTab.value==3){
                item{
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())){
                        for(service in hotel.services){
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
                }
            }
            if(selectedTab.value==4){
                item{
                    val review = remember {
                        mutableStateOf("")
                    }
                    val isErrorReview = remember {
                        mutableStateOf(false)
                    }
                    val title = remember {
                        mutableStateOf("")
                    }
                    val isErrorTitle = remember {
                        mutableStateOf(false)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row (modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Bottom){
                        Text(text = hotel.averageRating.toString()+".0", fontSize = 36.sp, fontWeight = FontWeight.W800)
                        Spacer(modifier = Modifier.size(5.dp))
                        Column {
                            Row {
                                for(i in 0 until 5){
                                    Icon(
                                        Icons.Outlined.Star, contentDescription =null, tint = Color(
                                            0xFFFFC107
                                        ), modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            Text(text = "(Based on ${hotel.totalReviews} reviews)", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .horizontalScroll(rememberScrollState())){
                        for(review in hotel.reviews){
                            Box(modifier = Modifier.padding(horizontal=5.dp)){
                                Box(modifier = Modifier
                                    .width(280.dp)
                                    .height(170.dp)
                                    // .background(Color(0xFFFEFEFE))
                                    .border(
                                        0.2.dp,
                                        Color(0xFFB6B4BB),
                                        RoundedCornerShape(10.dp)
                                    )
                                ){
                                    Column(
                                        Modifier
                                            .fillMaxWidth()){
                                        Row (modifier = Modifier
                                            .padding(5.dp)
                                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                            Row{
                                                Image(
                                                    painter = rememberAsyncImagePainter(review.customer.profile),
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
                                                Spacer(modifier = Modifier.size(10.dp))
                                                Column {
                                                    Text(text = review.customer.name, fontWeight = FontWeight.W700, fontSize = 14.sp)
                                                    Text(text = review.title, fontWeight = FontWeight.W400, fontSize = 11.sp, color = Color.Gray)
                                                }
                                            }
                                            Row(verticalAlignment = Alignment.Bottom){
                                                Icon(
                                                    Icons.Outlined.Star, contentDescription =null, tint = Color(
                                                        0xFFFFC107
                                                    ), modifier = Modifier.size(22.dp)
                                                )
                                                Text(text = review.stars.toString(), fontSize = 15.sp)
                                            }

                                        }
                                        Column(modifier = Modifier
                                            .padding(10.dp)
                                            .height(50.dp)) {
                                            Box(modifier = Modifier
                                                .fillMaxWidth()){
                                                Text(text = review.description, fontSize = 14.sp, fontWeight = FontWeight.W300, letterSpacing = 0.2.sp, color = Color.Black, lineHeight = 15.sp)
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
//                    Column(modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(10.dp)) {
//                        Spacer(modifier = Modifier.size(10.dp))
//                        OutlinedTextField(
//                            shape = RoundedCornerShape(5.dp),
//                            colors = TextFieldDefaults.outlinedTextFieldColors(
//                                focusedBorderColor = Color.Black,
//                                unfocusedBorderColor = Color.Gray,
//                                errorBorderColor = Color.Red,
//                                placeholderColor = Color.Gray,
//                                disabledPlaceholderColor = Color.Gray
//                            ),
//                            value = title.value,
//                            onValueChange = {
//                                if (it.length <= 300)
//                                    title.value=it
//                            },
//                            label = {
//                                Text(text = "Title",color = Color.Gray)
//                            },
//                            placeholder = {
//                                Text(text = "Title of Review")
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                        )
//                        if (isErrorTitle.value) {
//                            Text(
//                                text = "Review Title should be more than 30 characters.",
//                                color = Color.Red,
//                                fontSize = 11.sp
//                            )
//                        }
//                        Spacer(modifier = Modifier.size(10.dp))
//                        OutlinedTextField(
//                            shape = RoundedCornerShape(5.dp),
//                            colors = TextFieldDefaults.outlinedTextFieldColors(
//                                focusedBorderColor = Color.Black,
//                                unfocusedBorderColor = Color.Gray,
//                                errorBorderColor = Color.Red,
//                                placeholderColor = Color.Gray,
//                                disabledPlaceholderColor = Color.Gray
//                            ),
//                            value = review.value,
//                            onValueChange = {
//                                if (it.length <= 300)
//                                    review.value=it
//                            },
//                            label = {
//                                Text(text = "Review",color = Color.Gray)
//                            },
//                            placeholder = {
//                                Text(text = "Give Us a Review")
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                        )
//                        if (isErrorReview.value) {
//                            Text(
//                                text = "Review should be more than 30 characters.",
//                                color = Color.Red,
//                                fontSize = 11.sp
//                            )
//                        }
//                        Spacer(modifier = Modifier.size(10.dp))
//                        var rating = remember {
//                            mutableStateOf(5)
//                        }
//                        Row(modifier = Modifier){
//                            for(i in 0 until 5){
//                                Box(modifier = Modifier.clickable {
//                                    rating.value= i+1
//                                    Log.d("HHHHHHHH",rating.value.toString())
//                                }){
//                                    Icon(
//                                        Icons.Outlined.Star, contentDescription =null, tint = if(i>=rating.value){
//                                            Color(0xFF9B9B9B)
//                                                                                                          }else{
//                                            Color(
//                                                0xFFFFC107
//                                            )
//                                                                                                               }, modifier = Modifier.size(22.dp)
//                                    )
//                                }
//                            }
//                        }
//                        Spacer(modifier = Modifier.size(10.dp))
//                        Box(modifier = Modifier
//                            .clickable {
//                                isErrorReview.value = review.value.length < 29
//                                isErrorReview.value = title.value == ""
//                                if (!isErrorReview.value && !isErrorTitle.value) {
//                                    AddReviewToSystem(
//                                        context = context,
//                                        customerid = user._id,
//                                        stars = rating.value,
//                                        description = review.value,
//                                        hotelid = hotel._id,
//                                        title = title.value
//                                    ) {
//                                        if (it) {
//                                            navigator.replace(ViewReview)
//                                        }
//                                    }
//
//                                }
//                            }
//                            .background(
//                                GlobalStrings.CustomerColorMain,
//                                RoundedCornerShape(15.dp)
//                            )
//                            .fillMaxWidth()
//                            .height(45.dp), contentAlignment = Alignment.Center){
//                            Text(text = "Add Review", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
//                        }
//                    }

                }
            }
            if(selectedTab.value==5){
                item{
                    Spacer(modifier = Modifier.size(20.dp))
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                        GoogleMap(
                            modifier = Modifier
                                .width(screenWidthDp - 20.dp)
                                .height(300.dp),
//                                        .layout { measurable, constraints ->
//                                            val placeable = measurable.measure(constraints)
//                                            layout(placeable.width, (placeable.height * 0.9).toInt()) {
//                                                placeable.placeRelative(0, 0)
//                                            }
//                                        },
                            cameraPositionState = cameraPositionState,
                            properties = mapProperties,
                        ){

                            MapEffect() { map ->
                                map.setOnMapLoadedCallback {
                                    val icon12 = MapsCoupon.bitmapDescriptorFromVector(
                                        context, R.mipmap.houseicon,80
                                    )
                                    val circleOptions = CircleOptions().radius(1.0).center(LatLng(33.68956310667974, 72.98929098744796))
                                    val markerOptions = MarkerOptions()
                                        .position(LatLng(hotel.lat, hotel.lng)).icon(icon12)
                                    map.addMarker(markerOptions)
                                    map.addCircle(CircleOptions()
                                        .fillColor(0x51FF3D3D)
                                        .strokeColor(0x51FF3D3D)
                                        .center(LatLng(hotel.lat, hotel.lng))
                                        .radius(300.0)
                                    )
                                    Log.d("HHHHHASDASdasdas","enter")
                                }

                            }
                        }
                    }
                }
            }
            if(selectedTab.value==6){
                item{
                    Box(modifier = Modifier.padding(10.dp)){
                        Box(modifier = Modifier
                            .background(Color(0xFDF1ECFA), RoundedCornerShape(15.dp))
                            .padding(10.dp)
                            .fillMaxWidth()){
                            Column() {
                                Text(text = "Special Sunday", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                for(i in 0 until 5){
                                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically){
                                        Image(
                                            painter = rememberAsyncImagePainter("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLJY69KiD8GFtsyFj0vG9C5m8_y3cyBOcOTkEeAWG5V0M3xxk5WFZdLN7l4F-iMQ7FwSc&usqp=CAU"),
                                            contentDescription = "image",
                                            modifier = Modifier
                                                .size(35.dp)
                                                .clip(
                                                    RoundedCornerShape(
                                                        (CornerSize(
                                                            20.dp
                                                        ))
                                                    )
                                                ),
                                            contentScale = ContentScale.FillBounds
                                        )
                                        Spacer(modifier = Modifier.size(15.dp))
                                        Column{
                                            //Text(text =user.firstname+" "+user.lastName, fontSize = 18.sp, fontWeight = FontWeight.Light)
                                            Text(text = "Sea Food", fontSize = 12.sp, fontWeight = FontWeight.Light, color = Color.Black
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(30.dp))
                                    }
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                    Box(modifier = Modifier
                                        .width(100.dp)
                                        .height(25.dp)
                                        .background(
                                            GlobalStrings.CustomerColorMain,
                                            RoundedCornerShape(10.dp)
                                        ), contentAlignment = Alignment.Center){
                                        Text(text = "Rs. 6000", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(selectedTab.value==7){
                item{
                    var desc = hotel.refundPolicy.description
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                        Text(text = if(descshow.value==true){desc}else{desc.substring(0,(desc.length)/2)}, fontSize = 14.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Black)
                        Box(modifier = Modifier.clickable { descshow.value=!descshow.value }){
                            Text(text = if(descshow.value==true){"Show Less"}else{"Show More"}, color = GlobalStrings.CustomerColorMain,fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.size(15.dp))
                        for(policy in hotel.refundPolicy.refunds){
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)){
                                Image(
                                    rememberAsyncImagePainter(model = "https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Fspeedometer.png?alt=media&token=1c5edf2a-304f-46c3-929e-614b794ea6dd&_gl=1*y4cxpf*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTQ1MTg3My4zMy4xLjE2OTk0NTE4ODUuNDguMC4w"),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(RoundedCornerShape(40.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(10.dp))
                                Row{
                                    Text(text = "${policy.percentage}%" , fontWeight = FontWeight.W800)
                                    Text(text = " refund in ", fontWeight = FontWeight.W200)
                                    Text(text = policy.days.toString(), fontWeight = FontWeight.W800)
                                    Text(text = " Days", fontWeight = FontWeight.W200)
                                }
                            }
                        }
                    }
                }
            }


            getRoomByHotel(context=context, hotelid = hotel._id)

        }


    }

    fun AddReviewToSystem(context: Context, customerid : String,stars: Int,description: String, hotelid: String,title:String, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}customer/reviews/addReview"


        // Request parameters
        val params = JSONObject()
        params.put("customerid", customerid)
        params.put("stars", stars)
        params.put("hotelid", hotelid)
        params.put("description", description)
        params.put("title", title)
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
                Request.Method.POST, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("ASDWFVSA", response.toString())
                    Toast
                        .makeText(
                            context,
                            "Review Added",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    progressDialog.dismiss()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("ASDWFVSA", error.toString())
                    Log.e("ASDWFVSA", error.networkResponse.statusCode.toString())
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
    fun AddToWishlist(context: Context, customerid : String, hotelid: String,callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}customer/hotels/wishlist/addHotel"


        // Request parameters
        val params = JSONObject()
       // params.put("customerid", customerid)
        params.put("hotelid", hotelid)
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
                Request.Method.POST, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("ASDWFVSA", response.toString())
                    Toast
                        .makeText(
                            context,
                            "Added To WishList",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    progressDialog.dismiss()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("ASDWFVSA", error.toString())
                    Log.e("ASDWFVSA", error.networkResponse.statusCode.toString())
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
    fun RemoveToWishlist(context: Context, customerid : String, hotelid: String,callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}customer/hotels/wishlist/deleteHotel"


        // Request parameters
        val params = JSONObject()
        // params.put("customerid", customerid)
        params.put("hotelid", hotelid)
        Log.d("ASDWFVSA11", params.toString())
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
                Request.Method.DELETE, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("ASDWFVSA11", response.toString())
                    Toast
                        .makeText(
                            context,
                            "Removed from WishList",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    progressDialog.dismiss()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("ASDWFVSA", error.toString())
                    Log.e("ASDWFVSA", error.networkResponse.statusCode.toString())
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
    // GET Categories Function
    fun getRoomByHotel(context: Context,hotelid:String) {
        if(rooms.isNotEmpty()){
            return
        }
        val url = "${GlobalStrings.baseURL}customer/rooms/getHotelRooms/${hotelid}"
        Log.d("AAAAA",url)
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
            val request =
            @SuppressLint("SuspiciousIndentation")
            object : JsonObjectRequest(
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
                            inventories.add(inventory.get(i).toString())
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
                          var servieee  = dd.getJSONArray("services")
                        services.clear()
                        for(i in 0 until servieee.length()){
                            var category = servieee.getJSONObject(i)

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
//                            var serviceCa = category.getJSONObject("serviceCategory")
//                            var serviceCategoryID = serviceCa.getString("_id")
//                            var serviceCategoryTitle = serviceCa.getString("title")
//                            var serviceCategoryImage = serviceCa.getString("image")
//                            var serviceCategoryDeletedAt = serviceCa.getString("deletedAt")
//                            var serviceCategoryIsDeleted = serviceCa.getBoolean("isDeleted")
//                            var serviceCategoryCreatedAt = serviceCa.getString("createdAt")
//                            var serviceCategoryUpdatedAt = serviceCa.getString("updatedAt")
//                            var serviceCategory__V = serviceCa.getInt("__v")
                            var serviceCategoryObject =
                                ServiceCategory(
                                    _id = "serviceCategoryID",
                                    title = "serviceCategoryTitle",
                                    image = "serviceCategoryImage",
                                    isDeleted = false,
                                    deletedAt = "serviceCategoryDeletedAt",
                                    createdAt = "serviceCategoryCreatedAt",
                                    updatedAt = "serviceCategoryUpdatedAt",
                                    __v = 0
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
                        var room = RoomCustomerBooking(_id=_id,adults=adults,children=children,description=hotelname, floor = floor,images=imagesroom, inventories = inventories,price=price,roomNumber=roomNumber, services = services,size=size,type=type, videos = imagesroom
                        )
                                rooms.add(
                                    room
                                )

                    }
                    progressDialog.dismiss()
                    Log.d("HASHDASDAS555","HOgya")
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