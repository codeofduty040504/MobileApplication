package com.example.adminoffice.ui.theme.Customer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel
import com.example.adminoffice.ui.theme.Customer.Functions.HotelScreen
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.ViewHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState

data class ViewHotelOnMaps(
    val Hotels: SnapshotStateList<Hotel>
)  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    private var userLocation = LatLng(0.0, 0.0)
    var modalopen = mutableStateOf(false)
    lateinit var CurrentHotel : Hotel
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(33.6844, 73.0479), 10f)

        }

        val mapProperties = MapProperties(
            isMyLocationEnabled = userLocation != null,
        )
        if(modalopen.value==true){
            CustomProgressDialog(hotel = CurrentHotel)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties,
                ){

                    MapEffect(userLocation) { map ->
                        map.setOnMarkerClickListener {
                            for(hotel in  Hotels){
                                if(it.position.latitude==hotel.lat && it.position.longitude==hotel.lng){
                                    CurrentHotel=hotel
                                    Log.d("HPPPPP",hotel.name)
                                    modalopen.value=true
                                }
                            }
                            false
                            }
                        map.setOnMapLoadedCallback {
                            val icon12 = MapsCoupon.bitmapDescriptorFromVector(
                                context, R.mipmap.houseicon,80
                            )
                            for(hotel in Hotels){
                                val markerOptions = MarkerOptions()
                                    .position(LatLng(hotel.lat, hotel.lng)).icon(icon12).title(hotel.name.toString())
                                map.addMarker(markerOptions)
                                map.addCircle(
                                    CircleOptions()
                                    .fillColor(0x51FF3D3D)
                                    .strokeColor(0x51FF3D3D)
                                    .center(LatLng(hotel.lat, hotel.lng))
                                    .radius(300.0)
                                )

                            }

                        }

                    }
                }
            }
        }

    }
    @Composable
    fun CustomProgressDialog(hotel: Hotel) {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        Dialog(
            onDismissRequest = { modalopen.value =false}
        ) {
            Box(modifier = Modifier.clickable {
                navigator.replace(HotelScreen(hotel))
            }){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomSliderHotel(sliderList = hotel.images, imageModifier = Modifier.height(150.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                        Text(text = hotel.name, fontSize = 18.sp, fontWeight = FontWeight.W800, lineHeight = 17.sp)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                            Row(verticalAlignment = Alignment.Bottom){
//                            Image(
//                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Flocation.png?alt=media&token=a6475cc6-a856-40ba-8340-08bf9399c610&_gl=1*16269ov*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTIwNDU3Mi4zMi4xLjE2OTkyMDQ2MDEuMzEuMC4w"),
//                                contentDescription = "image",
//                                modifier = Modifier
//                                    .size(20.dp),
//                                contentScale = ContentScale.FillBounds
//                            )
                                Icon(painterResource(id = R.drawable.location), contentDescription = null, tint = GlobalStrings.CustomerColorMain,modifier=Modifier.size(20.dp))

                                Text(text = hotel.location, fontSize = 12.sp, fontWeight = FontWeight.W400, lineHeight = 13.sp, color = Color.Gray)
                            }

                        }
                        Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom){
                            Icon(
                                Icons.Outlined.Star, contentDescription =null, tint = Color(
                                    0xFFFFC107
                                ), modifier = Modifier.size(22.dp)
                            )
                            Text(text = hotel.averageRating.toString(), fontSize = 15.sp)
                            Text(text = "("+hotel.totalReviews+") reviews", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}