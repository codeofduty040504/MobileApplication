package com.example.adminoffice.ui.theme.Customer.Profile

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.method.LinkMovementMethod
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Wishlist.WishList
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable

object Policy  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var PolicyStatement = mutableStateOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        val user = getUserFromLocal(context)
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
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
                        Text(text = "Policy", fontSize = 18.sp, fontWeight = FontWeight.Black)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//                            Text(
//                                text = "Policy",
//                                color = GlobalStrings.CustomerColorMain,
//                                fontWeight = FontWeight.SemiBold,
//                                fontSize = 24.sp
//                            )
//                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = PolicyStatement.value, fontSize = 12.sp, lineHeight = 14.sp)
                        }
                        Spacer(modifier = Modifier.size(10.dp))

                    }
                    

                }
            }

        }
      //  getCategories(context)
    }

    // GET Categories Function
    fun getCategories(context: Context) {
        val url = "${GlobalStrings.baseURL}customer/settings/getSettings"
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (!isInternetAvailable(context)) {
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        } else {
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, FAQ.params,
                { response ->
                    Log.d("HASHDASDAS", response.toString())
                    var settinngs = response.getJSONObject("setting")
                    PolicyStatement.value = settinngs.get("policy").toString()
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS", error.toString())
                    Log.d("HASHDASDAS", error.networkResponse.statusCode.toString())

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