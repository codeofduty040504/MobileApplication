package com.example.adminoffice.ui.theme.Utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Utils.Screens.Profiling.Login

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(callback: ()-> Unit){
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    var role = getRoleFromLocalStorage(context)
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if(role=="admin"){
                    Text(
                        text = "Admin Dashboard",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }else{
                    Text(
                        text = "Owner Dashboard",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = {
                    saveTokenToLocalStorage(context, "")
                    navigator.popUntilRoot()
                    navigator.replace(Login)
                }) {
                    Icon(painterResource(id = R.drawable.baseline_logout_24), contentDescription = "", tint = Color.White)
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Black
        ),
        navigationIcon = {
            IconButton(onClick = callback) {
                Icon(Icons.Default.Menu, contentDescription = "", tint = Color.White)
            }
        },

        )
}