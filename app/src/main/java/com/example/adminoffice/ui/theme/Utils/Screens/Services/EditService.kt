package com.example.adminoffice.ui.theme.Utils.Screens.Services


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ClipDescription
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
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
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.Screens.Dashboard
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.AddExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewExpense
import com.example.adminoffice.ui.theme.Utils.Screens.Expense.ViewProfit
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.AddHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.ViewHotel
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
import com.example.adminoffice.ui.theme.Utils.Screens.Profiling.Login
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.AddRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.ViewRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.AddRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.ViewRoom

import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.isValidDescription
import com.example.adminoffice.ui.theme.Utils.isValidName
import com.example.adminoffice.ui.theme.Utils.saveTokenToLocalStorage
import com.example.adminoffice.ui.theme.Utils.uploadImageToFireBase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.util.UUID

data class EditServce(
    val Service: ViewService.TableRow
)  : Screen {
    data class category(val _id:String,val name: String,val image: String)
    var imageURL =mutableStateListOf<String>()
    var images =Service.serviceImage
    var usersJsonArrayError = mutableStateOf("")
    var categoriesAvailable = mutableStateListOf<category>()
    var typeAvailable = mutableStateListOf<String>("Room", "Hotel")
    var priceTypeAvailable = mutableStateListOf<String>("One Time", "On Every Use")
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation",
        "UnrememberedMutableState"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

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
                                                        navigator.replace(AboutUs)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(Policy)
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

                var isExpandedCategory = remember {
                    mutableStateOf(false)
                }

                var category by remember { mutableStateOf(Service.serviceCategory) }
                var categoryID by remember { mutableStateOf(Service.serviceCategoryID) }
                var isExpandedType = remember {
                    mutableStateOf(false)
                }
                var type by remember { mutableStateOf(Service.serviceType) }
                val serviceName = remember {
                    mutableStateOf(Service.serviceName)
                }
                val serviceDescription = remember {
                    mutableStateOf(Service.serviceDescription)
                }
                var isExpandedPriceType = remember {
                    mutableStateOf(false)
                }
                var priceFree by remember { mutableStateOf(false) }
                var priceType by remember { mutableStateOf(Service.serviceRate) }
                var price = remember { mutableStateOf(Service.servicePrice) }
                var isErrorPrice = remember { mutableStateOf(true) }
                var bitmap by remember { mutableStateOf<Bitmap?>(null) }
                var selectedImageUris by remember {
                    mutableStateOf<List<Uri>>(emptyList())
                }
                var imageuri by remember {
                    mutableStateOf<Uri?>(null)
                }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        images =""
                        imageuri = uri }
                )

                var isErrorServiceName= remember { mutableStateOf(true) }
                var isErrorServiceImage= remember { mutableStateOf(false) }
                var isErrorServiceCategory= remember { mutableStateOf(true) }
                var isErrorPriceType= remember { mutableStateOf(true) }
                var isErrorType= remember { mutableStateOf(true) }
                var isErrorDescription= remember { mutableStateOf(true) }

                Box(modifier = Modifier.padding(start = 30.dp, top = 70.dp, end = 30.dp, bottom = 0.dp)){

                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Text(
                            text = "Edit Service",
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            fontWeight = FontWeight.Bold,

                            )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                .padding(16.dp)
                                .clickable { isExpandedType.value = true }
                        ) {
                            Row {
                                Icon(painterResource(id = R.drawable.category), contentDescription = "category", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= MaterialTheme.colorScheme.primary)
                                Text(text = if(type==""){
                                    "Select Type"
                                }
                                else{
                                    type
                                },
                                    color =  if(type==""){
                                        Color.Gray
                                    }
                                    else{
                                        Color.Black
                                    }
                                )
                            }

                            DropdownMenu(
                                expanded = isExpandedType.value,
                                onDismissRequest = {isExpandedType.value=false },
                                modifier = Modifier
                                    .size(200.dp, 100.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                            ) {
                                typeAvailable.forEach { option ->
                                    DropdownMenuItem(text = { Text(text = option) } , onClick = {
                                        type = option
                                        isExpandedType.value = false
                                    })
                                }
                            }
                        }
                        if (!isErrorType.value) {
                            Text(
                                text = "Please select a Type.",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                .padding(16.dp)
                                .clickable { isExpandedCategory.value = true }
                        ) {
                            Row {
                                Icon(painterResource(id = R.drawable.category), contentDescription = "category", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= MaterialTheme.colorScheme.primary)
                                Text(text = if(category==""){
                                    "Select Category"
                                }
                                else{
                                    category
                                },
                                    color =  if(category==""){
                                        Color.Gray
                                    }
                                    else{
                                        Color.Black
                                    }
                                )
                            }

                            DropdownMenu(
                                expanded = isExpandedCategory.value,
                                onDismissRequest = {isExpandedCategory.value=false },
                                modifier = Modifier
                                    .size(300.dp, 150.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                            ) {
                                categoriesAvailable.forEach { option ->
                                    DropdownMenuItem(text = {
                                        Row(){
                                            Image(
                                                rememberAsyncImagePainter(model = option.image),
                                                contentDescription =""
                                            )
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = option.name)
                                        }
                                    } , onClick = {
                                        category = option.name
                                        categoryID = option._id
                                        isExpandedCategory.value = false
                                    })
                                }
                            }
                        }
                        if (!isErrorServiceCategory.value) {
                            Text(
                                text = "Please select a Category.",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        OutlinedTextField(
                            shape = RoundedCornerShape(5.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                                errorBorderColor = Color.Red,
                                placeholderColor = Color.Gray,
                                disabledPlaceholderColor = Color.Gray
                            ),
                            value = serviceName.value,
                            onValueChange = {
                                if (it.length <= 30)
                                    serviceName.value=it
                            },
                            leadingIcon = {
                                Icon(painterResource(id = R.drawable.bookmark), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                            },
                            label = {
                                Text(text = "Service Name",color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter Service Name")
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true
                        )
                        if (!isErrorServiceName.value) {
                            Text(
                                text = "Please provide a valid Category Name",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        OutlinedTextField(
                            shape = RoundedCornerShape(5.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                                errorBorderColor = Color.Red,
                                placeholderColor = Color.Gray,
                                disabledPlaceholderColor = Color.Gray
                            ),
                            value = serviceDescription.value,
                            onValueChange = {
                                if (it.length <= 300)
                                    serviceDescription.value=it
                            },
                            leadingIcon = {
                                Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                            },
                            label = {
                                Text(text = "Service Description",color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter Service Description")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                        )
                        if (!isErrorDescription.value) {
                            Text(
                                text = "Description should be more than 30 characters.",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                .padding(16.dp)
                                .clickable { isExpandedPriceType.value = true }
                        ) {
                            Row {
                                Icon(painterResource(id = R.drawable.category), contentDescription = "category", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= MaterialTheme.colorScheme.primary)
                                Text(text = if(priceType==""){
                                    "Select Price Type"
                                }
                                else{
                                    priceType
                                },
                                    color =  if(priceType==""){
                                        Color.Gray
                                    }
                                    else{
                                        Color.Black
                                    }
                                )
                            }

                            DropdownMenu(
                                expanded = isExpandedPriceType.value,
                                onDismissRequest = {isExpandedPriceType.value=false },
                                modifier = Modifier
                                    .size(300.dp, 100.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                            ) {
                                priceTypeAvailable.forEach { option ->
                                    DropdownMenuItem(text = { Text(text = option) } , onClick = {
                                        priceType = option
                                        isExpandedPriceType.value = false
                                    })
                                }
                            }
                        }
                        if (!isErrorPriceType.value) {
                            Text(
                                text = "Please select a Price Type.",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        Row{
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
                                    if (it.length <= 10)
                                        price.value=it
                                },
                                leadingIcon = {
                                    Icon(painterResource(id = R.drawable.money), contentDescription = "add", tint = MaterialTheme.colorScheme.primary)
                                },
                                label = {
                                    Text(text = "Service Price",color = Color.Gray)
                                },
                                placeholder = {
                                    Text(text = "Enter Service Price")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone
                                ),
                                modifier = Modifier,
                                singleLine = true,
                                enabled = !priceFree
                            )
                            Column(modifier = Modifier.padding(5.dp)) {
                                Text(text = "FREE")
                                Checkbox(
                                    checked = priceFree,
                                    onCheckedChange = {
                                        priceFree = it
                                        price.value = 0.toString()
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        if (!isErrorPrice.value) {
                            Text(
                                text = "Please enter a Price.",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)){
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                                .height(60.dp)
                                .clickable {
                                    launcher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                                .padding(0.dp, 5.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = "Select Image", fontSize = 12.sp)
                            }

                        }
                        imageuri?.let {
                            bitmap = if(Build.VERSION.SDK_INT < 28){
                                MediaStore.Images.Media.getBitmap(context.contentResolver,it)
                            }
                            else{
                                val source = ImageDecoder.createSource(context.contentResolver,it)
                                ImageDecoder.decodeBitmap(source)
                            }

                            Row(verticalAlignment = Alignment.Bottom) {
                                Image(bitmap= bitmap?.asImageBitmap()!!, contentDescription = "", modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape((CornerSize(20.dp)))),
                                    contentScale = ContentScale.FillBounds)
                                Spacer(modifier = Modifier
                                    .height(20.dp)
                                    .width(30.dp))
                            }
                        }
                        if(imageuri==null){
                            Image(
                                painter = rememberAsyncImagePainter(Service.serviceImage),
                                contentDescription = "image",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape((CornerSize(20.dp)))),
                                contentScale = ContentScale.FillBounds
                            )
                        }
                        if (isErrorServiceImage.value) {
                            Text(
                                text = "Please provide an Image for Service",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        OutlinedButton(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 10.dp)
                                .size(400.dp, 45.dp),
                            onClick = {
                                isErrorServiceName.value =  isValidName(serviceName.value)
                                isErrorDescription.value = isValidDescription(serviceDescription.value)
                                isErrorType.value = type != ""
                                isErrorServiceCategory.value = category != ""
                                isErrorPriceType.value = priceType != ""
                                isErrorPrice.value = price.value != "" || priceFree
                                if(isErrorServiceName.value
                                    && isErrorPrice.value && isErrorPriceType.value && isErrorServiceCategory.value
                                    && isErrorType.value && isErrorDescription.value){
                                    scope.launch {
                                        val progressDialog = ProgressDialog(context)
                                        progressDialog.setTitle("Uploading...")
                                        progressDialog.show()
                                        if(imageuri==null){
                                            AddService(context = context, id = categoryID, description = serviceDescription.value,
                                                image = images, name = serviceName.value, price = price.value.toInt(), priceRate = priceType,
                                                type = type,_id=Service._id){Success ->
                                                if(Success){
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "Service Updated",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                    navigator.replace(ViewService)
                                                }
                                                Log.d("HAPP",Success.toString())
                                            }
                                        }
                                        else{
                                            scope.launch {
                                                uploadImage(context,imageuri!!)
                                                AddService(context = context, id = categoryID, description = serviceDescription.value,
                                                    image = images, name = serviceName.value, price = price.value.toInt(), priceRate = priceType,
                                                    type = type,_id=Service._id){Success ->
                                                    if(Success){
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Service Updated",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                        navigator.replace(ViewService)
                                                    }
                                                    Log.d("HAPP",Success.toString())
                                                }
                                            }
                                            }
                                        progressDialog.dismiss()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(2.dp,MaterialTheme.colorScheme.primary)
                        ) {
                            Text(text = "Update Service", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
        getCategories(context)
    }
    // UploadImage method
    suspend fun uploadImageToFireBase(context : Context, filePath: List<Uri?>) {
        // get the Firebase  storage reference
        var storage = FirebaseStorage.getInstance();
        var storageReference = storage.getReference();
        var message = ""
        val jobs = mutableListOf<Job>()
        if (filePath != null) {
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            val ref: StorageReference = storageReference
                .child(
                    "images/"
                            + UUID.randomUUID().toString()
                )

            for(i in 0 until filePath.size){
                val job = CoroutineScope(Dispatchers.IO).async {
                    try {
                        var imageOne = filePath[i]
                        ref.putFile(imageOne!!).await()
                        val downloadUrl = ref.downloadUrl.await()
                        imageURL.add(downloadUrl.toString())
                        Log.e("HAPP", downloadUrl.toString())
                    } catch (e: Exception) {
                        Log.e("HAPP", "Error uploading image: ${e.message}")
                    }

                }
                jobs.add(job)
            }
            jobs.joinAll()
            progressDialog.dismiss()
            Log.d("HAPP","HERE WE GO")
        }
    }

    // UploadImage method
    suspend fun uploadImage(context : Context, filePath: Uri) {
        // get the Firebase  storage reference
        var storage = FirebaseStorage.getInstance();
        var storageReference = storage.getReference();
        var message = ""
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()
        val jobs = mutableListOf<Job>()
        if (filePath != null) {
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            val ref: StorageReference = storageReference
                .child(
                    "images/"
                            + UUID.randomUUID().toString()
                )

            val job = CoroutineScope(Dispatchers.IO).async {
                try {
                    var imageOne = filePath
                    ref.putFile(imageOne!!).await()
                    val downloadUrl = ref.downloadUrl.await()
                    images = downloadUrl.toString()
                    Log.e("HAPP", downloadUrl.toString())
                } catch (e: Exception) {
                    Log.e("HAPP", "Error uploading image: ${e.message}")
                }

            }
            progressDialog.dismiss()
            jobs.add(job)
        }
        progressDialog.dismiss()
        jobs.joinAll()
        progressDialog.dismiss()
    }

    // Add Category Function
    fun AddService(_id:String,context: Context, id: String, type: String, name: String,
                   description: String, image: String,price:Int,priceRate:String,callback: (Boolean) -> Unit){
        Log.d("HAPP",_id.toString())
        val url = "${GlobalStrings.baseURL}admin/services/updateService/${_id}"

        // Request parameters
        val params = JSONObject()
        params.put("name", name)
        params.put("image", image)
        params.put("categoryid", id)
        params.put("type", type)
        params.put("description", description)
        params.put("price", price)
        params.put("priceRate", priceRate)
        Log.d("HAPP",params.toString())
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
                Request.Method.PUT, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("HAPP", response.toString())

                    progressDialog.dismiss()

                    // Assuming the API returns a JSON object with a field "valid" indicating user validity
                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("HAPP", error.toString())
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
    fun getCategories(context: Context) {
        val url = "${GlobalStrings.baseURL}admin/services/getServiceCategories"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Categories...")
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
                Request.Method.GET, url, ViewServiceCategory.params,
                { response ->
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("serviceCategories")
                    categoriesAvailable.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var serviceName = category.getString("title")

                        categoriesAvailable.add(
                            category(
                                _id = _id,
                                name = serviceName,
                                image = serviceName,
                            )
                        )
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    categoriesAvailable.clear()
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
}

