package com.example.adminoffice.ui.theme.Customer.Profile

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable

object FAQs  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    data class FAQItem(val question: String, val answer: String)
    var serviceCategories = mutableStateListOf<FAQItem>()
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
                Box(modifier = Modifier
                    .size(40.dp)
                    .background(GlobalStrings.CustomerColorMain, RoundedCornerShape(10.dp))
                    .padding(10.dp)
                    .clickable {
                        navigator.pop()
                    }){
                    Icon(Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(30.dp), tint = Color.White)
                }
                Spacer(modifier = Modifier.width((screenWidthDp/4)-5.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "FAQs", fontSize = 18.sp, fontWeight = FontWeight.Black)
            }
            Column (modifier = Modifier.padding(20.dp)){

                Image(
                    painter = rememberAsyncImagePainter("https://cdn.shopify.com/app-store/listing_images/be8e18fe7fd62b265563360c2986633d/promotional_image/CLHpktmI5_QCEAE=.jpeg?height=720&quality=90&width=1280"),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp, 200.dp)
                        .clip(RoundedCornerShape((CornerSize(20.dp))))
                )

                FAQComponent(faqList = serviceCategories)
            }
        }
        getCategories(context)
    }
    // GET Categories Function
    fun getCategories(context: Context) {
        val url = "${GlobalStrings.baseURL}customer/settings/getSettings"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading FAQ...")
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
                Request.Method.GET, url, FAQ.params,
                { response ->
                    Log.d("HASHDASDAS",response.toString())
                    var settinngs  = response.getJSONObject("setting")
                    var categories  = settinngs.getJSONArray("faqs")
                    serviceCategories.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)
                        var question = category.getString("question")
                        var answer = category.getString("answer")

                        serviceCategories.add(
                            FAQItem(
                                question = question,
                                answer = answer,
                            )
                        )
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS",error.toString())
                    Log.d("HASHDASDAS",error.networkResponse.statusCode.toString())
                    serviceCategories.clear()
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
    @Composable
    fun FAQComponent(faqList: List<FAQItem>) {
        LazyColumn {
            items(faqList) { faqItem ->
                FAQItemCard(faqItem)
            }
        }
    }
    @Composable
    fun FAQItemCard(faqItem: FAQItem) {
        var expanded by remember { mutableStateOf(false) }

        Box(modifier = Modifier.padding(10.dp)){
            Box(modifier = Modifier
                .background(GlobalStrings.CustomerColorMain, RoundedCornerShape(10.dp))
                .clickable { expanded = !expanded }){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                ) {
                   Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                       Text(
                           text = faqItem.question,
                           fontWeight = FontWeight.Bold,
                           color = Color.White
                       )
                       Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.White)
                   }

                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = faqItem.answer,color = Color.White)
                    }
                }
            }
        }
    }
}