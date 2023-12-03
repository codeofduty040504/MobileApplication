package com.example.adminoffice.ui.theme.Customer.Profile

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.example.adminoffice.ui.theme.Customer.Bookings.ViewBookingsCustomer
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Complaint
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Customer.Wishlist.WishList
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import org.json.JSONObject

object CouponsClaimed  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var Complaints = mutableStateListOf<Complaint>()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var user = getUserFromLocal(context)
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        Scaffold() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
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
                    Text(text = "Coupons", fontSize = 18.sp, fontWeight = FontWeight.Black)
                }
                Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                    for(i in 0 until 8){
                        Box(
                            modifier = Modifier
                                .width(screenWidthDp).padding(10.dp)
                        ) {
                            Box(modifier = Modifier
                                .padding(5.dp)
                                .border(
                                    0.8.dp, Color(0xD2E6E6E6),
                                    RoundedCornerShape(10.dp)
                                )){
                                Column(modifier = Modifier.fillMaxWidth()) {
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
                                                        .size(70.dp)
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
                                                        fontWeight = FontWeight.W400,
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
        }
    }

}