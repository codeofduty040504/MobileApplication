package com.example.adminoffice.ui.theme.Customer.Coupons


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Customer.Wishlist.WishList

object CouponsList  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                //Text(text = "View Bookings")
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                IconButton(onClick = {
                    navigator.pop()
                     navigator.push(CouponsList)
                }, modifier = Modifier.padding(10.dp)) {
                    Icon(painter = rememberVectorPainter(Icons.Outlined.LocationOn), contentDescription = "AR", tint = Color.White)
                }
                IconButton(onClick = {}, modifier = Modifier.padding(10.dp)) {
                    Icon(painter = rememberVectorPainter(Icons.Outlined.DateRange), contentDescription = "Booking", tint = Color.White)
                }
                IconButton(onClick = {
                    navigator.pop()
                    // navigator.push(LandingPage)
                }, modifier = Modifier.padding(10.dp)) {
                    Icon(painter = rememberVectorPainter(Icons.Filled.Home), contentDescription = "Landing", tint = Color.White)
                }
                IconButton(onClick = {
                    navigator.pop()
                     navigator.push(WishList)
                }, modifier = Modifier.padding(10.dp)) {
                    Icon(painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder), contentDescription = "WishList", tint = Color.White)
                }
                IconButton(onClick = {
                    navigator.pop()
                    navigator.push(ViewProfile)
                }, modifier = Modifier.padding(10.dp)) {
                    Icon(painter = rememberVectorPainter(Icons.Outlined.Person), contentDescription = "Account", tint = Color.White)
                }
            }
        }

    }
}