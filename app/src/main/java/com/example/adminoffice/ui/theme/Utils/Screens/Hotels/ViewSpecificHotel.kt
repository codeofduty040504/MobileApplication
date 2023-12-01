package com.example.adminoffice.ui.theme.Utils.Screens.Hotels

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Hotel
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import kotlinx.coroutines.launch

import androidx.compose.ui.unit.sp
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Utils.DrawerUni
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.AddExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewProfit
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.AddInventory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.AddInventoryCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.ViewInventory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.ViewInventoryCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.AddDish
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.AddDishCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.AddMenu
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.ViewDish
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.ViewDishCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Menus.ViewMenu
import com.example.adminoffice.ui.theme.Utils.Screens.Payments.ViewPayments
import com.example.adminoffice.ui.theme.Utils.Screens.Payments.ViewRefunds
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.AddRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.ViewRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.AddRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.ViewRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy

data class ViewSpecificHotel(
    val Hotel : Hotel
)  : Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember { mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        ModalNavigationDrawer(
            drawerContent = {
                DrawerUni(scope,drawerState)
            },
            drawerState = drawerState,
        ) {
            Scaffold(
                topBar = {
                    CustomTopAppBar {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }
            )
            {
                Column(modifier = Modifier.padding(start = 0.dp, top = 60.dp, end = 0.dp, bottom = 0.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .horizontalScroll(rememberScrollState()),
                    ){
                        for(i in 0 until Hotel.images.size){
                            Image(rememberAsyncImagePainter(model = Hotel.images[i]), contentDescription = null, modifier = Modifier
                                .width(500.dp)
                                .height(400.dp), contentScale = ContentScale.FillBounds)
                        }
                    }
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = Hotel.name, fontSize = 20.sp,fontWeight=FontWeight.SemiBold)
                        Spacer(Modifier.height(10.dp))
                        Text(text = Hotel.description, fontSize = 16.sp,fontWeight=FontWeight.Medium)
                        var selectedTabIndex by remember { mutableStateOf(0) }
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            containerColor = Color.White,
                            contentColor = Color.Black,
                        ) {
                            Tab(
                                selected = selectedTabIndex == 0,
                                onClick = { selectedTabIndex = 0 },
                                text = { Text("Floors") },
                                selectedContentColor = GlobalStrings.AdminColorMain,
                                unselectedContentColor = Color.Black,
                            )
//                            Tab(
//                                selected = selectedTabIndex == 1,
//                                onClick = {
//                                    selectedTabIndex = 1
//                                },
//                                text = { Text("Rooms") },
//                                selectedContentColor = GlobalStrings.AdminColorMain,
//                                unselectedContentColor = Color.Black,
//                            )
                            Tab(
                                selected = selectedTabIndex == 1,
                                onClick = {
                                    selectedTabIndex = 1
                                },
                                text = { Text("Refund") },
                                selectedContentColor = GlobalStrings.AdminColorMain,
                                unselectedContentColor = Color.Black,
                            )
                            Tab(
                                selected = selectedTabIndex == 2,
                                onClick = {
                                    selectedTabIndex = 2
                                },
                                text = { Text("Services") },
                                selectedContentColor = GlobalStrings.AdminColorMain,
                                unselectedContentColor = Color.Black,
                            )
                        }

                        when (selectedTabIndex) {
                            0 ->  Column {
                                val chunkedImageUris = Hotel.floors.chunked(2)

                                Column (Modifier.verticalScroll(rememberScrollState())){
                                    for (row in chunkedImageUris) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            for (floor in row) {
                                                Row(verticalAlignment = Alignment.CenterVertically){
                                                    Image(
                                                        rememberAsyncImagePainter(model = "https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/checkmark.png?alt=media&token=f1df8c89-e0e7-4939-9df3-4b443c33fe99"),
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .size(30.dp)
                                                            .clip(RoundedCornerShape(40.dp)),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Text(text = floor)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
//                            1 -> Column {
//                                Text(text = "Rooms")
//                            }
                            1 -> Column(Modifier.verticalScroll(rememberScrollState())) {
                                Text(text = "Refund Policy Description", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                               Text(text = Hotel.refundPolicy.description)
                                for(refund in Hotel.refundPolicy.refunds){
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(15.dp)){
                                        Image(
                                            rememberAsyncImagePainter(model = "https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/cashback.png?alt=media&token=f2a1ee16-f007-4ad9-838f-f67defd183b7"),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .clip(RoundedCornerShape(40.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(Modifier.width(10.dp))
                                        Text(text = refund.percentage.toString() +" refund in "+refund.days.toString()+" Days")
                                    }
                                }
                            }
                            2 -> Column {
                                for(service in Hotel.services){
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                        .padding(15.dp)
                                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                        Row{
                                            Image(
                                                rememberAsyncImagePainter(model = service.image),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .clip(RoundedCornerShape(40.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(text = service.name, fontSize = 14.sp, fontWeight = FontWeight.W800)
                                                Text(text = service.description, fontSize = 10.sp, fontWeight = FontWeight.Light)
                                            }
                                        }
                                            Row {
                                                Text(text = "Rs. ", color = Color.Red, fontSize = 14.sp)
                                                Text(text = service.price.toString(), fontSize = 16.sp)
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