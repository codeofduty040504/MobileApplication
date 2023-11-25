package com.example.adminoffice.ui.theme.Customer.Coupons

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Display
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.MainActivity
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Customer.Coupons.models.DirectionResponses
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


var responseAsli = JSONObject()
private var hotelname : String = "Name not Available"
private var discount = ""
private var coinModel = ""
private var latii : Double = 0.0
private var longii : Double = 0.0
private var Lat : Double = 33.65708484914112   // 33.65708484914112, 73.1577907930079
private var Lon : Double = 73.1577907930079
private var currentLocation: Location? = null
private var mLocationPermissionGranted = false
private lateinit var mFusedLocationClient: FusedLocationProviderClient
private lateinit var locationRequest: LocationRequest
private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
val dist = FloatArray(1)
private var userLocation = LatLng(Lat, Lon)
private lateinit var trackMarker : LatLng
private lateinit var selectedMarkerID : String
private var LatSend : Double = 0.0
private var LonSend : Double = 0.0
private var discountToBeSent = ""
private var coinModelToBeSent = ""
private var hotelToBeSent = ""
private var polyLine: Polyline? = null
private var polyline : PolylineOptions? = null


object MapsCoupon  : Screen {
    data class TableRow(
        val _id: String,
        val latitude: Double,
        val longitude: Double,
        val hotel: String,
        val model: Bitmap,
        val modelImage: String,
        val discount: String,
        )
    var coupons = mutableStateListOf<TableRow>()
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val Longitude = remember { mutableStateOf(Lon) }
        val Latitude = remember { mutableStateOf(Lat) }
        val bitmapState = remember {
            mutableStateOf<BitmapDescriptor?>(null)
        }
        var Bitt: Bitmap
        val distance = remember {
            mutableStateOf("")
        }
        val reload = remember {
            mutableStateOf(false)
        }
        val distancePanel = remember {
            mutableStateOf(false)
        }
        val camerabtn = remember {
            mutableStateOf(false)
        }
        LaunchedEffect(true) {
            while (true) {
                Longitude.value = Lon
                Latitude.value = Lat
                delay(1000) // Update every 1 second
            }
        }
        val mapProperties = MapProperties(
            // Only enable if user has accepted location permissions.
            isMyLocationEnabled = userLocation != null,
        )

        // Provide Location Services
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 1000 // Interval in milliseconds

