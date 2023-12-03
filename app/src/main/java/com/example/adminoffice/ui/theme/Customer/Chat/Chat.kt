package com.example.adminoffice.ui.theme.Customer.Chat


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage

object ChatCustomer  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var Hotels =
        mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel>()
    var storage = FirebaseStorage.getInstance();
    var storageReference = storage.getReference();


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var user = getUserFromLocal(context)
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        // Write a message to the database
        val database = Firebase.storage
        FirebaseStorage.getInstance()
        val myRef = database.getReference("message")
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(modifier = Modifier.fillMaxWidth().padding(10.dp), contentAlignment = Alignment.Center){
                    Text(text = "Chat", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Divider(color = Color(0xFFE2E2E2))
                Column(){

                }
            }
        }

    }
}
