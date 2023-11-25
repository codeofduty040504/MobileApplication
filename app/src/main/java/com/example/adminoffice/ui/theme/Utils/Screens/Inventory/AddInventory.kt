package com.example.adminoffice.ui.theme.Utils.Screens.Inventory


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
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.MaskVisualTransformation
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Dashboard
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.AddHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.ViewHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Profiling.Login
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.AddRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.ViewRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
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
import androidx.compose.material3.Surface
import androidx.compose.ui.text.input.TextFieldValue
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
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import kotlin.math.roundToInt

object AddInventory  : Screen {
    data class category(val _id:String,val name: String,val image: String)
    var imageURL =mutableStateListOf<String>()
    var images =""
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

                var isExpandedCategory = remember {
                    mutableStateOf(false)
                }

                var category by remember { mutableStateOf("") }
                var categoryID by remember { mutableStateOf("") }
                var isExpandedType = remember {
                    mutableStateOf(false)
                }
                var type by remember { mutableStateOf("") }
                val serviceName = remember {
                    mutableStateOf("")
                }
                val serviceColor = remember {
                    mutableStateOf("")
                }
                val serviceDescription = remember {
                    mutableStateOf("")
                }
                var isExpandedPriceType = remember {
                    mutableStateOf(false)
                }
                var priceFree by remember { mutableStateOf(false) }
                var priceType by remember { mutableStateOf("") }
                var price = remember { mutableStateOf("") }
                var isErrorPrice = remember { mutableStateOf(true) }
                var bitmap by remember { mutableStateOf<Bitmap?>(null) }