        // Location Call Back
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                currentLocation = locationResult.lastLocation
                Lat = currentLocation!!.latitude
                Lon = currentLocation!!.longitude
                userLocation = LatLng(Lat, Lon)
            }
        }
        // Fused Location provider
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        else{
          //  Toast.makeText(context, "Please Provide Location Permissions", Toast.LENGTH_SHORT).show()
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        // Location Permissions
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)            == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true

        }
        else {
            ActivityCompat.requestPermissions(
                MainActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
           // Toast.makeText(context, "Please Provide Permissions", Toast.LENGTH_SHORT).show()

        }
        if (!mLocationPermissionGranted) {
            Toast.makeText(context, "Location Permission is not granted", Toast.LENGTH_SHORT).show()

            return
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {

                val islamabad = LatLng(33.6844, 73.0479)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(islamabad, 10f)

                }
                if(distancePanel.value==true){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(text = distance.value, fontSize = 16.sp, textAlign = TextAlign.Center)
                    }
                }
                if(camerabtn.value==true){
                    Dialog(
                        onDismissRequest = { }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(modifier = Modifier.padding(10.dp)){
                                Text(text = "You have reached the Discount Coupon please collect the Discount.")
                                Button(
                                    onClick = {
                                        reload.value= false
                                        navigator.push(ARCoupon(hotelname= hotelToBeSent, couponid = selectedMarkerID,
                                            discount= discountToBeSent, latitude = LatSend.toString(), longitude = LonSend.toString(),
                                            coinModel= coinModelToBeSent))
                                    },
                                    modifier = Modifier
//                            .layout { measurable, constraints ->
//                                val placeable = measurable.measure(constraints)
//                                layout(placeable.width, placeable.height) {
//                                    placeable.placeRelative(
//                                        x = 350,
//                                        y = 50
//                                    )
//                                }
//                            }
                                        .height(50.dp)
                                        .width(150.dp),
                                ) {
                                    Text(text = "Collect Coupon", color = Color.White)
                                }
                            }
                        }
                    }
//                    FloatingActionButton(
//                        onClick = {
//                            reload.value= false
//                            navigator.push(ARCoupon(hotelname= hotelToBeSent, couponid = selectedMarkerID,
//                                discount= discountToBeSent, latitude = LatSend.toString(), longitude = LonSend.toString(),
//                                coinModel= coinModelToBeSent))
//                        },
//                        modifier = Modifier
//                            .layout { measurable, constraints ->
//                                val placeable = measurable.measure(constraints)
//                                layout(placeable.width, placeable.height) {
//                                    placeable.placeRelative(
//                                        x = 350,
//                                        y = 50
//                                    )
//                                }
//                            }
//                            .height(50.dp)
//                            .width(150.dp),
//                        contentColor = Color.White,
//                        containerColor = Color.Black
//                    ) {
//                        Text(text = "Open Camera", color = Color.White)
//                    }
                }
            //    Text(text = "ASASDASDASDASDAS")
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .layout { measurable, constraints ->
//                            val placeable = measurable.measure(constraints)
//                            layout(placeable.width, (placeable.height * 0.9).toInt()) {
//                                placeable.placeRelative(0, 0)
//                            }
//                        },
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties,
                ){

                    MapEffect(userLocation) { map ->
//                        map.setOnMapLoadedCallback {
//                            scope.launch {
//                                getCoupons(context)
//                                    var Bitt: Bitmap
//                                    val imageRequest = ImageRequest.Builder(context)
//                                        .data("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg")
//                                        .target { drawable ->
//                                            var bitmap = drawable.toBitmap() // This is the bitmap 🚨
//                                            for(coupon in coupons){
//                                                map.addMarker(
//                                                    MarkerOptions()
//                                                        .position(LatLng(coupon.latitude, coupon.longitude))       //33.64926760963938, 73.15453232632127
//                                                        .title(coupon.hotel).icon(BitmapDescriptorFactory.fromBitmap(bitmap))
//                                                        .snippet(coupon.discount+"% discount")
//                                                )?.tag = coupon._id+","+coupon.discount+","+coupon.hotel+","+ coupon.modelImage
//                                            }
//
//                                        }
//                                        .build()
//                                    ImageLoader(context).enqueue(imageRequest)
//
////                               for(coupon in coupons){
////                                   map.addMarker(
////                                       MarkerOptions()
////                                           .position(LatLng(coupon.latitude, coupon.longitude))       //33.64926760963938, 73.15453232632127
////                                           .title(coupon.hotel).icon(BitmapDescriptorFactory.fromBitmap(coupon.model))
////                                           .snippet(coupon.discount+"% discount")
////                                   )?.tag = coupon._id+","+coupon.discount+","+coupon.hotel+","+ coupon.model
////                               }
//                            }
//                        }
                        map.setOnMapLoadedCallback {
                            //  To get All Coupons
                            val url = "https://scs-backend-code.herokuapp.com/customer/coupons/getCoupons"

                            // Request parameters
                            val params = JSONObject()
                            val request = object : JsonObjectRequest(
                                Method.GET, url, params,
                                { response ->
                                    responseAsli = response
                                    Log.d("GGGGGGG",response.toString())
                                    var abc  = responseAsli.getJSONArray("coupons")
                                    for (i in 0 until abc.length()) {
                                        var jsonObject2222 = abc.getJSONObject(i)
                                        var iii = jsonObject2222
                                        try {
                                            val jsonObject = iii
                                            if (jsonObject.has("hotel")) {
                                                val hotelObject = jsonObject.getJSONObject("hotel")
                                                hotelname = hotelObject.optString("name")
                                                discount = jsonObject.getString("percentage")
                                                var abc = jsonObject.getJSONObject("coinModel")
                                                var ik = abc.getJSONObject("modelUrl")
                                                coinModel = ik.getString("androidSrc")
                                                val idobject = jsonObject.getString("_id")
                                                var model = ik.getString("image")

                                                if (jsonObject.has("locationsList")) {
                                                    var locationObject = jsonObject.getJSONArray("locationsList")
                                                    for (i in 0 until locationObject.length()) {
                                                        val jsonObject22 = locationObject.getJSONObject(i)
                                                        latii = jsonObject22.getDouble("lat")
                                                        longii = jsonObject22.getDouble("lng")

                                                        val icon12 = bitmapDescriptorFromVector(
                                                            context, R.mipmap.coin
                                                        )
//                                                    val furl = URL("https://image.shutterstock.com/image-vector/dotted-spiral-vortex-royaltyfree-images-600w-2227567913.jpg")
//                                                    val bm = BitmapFactory.decodeStream(furl.openConnection().getInputStream())
//                                                    val bd = BitmapDescriptorFactory.fromBitmap(bm)
                                                        try{
//                                                        val imageURLBase = model
//                                                        val imageURL = URL(imageURLBase)
//                                                        val connection = imageURL.openConnection()
//                                                        val iconStream = connection.getInputStream()
//                                                        var bmp = BitmapFactory.decodeStream(iconStream)
////                                                        val url = URL(model)
////                                                        val bmp = BitmapFactory.decodeStream(
////                                                            url.openConnection().getInputStream()
////                                                        )


//                                                        // Usage example
//                                                        val imageUrl = model
//                                                        val targetSize = 10 // Set your desired size here
//
//                                                        loadImageUrlToBitmapDescriptor(context, imageUrl, targetSize) { bitmapDescriptor ->
//                                                            // Use the generated BitmapDescriptor
//                                                            // For example, you can set a marker on a Google Map
//                                                            val markerOptions = MarkerOptions()
//                                                                .position(LatLng(latii, longii))
//                                                                .icon(bitmapDescriptor)
//                                                                .title(hotelname)
//
//                                                            map.addMarker(markerOptions)
                                                            //     }
//                                                            Glide.with(context)
//                                                                .asBitmap()
//                                                                .load("/Image_created_with_a_mobile_phone.png")
//                                                                .into(object :
//                                                                    CustomTarget<Bitmap?>(50, 50) {
//
//                                                                    override fun onResourceReady(
//                                                                        resource: Bitmap,
//                                                                        transition: Transition<in Bitmap?>?
//                                                                    ) {
//                                                                        map.addMarker(
//                                                                            MarkerOptions()
//                                                                                .position(
//                                                                                    LatLng(
//                                                                                        latii,
//                                                                                        longii
//                                                                                    )
//                                                                                )
//                                                                                .anchor(0.5f, 0.5f)
//                                                                                .title("title")
//                                                                                .snippet("snippet")
//                                                                                .icon(
//                                                                                    BitmapDescriptorFactory.fromBitmap(
//                                                                                        resource
//                                                                                    )
//                                                                                )
//                                                                        )
//                                                                    }
//
//
//                                                                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
//                                                                })

                                                                map.addMarker(
                                                                    MarkerOptions()
                                                                        .position(LatLng(latii, longii))       //33.64926760963938, 73.15453232632127
                                                                        .title(hotelname).icon(icon12)
                                                                        //.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                                                        .snippet(discount+"% discount")
                                                                )?.tag = idobject+","+discount+","+hotelname+","+ coinModel

                                                        }
                                                        catch (e : Exception){
                                                            e.printStackTrace()
                                                            Toast.makeText(context, "Sorry, Some of the Locations are not loaded.", Toast.LENGTH_SHORT).show()

                                                        }
                                                    }

                                                }
                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                                },
                                { error ->
                                    Log.e("Coupon Error", error.toString())
                                }) {

                                @Throws(AuthFailureError::class)
                                override fun getHeaders(): MutableMap<String, String> {
                                    val token = getTokenFromLocalStorage(context = context)
                                    val headers = HashMap<String, String>()
                                    headers["Content-Type"] = "application/json"
                                    headers["Authorization"] = "${token}"
                                    return headers
                                }
                            }


                            // Add the request to the RequestQueue.
                            val requestQueue = Volley.newRequestQueue(context)
                            requestQueue.add(request)
                        }
                        map.setOnMarkerClickListener { marker ->
                            Toast.makeText(context, "Please wait while the route is being calculated.", Toast.LENGTH_SHORT).show()
                            reload.value = true
                           // camerabtn.value = true
                            distancePanel.value=true
                            trackMarker = marker.position
                            LatSend = trackMarker.latitude
                            LonSend = trackMarker.longitude
                            val markerdiscount = marker.tag.toString()
                            val parts = markerdiscount.split(",")
                            if (parts.size == 4) {
                                selectedMarkerID = parts[0]
                                discountToBeSent = parts[1]
                                hotelToBeSent = parts[2]
                                coinModelToBeSent = parts[3]
                            }
                            mFusedLocationClient.lastLocation.addOnCompleteListener() { task ->
                                val location: Location? = task.result
                            }
                            false
                        }
                        // Timer to get Routes and update every 1 seconds
                        val timerone = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
                            if(::trackMarker.isInitialized && reload.value){
                                Location.distanceBetween(userLocation.latitude,
                                    userLocation.longitude,trackMarker.latitude,trackMarker.longitude,dist);
                                if(dist[0]/1000 >1){
                                    val decimalFormat = DecimalFormat("#.#")
                                    distance.value ="You are "+ decimalFormat.format((dist[0]/1000)).toFloat().toString()+"km Away"
                                }
                                else if(dist[0]/30 < 1){
                                    camerabtn.value = true
                                    distance.value = "You are inside the radius, collect the coupon."
                                }
                                else if(dist[0]/1000 < 1){
                                    camerabtn.value = false
                                    distance.value ="You are "+  (dist[0]).toInt().toString()+"m Away"
                                }

                                else{
                                    camerabtn.value = false
                                }

                            }
                        },0,1, TimeUnit.SECONDS)
                        // Timer to get Routes and update every 7 seconds
                        val timerseven = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
                            val fromFKIP = userLocation.latitude.toString() + "," + userLocation.longitude.toString()
                            if(::trackMarker.isInitialized && reload.value){
                                try {
                                    // Api call to get Routes
                                    val toComsats = trackMarker.latitude.toString() +","+ trackMarker.longitude.toString()
                                    val apiServices = RetrofitClient.apiServices(context)
                                    apiServices.getDirection(fromFKIP, toComsats, "AIzaSyBIabP4xrr3a_1cVKcQmJm80m2xlooqgf8").enqueue(object : Callback<DirectionResponses> {
                                        override fun onResponse(call: Call<DirectionResponses>, response: Response<DirectionResponses>) {
                                            drawPolyline(response)
                                            if(polyLine == null){
                                                try{
                                                    polyLine = map.addPolyline(
                                                        polyline!!
                                                    )
                                                }
                                                catch (e:Exception){
                                                    e.printStackTrace()
                                                }
                                            }
                                            else{
                                                try{
                                                    polyLine!!.remove();
                                                    polyLine = map.addPolyline(
                                                        polyline!!
                                                    )
                                                }
                                                catch (e:Exception){
                                                    e.printStackTrace()
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                                            Log.e("error", t.localizedMessage)
                                        }
                                    })
                                }
                                catch (e:Exception){
                                    Toast.makeText(context, "We are sorry the Route can't be calculated, try again Later.", Toast.LENGTH_LONG).show()
                                    e.printStackTrace()
                                }
                            }
                        },0,7, TimeUnit.SECONDS)
                    }
                }

























//            Row (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//                    .background(color = MaterialTheme.colorScheme.primary),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ){
//                IconButton(onClick = {}, modifier = Modifier.padding(10.dp)) {
//                    Icon(painter = rememberVectorPainter(Icons.Outlined.LocationOn), contentDescription = "AR", tint = Color.White)
//                }
//                IconButton(onClick = {
//                    navigator.pop()
//                    navigator.push(ViewBookings)
//                }, modifier = Modifier.padding(10.dp)) {
//                    Icon(painter = rememberVectorPainter(Icons.Outlined.DateRange), contentDescription = "Booking", tint = Color.White)
//                }
//                IconButton(onClick = {
//                    navigator.pop()
//                    navigator.push(LandingPage)
//                                     }, modifier = Modifier.padding(10.dp)) {
//                    Icon(painter = rememberVectorPainter(Icons.Filled.Home), contentDescription = "Landing", tint = Color.White)
//                }
//                IconButton(onClick = {
//                    navigator.pop()
//                    navigator.push(WishList) }, modifier = Modifier.padding(10.dp)) {
//                    Icon(painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder), contentDescription = "WishList", tint = Color.White)
//                }
//                IconButton(onClick = {
//                    navigator.pop()
//                    navigator.push(ViewProfile)
//                }, modifier = Modifier.padding(10.dp)) {
//                    Icon(painter = rememberVectorPainter(Icons.Outlined.Person), contentDescription = "Account", tint = Color.White)
//                }
//            }
            }

        }

    }

    private fun loadBitmap(context: Context): Bitmap {
        var bitmap : Bitmap =Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888)
        val imageRequest = ImageRequest.Builder(context)
            .data("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg")
            .target { drawable ->
                var bitmap = drawable.toBitmap() // This is the bitmap 🚨
                bitmap.height = 5
                bitmap.width = 5
            }
            .build()
        ImageLoader(context).enqueue(imageRequest)
        return bitmap
    }

    // GET Categories Function
    fun getCoupons(context: Context) {
        val url = "${GlobalStrings.baseURL}customer/coupons/getCoupons"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Coupons...")
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
            val request = @SuppressLint("SuspiciousIndentation")
            object : JsonObjectRequest(
                Request.Method.GET, url, ViewServiceCategory.params,
                { response ->
                    coupons.clear()
                    Log.d("HASHDASDAS", response.toString())
                    responseAsli = response
                            Log.d("GGGGGGG", response.toString())
                    var abc = responseAsli.getJSONArray("coupons")
                    for (i in 0 until abc.length()) {
                        var jsonObject2222 = abc.getJSONObject(i)
                        var iii = jsonObject2222
                        try {
                            val jsonObject = iii
                            if (jsonObject.has("hotel")) {
                                val hotelObject = jsonObject.getJSONObject("hotel")
                                hotelname = hotelObject.optString("name")
                                discount = jsonObject.getString("percentage")
                                var abc = jsonObject.getJSONObject("coinModel")
                                var ik = abc.getJSONObject("modelUrl")
                                coinModel = ik.getString("androidSrc")
                                val idobject = jsonObject.getString("_id")
                                var model = ik.getString("image")

                                if (jsonObject.has("locationsList")) {
                                    var locationObject = jsonObject.getJSONArray("locationsList")
                                    for (i in 0 until locationObject.length()) {
                                        val jsonObject22 = locationObject.getJSONObject(i)
                                        latii = jsonObject22.getDouble("lat")
                                        longii = jsonObject22.getDouble("lng")
//                                        val imageRequest = ImageRequest.Builder(context)
//                                            .data("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg")
//                                            .target { drawable ->
                                                //var bitmap = drawable.toBitmap() // This is the bitmap
                                        val bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)
                                        coupons.add(TableRow(_id =idobject, latitude = latii, longitude = longii, hotel =  hotelname,model=bitmap,
                                                    discount=discount, modelImage =coinModel ))
//                                            }
//                                            .build()
//                                        ImageLoader(context).enqueue(imageRequest)
//                                        val icon12 = bitmapDescriptorFromVector(
//                                            context, R.drawable.baseline_remove_red_eye_24
//                                        )
                                    }
                                }
                            }
                        }
                        catch (e:Exception){

                        }
                    }
                    progressDialog.dismiss()
                },
                { error ->

                    Log.d("HASHDASDAS",error.toString())
                   coupons.clear()
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

    //Get Bitmap from Url
    suspend fun getBitmapFromURL(imgUrl: String?): Bitmap? =
        withContext(Dispatchers.IO) {
            try {
                val url = URL(imgUrl)
                val connection: HttpURLConnection =
                    url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                // Log exception
                null
            }
        }

    fun loadImageUrlToBitmapDescriptor(context: Context, imageUrl: String, targetSize: Int, callback: (BitmapDescriptor) -> Unit) {
        Thread {
            try {
                val inputStream: InputStream = URL(imageUrl).openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetSize, targetSize, false)
                val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(scaledBitmap)

                // Ensure the callback is called on the main thread
                Handler(Looper.getMainLooper()).post {
                    callback(bitmapDescriptor)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                // Handle the error
            }
        }.start()
    }

    private fun loadBitmapFromUrl(imageUrl: String): Bitmap? {
        return try {
            val inputStream = java.net.URL(imageUrl).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
//
//    fun Marker.loadIcon(context: Context, url: String?) {
//        Glide.with(context)
//            .asBitmap()
//            .load(url)
//            .error(R.drawable.wallet) // to show a default icon in case of any errors
//            .listener(object : RequestListener<Bitmap> {
//                fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Bitmap?,
//                    model: Any?,
//                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
//                    dataSource: com.bumptech.glide.load.DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//                fun onResourceReady(
//                    resource: Bitmap?,
//                    model: Any?,
//                    target: Target,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return resource?.let {
//                        BitmapDescriptorFactory.fromBitmap(it)
//                    }?.let {
//                        setIcon(it); true
//                    } ?: false
//                }
//
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    TODO("Not yet implemented")
//                }
//            }).submit()
//    }


    fun bitmapDescriptorFromVector(
        context: Context,
        vectorResId: Int,
        size : Int = 40
    ): BitmapDescriptor? {

        // retrieve the actual drawable
        val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
        drawable.setBounds(0, 0, size, size)
        val bm = Bitmap.createBitmap(
            size,
            size,
            Bitmap.Config.ARGB_8888
        )

        // draw it onto the bitmap
        val canvas = android.graphics.Canvas(bm)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bm)
    }

    // To Draw Waypoint to the Coin Marker
    @SuppressLint("ResourceAsColor")
    private fun drawPolyline(response: Response<DirectionResponses>) {
        try {
            var shape = response.body()?.routes?.get(0)?.overviewPolyline?.points
            polyline = PolylineOptions()
                .addAll(PolyUtil.decode(shape))
                .width(16f)
                .color(0xFF0000FF.toInt())
        }
        catch (e:Exception){
            // Toast.makeText(this, "Sorry, Waypoint could not be loaded.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    // Api Services For Directions
    private interface ApiServices {
        @GET("maps/api/directions/json")
        fun getDirection(@Query("origin") origin: String,
                         @Query("destination") destination: String,
                         @Query("key") apiKey: String): Call<DirectionResponses>
    }


    // To make the URL to get JSON results for Google API
    private object RetrofitClient {
        fun apiServices(context: Context): ApiServices {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://maps.googleapis.com")
                .build()

            return retrofit.create<ApiServices>(ApiServices::class.java)
        }
    }

}