package com.example.adminoffice.ui.theme.Customer.Profile

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Wishlist.WishList
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
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
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = "Policy",
                                color = GlobalStrings.CustomerColorMain,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp
                            )
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = PolicyStatement.value, fontSize = 12.sp, lineHeight = 14.sp)
                        }
                        Spacer(modifier = Modifier.size(10.dp))

                        HtmlTextExample()
                    }
                }
            }

        }
       // getCategories(context)
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

    @Composable
    fun HtmlTextExample() {
        val htmlText =
            "<p style=\"text-align: start\">At <strong>DABS,</strong> we prioritize the privacy and security of our users. This Privacy Policy outlines our commitment to safeguarding the personal information you entrust to us through our hotel booking platform.</p>"

        val annotatedString = buildAnnotatedString {
            val startIndex = htmlText.indexOf("<strong>")
            val endIndex = htmlText.indexOf("</strong>")

            if (startIndex != -1 && endIndex != -1) {
                // Style the "strong" text with bold
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(htmlText.substring(startIndex + "<strong>".length, endIndex))
                }

                // Append the rest of the text
                append(htmlText.substring(endIndex + "</strong>".length))
            } else {
                // If no strong tag found, append the entire text
                append(htmlText)
            }
        }

        Text(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp), color = Color.Black
        )
    }
}