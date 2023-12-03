package com.example.adminoffice.ui.theme.Customer.Wishlist

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
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
import com.example.adminoffice.ui.theme.Customer.Bookings.ViewBookingsCustomer
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review
import com.example.adminoffice.ui.theme.Customer.HotelCardWithSlider
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import org.json.JSONObject

object WishList  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var Hotels = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel>()
    var notRan = true
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var user = getUserFromLocal(context)
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column (modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeightDp - 50.dp)
                    , horizontalAlignment = Alignment.CenterHorizontally){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), contentAlignment = Alignment.Center){
                        Text(text = "Wishlist", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Divider(color = Color(0xFFE2E2E2))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(
                            rememberScrollState()
                        )) {
                        if(Hotels.isEmpty()){
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 50.dp)
                                .height(screenHeightDp - 200.dp), contentAlignment = Alignment.Center){
                                Column(horizontalAlignment = Alignment.CenterHorizontally){
                                    Image(
                                        painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2Fno-records.png?alt=media&token=d15e43c9-f619-47c5-8817-41ab00247cc4"),
                                        contentDescription = "image",
                                        modifier = Modifier
                                            .size(300.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    (CornerSize(
                                                        10.dp
                                                    ))
                                                )
                                            ),
                                        contentScale = ContentScale.FillBounds
                                    )

                                    Text(text = "No Hotels Found", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        else{
                            for(it in Hotels){
                                Spacer(modifier = Modifier.size(20.dp))
                                Box{

                                    HotelCardWithSlider(
                                        name = it.name, images = it.images, location = it.location, price = it.averagePrice.toString(),
                                        rating = it.averageRating.toString(), shown = false)
                                    Box(modifier = Modifier.background(Color.Red, RoundedCornerShape(10.dp)).clickable {
                                        RemoveToWishlist(context=context,hotelid= it._id, customerid = user._id){
                                            if(it){
                                                getWishList(context)
                                            }
                                        }
                                    }){
                                        Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.size(30.dp), tint = Color.White)
                                    }
                                }
                                Spacer(modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Divider(
                        color = Color(0xFF8A8A8A),
                        thickness = 0.4.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = Color.White),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        IconButton(onClick = {
                            navigator.push(MapsCoupon)
                        }, modifier = Modifier.padding(10.dp)) {
                            Icon(painter = rememberVectorPainter(Icons.Outlined.LocationOn), contentDescription = "AR", tint = Color.Black)
                        }
                        IconButton(onClick = {
                            navigator.replace(ViewBookingsCustomer)
                        }, modifier = Modifier.padding(10.dp)) {
                            Icon(painter = rememberVectorPainter(Icons.Outlined.DateRange), contentDescription = "Booking", tint = Color.Black)
                        }
                        IconButton(onClick = {navigator.replace(LandingPage)}, modifier = Modifier.padding(12.dp)) {
                            Icon(painter = rememberVectorPainter(Icons.Outlined.Home), contentDescription = "Landing", tint = Color.Black)
                        }
                        IconButton(onClick = {
                            navigator.replace(WishList)
                        }, modifier = Modifier.padding(10.dp)) {
                            Icon(painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder), contentDescription = "WishList", tint = GlobalStrings.CustomerColorMain)
                        }
                        IconButton(onClick = {
                            navigator.replace(ViewProfile)
                        }, modifier = Modifier.padding(10.dp)) {
                            Icon(painter = rememberVectorPainter(Icons.Outlined.Person), contentDescription = "Account", tint = Color.Black)
                        }
                    }
                }
            }
        }
            getWishList(context)

    }
    fun RemoveToWishlist(context: Context, customerid : String, hotelid: String, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}customer/hotels/wishlist/deleteHotel"


        // Request parameters
        val params = JSONObject()
        // params.put("customerid", customerid)
        params.put("hotelid", hotelid)
        Log.d("ASDWFVSA11", params.toString())
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
                Request.Method.DELETE, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("ASDWFVSA11", response.toString())
                    Toast
                        .makeText(
                            context,
                            "Removed from WishList",
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

    fun getWishList(context: Context){
        if(!notRan){
            return
        }
        val url = "${GlobalStrings.baseURL}customer/hotels/wishlist/getHotels"
        Log.d("ASDWFVSA11", url.toString())

        // Request parameters
        val params = JSONObject()
        // params.put("customerid", customerid)
   //     params.put("hotelid", hotelid)
        Log.d("ASDWFVSA11", params.toString())
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
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
                    var hotels  = response.getJSONArray("wishlist")
                    for(i in 0 until hotels.length()){
                        var hotel = hotels.getJSONObject(i)
                        var ServiceList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service>()
                        var VideoList = mutableStateListOf<String>()
                        var FloorList = mutableStateListOf<String>()
                        var RulesList = mutableStateListOf<String>()
                        var ReviewsList = mutableStateListOf<Review>()
                        var RefundList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Refund>()
                        var ImageList = mutableStateListOf<String>()
                        var RoomsList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room>()
                        var userID = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Userid(__v = 0, _id = "hotelOwner_id", cnic = "hotelOwneruseridcnic",
                            contactNo = "hotelOwneruseridcontactNo", createdAt = "hotelOwneruseridcreatedAt", deletedAt = "hotelOwneruseriddeletedAt",
                            email = "hotelOwneruseridemail", firstName = "hotelOwneruseridfirstName", isDeleted = false,
                            lastName = "hotelOwneruseridlastName", password = "hotelOwneruseridpassword", profilePicture = "hotelOwneruseridprofilePicture",
                            role = "hotelOwneruseridrole", status = "hotelOwneruseridstatus", updatedAt = "hotelOwneruseridupdatedAt", userCategory = "hotelOwneruseriduserCategory",
                            verified = true)
                        var hotelOwnerOBJ = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.HotelOwner(__v = 0, _id = "hotelOwner_id", createdAt = "hotelOwnercreatedAt",
                            deletedAt = "hotelOwnerdeletedAt", isDeleted = false, updatedAt = "hotelOwnerupdatedAt",
                            userid = userID)


                        var _id = hotel.getString("_id")
                        // var id = hotel.getString("id")
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
                       // var hotelaverageRating = hotel.get("averageRating").toString()
//                        var hoteltotalReviews = hotel.getInt("totalReviews")
//                        var hotelaveragePrice = hotel.getInt("averagePrice")
                        //  var hotelid = hotel.getInt("id")
                        //var hotelownerName = hotel.getString("ownerName")
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
//                        for(i in 0 until hotelreviews.length()){
//                            var reviewOne = hotelreviews.getJSONObject(i)
//                            var review_id = reviewOne.getString("_id")
//                            var customer = reviewOne.getString("customer")
//                            var CC = Customer(name = "AA", profile = "aaas")
//                            var title = reviewOne.getString("title")
//                            var description = reviewOne.getString("description")
//                            var isDeleted = reviewOne.getBoolean("isDeleted")
//                            var deletedAt = reviewOne.get("deletedAt")
//                            var stars = reviewOne.getInt("stars")
//                            var createdAt = reviewOne.getString("createdAt")
//                            var updatedAt = reviewOne.getString("updatedAt")
//                            var __v = reviewOne.getInt("__v")
//                            var Revieww = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review(__v = __v,_id=review_id,
//                                createdAt=createdAt,customer=CC,deletedAt=deletedAt, description = description, isDeleted = isDeleted, stars = stars,title=title, updatedAt = updatedAt)
//                            ReviewsList.add(Revieww)
//                        }
                        var hotelfloors = hotel.getJSONArray("floors")
                        for(i in 0 until hotelfloors.length()){
                            var hotelFloorList = hotelfloors.getString(i)
                            FloorList.add(hotelFloorList)
                        }

                        var hotelservices = hotel.getJSONArray("services")
//                        for(i in 0 until hotelservices.length()){
//                            var serviceOBJ = hotelservices.getJSONObject(i)
//                            var hotelservices_id = serviceOBJ.getString("_id")
//                            var hotelservicestype = serviceOBJ.getString("type")
//                            var hotelservicesname = serviceOBJ.getString("name")
//                            var hotelservicesdescription = serviceOBJ.getString("description")
//                            var hotelservicesimage = serviceOBJ.getString("image")
//                            var hotelservicespriceRate = serviceOBJ.getString("priceRate")
//                            var hotelservicesprice = serviceOBJ.getInt("price")
//                            var hotelservicesaddedByRole = serviceOBJ.getString("addedByRole")
//                            var hotelservicesdeletedAt = serviceOBJ.get("deletedAt")
//                            var hotelservicesvisible = serviceOBJ.getBoolean("visible")
//                            var hotelservicesisDeleted = serviceOBJ.getBoolean("isDeleted")
//                            var hotelservicescreatedAt = serviceOBJ.getString("createdAt")
//                            var hotelservicesupdatedAt = serviceOBJ.getString("updatedAt")
//                            var hotelservices__v = serviceOBJ.getInt("__v")
////                            var hotelservicesserviceCategory = serviceOBJ.getJSONObject("serviceCategory")
////                            var hotelservicesserviceCategory_id = hotelservicesserviceCategory.getString("_id")
////                            var hotelservicesserviceCategorytitle = hotelservicesserviceCategory.getString("title")
////                            var hotelservicesserviceCategoryimage = hotelservicesserviceCategory.getString("image")
////                            var hotelservicesserviceCategoryisDeleted = hotelservicesserviceCategory.getBoolean("isDeleted")
////                            var hotelservicesserviceCategorydeletedAt = hotelservicesserviceCategory.get("deletedAt")
////                            var hotelservicesserviceCategorycreatedAt = hotelservicesserviceCategory.getString("createdAt")
////                            var hotelservicesserviceCategoryupdatedAt = hotelservicesserviceCategory.getString("updatedAt")
////                            var hotelservicesserviceCategory__v = hotelservicesserviceCategory.getInt("__v")
//                            var cat = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.ServiceCategory(__v = 0,_id="hotelservicesserviceCategory_id", createdAt = "hotelservicesserviceCategorycreatedAt",
//                                deletedAt ="hotelservicesserviceCategorydeletedAt", image = "hotelservicesserviceCategoryimage", isDeleted = false,
//                                title = "hotelservicesserviceCategorytitle", updatedAt = "hotelservicesserviceCategoryupdatedAt")
//                            var ser = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service(__v = hotelservices__v,_id=hotelservices_id, addedByRole = hotelservicesaddedByRole,
//                                createdAt = hotelservicescreatedAt, deletedAt = hotelservicesdeletedAt, description = hotelservicesdescription,
//                                image = hotelservicesimage,isDeleted = hotelservicesisDeleted, name = hotelservicesname,
//                                price = hotelservicesprice, priceRate = hotelservicespriceRate, type = hotelservicestype,
//                                updatedAt = hotelservicesupdatedAt, visible = hotelservicesvisible, serviceCategory = cat)
//                            ServiceList.add(ser)
//                        }


//                        var hotelrefundPolicy = hotel.getJSONObject("refundPolicy")
//                        var hotelrefundPolicy_id = hotelrefundPolicy.getString("_id")
//                        var hotelrefundPolicydescription = hotelrefundPolicy.getString("description")
//                        var hotelrefundPolicyrefunds = hotelrefundPolicy.getJSONArray("refunds")
//                        for(i in 0 until hotelrefundPolicyrefunds.length()){
//                            var refundsOBJ = hotelrefundPolicyrefunds.getJSONObject(i)
//                            var refundsOBJdays = refundsOBJ.getInt("days")
//                            var refundsOBJpercentage = refundsOBJ.getInt("percentage")
//                            var refundsOBJ_id = refundsOBJ.getString("_id")
//                            var refundsOBJisDeleted = refundsOBJ.getBoolean("isDeleted")
//                            var refundsOBJdeletedAt = refundsOBJ.get("deletedAt")
//                            var refundOBJ = Refund(_id=refundsOBJ_id, days = refundsOBJdays, deletedAt = refundsOBJdeletedAt,
//                                isDeleted = refundsOBJisDeleted, percentage = refundsOBJpercentage)
//                            RefundList.add(refundOBJ)
//
//                        }

//                        var hotelrefundPolicyisDeleted = hotelrefundPolicy.getBoolean("isDeleted")
//                        var hotelrefundPolicydeletedAt = hotelrefundPolicy.get("deletedAt")
//                        var hotelrefundPolicycreatedAt = hotelrefundPolicy.getString("createdAt")
//                        var hotelrefundPolicyupdatedAt = hotelrefundPolicy.getString("updatedAt")
//                        var hotelrefundPolicy__v = hotelrefundPolicy.getInt("__v")
//                        var RefundPolicy = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy(__v = 0,_id="hotelrefundPolicy_id", createdAt = "hotelrefundPolicycreatedAt",
//                            deletedAt = "hotelrefundPolicydeletedAt", description = "hotelrefundPolicydescription", isDeleted = false,
//                            refunds = RefundList, updatedAt = "hotelrefundPolicyupdatedAt")
                        var RefundPolicy = RefundPolicy(__v = 0,_id="hotelrefundPolicy_id", createdAt = "hotelrefundPolicycreatedAt",
                            deletedAt = "hotelrefundPolicydeletedAt", description = "hotelrefundPolicydescription", isDeleted = false,
                            refunds = RefundList, updatedAt = "hotelrefundPolicyupdatedAt")
                        try{
                            var rooms = hotel.getJSONArray("room")
                            for(i in 0 until rooms.length()){
                                var room = rooms.getJSONObject(i)
                                var _id = room.getString("_id")
                                var floor = room.getString("floor")
                                var roomNumber = room.getString("roomNumber")
                                var type = room.getString("type")
                                var description = room.getString("description")
                                var images = room.getJSONArray("images")
                                var imglist = mutableStateListOf<String>()
                                for(i in 0 until images.length()){

                                    var img = images.getString(i)
                                    imglist.add(img)
                                }
                                var videos = room.getJSONArray("videos")
                                var vidlist = mutableStateListOf<String>()
                                for(i in 0 until videos.length()){

                                    var img = videos.getString(i)
                                    vidlist.add(img)
                                }
                                var adults = room.getInt("adults")
                                var children = room.getInt("children")
                                var price = room.getInt("price")
                                var size = room.getString("size")
                                var Room = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room(_id=_id,
                                    hotel="asd",floor=floor,roomNumber=roomNumber,type=type,description=description,
                                    images=imglist,videos=vidlist,adults=adults,children=children,price=price,size=size,
                                    panoramicView = null, roomServices = imglist, inventories =imglist,isDeleted = false,deletedAt = null, models = imglist, createdAt = "asdasd", updatedAt = "Asd", __v = 0 )
                                RoomsList.add(Room)
                            }

                        }
                        catch(e:Exception){
                            e.printStackTrace()
                        }
//                        var avgrat = 0
//                        if(hotelaverageRating=="null"){
//                            avgrat=0
//                        }
//                        else{
//                            avgrat=hotelaverageRating.toInt()
//                        }
                        var Hotel = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel(__v = hotel__v,_id=_id, averagePrice = 22, averageRating = 5,
                            city = hotelcity, createdAt = hotelcreatedAt, deletedAt = hoteldeletedAt, description = hoteldescription,
                            facebookURL = hotelfacebookURL, floors = FloorList, hotelOwner = hotelOwnerOBJ, id = 0, images = ImageList,
                            instagramURL = hotelinstagramURL, isDeleted = hotelisDeleted, lat = hotellat, lng = hotellng, location = hotellocation,
                            name = hotelname, ownerName = "hotelownerName", phoneNo = hotelphoneNo, refundPolicy =RefundPolicy, reviews = ReviewsList,
                            rules = RulesList, services = ServiceList, status = hotelstatus, telephoneNo = hoteltelephoneNo, totalReviews = 3,
                            updatedAt = hotelupdatedAt, videos = VideoList, websiteURL = hotelwebsiteURL, rooms = RoomsList)

                        Hotels.add(Hotel)

                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS222",error.toString())
                    Hotels.clear()
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