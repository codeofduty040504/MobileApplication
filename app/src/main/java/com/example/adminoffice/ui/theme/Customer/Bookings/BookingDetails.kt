package com.example.adminoffice.ui.theme.Customer.Bookings

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Customer.CartFunctions.Services
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Booking
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import java.text.SimpleDateFormat
import java.util.Calendar

data class BookingDetails(
    val booking: Booking
)  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController

    private var userLocation = LatLng(0.0, 0.0)

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

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column (modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(10.dp)){
               Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
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
                           .padding(start = 10.dp)
                           .width(screenWidthDp - 140.dp),
                   ) {
                       Text(text = booking.hotelName, fontSize = 16.sp, fontWeight = FontWeight.Black)
                       Text(text = booking.hotel.city, fontSize = 12.sp, fontWeight = FontWeight.ExtraLight, color = Color.Gray)

                   }
               }
                Spacer(modifier = Modifier.size(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Column{
                        Spacer(modifier = Modifier.size(2.dp))
                        Text(text = "CHECK IN", fontSize = 12.sp, fontWeight = FontWeight.ExtraLight, color = Color.Gray)
                        Text(text = booking.checkInDate.substring(0,10), fontSize = 18.sp, fontWeight = FontWeight.Black)
                    }
                    Column(horizontalAlignment = Alignment.End){
                        Spacer(modifier = Modifier.size(2.dp))
                        Text(text = "CHECK OUT", fontSize = 12.sp, fontWeight = FontWeight.ExtraLight, color = Color.Gray)
                        Text(text = booking.checkOutDate, fontSize = 18.sp, fontWeight = FontWeight.Black)
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))

                Text(text = "Rooms", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.size(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Box(Modifier.width(150.dp), contentAlignment = Alignment.BottomStart){
                        Text(text = "Room Number", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                    }
                    Box(Modifier.width(150.dp), contentAlignment = Alignment.BottomCenter){
                        Text(text = "Type", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                    }
                    Box(Modifier.width(150.dp), contentAlignment = Alignment.BottomEnd){
                        Text(text = "Price", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                    }
                }
                Divider(Modifier.fillMaxWidth(),
                    0.5.dp,Color.Gray
                )
                for(room in booking.rooms){
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Box(Modifier.width(150.dp), contentAlignment = Alignment.BottomStart){
                            Text(text = room.roomNumber.toString(), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color.Gray)
                        }
                        Box(Modifier.width(150.dp), contentAlignment = Alignment.BottomCenter){
                            Text(text = room.type.toString(), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color.Gray)
                        }
                        Box(Modifier.width(150.dp), contentAlignment = Alignment.BottomEnd){
                            Text(text = room.price.toString(), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color.Gray)
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Box(Modifier, contentAlignment = Alignment.BottomStart){
                        Text(text = "Services", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color.Gray)
                    }
                    Box(Modifier, contentAlignment = Alignment.BottomCenter){
                        Text(text = booking.serviceCharges.toString(), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = Color.Gray)
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = GlobalStrings.CustomerColorMain,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .height(40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Total Bill", fontSize = 14.sp, fontWeight = FontWeight.Light, color = Color.White, modifier = Modifier.padding(horizontal = 5.dp))


                    Text(text = "Rs. "+(booking.serviceCharges+booking.roomCharges).toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 5.dp))
                }
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
                                val markerOptions = MarkerOptions()
                                    .position(LatLng(booking.hotel._id.toDouble(), booking.hotel.name.toDouble())).icon(icon12)
                                map.addMarker(markerOptions)
                                map.addCircle(
                                    CircleOptions()
                                    .fillColor(0x51FF3D3D)
                                    .strokeColor(0x51FF3D3D)
                                    .center(LatLng(33.333333, 73.333333))
                                    .radius(300.0)
                                )
                                Log.d("HHHHHASDASdasdas","enter")
                            }

                        }
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(text = "Refund Policy", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Text(text = booking.hotel.refundPolicy.description, fontSize = 12.sp, fontWeight = FontWeight.Light)
                for(ref in booking.hotel.refundPolicy.refunds){
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
                            Text(text = ref.percentage.toString() , fontWeight = FontWeight.W800)
                            Text(text = " refund in ", fontWeight = FontWeight.W200)
                            Text(text = ref.days.toString(), fontWeight = FontWeight.W800)
                            Text(text = " Days", fontWeight = FontWeight.W200)
                        }
                    }
                }
                Text(text = "Booking Details", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Text(text = booking.bookingDetails, fontSize = 12.sp, fontWeight = FontWeight.Light)

            }
        }

    }
}