                var imageuri by remember {
                    mutableStateOf<Uri?>(null)
                }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        images =""
                        imageuri = uri }
                )
                var selectedColor by remember { mutableStateOf(Color.Red) }
                var isColorPickerVisible by remember { mutableStateOf(false) }

                var isErrorServiceName= remember { mutableStateOf(true) }
                var isErrorServiceImage= remember { mutableStateOf(false) }
                var isErrorServiceCategory= remember { mutableStateOf(true) }
                var isErrorPriceType= remember { mutableStateOf(true) }
                var isErrorType= remember { mutableStateOf(true) }
                var isErrorDescription= remember { mutableStateOf(true) }

                Box(modifier = Modifier.padding(start = 30.dp, top = 70.dp, end = 30.dp, bottom = 0.dp)){

                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Text(
                            text = "Add Inventory",
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            fontWeight = FontWeight.Bold,

                            )
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
                            ) {
                                categoriesAvailable.forEach { option ->
                                    DropdownMenuItem(text = {
                                        Row(verticalAlignment = Alignment.CenterVertically){
                                            Image(
                                                rememberAsyncImagePainter(model = option.image),
                                                contentDescription ="", modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(
                                                        RoundedCornerShape(40.dp)
                                                    )
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
                                Text(text = "Inventory Name",color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter Inventory Name")
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true
                        )
                        if (!isErrorServiceName.value) {
                            Text(
                                text = "Please provide a valid Inventory Name",
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
                                Text(text = "Inventory Description",color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter Inventory Description")
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
                                .clickable { isColorPickerVisible=true }
                        ) {
                            Row {
                                Icon(painterResource(id = R.drawable.category), contentDescription = "category", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= MaterialTheme.colorScheme.primary)
                                Text(text = if(ColorToHex(selectedColor)==""){
                                    "Select Inventory Color"
                                }
                                else{
                                    ColorToHex(selectedColor)
                                },
                                    color =  if(ColorToHex(selectedColor)==""){
                                        Color.Gray
                                    }
                                    else{
                                        Color.Black
                                    }
                                )
                            }

                        }
                        if(isColorPickerVisible){
                            ColorPicker(
                                initialColor = selectedColor,
                                onColorSelected = { newColor ->
                                    selectedColor = newColor
                                    isColorPickerVisible = false
                                },
                                onDismiss = { isColorPickerVisible = false }
                            )
                        }
                        if (!isErrorPriceType.value) {
                            Text(
                                text = "Please enter an Inventory Color",
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
                        if (isErrorServiceImage.value) {
                            Text(
                                text = "Please provide an Image for Inventory",
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
                                isErrorServiceCategory.value = category != ""
                                isErrorPriceType.value = ColorToHex(selectedColor) != ""
                                isErrorServiceImage.value = imageuri == null
                                if(isErrorServiceName.value && !isErrorServiceImage.value
                                    && isErrorPrice.value && isErrorPriceType.value && isErrorServiceCategory.value
                                    && isErrorType.value && isErrorDescription.value){
                                    scope.launch {
                                        val progressDialog = ProgressDialog(context)
                                        progressDialog.setTitle("Uploading...")
                                        progressDialog.show()
                                        uploadImage(context,imageuri!!)
                                        var color = ColorToHex(selectedColor)
                                        AddService(context = context, id = categoryID, description = serviceDescription.value,
                                            image = images, name = serviceName.value, color = color){Success ->
                                            if(Success){
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Inventory Added",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                                navigator.replace(ViewInventory)
                                            }
                                            Log.d("HAPP",Success.toString())
                                        }
                                        progressDialog.dismiss()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(2.dp,MaterialTheme.colorScheme.primary)
                        ) {
                            Text(text = "Add Inventory", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                        }
                        
                        
                    }
                }
            }
        }
        getCategories(context)
    }

    @Composable
    fun ColorPicker(
        initialColor: Color,
        onColorSelected: (Color) -> Unit,
        onDismiss: () -> Unit
    ) {
        var selectedColor by remember { mutableStateOf(initialColor) }
        var hexColor by remember { mutableStateOf(ColorToHex(initialColor)) }
        var hexColorInput by remember { mutableStateOf(TextFieldValue(hexColor)) }

        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                modifier = Modifier.padding(16.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(selectedColor)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ColorSlider(
                        label = "Red",
                        value = selectedColor.red,
                        onValueChange = { value ->
                            selectedColor = Color(value, selectedColor.green, selectedColor.blue)
                            hexColor = ColorToHex(selectedColor)
                            hexColorInput = TextFieldValue(hexColor)
                        }
                    )

                    ColorSlider(
                        label = "Green",
                        value = selectedColor.green,
                        onValueChange = { value ->
                            selectedColor = Color(selectedColor.red, value, selectedColor.blue)
                            hexColor = ColorToHex(selectedColor)
                            hexColorInput = TextFieldValue(hexColor)
                        }
                    )

                    ColorSlider(
                        label = "Blue",
                        value = selectedColor.blue,
                        onValueChange = { value ->
                            selectedColor = Color(selectedColor.red, selectedColor.green, value)
                            hexColor = ColorToHex(selectedColor)
                            hexColorInput = TextFieldValue(hexColor)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BasicTextField(
                        value = hexColorInput,
                        onValueChange = {
                            hexColorInput = it
                            val newColor = HexToColor(it.text)
                            selectedColor = newColor ?: selectedColor
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                            .align(Alignment.End)
                    ) {
                        TextButton(
                            onClick = { onDismiss() }
                        ) {
                            Text("Cancel")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        TextButton(
                            onClick = {
                                onColorSelected(selectedColor)
                                onDismiss()
                            }
                        ) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ColorSlider(
        label: String,
        value: Float,
        onValueChange: (Float) -> Unit
    ) {
        Column {
            Text(text = label)
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 0f..1f,
                steps = 100,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = (value * 255).roundToInt().toString(),
                onValueChange = {
                    val intValue = it.toIntOrNull() ?: 0
                    val normalizedValue = intValue.toFloat() / 255
                    onValueChange(normalizedValue.coerceIn(0f, 1f))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    fun ColorToHex(color: Color): String {
        return String.format("#%02X%02X%02X",
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt()
        )
    }

    fun HexToColor(hex: String): Color? {
        try {
            val sanitizedHex = if (hex.startsWith("#")) hex.substring(1) else hex
            val red = Integer.parseInt(sanitizedHex.substring(0, 2), 16) / 255f
            val green = Integer.parseInt(sanitizedHex.substring(2, 4), 16) / 255f
            val blue = Integer.parseInt(sanitizedHex.substring(4, 6), 16) / 255f
            return Color(red, green, blue)
        } catch (e: Exception) {
            return null
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
    fun AddService(context: Context, id: String, name: String,
                   description: String, image: String,color:String,callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}admin/inventories/addInventory"

        // Request parameters
        val params = JSONObject()
        params.put("name", name)
        params.put("image", image)
        params.put("categoryid", id)
        params.put("color", color)
        params.put("description", description)
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
        val url = "${GlobalStrings.baseURL}admin/inventories/getInventoryCategories"
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
                    var categories  = response.getJSONArray("inventoryCategories")
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
                                image = serviceImage,
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

