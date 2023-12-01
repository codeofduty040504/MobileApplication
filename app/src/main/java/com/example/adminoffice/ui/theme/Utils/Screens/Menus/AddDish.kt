package com.example.adminoffice.ui.theme.Utils.Screens.Menus



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
import com.example.adminoffice.ui.theme.Utils.Screens.Payments.ViewPayments
import com.example.adminoffice.ui.theme.Utils.Screens.Payments.ViewRefunds
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.AddRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.ViewRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.getRoleFromLocalStorage
import org.json.JSONArray
import kotlin.math.roundToInt

object AddDish  : Screen {
    data class TableRow(
        val _id: String,
        val id: Int,
        val serviceImage: String,
        val serviceName: String,
        val description: String)
    var serviceCategories = mutableStateListOf<TableRow>()
    var imageURL =mutableStateListOf<String>()
    var images =""

    var usersJsonArrayError = mutableStateOf("")
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation",
        "UnrememberedMutableState"
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){

        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val servings = remember {
            mutableStateOf("")
        }
        val price = remember {
            mutableStateOf("")
        }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        ModalNavigationDrawer(
            drawerContent = {
                DrawerUni(scope,drawerState)
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
                val serviceName = remember {
                    mutableStateOf("")
                }
                val serviceDescription = remember {
                    mutableStateOf("")
                }
                var isErrorServings by remember { mutableStateOf(false) }
                var isErrorPrice by remember { mutableStateOf(false) }
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
                var selectedImageUris by remember {
                    mutableStateOf<List<Uri>>(emptyList())
                }
                val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickMultipleVisualMedia(),
                    onResult = { uris -> selectedImageUris = uris }
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
                            text = "Add Dish",
                            color = GlobalStrings.AdminColorMain,
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
                                Icon(painterResource(id = R.drawable.category), contentDescription = "category", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= GlobalStrings.AdminColorMain)
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
                                serviceCategories.forEach { option ->
                                    DropdownMenuItem(text = {
                                        Row(verticalAlignment = Alignment.CenterVertically){
                                            Image(
                                                rememberAsyncImagePainter(model = option.serviceImage),
                                                contentDescription ="", modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(
                                                        RoundedCornerShape(40.dp)
                                                    )
                                            )
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Text(text = option.serviceName)
                                        }
                                    } , onClick = {
                                        category = option.serviceName
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
                                Icon(painterResource(id = R.drawable.bookmark), contentDescription = "add", tint = GlobalStrings.AdminColorMain)
                            },
                            label = {
                                Text(text = "Dish Name",color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter Dish Name")
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            singleLine = true
                        )
                        if (!isErrorServiceName.value) {
                            Text(
                                text = "Please provide a valid Dish Name",
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
                            value = servings.value,
                            onValueChange = {
                                servings.value=it
                            },
                            leadingIcon = {
                                Icon(painterResource(id = R.drawable.dish), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                            },
                            label = {
                                Text(text = "Servings", color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter number of Servings")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone
                            ),
                            singleLine = true,
                        )
                        if (isErrorServings) {
                            Text(
                                text = "Please provide number of Servings for the Dish",
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
                            value = price.value,
                            onValueChange = {
                                if (it.length <= 11) price.value=it
//                    isErrorPhoneNumber = isValidPhoneNumber(PhoneNumber.value)
                            },
                            leadingIcon = {
                                Icon(painterResource(id = R.drawable.money), contentDescription = "person", tint = GlobalStrings.AdminColorMain)
                            },
                            label = {
                                Text(text = "Price", color = Color.Gray)
                            },
                            placeholder = {
                                Text(text = "Enter Price for Dish")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone
                            ),
                            singleLine = true,
                        )
                        if (isErrorPrice) {
                            Text(
                                text = "Please provide a Price for Dish",
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
                                Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = GlobalStrings.AdminColorMain)
                            },
                            label = {
                                Text(text = "Dish Description",color = Color.Gray)
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
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)){
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                                .height(60.dp)
                                .clickable {
                                    multiplePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                                .padding(0.dp, 5.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = "Select Image", fontSize = 12.sp)
                            }

                        }
                        selectedImageUris?.let {
                            val rowSize = 3
                            val chunkedImageUris = selectedImageUris.chunked(rowSize)

                            Column {
                                for (row in chunkedImageUris) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        for (imageUri in row) {
                                            val bitmap: ImageBitmap? = remember(imageUri) {
                                                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                                                    MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                                                } else {
                                                    val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                                                    ImageDecoder.decodeBitmap(source)
                                                }
                                                bitmap.asImageBitmap()
                                            }

                                            Image(
                                                bitmap = bitmap!!,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(100.dp)
                                                    .clip(RoundedCornerShape(20.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (isErrorServiceImage.value) {
                            Text(
                                text = "Please provide an Image for Dish",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                        OutlinedButton(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GlobalStrings.AdminColorMain,
                                contentColor = Color.White),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 10.dp)
                                .size(400.dp, 45.dp),
                            onClick = {
                                isErrorServiceName.value =  isValidName(serviceName.value)
                                isErrorDescription.value = isValidDescription(serviceDescription.value)
                                isErrorServiceCategory.value = category != ""
                                isErrorServiceImage.value = selectedImageUris.isEmpty()
                                isErrorServings = servings.value ==""
                                isErrorPrice = price.value ==""
                                if(isErrorServiceName.value && !isErrorServiceImage.value && !isErrorPrice && isErrorServiceCategory.value
                                    && isErrorDescription.value && !isErrorServings){
                                    scope.launch {
                                        val progressDialog = ProgressDialog(context)
                                        progressDialog.setTitle("Uploading...")
                                        progressDialog.show()
                                        uploadImageToFireBase(
                                            context = context,
                                            filePath = selectedImageUris
                                        )
                                        AddService(context = context, id = categoryID, description = serviceDescription.value,
                                            image = imageURL, name = serviceName.value, price = price.value.toInt(), servings = servings.value.toInt()){Success ->
                                            if(Success){
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Dish Added",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                                navigator.replace(ViewDish)
                                            }
                                            Log.d("HAPP",Success.toString())
                                        }
                                        progressDialog.dismiss()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(2.dp,GlobalStrings.AdminColorMain)
                        ) {
                            Text(text = "Add Dish", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                        }


                    }
                }
            }
        }
        getCategories(context)
    }


    // Add Category Function
    fun AddService(context: Context, id: String, name: String,
                   description: String, image: List<String>,price:Int,servings:Int,callback: (Boolean) -> Unit){
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/menus/addDish"
        val imagess = JSONArray()
        for(i in image){
            imagess.put(i)
            Log.d("OOOO",i)
        }
        // Request parameters
        val params = JSONObject()
        params.put("name", name)
        params.put("images", imagess)
        params.put("dishcategoryid", id)
        params.put("servings", servings)
        params.put("price", price)
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
        val url = "${GlobalStrings.baseURL}${getTokenFromLocalStorage(context)}/menus/getDishCategories"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Categories...")
        progressDialog.show()
        val params = JSONObject()
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
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("dishCategories")
                    serviceCategories.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)

                        var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var serviceName = category.getString("title")
                        var description = category.getString("description")

                        serviceCategories.add(
                            TableRow(
                                _id = _id,
                                id = id,
                                serviceImage = serviceImage,
                                serviceName = serviceName,
                                description = description
                            )
                        )
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    ViewDishCategory.serviceCategories.clear()
                    ViewDishCategory.usersJsonArrayError = mutableStateOf(error.toString())
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
                jobs.joinAll()
            }
            progressDialog.dismiss()
            Log.d("HAPP","HERE WE GO")
        }
    }
}

