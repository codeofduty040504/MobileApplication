package com.example.adminoffice.ui.theme.Utils.Screens.Users

import cafe.adriel.voyager.core.screen.Screen
import android.annotation.SuppressLint
import android.app.ProgressDialog
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
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Utils.CustomTopAppBar
import com.example.adminoffice.ui.theme.Utils.DrawerUni
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
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.AddRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.ViewRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.AddRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.ViewRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
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

data class EditUser(
    val userJSONArray: ViewUsers.TableRow
) : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController

    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var imageURL = userJSONArray.profilePicture
    var message = ""

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

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
                },
                content = {

                    Box(modifier = Modifier.fillMaxWidth()) {
                        val navigator = LocalNavigator.currentOrThrow
                        val context = LocalContext.current
                        var isExpandedRole = remember {
                            mutableStateOf(false)
                        }
                        var isErrorPassword by remember { mutableStateOf(false) }
                        var isErrorRole by remember { mutableStateOf(false) }
                        var isErrorConfirmPassword by remember { mutableStateOf(false) }
                        var isErrorCNIC by remember { mutableStateOf(false) }
                        var isErrorLastName by remember { mutableStateOf(false) }
                        var isErrorFirstName by remember { mutableStateOf(false) }
                        var isErrorPhoneNumber by remember { mutableStateOf(false) }
                        var isErrorEmail by remember { mutableStateOf(false) }
                        var secure by remember { mutableStateOf(true) }
                        var secureConfirm by remember { mutableStateOf(true) }
                        var role by remember { mutableStateOf(userJSONArray.role) }
                        val rolesAvailable = listOf("Customer", "Hotel Owner", "Staff")
                        val firstName = remember {
                            mutableStateOf(userJSONArray.firstName)
                        }
                        val lastName = remember {
                            mutableStateOf(userJSONArray.lastName)
                        }
                        var imageuri by remember {
                            mutableStateOf<Uri?>(null)
                        }
                        val password = remember {
                            mutableStateOf("")
                        }
                        val confirmPassword = remember {
                            mutableStateOf("")
                        }
                        val CNIC = remember {
                            mutableStateOf(userJSONArray.cnic)
                        }
                        val email = remember {
                            mutableStateOf(userJSONArray.email)
                        }
                        val PhoneNumber = remember {
                            mutableStateOf(userJSONArray.phoneNumber)
                        }
                        var bitmap by remember { mutableStateOf<Bitmap?>(null) }
                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.PickVisualMedia(),
                            onResult = { uri ->
                                imageuri = uri }
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth().verticalScroll(rememberScrollState())
                                .padding(vertical = 60.dp, horizontal = 30.dp),
                        ) {
                            Text(
                                text = "Edit User",
                                color = GlobalStrings.AdminColorMain,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                fontWeight = FontWeight.Bold,

                                )
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    launcher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                                .padding(0.dp, 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (imageuri == null) {
                                    if(userJSONArray.profilePicture==""){
                                        Image(
                                            painter = rememberAsyncImagePainter("https://cdn4.iconfinder.com/data/icons/social-messaging-ui-color-squares-01/3/30-512.png"),
                                            contentDescription = "image",
                                            modifier = Modifier
                                                .size(100.dp)
                                                .clip(RoundedCornerShape((CornerSize(20.dp))))
                                        )
                                    }
                                    else{
                                        Image(
                                            painter = rememberAsyncImagePainter(userJSONArray.profilePicture),
                                            contentDescription = "image",
                                            modifier = Modifier
                                                .size(100.dp)
                                                .clip(RoundedCornerShape((CornerSize(20.dp))))
                                        )
                                    }
                                } else {
                                    imageuri?.let {
                                        bitmap = if (Build.VERSION.SDK_INT < 28) {
                                            MediaStore.Images.Media.getBitmap(
                                                context.contentResolver,
                                                it
                                            )
                                        } else {
                                            val source =
                                                ImageDecoder.createSource(context.contentResolver, it)
                                            ImageDecoder.decodeBitmap(source)
                                        }

                                        Row {
                                            Image(
                                                bitmap = bitmap?.asImageBitmap()!!,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(100.dp)
                                                    .clip(RoundedCornerShape((CornerSize(20.dp)))),
                                                contentScale = ContentScale.FillBounds
                                            )
//                                            Spacer(
//                                                modifier = Modifier
//                                                    .height(20.dp)
//                                                    .width(30.dp)
//                                            )
//                                            if (imageURL == "") {
//                                                OutlinedButton(
//                                                    onClick = { uploadImage(context, imageuri!!) },
//                                                    shape = RoundedCornerShape(4.dp)
//                                                ) {
//                                                    Text(text = "Confirm")
//                                                }
//                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                    .padding(16.dp)
                                    .clickable { isExpandedRole.value = true }
                            ) {
                                Row {
                                    Icon(
                                        painterResource(id = R.drawable.role),
                                        contentDescription = "person",
                                        modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
                                        tint = GlobalStrings.AdminColorMain
                                    )
                                    Text(
                                        text = if (role == "") {
                                            "Select User Role"
                                        } else {
                                            role
                                        },
                                        color = if (role == "") {
                                            Color.Gray
                                        } else {
                                            Color.Black
                                        }
                                    )
                                }

                                DropdownMenu(
                                    expanded = isExpandedRole.value,
                                    onDismissRequest = { isExpandedRole.value = false },
                                    modifier = Modifier
                                        .size(300.dp, 150.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = 40.dp)
                                ) {
                                    rolesAvailable.forEach { option ->
                                        DropdownMenuItem(text = { Text(text = option) }, onClick = {
                                            role = option
                                            isExpandedRole.value = false
                                        })
                                    }
                                }
                            }
                            if (isErrorRole) {
                                Text(
                                    text = "Please select a Role",
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
                                value = CNIC.value,
                                onValueChange = {
                                    if (it.length <= 13)
                                        CNIC.value = it
//                    isErrorCNIC = isValidCNIC(CNIC.value)
                                },
                                leadingIcon = {
                                    Icon(
                                        painterResource(id = R.drawable.staff),
                                        contentDescription = "person",
                                        tint = GlobalStrings.AdminColorMain
                                    )
                                },
                                label = {
                                    Text(text = "CNIC", color = Color.Gray)
                                },
                                placeholder = {
                                    Text(text = "Enter CNIC")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone
                                ),
                                singleLine = true,
                                visualTransformation = MaskVisualTransformation("#####-#######-#")
                            )
                            if (isErrorCNIC) {
                                Text(
                                    text = "Please provide a valid CNIC",
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
                                value = firstName.value,
                                onValueChange = {
                                    if (it.length <= 10)
                                        firstName.value = it
                                },
                                leadingIcon = {
                                    Icon(
                                        painterResource(id = R.drawable.man),
                                        contentDescription = "add",
                                        tint = GlobalStrings.AdminColorMain
                                    )
                                },
                                label = {
                                    Text(text = "First Name", color = Color.Gray)
                                },
                                placeholder = {
                                    Text(text = "Enter First Name")
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                singleLine = true
                            )
                            if (isErrorFirstName) {
                                Text(
                                    text = "Please provide a valid First Name",
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
                                value = lastName.value,
                                onValueChange = {
                                    if (it.length <= 10)
                                        lastName.value = it
                                },
                                leadingIcon = {
                                    Icon(
                                        painterResource(id = R.drawable.man),
                                        contentDescription = "add",
                                        tint = GlobalStrings.AdminColorMain
                                    )
                                },
                                label = {
                                    Text(text = "Last Name", color = Color.Gray)
                                },
                                placeholder = {
                                    Text(text = "Enter Last Name")
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                singleLine = true
                            )
                            if (isErrorLastName) {
                                Text(
                                    text = "Please provide a valid Last Name",
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
                                value = email.value,
                                onValueChange = {
                                    if (it.length <= 30) email.value = it
//                    isErrorEmail = isValidEmail(email.value)
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Email,
                                        contentDescription = "person",
                                        tint = GlobalStrings.AdminColorMain
                                    )
                                },
                                label = {
                                    Text(text = "Email", color = Color.Gray)
                                },
                                placeholder = {
                                    Text(text = "Enter Email")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email
                                ),
                                singleLine = true
                            )
                            if (isErrorEmail) {
                                Text(
                                    text = "Please provide a valid Email",
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
                                value = PhoneNumber.value,
                                onValueChange = {
                                    if (it.length <= 11) PhoneNumber.value = it
//                    isErrorPhoneNumber = isValidPhoneNumber(PhoneNumber.value)
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Phone,
                                        contentDescription = "person",
                                        tint = GlobalStrings.AdminColorMain
                                    )
                                },
                                label = {
                                    Text(text = "Phone Number", color = Color.Gray)
                                },
                                placeholder = {
                                    Text(text = "Enter 11 digit Phone Number")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone
                                ),
                                singleLine = true,
                                visualTransformation = MaskVisualTransformation("####-#######")
                            )
                            if (isErrorPhoneNumber) {
                                Text(
                                    text = "Please provide a valid Phone Number",
                                    color = Color.Red,
                                    modifier = Modifier.align(Alignment.Start),
                                    fontSize = 11.sp
                                )
                            }

                            OutlinedButton(
                                border = BorderStroke(2.dp, GlobalStrings.AdminColorMain),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GlobalStrings.AdminColorMain,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 10.dp)
                                    .size(400.dp, 45.dp),
                                onClick = {
                                    isErrorEmail = !isValidEmail(email.value)
                                    isErrorPhoneNumber = !isValidPhoneNumber(PhoneNumber.value)
                                    isErrorCNIC = !isValidCNIC(CNIC.value)
                                    isErrorFirstName = !isValidFirstName(firstName.value)
                                    isErrorLastName = !isValidLastName(lastName.value)
                                    isErrorRole = role == ""
                                    if (!isErrorPassword && !isErrorEmail && !isErrorCNIC
                                        && !isErrorConfirmPassword && !isErrorFirstName
                                        && !isErrorLastName && !isErrorPhoneNumber
                                    ) {
                                        var userId = userJSONArray._id
                                        scope.launch {
                                            if(imageuri!=null){
                                                uploadImageToFireBase(context=context, filePath = imageuri)
                                            }
                                            if(userJSONArray.email== email.value){
                                                updateUser(
                                                    context,
                                                    null.toString(),
                                                    password.value,
                                                    PhoneNumber.value,
                                                    CNIC.value,
                                                    firstName.value,
                                                    lastName.value,
                                                    imageURL,
                                                    role,
                                                    userId
                                                ) { isSuccess ->
                                                    if (message.isNotEmpty()) {
                                                        Log.d("Logg", "heheheheh token")
                                                        CNIC.value = ""
                                                        email.value = ""
                                                        password.value = ""
                                                        firstName.value = ""
                                                        lastName.value = ""
                                                        PhoneNumber.value = ""
                                                        role = ""
                                                        confirmPassword.value = ""
                                                        imageuri = null
                                                        navigator.pop()
                                                        navigator.push(ViewUsers)
                                                    }

                                                }
                                            }
                                            else{
                                                updateUser(
                                                    context,
                                                    email.value,
                                                    password.value,
                                                    PhoneNumber.value,
                                                    CNIC.value,
                                                    firstName.value,
                                                    lastName.value,
                                                    imageURL,
                                                    role,
                                                    userId
                                                ) { isSuccess ->
                                                    if (message.isNotEmpty()) {
                                                        Log.d("Logg", "heheheheh token")
                                                        CNIC.value = ""
                                                        email.value = ""
                                                        password.value = ""
                                                        firstName.value = ""
                                                        lastName.value = ""
                                                        PhoneNumber.value = ""
                                                        role = ""
                                                        confirmPassword.value = ""
                                                        imageuri = null
                                                        navigator.pop()
                                                        navigator.push(ViewUsers)
                                                    }

                                                }
                                            }
                                        }

                                    }

                                },
                                shape = RoundedCornerShape(15.dp),
                            ) {
                                Text(
                                    text = "Update User",
                                    color = Color.White,
                                    letterSpacing = 0.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                        }
                    }
                }
            )

        }
    }
    // UploadImage method
    suspend fun uploadImageToFireBase(context : Context, filePath: Uri?) {
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

                val job = CoroutineScope(Dispatchers.IO).async {
                    try {
                        var imageOne = filePath
                        ref.putFile(imageOne!!).await()
                        val downloadUrl = ref.downloadUrl.await()
                        imageURL= (downloadUrl.toString())
                        Log.e("HAPP", downloadUrl.toString())
                    } catch (e: Exception) {
                        Log.e("HAPP", "Error uploading image: ${e.message}")
                    }
            }
            jobs.add(job)
            jobs.joinAll()
            progressDialog.dismiss()
            Log.d("HAPP","HERE WE GO")
        }
    }
    // Register Function
    fun updateUser(
        context: Context,
        email: String,
        password: String,
        contactNo: String,
        cnic: String,
        firstName: String,
        lastName: String,
        profilePicture: String,
        role: String,
        userID : String,
        callback: (Boolean) -> Unit
    ) {
        val url = "${GlobalStrings.baseURL}admin/users/updateUser/${userID}"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()
        var category = ""
        if (role == "Customer" || role=="customer") {
            category = "customer"
        } else if (role == "Hotel Owner" || role=="owner") {
            category = "owner"
        } else {
            category = "staff"
        }

        // Request parameters
        val params = JSONObject()
        if(email=="null"){
            params.put("email", "null")
        }
        else{
            params.put("email", email)
        }
       // params.put("password", null)
        params.put("contactNo", contactNo)
        params.put("cnic", cnic)
        params.put("role", category)
        params.put("firstName", firstName)
        params.put("lastName", lastName)
        params.put("profilePicture", profilePicture)
        Log.e("Register1233", params.toString())
        Log.e("Register1233", userID)
        if (!isInternetAvailable(context)) {
            Toast
                .makeText(
                    context,
                    "Internet is not Available",
                    Toast.LENGTH_SHORT
                )
                .show()
            progressDialog.dismiss()
        } else {
            val request = object : JsonObjectRequest(
                Request.Method.PUT, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("Register1233", response.toString())
                    // You can check the response here to verify if the user is valid
                    message = response.getString("message")
                    Log.d("Register1233", message)
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "User Updated",
                            Toast.LENGTH_SHORT
                        )
                        .show()

                    // Assuming the API returns a JSON object with a field "valid" indicating user validity
                    val isValidUser = response.optBoolean("valid", false)
                    callback(isValidUser)
                },
                { error ->
                    // Handle error response
                    Log.e("Register1233", error.toString())
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


    fun isValidPassword(password: String): Boolean {
        val uppercaseRegex = Regex("(?=.*[A-Z])")
        val lowercaseRegex = Regex("(?=.*[a-z])")
        val digitRegex = Regex("(?=.*[0-9])")
        val specialCharRegex = Regex("[!#\$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+")

        return password.length >= 8 && uppercaseRegex.containsMatchIn(password) &&
                lowercaseRegex.containsMatchIn(password) && digitRegex.containsMatchIn(password) &&
                specialCharRegex.containsMatchIn(password)
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

        return emailRegex.matches(email)
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneRegex = Regex("\\d{4}\\d{7}")
        return phoneRegex.matches(phoneNumber)
    }

    fun isValidCNIC(cnic: String): Boolean {
        val cnicRegex = Regex("\\d{5}\\d{7}\\d")
        return cnicRegex.matches(cnic)
    }

    fun isConfirmPassword(password: String, confirmPassword: String): Boolean {
        if (password == confirmPassword) {
            return true
        } else {
            return false
        }
    }

    fun isValidFirstName(firstName: String): Boolean {
        val nameRegex = Regex("^[a-zA-Z]+\$")
        return nameRegex.matches(firstName)
    }

    fun isValidLastName(lastName: String): Boolean {
        val nameRegex = Regex("^[a-zA-Z]+\$")
        return nameRegex.matches(lastName)
    }

    // UploadImage method
    fun uploadImage(context: Context, filePath: Uri) {
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

            ref.putFile(filePath)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Image Uploaded!!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    val hap = ref.downloadUrl
                    Log.d("HAPP", hap.toString())
                    ref.downloadUrl
                        .addOnSuccessListener {
                            Log.d("HHHH", it.toString())
                            imageURL = it.toString()
                        }
                        .addOnFailureListener {
                            Toast
                                .makeText(
                                    context,
                                    "Image URL cannot be fetched.",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Failed " + e.message,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->

                    val progress = ((100.0
                            * taskSnapshot.bytesTransferred
                            / taskSnapshot.totalByteCount))
                    progressDialog.setMessage(
                        "Uploaded "
                                + progress.toInt() + "%"
                    )
                }

        }
    }


}