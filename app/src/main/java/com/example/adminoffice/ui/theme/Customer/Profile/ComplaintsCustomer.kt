package com.example.adminoffice.ui.theme.Customer.Profile

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
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
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Complaint
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Customer.Wishlist.WishList
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import org.json.JSONObject

object ComplaintsCustomer  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var Complaints = mutableStateListOf<Complaint>()
    var Ran = false
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var user = getUserFromLocal(context)
       Scaffold() {
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .background(Color.White)
           ) {
               Box(modifier = Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.Center){
                   Text(text = "Complaints", fontSize = 20.sp, fontWeight = FontWeight.Bold)
               }
               Divider(color = Color(0xFFE2E2E2))
               Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                   for(comp in Complaints){
                       Box(modifier = Modifier
                           .padding(20.dp)
                           .border(0.4.dp, Color(0xFFDFDFDF), RoundedCornerShape(15.dp))){
                           Column(modifier = Modifier.padding(5.dp)) {
                               Row(modifier = Modifier.padding(vertical =10.dp), verticalAlignment = Alignment.CenterVertically){
                                   Image(
                                       painter = rememberAsyncImagePainter(if(user.profilePicture=="user"){"https://icon-library.com/images/no-user-image-icon/no-user-image-icon-9.jpg"}else{
                                           user.profilePicture}),
                                       contentDescription = "image",
                                       modifier = Modifier
                                           .size(40.dp)
                                           .clip(
                                               RoundedCornerShape(
                                                   (CornerSize(
                                                       20.dp
                                                   ))
                                               )
                                           ),
                                       contentScale = ContentScale.FillBounds
                                   )
                                   Spacer(modifier = Modifier.size(15.dp))
                                   Column{
                                       Text(text =user.firstname+" "+user.lastName, fontSize = 14.sp, fontWeight = FontWeight.Light)
                                       Text(text = user.email, fontSize = 10.sp, fontWeight = FontWeight.Light, color = Color.Gray
                                       )
                                   }
                                   Spacer(modifier = Modifier.size(30.dp))
                                   Box(modifier = Modifier
                                       .height(20.dp)
                                       .width(100.dp)
                                       .background(if(comp.status=="pending"){Color(0xFFCA0007)
                                       }else{Color(0xFF229728)}, RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center){
                                       Text(text = comp.status, color = Color.White, fontSize = 10.sp)
                                   }
                                   Spacer(modifier = Modifier.size(15.dp))
                                   Box(modifier = Modifier.background(Color.Red, RoundedCornerShape(10.dp)).size(30.dp).clickable {
                                       deleteComplaints(context,comp._id)
                                   }, contentAlignment = Alignment.Center){
                                       Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.size(15.dp), tint = Color.White)
                                   }
                               }
                               Box(modifier = Modifier.padding(5.dp)){
                                   Text(text = comp.description, lineHeight = 17.sp)
                               }
                               if(comp.feedback!=""){
                                   Column (modifier = Modifier.padding(top = 20.dp, start = 20.dp, bottom = 5.dp)){
                                       Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically){
                                           Column{
                                               Text(text ="Feedback", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                           }
                                       }
                                       Box(modifier = Modifier.padding(5.dp)){
                                           Text(text = comp.feedback, fontSize = 12.sp, lineHeight = 13.sp)
                                       }
                                   }
                               }

                           }
                       }
                   }
               }
           }
       }
            getComplaints(context)
    }
    fun getComplaints(context: Context){

        val url = "${GlobalStrings.baseURL}customer/complaints/getComplaints"
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
                    Complaints.clear()
                    Log.d("HAPP",response.toString())
                    var complaints  = response.getJSONArray("complaints")
                    for(i in 0 until complaints.length()){
                        var feedback = ""
                        var complaint = complaints.getJSONObject(i)
                        var _id = complaint.getString("_id")
                        var title = complaint.getString("title")
                        var description = complaint.getString("description")
                        if(complaint.has("feedback")){
                            feedback = complaint.getString("feedback")
                        }
                        var category = complaint.getString("category")
                        var status = complaint.getString("status")
                        var complaintsss = Complaint(_id=_id, category = category,
                            description=description,feedback=feedback, image = "", status =status,
                            title=title)
                        Complaints.add(complaintsss)
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Complaints.clear()
                    Log.d("HASHDASDAS222",error.toString())
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

    fun deleteComplaints(context: Context,id:String){

        val url = "${GlobalStrings.baseURL}customer/complaints/deleteComplaint/${id}"
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
                Request.Method.DELETE, url, params,
                { response ->
                    Log.d("ASDWFVSA11",response.toString())
                    progressDialog.dismiss()
                    getComplaints(context)
                },
                { error ->
                    Log.d("ASDWFVSA11",error.toString())
                    Log.d("ASDWFVSA11",error.networkResponse.statusCode.toString())
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