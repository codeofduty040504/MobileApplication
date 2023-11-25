package com.example.adminoffice.ui.theme.Utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun Header(first:Painter,second:String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(first, contentDescription = second, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = second, fontSize = 14.sp)
    }
}

@Composable
fun SubHeader(subItem:String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(32.dp))
        Text(text = subItem, fontSize = 14.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Logo(scope:CoroutineScope,drawerState:DrawerState){
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Image(
            painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2FAsset%201.png?alt=media&token=f9d050ca-ee90-4fe3-a44d-fe9d896ff3f4&_gl=1*khoe9m*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NzcyMzYxOS4zMC4xLjE2OTc3MjM4OTguNjAuMC4w"),
            contentDescription = "image",
            modifier = Modifier
                .width(50.dp).height(50.dp)
                .clip(RoundedCornerShape((CornerSize(10.dp)))),
            contentScale = ContentScale.FillBounds
        )
        IconButton(onClick = { scope.launch {
            drawerState.close()
        } }) {
            Icon(painter = painterResource(id = R.drawable.close), contentDescription = "")
        }
    }
}


@Composable
fun Menu(): List<Triple<Painter, String, List<String>>> {
    val menuList = listOf(
        Triple(painterResource(id = R.drawable.home), "1-Dashboard", listOf()),
        Triple(painterResource(id = R.drawable.user), "2-Users", listOf("2.1-Add User", "2.2-View Users")),
        Triple(painterResource(id = R.drawable.tv), "3-Services", listOf("3.1-Add Category", "3.2-View Category","3.3-Add Service", "3.4-View Services")),
        Triple(painterResource(id = R.drawable.hotel), "4-Hotels", listOf("4.1-Add Hotel", "4.2-View Hotels", "4.3-Add Room","4.4-View Rooms")),
        Triple(painterResource(id = R.drawable.inventory), "5-Inventory", listOf("5.1-Add Category", "5.2-View Category", "5.3-Add Inventory","5.4-View Inventory")),
        Triple(painterResource(id = R.drawable.discount) , "6-Coupons", listOf("6.1-Add Coupon","6.2-View Coupon")),
        Triple(painterResource(id = R.drawable.bookmark), "7-Bookings", listOf("7.1-Add Booking", "7.2-View Booking")),
        Triple(painterResource(id = R.drawable.money), "8-Payments", listOf("8.1-View Payments", "8.2-View Refunds")),
        Triple(painterResource(id = R.drawable.menu), "9-Menu", listOf("9.1-Add Category", "9.2-View Category", "9.3-Add Dish","9.4-View Dish","9.5-Add Menu","9.6-View Menu")),
        Triple(painterResource(id = R.drawable.feedback), "10-Feedback", listOf("10.1-Add Review", "10.2-View Review")),
        Triple(painterResource(id = R.drawable.chat), "11-Chats", listOf()),
        Triple(painterResource(id = R.drawable.account), "12-Accounting", listOf("12.1-Add Revenue", "12.2-View Revenue", "12.3-Add Expense","12.4-View Expense","12.5-View Profit")),
       // Triple(painterResource(id = R.drawable.staff), "13-Staffs", listOf("13.1-Add Category", "13.2-View Category", "13.3-Add Staff","13.4-View Staff")),
        Triple(painterResource(id = R.drawable.settings), "13-Settings", listOf("13.1-About Us", "13.2-FAQ", "13.3-Policies")),
    )
    return menuList
}