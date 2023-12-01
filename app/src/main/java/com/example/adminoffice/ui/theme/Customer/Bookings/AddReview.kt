package com.example.adminoffice.ui.theme.Customer.Bookings

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Customer.Bookings.BookingDetails
import com.example.adminoffice.ui.theme.Customer.Bookings.CancelBookingCustomer
import com.example.adminoffice.ui.theme.Customer.Bookings.ExtendBookingCustomer
import com.example.adminoffice.ui.theme.Customer.Bookings.ViewBookingsCustomer
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Customer
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Refund
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service
import com.example.adminoffice.ui.theme.Customer.HotelCardWithSlider
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.DabsUser
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Booking
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.MaskVisualTransformation
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.saveUsertoLocal
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

data class AddReview(
    val booking : Booking
) : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    // get the Firebase  storage reference
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();
    var imageURL = ""
    var message = ""
    var UserDABS = mutableStateListOf<DabsUser>()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val review = remember {
            mutableStateOf("")
        }
        var user = getUserFromLocal(context)
        val isErrorReview = remember {
            mutableStateOf(false)
        }
        val title = remember {
            mutableStateOf("")
        }
        val isErrorTitle = remember {
            mutableStateOf(false)
        }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        ModalNavigationDrawer(
            drawerContent = {},
            drawerState= drawerState,
            gesturesEnabled = false
        ) {
            Scaffold()
            {
                Row(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.Center){
                    Text(text = "Give us a Review", color = GlobalStrings.CustomerColorMain,
                        fontSize = 24.sp
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color.Red,
                            placeholderColor = Color.Gray,
                            disabledPlaceholderColor = Color.Gray
                        ),
                        value = title.value,
                        onValueChange = {
                            if (it.length <= 300)
                                title.value=it
                        },
                        label = {
                            Text(text = "Title",color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Title of Review")
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    if (isErrorTitle.value) {
                        Text(
                            text = "Review Title should be more than 30 characters.",
                            color = Color.Red,
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    OutlinedTextField(
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color.Red,
                            placeholderColor = Color.Gray,
                            disabledPlaceholderColor = Color.Gray
                        ),
                        value = review.value,
                        onValueChange = {
                            if (it.length <= 300)
                                review.value=it
                        },
                        label = {
                            Text(text = "Review",color = Color.Gray)
                        },
                        placeholder = {
                            Text(text = "Give Us a Review")
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    if (isErrorReview.value) {
                        Text(
                            text = "Review should be more than 30 characters.",
                            color = Color.Red,
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    var rating = remember {
                        mutableStateOf(5)
                    }
                    Row(modifier = Modifier){
                        for(i in 0 until 5){
                            Box(modifier = Modifier.clickable {
                                rating.value= i+1
                                Log.d("HHHHHHHH",rating.value.toString())
                            }){
                                Icon(
                                    Icons.Outlined.Star, contentDescription =null, tint = if(i>=rating.value){
                                        Color(0xFF9B9B9B)
                                    }else{
                                        Color(
                                            0xFFFFC107
                                        )
                                    }, modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .clickable {
                            isErrorReview.value = review.value.length < 29
                            isErrorReview.value = title.value == ""
                            if (!isErrorReview.value && !isErrorTitle.value) {
                                AddReviewToSystem(
                                    context = context,
                                    customerid = user._id,
                                    stars = rating.value,
                                    description = review.value,
                                    hotelid = booking.hotel.description,
                                    title = title.value
                                ) {
                                    if (it) {
                                        navigator.pop()
                                    }
                                }

                            }
                        }
                        .background(
                            GlobalStrings.CustomerColorMain,
                            RoundedCornerShape(15.dp)
                        )
                        .fillMaxWidth()
                        .height(45.dp), contentAlignment = Alignment.Center){
                        Text(text = "Add Review", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }

    fun AddReviewToSystem(context: Context, customerid : String,stars: Int,description: String, hotelid: String,title:String, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}customer/reviews/addReview"


        // Request parameters
        val params = JSONObject()
        params.put("customerid", customerid)
        params.put("stars", stars)
        params.put("hotelid", hotelid)
        params.put("description", description)
        params.put("title", title)
        Log.d("ASDWFVSA", params.toString())
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
                    Log.d("ASDWFVSA", response.toString())
                    Toast
                        .makeText(
                            context,
                            "Review Added",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    progressDialog.dismiss()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("ASDWFVSA", error.toString())
                    Log.e("ASDWFVSA", error.networkResponse.statusCode.toString())
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
                    imageURL = (downloadUrl.toString())
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

    fun addComplaint(context: Context,
                     bookingid: String,
                     category: String,
                     description:String,
                     image:String,
                     title: String,
                     callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}customer/complaints/addComplaint"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        progressDialog.show()

        // Request parameters
        val params = JSONObject()
        params.put("category", category)
        params.put("bookingid", bookingid)
        params.put("description", description)
        params.put("image", image)
        params.put("title", title)
        Log.e("Register", params.toString())
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
                    Log.d("Register", response.toString())
                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("Register", error.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "An Error Occurred while adding the Complaint. Please check your Parameters.",
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