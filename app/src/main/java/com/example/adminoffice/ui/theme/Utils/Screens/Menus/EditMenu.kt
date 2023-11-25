package com.example.adminoffice.ui.theme.Utils.Screens.Menus


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Hotel
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.HotelOwner
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Room
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.ServiceCategory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Userid
import com.example.adminoffice.ui.theme.Utils.DataClasses.Inventories.Inventory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Inventories.InventoryCategory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Menus.Dish
import com.example.adminoffice.ui.theme.Utils.DataClasses.Menus.DishCategory
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.MaskVisualTransformation
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.AddExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewProfit
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.AddHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.ViewHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.AddInventory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.AddInventoryCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.ViewInventory
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.ViewInventoryCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Payments.ViewPayments
import com.example.adminoffice.ui.theme.Utils.Screens.Payments.ViewRefunds
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.AddRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.ViewRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.AddRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.ViewRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.EditServce
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.isValidDescription
import com.example.adminoffice.ui.theme.Utils.isValidName
import com.example.adminoffice.ui.theme.Utils.isValidPhoneNumber
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

data class EditMenu
    (
            val menus : ViewMenu.TableRow
            ): Screen {

    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var CategoryDish = mutableStateListOf<String>()
    var Hotels = mutableStateListOf<Hotel>()
    var usersJsonArrayError = mutableStateOf("")
    var HotelJsonArrayError = mutableStateOf("")
    var message = ""
    var hotelID = menus.hotelID
    var modalopen = mutableStateOf(false)

    var serviceCategories = mutableStateListOf<Dish>()
    var dishesSelect = mutableStateListOf<Dish>()
    var Totalprice = mutableStateOf(0)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var step = remember {
            mutableStateOf(1)
        }

        var isExpandedHotel = remember {
            mutableStateOf(false)
        }
        var isErrorHotel by remember { mutableStateOf(false) }
        var isErrorPrice by remember { mutableStateOf(false) }
        var hotelSelect by remember { mutableStateOf(menus.hotelname) }
        val price = remember {
            mutableStateOf(menus.title)
        }


        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Logo(scope = scope, drawerState = drawerState)
                    Menu().forEachIndexed{
                            index, data ->
                        NavigationDrawerItem(
                            modifier = Modifier.height(45.dp),
                            label = { Header(first = data.first, second = data.second) },
                            selected = selectedItem==index,
                            onClick = {
                                selectedItem=index
                                selectedSubItem = -1
                                if(selectedItem==0){
                                    scope.launch {
                                        drawerState.close()
                                        navigator.pop()
                                    }

                                }
                                else if(selectedItem==10){
                                    scope.launch {
                                        drawerState.close()
                                        navigator.replace(Chat)
                                    }

                                }
                            })
                        if (selectedItem == index) {
                            val subMenuItems = data.third
                            Column {
                                subMenuItems.forEachIndexed { index, subItem ->
                                    NavigationDrawerItem(
                                        modifier = Modifier.height(45.dp),
                                        label = {
                                            SubHeader(subItem=subItem)
                                        },
                                        selected = selectedSubItem == index,
                                        onClick = {
                                            //onSubItemClick()
                                            scope.launch {
                                                drawerState.close()
                                                navigator.pop()
                                                if (selectedItem == 1) {
                                                    if (index == 1) {
                                                        navigator.replace(ViewUsers)
                                                    }
                                                    if (index == 0) {
                                                        navigator.replace(Home)
                                                    }
                                                } else if (selectedItem == 2) {
                                                    if (index == 1) {
                                                        navigator.replace(ViewServiceCategory)
                                                    }
                                                    if (index == 0) {
                                                        navigator.replace(AddServiceCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddService)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewService)
                                                    }
                                                } else if (selectedItem == 3) {
                                                    if (index == 0) {
                                                        navigator.replace(AddHotel)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewHotel)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddRoom)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewRoom)
                                                    }
                                                } else if (selectedItem == 4) {
                                                    if (index == 0) {
                                                        navigator.replace(AddInventoryCategory)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewInventoryCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddInventory)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewInventory)
                                                    }
                                                }
                                                else if (selectedItem == 5) {
                                                    if (index == 0) {
                                                        navigator.replace(AddCoupon)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewCoupons)
                                                    }
                                                }else if (selectedItem == 6) {
                                                    if (index == 0) {
                                                        navigator.replace(AddBooking)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewBookings)
                                                    }
                                                } else if (selectedItem == 7) {
                                                    if (index == 0) {
                                                        navigator.replace(ViewPayments)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewRefunds)
                                                    }
                                                } else if (selectedItem == 8) {
                                                    if (index == 0) {
                                                        navigator.replace(AddDishCategory)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewDishCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddDish)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewDish)
                                                    }
                                                    if (index == 4) {
                                                        navigator.replace(AddMenu)
                                                    }
                                                    if (index == 5) {
                                                        navigator.replace(ViewMenu)
                                                    }
                                                } else if (selectedItem == 9) {
                                                    if (index == 0) {
                                                        navigator.replace(AddReview)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewReview)
                                                    }
                                                } else if (selectedItem == 11) {
                                                    if (index == 0) {
                                                        navigator.replace(AddRevenue)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewRevenue)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddExpense)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewExpense)
                                                    }
                                                    if (index == 4) {
                                                        navigator.replace(ViewProfit)
                                                    }
                                                }
                                                else if (selectedItem == 12) {
                                                    if (index == 1) {
                                                        navigator.replace(FAQ)
                                                    }
                                                    if (index == 0) {
                                                        navigator.push(AboutUs)
                                                    }
                                                    if (index == 2) {
                                                        navigator.push(Policy)
                                                    }
                                                }
                                            }


                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            },
            drawerState= drawerState,
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
                Box(modifier = Modifier.padding(start = 0.dp, top = 70.dp, end = 0.dp, bottom = 0.dp)){
                    Column {
                        if(step.value==1){
                            Column (Modifier.verticalScroll(rememberScrollState())){
                                Box(modifier = Modifier.fillMaxWidth()){
                                    Row(modifier = Modifier
                                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .padding(10.dp)
                                            .border(
                                                2.dp,
                                                MaterialTheme.colorScheme.primary,
                                                shape = RoundedCornerShape(45.dp)
                                            )){
                                            Icon(painterResource(id= R.drawable.dish ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                        }
                                        Column {
                                            Text(text = "1.Add Menu", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "Create Menu", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                        }
                                    }
                                }
                                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                            .padding(16.dp)
                                            .clickable { isExpandedHotel.value = true }
                                    ) {
                                        Row {
                                            Icon(painterResource(id = R.drawable.house), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= MaterialTheme.colorScheme.primary)
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
                                            expanded = isExpandedHotel.value,
                                            onDismissRequest = {isExpandedHotel.value=false },
                                            modifier = Modifier
                                                .size(300.dp, 120.dp)
                                                .fillMaxWidth()
                                        ) {
                                            Hotels.forEach { option ->
                                                DropdownMenuItem(text = { Column{
                                                    Text(text = option.name)
                                                    Text(text = option.location, fontSize = 10.sp)
                                                } } , leadingIcon = {
                                                    Box(){
                                                        Image(
                                                            painter = rememberAsyncImagePainter(option.images[0], contentScale = ContentScale.Fit),
                                                            contentDescription = "image",
                                                            modifier = Modifier
                                                                .size(70.dp)
                                                                .clip(
                                                                    RoundedCornerShape(
                                                                        (CornerSize(
                                                                            20.dp
                                                                        ))
                                                                    )
                                                                )
                                                        )
                                                    }
                                                }, onClick = {
                                                    hotelSelect = option.name
                                                    hotelID = option._id
                                                    isExpandedHotel.value = false
                                                }, modifier = Modifier.padding(5.dp))
                                            }
                                        }
                                    }
                                    if (isErrorHotel) {
                                        Text(
                                            text = "Please select a Hotel",
                                            color = Color.Red,
                                            modifier = Modifier.align(Alignment.Start),
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    OutlinedTextField(
                                        shape = RoundedCornerShape(5.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = Color.Black,
                                            unfocusedBorderColor = Color.Gray,
                                            errorBorderColor = Color.Red,
                                            placeholderColor = Color.Gray,
                                            disabledPlaceholderColor = Color.Gray
                                        ),
                                        value = price.value,
                                        onValueChange = {
                                            if (it.length <= 11) price.value=it
                                        },
                                        leadingIcon = {
                                            Icon(painterResource(id = R.drawable.money), contentDescription = "person", tint = MaterialTheme.colorScheme.primary)
                                        },
                                        label = {
                                            Text(text = "Menu Title", color = Color.Gray)
                                        },
                                        placeholder = {
                                            Text(text = "Enter Menu Title")
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                    )
                                    if (isErrorPrice) {
                                        Text(
                                            text = "Please provide a Title for Menu",
                                            color = Color.Red,
                                            modifier = Modifier.align(Alignment.Start),
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                        Box(modifier = Modifier
                                            .width(120.dp)
                                            .height(40.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(color = MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center){


                                            Text(text = "Rs."+Totalprice.value.toString(), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                    for(cat in CategoryDish){
                                        Text(text = cat, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                                        for (dish in serviceCategories){
                                            if(dish.categoryTitle==cat){
                                                Box(
                                                    Modifier
                                                        .padding(10.dp)
                                                        .background(
                                                            color = if (dishesSelect.contains(dish)) {
                                                                Color(0x90C1DDF3)
                                                            } else {
                                                                Color.White
                                                            }, shape =
                                                            RoundedCornerShape(20.dp)

                                                        )
                                                        .border(
                                                            0.2.dp, Color.Gray,
                                                            RoundedCornerShape(20.dp)
                                                        )
                                                        .clickable {
                                                            if (dishesSelect.contains(dish)) {
                                                                dishesSelect.remove(dish)
                                                                Totalprice.value = 0
                                                                for (dis in dishesSelect) {
                                                                    Totalprice.value =
                                                                        Totalprice.value + dis.price
                                                                }
                                                            } else {
                                                                dishesSelect.add(dish)
                                                                Totalprice.value = 0
                                                                for (dis in dishesSelect) {
                                                                    Totalprice.value =
                                                                        Totalprice.value + dis.price
                                                                }
                                                            }
                                                        }){
                                                    Row(Modifier.padding(10.dp)){
                                                        Image(
                                                            painter = rememberAsyncImagePainter(dish.images[0]),
                                                            contentDescription = "image",
                                                            modifier = Modifier
                                                                .size(20.dp)
                                                                .clip(
                                                                    RoundedCornerShape(
                                                                        (CornerSize(
                                                                            10.dp
                                                                        ))
                                                                    )
                                                                ),
                                                            contentScale = ContentScale.FillBounds
                                                        )
                                                        Spacer(modifier = Modifier.width(10.dp))
                                                        Text(text = dish.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                    }
                                                }
                                            }
                                        }
                                    }



                                    Row(modifier= Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End){
                                        Spacer(modifier = Modifier.width(10.dp))
                                        OutlinedButton(onClick = {
                                            isErrorPrice = price.value == ""
                                            isErrorHotel = hotelID == ""

                                            if(!isErrorPrice
                                                && !isErrorHotel
                                            ){
                                                step.value++
                                            }
                                        },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color.White, // Text color
                                                containerColor =MaterialTheme.colorScheme.primary,// Border color
                                                // You can customize other colors here
                                            ),
                                            border= BorderStroke(1.dp,MaterialTheme.colorScheme.primary,),
                                            shape = RoundedCornerShape(CornerSize(3.dp))) {
                                            Text(text = "Next")
                                        }
                                    }
                                }
                            }
                        }
                        if(step.value==2){
                            Column(Modifier.padding(10.dp)){
                                Box(modifier = Modifier.fillMaxWidth()){
                                    Row(modifier = Modifier
                                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier
                                            .padding(10.dp)
                                            .border(
                                                2.dp,
                                                MaterialTheme.colorScheme.primary,
                                                shape = RoundedCornerShape(45.dp)
                                            )){
                                            Icon(painterResource(id= R.drawable.house ), contentDescription = "House" , modifier = Modifier.padding(10.dp))
                                        }
                                        Column {
                                            Text(text = "2.Preview Menu", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "View Menu", fontSize = 10.sp, fontWeight = FontWeight.Light,color= Color.Gray)
                                        }
                                    }
                                }
                                for(cat in CategoryDish){
                                    for (dish in serviceCategories){
                                        if(dish.categoryTitle==cat){
                                            if(dishesSelect.contains(dish)){
                                                Box(
                                                    Modifier
                                                        .padding(10.dp)
                                                        .border(
                                                            0.2.dp, Color.Gray,
                                                            RoundedCornerShape(20.dp)
                                                        )){
                                                    Row(Modifier.padding(10.dp)){
                                                        Image(
                                                            painter = rememberAsyncImagePainter(dish.images[0]),
                                                            contentDescription = "image",
                                                            modifier = Modifier
                                                                .size(20.dp)
                                                                .clip(
                                                                    RoundedCornerShape(
                                                                        (CornerSize(
                                                                            10.dp
                                                                        ))
                                                                    )
                                                                ),
                                                            contentScale = ContentScale.FillBounds
                                                        )
                                                        Spacer(modifier = Modifier.width(10.dp))
                                                        Text(text = dish.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                Row(modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {

                                        step.value--
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.Black, // Text color
                                            containerColor = Color.White, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,Color.Black),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Previous")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    OutlinedButton(onClick = {

                                        addMenu(context = context, dishes = dishesSelect, title = price.value, hotelid = hotelID,id=menus._id){
                                                it->
                                            if(it){
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Menu Updated",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                                navigator.pop()
                                            }
                                        }
                                    },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color.White, // Text color
                                            containerColor = MaterialTheme.colorScheme.primary, // Border color
                                            // You can customize other colors here
                                        ),
                                        border= BorderStroke(1.dp,MaterialTheme.colorScheme.primary,),
                                        shape = RoundedCornerShape(CornerSize(3.dp))) {
                                        Text(text = "Update Menu")
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
        getHotels(context)
        getCategories(context)
    }
    // GET Hotels Function
    fun getHotels(context: Context) {
        val url = "${GlobalStrings.baseURL}admin/hotels/getHotels"
        val progressDialog = ProgressDialog(context)
        val params = JSONObject()
        progressDialog.setTitle("Loading Hotels...")
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
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, params,
                { response ->
                    Hotels.clear()
                    Log.d("HAPP",response.toString())
                    var hotels  = response.getJSONArray("hotels")
                    for(i in 0 until hotels.length()){
                        var hotel = hotels.getJSONObject(i)
                        var ServiceList = mutableStateListOf<Service>()
                        var VideoList = mutableStateListOf<String>()
                        var FloorList = mutableStateListOf<String>()
                        var RulesList = mutableStateListOf<String>()
                        var ReviewsList = mutableStateListOf<String>()
                        var RefundList = mutableStateListOf<Refund>()
                        var ImageList = mutableStateListOf<String>()
                        var RoomsList = mutableStateListOf<Room>()
                        var hotelOwner = hotel.getJSONObject("hotelOwner")
                        var hotelOwner_id = hotelOwner.getString("_id")
                        var hotelOwneruserid = hotelOwner.getJSONObject("userid")
                        var hotelOwneruseriduserCategory= hotelOwneruserid.getString("userCategory")
                        var hotelOwneruseriduser_id= hotelOwneruserid.getString("_id")
                        var hotelOwneruseridfirstName = hotelOwneruserid.getString("firstName")
                        var hotelOwneruseridlastName = hotelOwneruserid.getString("lastName")
                        var hotelOwneruseridemail = hotelOwneruserid.getString("email")
                        var hotelOwneruseridpassword = hotelOwneruserid.getString("password")
                        var hotelOwneruseridcontactNo = hotelOwneruserid.getString("contactNo")
                        var hotelOwneruseridcnic = hotelOwneruserid.getString("cnic")
                        var hotelOwneruseridprofilePicture = hotelOwneruserid.getString("profilePicture")
                        var hotelOwneruseridstatus = hotelOwneruserid.getString("status")
                        var hotelOwneruseridrole = hotelOwneruserid.getString("role")
                        var hotelOwneruseridverified = hotelOwneruserid.getBoolean("verified")
                        var hotelOwneruseridisDeleted = hotelOwneruserid.getBoolean("isDeleted")
                        var hotelOwneruseriddeletedAt = hotelOwneruserid.get("deletedAt")
                        var hotelOwneruseridcreatedAt = hotelOwneruserid.getString("createdAt")
                        var hotelOwneruseridupdatedAt = hotelOwneruserid.getString("updatedAt")
                        var hotelOwneruserid__v = hotelOwneruserid.getInt("__v")
                        var hotelOwnerisDeleted = hotelOwner.get("isDeleted")
                        var hotelOwnerdeletedAt = hotelOwner.get("deletedAt")
                        var hotelOwnercreatedAt = hotelOwner.getString("createdAt")
                        var hotelOwnerupdatedAt = hotelOwner.getString("updatedAt")
                        var hotelOwner__v = hotelOwner.getInt("__v")
                        var userID = Userid(__v = hotelOwneruserid__v, _id = hotelOwneruseriduser_id, cnic = hotelOwneruseridcnic,
                            contactNo = hotelOwneruseridcontactNo, createdAt = hotelOwneruseridcreatedAt, deletedAt = hotelOwneruseriddeletedAt,
                            email = hotelOwneruseridemail, firstName = hotelOwneruseridfirstName, isDeleted = hotelOwneruseridisDeleted,
                            lastName = hotelOwneruseridlastName, password = hotelOwneruseridpassword, profilePicture = hotelOwneruseridprofilePicture,
                            role = hotelOwneruseridrole, status = hotelOwneruseridstatus, updatedAt = hotelOwneruseridupdatedAt, userCategory = hotelOwneruseriduserCategory,
                            verified = hotelOwneruseridverified)
                        var hotelOwnerOBJ = HotelOwner(__v = hotelOwner__v, _id = hotelOwner_id, createdAt = hotelOwnercreatedAt,
                            deletedAt = hotelOwnerdeletedAt, isDeleted = hotelOwneruseridisDeleted, updatedAt = hotelOwnerupdatedAt,
                            userid = userID)


                        var _id = hotel.getString("_id")
                        var id = hotel.getString("id")
                        var hotelname = hotel.getString("name")
                        var hoteldescription = hotel.getString("description")
                        var hotellocation = hotel.getString("location")
                        var hotellat = hotel.getDouble("lat")
                        var hotellng = hotel.getDouble("lng")
                        var hotelcity = hotel.getString("city")
                        var hotelwebsiteURL = hotel.getString("websiteURL")
                        var hotelstatus = hotel.getString("status")
                        var hotelinstagramURL = hotel.getString("instagramURL")
                        var hotelfacebookURL = hotel.getString("facebookURL")
                        var hotelcreatedAt = hotel.getString("createdAt")
                        var hotelupdatedAt = hotel.getString("updatedAt")
                        var hotelisDeleted = hotel.getBoolean("isDeleted")
                        var hoteldeletedAt = hotel.get("deletedAt")
                        var hotel__v = hotel.getInt("__v")
                        var hotelaverageRating = hotel.getInt("averageRating")
                        var hoteltotalReviews = hotel.getInt("totalReviews")
                        var hotelaveragePrice = hotel.getInt("averagePrice")
                        var hotelid = hotel.getInt("id")
                        var hotelownerName = hotel.getString("ownerName")
                        var hotelphoneNo = hotel.getString("phoneNo")
                        var hoteltelephoneNo = hotel.getString("telephoneNo")

                        var hotelimages = hotel.getJSONArray("images")
                        for(i in 0 until hotelimages.length()){
                            var hotelImage = hotelimages.getString(i)
                            ImageList.add(hotelImage)
                        }

                        var hotelvideos = hotel.getJSONArray("videos")
                        for(i in 0 until hotelvideos.length()){
                            var hotelVideoList = hotelvideos.getString(i)
                            VideoList.add(hotelVideoList)
                        }

                        var hotelrules = hotel.getJSONArray("rules")
                        for(i in 0 until hotelrules.length()){
                            var hotelRulesList = hotelrules.getString(i)
                            RulesList.add(hotelRulesList)
                        }
                        var hotelreviews = hotel.getJSONArray("reviews")
                        for(i in 0 until hotelreviews.length()){
                            var hotelReviewList = hotelreviews.getString(i)
                            ReviewsList.add(hotelReviewList)
                        }
                        var hotelfloors = hotel.getJSONArray("floors")
                        for(i in 0 until hotelfloors.length()){
                            var hotelFloorList = hotelfloors.getString(i)
                            FloorList.add(hotelFloorList)
                        }

                        var hotelservices = hotel.getJSONArray("services")
                        for(i in 0 until hotelservices.length()){
                            var serviceOBJ = hotelservices.getJSONObject(i)
                            var hotelservices_id = serviceOBJ.getString("_id")
                            var hotelservicestype = serviceOBJ.getString("type")
                            var hotelservicesname = serviceOBJ.getString("name")
                            var hotelservicesdescription = serviceOBJ.getString("description")
                            var hotelservicesimage = serviceOBJ.getString("image")
                            var hotelservicespriceRate = serviceOBJ.getString("priceRate")
                            var hotelservicesprice = serviceOBJ.getInt("price")
                            var hotelservicesaddedByRole = serviceOBJ.getString("addedByRole")
                            var hotelservicesdeletedAt = serviceOBJ.get("deletedAt")
                            var hotelservicesvisible = serviceOBJ.getBoolean("visible")
                            var hotelservicesisDeleted = serviceOBJ.getBoolean("isDeleted")
                            var hotelservicescreatedAt = serviceOBJ.getString("createdAt")
                            var hotelservicesupdatedAt = serviceOBJ.getString("updatedAt")
                            var hotelservices__v = serviceOBJ.getInt("__v")
                            var hotelservicesserviceCategory = serviceOBJ.getJSONObject("serviceCategory")
                            var hotelservicesserviceCategory_id = hotelservicesserviceCategory.getString("_id")
                            var hotelservicesserviceCategorytitle = hotelservicesserviceCategory.getString("title")
                            var hotelservicesserviceCategoryimage = hotelservicesserviceCategory.getString("image")
                            var hotelservicesserviceCategoryisDeleted = hotelservicesserviceCategory.getBoolean("isDeleted")
                            var hotelservicesserviceCategorydeletedAt = hotelservicesserviceCategory.get("deletedAt")
                            var hotelservicesserviceCategorycreatedAt = hotelservicesserviceCategory.getString("createdAt")
                            var hotelservicesserviceCategoryupdatedAt = hotelservicesserviceCategory.getString("updatedAt")
                            var hotelservicesserviceCategory__v = hotelservicesserviceCategory.getInt("__v")
                            var cat = ServiceCategory(__v = hotelservicesserviceCategory__v,_id=hotelservicesserviceCategory_id, createdAt = hotelservicesserviceCategorycreatedAt,
                                deletedAt =hotelservicesserviceCategorydeletedAt, image = hotelservicesserviceCategoryimage, isDeleted = hotelservicesserviceCategoryisDeleted,
                                title = hotelservicesserviceCategorytitle, updatedAt = hotelservicesserviceCategoryupdatedAt)
                            var ser = Service(__v = hotelservices__v,_id=hotelservices_id, addedByRole = hotelservicesaddedByRole,
                                createdAt = hotelservicescreatedAt, deletedAt = hotelservicesdeletedAt, description = hotelservicesdescription,
                                image = hotelservicesimage,isDeleted = hotelservicesisDeleted, name = hotelservicesname,
                                price = hotelservicesprice, priceRate = hotelservicespriceRate, type = hotelservicestype,
                                updatedAt = hotelservicesupdatedAt, visible = hotelservicesvisible, serviceCategory = cat)
                            ServiceList.add(ser)
                        }


                        var hotelrefundPolicy = hotel.getJSONObject("refundPolicy")
                        var hotelrefundPolicy_id = hotelrefundPolicy.getString("_id")
                        var hotelrefundPolicydescription = hotelrefundPolicy.getString("description")
                        var hotelrefundPolicyrefunds = hotelrefundPolicy.getJSONArray("refunds")
                        for(i in 0 until hotelrefundPolicyrefunds.length()){
                            var refundsOBJ = hotelrefundPolicyrefunds.getJSONObject(i)
                            var refundsOBJdays = refundsOBJ.getInt("days")
                            var refundsOBJpercentage = refundsOBJ.getInt("percentage")
                            var refundsOBJ_id = refundsOBJ.getString("_id")
                            var refundsOBJisDeleted = refundsOBJ.getBoolean("isDeleted")
                            var refundsOBJdeletedAt = refundsOBJ.get("deletedAt")
                            var refundOBJ = Refund(_id=refundsOBJ_id, days = refundsOBJdays, deletedAt = refundsOBJdeletedAt,
                                isDeleted = refundsOBJisDeleted, percentage = refundsOBJpercentage)
                            RefundList.add(refundOBJ)

                        }

                        var hotelrefundPolicyisDeleted = hotelrefundPolicy.getBoolean("isDeleted")
                        var hotelrefundPolicydeletedAt = hotelrefundPolicy.get("deletedAt")
                        var hotelrefundPolicycreatedAt = hotelrefundPolicy.getString("createdAt")
                        var hotelrefundPolicyupdatedAt = hotelrefundPolicy.getString("updatedAt")
                        var hotelrefundPolicy__v = hotelrefundPolicy.getInt("__v")

                        var RefundPolicy = RefundPolicy(__v = hotelrefundPolicy__v,_id=hotelrefundPolicy_id, createdAt = hotelrefundPolicycreatedAt,
                            deletedAt = hotelrefundPolicydeletedAt, description = hotelrefundPolicydescription, isDeleted = hotelrefundPolicyisDeleted,
                            refunds = RefundList, updatedAt = hotelrefundPolicyupdatedAt)


                        var Hotel = Hotel(__v = hotel__v,_id=_id, averagePrice = hotelaveragePrice, averageRating = hotelaverageRating,
                            city = hotelcity, createdAt = hotelcreatedAt, deletedAt = hoteldeletedAt, description = hoteldescription,
                            facebookURL = hotelfacebookURL, floors = FloorList, hotelOwner = hotelOwnerOBJ, id = hotelid, images = ImageList,
                            instagramURL = hotelinstagramURL, isDeleted = hotelisDeleted, lat = hotellat, lng = hotellng, location = hotellocation,
                            name = hotelname, ownerName = hotelownerName, phoneNo = hotelphoneNo, refundPolicy =RefundPolicy, reviews = ReviewsList,
                            rules = RulesList, services = ServiceList, status = hotelstatus, telephoneNo = hoteltelephoneNo, totalReviews = hoteltotalReviews,
                            updatedAt = hotelupdatedAt, videos = VideoList, websiteURL = hotelwebsiteURL, rooms = RoomsList)

                        Hotels.add(Hotel)

                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS",error.toString())
                    Hotels.clear()
                    HotelJsonArrayError = mutableStateOf(error.toString())
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

    // GET Categories Function
    fun getCategories(context: Context) {
        val url = "${GlobalStrings.baseURL}admin/menus/getDishes"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Dishes...")
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
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, ViewDish.params,
                { response ->
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("dishes")
                    serviceCategories.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)
                        var imagess = mutableStateListOf<String>()
                        var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getJSONArray("images")
                        for(i in 0 until serviceImage.length()){
                            imagess.add(serviceImage.getString(i))
                        }
                        var serviceName = category.getString("name")
                        var servicedescription = category.getString("description")
                        var serviceprice = category.getInt("price")
                        var serviceservings = category.getInt("servings")
                        var serviceaddedByRole = category.getString("addedByRole")
                        var servicecategoryTitle = category.getString("categoryTitle")
                        var serviceCa = category.getJSONObject("dishCategory")
                        var serviceCategoryID = serviceCa.getString("_id")
                        var serviceCategorydescription = serviceCa.getString("description")
                        var serviceCategoryimage = serviceCa.getString("image")
                        var serviceCategorytitle = serviceCa.getString("title")

                        var categoryOBJ = DishCategory(_id = serviceCategoryID, description =serviceCategorydescription, image = serviceCategoryimage, title =  serviceCategorytitle)
                        serviceCategories.add(
                            Dish(_id=_id, addedByRole = serviceaddedByRole, categoryTitle =servicecategoryTitle, description = servicedescription, dishCategory = categoryOBJ,
                                id = id, images =imagess, name = serviceName, price = serviceprice, servings =serviceservings  )
                        )
                        if(!CategoryDish.contains(serviceCategorytitle)){
                            CategoryDish.add(serviceCategorytitle)
                        }
                    }
                    for(i in menus.dishes){
                        for(serv in serviceCategories){
                            if(i==serv._id){
                                dishesSelect.add(serv)
                            }
                        }
                    }
                    Totalprice.value = 0
                    for (dis in dishesSelect) {
                        Totalprice.value =
                            Totalprice.value + dis.price
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    serviceCategories.clear()
                    usersJsonArrayError = mutableStateOf(error.toString())
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

    // Add Room Function
    fun addMenu(context: Context,
                dishes: List<Dish>,
                title: String,
                id:String,
                hotelid: String,callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}admin/menus/updateMenu/${id}"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()
        val jsonArrayDishes = JSONArray()
        dishes.forEach{ item ->
            jsonArrayDishes.put(item._id)
        }
        // Request parameters
        val params = JSONObject()
        params.put("dishes", jsonArrayDishes)
        params.put("hotelid", hotelid)
        params.put("title", title)
        Log.e("HAPP", params.toString())
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
                Request.Method.PUT, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("HAPP", response.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Menu is Updated",
                            Toast.LENGTH_SHORT
                        )
                        .show()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("HAPP", error.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "An Error Occurred while adding the User. Please check your Parameters.",
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
}