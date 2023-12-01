package com.example.adminoffice.ui.theme.Customer.Profile

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
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
import com.example.adminoffice.ui.theme.Customer.Bookings.ViewBookingsCustomer
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable

object ReviewsCustomer  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var user = getUserFromLocal(context)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.Center){
                Text(text = "Reveiws", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Divider(color = Color(0xFFE2E2E2))
            Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                for(i in 0 until 5){
                    Box(modifier = Modifier
                        .padding(20.dp)
                        .border(0.4.dp, Color(0xFFDFDFDF), RoundedCornerShape(15.dp))){
                        Column(modifier = Modifier.padding(5.dp)) {
                            Row(modifier = Modifier.padding(vertical =10.dp), verticalAlignment = Alignment.CenterVertically){
                                Image(
                                    painter = rememberAsyncImagePainter(if(user.profilePicture=="user"){"https://icon-library.com/images/no-user-image-icon/no-user-image-icon-9.jpg"}else{
                                        user.profilePicture}),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(40.dp)
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
                                    Text(text =user.firstname+" "+user.lastName, fontSize = 14.sp, fontWeight = FontWeight.Light)
                                    Text(text = user.email, fontSize = 10.sp, fontWeight = FontWeight.Light, color = Color.Gray
                                    )
                                }
                                Spacer(modifier = Modifier.size(30.dp))
                                Box(modifier = Modifier
                                    .height(20.dp)
                                    .width(100.dp)
                                    .background(Color(0xFF229728), RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center){
                                    Text(text = "Completed", color = Color.White, fontSize = 10.sp)
                                }
                            }
                            Box(modifier = Modifier.padding(5.dp)){
                                Text(text = "The Noise was so Loud. The Noise was so Loud. The Noise was so Loud. The Noise was so Loud. The Noise was so Loud. The Noise was so Loud. The Noise was so Loud. The Noise was so Loud. The Noise was so Loud. ", lineHeight = 17.sp)
                            }
                            Column (modifier = Modifier.padding(horizontal = 20.dp)){
                                Row(modifier = Modifier.padding(vertical =10.dp), verticalAlignment = Alignment.CenterVertically){
                                    Image(
                                        painter = rememberAsyncImagePainter(if(user.profilePicture=="user"){"https://icon-library.com/images/no-user-image-icon/no-user-image-icon-9.jpg"}else{
                                            user.profilePicture}),
                                        contentDescription = "image",
                                        modifier = Modifier
                                            .size(30.dp)
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
                                        Text(text ="Minora Hotel", fontSize = 12.sp, fontWeight = FontWeight.Light)
                                        Text(text = "0306-1231231", fontSize = 8.sp, fontWeight = FontWeight.Light, color = Color.Gray
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(30.dp))
                                }
                                Box(modifier = Modifier.padding(5.dp)){
                                    Text(text = "Easy nhi lag rha yr bilkul bhi tu. han bilkul easy nhi lag rha", fontSize = 12.sp, lineHeight = 13.sp)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}