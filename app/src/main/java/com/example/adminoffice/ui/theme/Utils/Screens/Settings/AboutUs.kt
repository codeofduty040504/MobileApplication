package com.example.adminoffice.ui.theme.Utils.Screens.Settings


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import cafe.adriel.voyager.core.screen.Screen
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
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
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
import com.example.adminoffice.ui.theme.Utils.Screens.Services.EditServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.isValidDescription
import com.example.adminoffice.ui.theme.Utils.isValidName
import kotlinx.coroutines.launch
import org.json.JSONObject

object AboutUs  : Screen {
    data class TableRow(
        val _id: String,
        val question: String,
        val answer: String,
    )

    var deletemodalopen = mutableStateOf(false)
    val params = JSONObject()
    var usersJsonArrayError = mutableStateOf("")
    var modalopen = mutableStateOf(false)
    var modalopenFAQ = mutableStateOf(false)
    lateinit var current: TableRow
    var serviceCategories = mutableStateListOf<TableRow>()

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
                }
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 60.dp, horizontal = 10.dp)
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                        Text(text = "About Us", color = GlobalStrings.AdminColorMain, fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                        Icon(painterResource(id = R.drawable.quote),contentDescription = "quote", modifier = Modifier.size(20.dp), tint = GlobalStrings.AdminColorMain)
                        Text(text = "Introducing DABS (Digital Albergo Booking System), your gateway to a seamless hotel booking experience. Imagine a platform that not only simplifies reservations but also enhances your comfort while ensuring the best possible rates. With DABS, we've reimagined hotel booking, offering a centralized hub where you can effortlessly find and secure accommodations that suit your preferences.", fontSize = 12.sp, lineHeight = 14.sp)

                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                        Text(text = "Meet Our Team", color = GlobalStrings.AdminColorMain, fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 0.dp, end = 0.dp, bottom = 0.dp), horizontalArrangement = Arrangement.Center){
                        Text(text = "Introducing our dream team, the driving force behind project DABS, a synergy of visionary designers, tech enthusiasts, and ingenious minds, all united to turn creativity into realit", fontSize = 12.sp, lineHeight = 14.sp)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .border(1.dp, Color(0xFFB6B4BB), RoundedCornerShape(10.dp))
                        .padding(2.dp)){
                       Column(
                           Modifier
                               .fillMaxWidth()
                               .padding(5.dp)){
                           Image(
                               painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/tra-ed5418c9.jpeg?alt=media&token=ee247443-2816-48ea-91ab-81edeb05f1c6&_gl=1*cs0xnn*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NjkzNDUyNi4yNS4xLjE2OTY5MzQ1NjQuMjIuMC4w"),
                               contentDescription = "image",
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .height(300.dp)
                                   .clip(
                                       RoundedCornerShape(
                                           (CornerSize(
                                               5.dp
                                           ))
                                       )
                                   ),
                               contentScale = ContentScale.FillBounds
                           )
                           Text(text = "Dr. Tehseen Riaz Abbasi", fontSize = 16.sp, fontWeight = FontWeight.W800)
                           Text(text = "Product Manager", color = GlobalStrings.AdminColorMain, fontSize = 12.sp)
                           Spacer(modifier = Modifier.size(5.dp))
                           Text(text = "As the Product Manager, I oversaw the quality assurance for the Dabs, harmonizing its development and ensuring impeccable standards in every aspect", fontSize = 12.sp, lineHeight = 13.sp)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                                Image(
                                    painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2Fgithub.png?alt=media&token=9d3fc7be-2163-48a3-a114-acf9326a168a&_gl=1*ky0f8z*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NjkzNDUyNi4yNS4xLjE2OTY5MzUyNjcuNDYuMC4w"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Image(
                                    painter = rememberAsyncImagePainter("https://png.pngtree.com/png-clipart/20190613/original/pngtree-linked-in-icon-png-image_3584840.jpg"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Image(
                                    painter = rememberAsyncImagePainter("https://i.pinimg.com/originals/0d/9c/24/0d9c24b21e25f1ecd9f69026f692322e.png"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                       }

                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .border(1.dp, Color(0xFFB6B4BB), RoundedCornerShape(10.dp))
                        .padding(2.dp)){
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(5.dp)){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2FDANYAL.jpg?alt=media&token=12df6239-36b4-4b7f-8407-8c5196405acb&_gl=1*1cmeumz*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NjkzNDUyNi4yNS4xLjE2OTY5MzU4MjAuOS4wLjA."),
                                contentDescription = "image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            (CornerSize(
                                                5.dp
                                            ))
                                        )
                                    ),
                                contentScale = ContentScale.FillBounds
                            )
                            Text(text = "Danyal Arif", fontSize = 16.sp, fontWeight = FontWeight.W800)
                            Text(text = "Full Stack Developer", color = GlobalStrings.AdminColorMain, fontSize = 12.sp)
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(text = "In this project, I assumed the role of a Full Stack Developer, actively contributing to its development process and ensuring a smooth progression forward.", fontSize = 12.sp, lineHeight = 13.sp)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                                Image(
                                    painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2Fgithub.png?alt=media&token=9d3fc7be-2163-48a3-a114-acf9326a168a&_gl=1*ky0f8z*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NjkzNDUyNi4yNS4xLjE2OTY5MzUyNjcuNDYuMC4w"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Image(
                                    painter = rememberAsyncImagePainter("https://png.pngtree.com/png-clipart/20190613/original/pngtree-linked-in-icon-png-image_3584840.jpg"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Image(
                                    painter = rememberAsyncImagePainter("https://i.pinimg.com/originals/0d/9c/24/0d9c24b21e25f1ecd9f69026f692322e.png"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .border(1.dp, Color(0xFFB6B4BB), RoundedCornerShape(10.dp))
                        .padding(2.dp)){
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(5.dp)){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2FGMD.jpg?alt=media&token=97735bad-6278-4461-8461-54bbaac76e64&_gl=1*1ho3pvh*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NjkzNDUyNi4yNS4xLjE2OTY5MzU4OTAuNjAuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            (CornerSize(
                                                5.dp
                                            ))
                                        )
                                    ),
                                contentScale = ContentScale.FillBounds
                            )
                            Text(text = "Ghulam Mohiuddin", fontSize = 16.sp, fontWeight = FontWeight.W800)
                            Text(text = "Front End Developer", color = GlobalStrings.AdminColorMain, fontSize = 12.sp)
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(text = "As a Front End Developer, I contributed significantly to the Dabs project, utilizing my skills to shape its user interface and elevate the overall user experience.", fontSize = 12.sp, lineHeight = 13.sp)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                                Image(
                                    painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2Fgithub.png?alt=media&token=9d3fc7be-2163-48a3-a114-acf9326a168a&_gl=1*ky0f8z*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NjkzNDUyNi4yNS4xLjE2OTY5MzUyNjcuNDYuMC4w"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Image(
                                    painter = rememberAsyncImagePainter("https://png.pngtree.com/png-clipart/20190613/original/pngtree-linked-in-icon-png-image_3584840.jpg"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Image(
                                    painter = rememberAsyncImagePainter("https://i.pinimg.com/originals/0d/9c/24/0d9c24b21e25f1ecd9f69026f692322e.png"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .border(1.dp, Color(0xFFB6B4BB), RoundedCornerShape(10.dp))
                        .padding(2.dp)){
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(5.dp)){
                            Image(
                                painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2FKUMAIL.jpg?alt=media&token=55d04fbc-b583-4919-9f6c-12fdf42d6d6c&_gl=1*1qf35ao*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NjkzNDUyNi4yNS4xLjE2OTY5MzU5OTYuNjAuMC4w"),
                                contentDescription = "image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            (CornerSize(
                                                5.dp
                                            ))
                                        )
                                    ),
                                contentScale = ContentScale.FillBounds
                            )
                            Text(text = "Kumail Raza", fontSize = 16.sp, fontWeight = FontWeight.W800)
                            Text(text = "Mobile Developer & AR", color = GlobalStrings.AdminColorMain, fontSize = 12.sp)
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(text = "For App Development, I refined user experiences while in the domain of AR, I integrated cutting-edge technologies to amplify the project's impact.", fontSize = 12.sp, lineHeight = 13.sp)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                                Image(
                                    painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2Fgithub.png?alt=media&token=9d3fc7be-2163-48a3-a114-acf9326a168a&_gl=1*ky0f8z*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5NjkzNDUyNi4yNS4xLjE2OTY5MzUyNjcuNDYuMC4w"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Image(
                                    painter = rememberAsyncImagePainter("https://png.pngtree.com/png-clipart/20190613/original/pngtree-linked-in-icon-png-image_3584840.jpg"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp).clickable {
                                             val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://media.licdn.com/dms/image/D4D03AQFyILR2DbsGrA/profile-displayphoto-shrink_400_400/0/1679773149138?e=1698278400&v=beta&t=zjFMdd9eWvtdfenONbx65ps_OALTReyvry7lcWwD06o"))
                                             context.startActivity(intent)
                                        }
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                Image(
                                    painter = rememberAsyncImagePainter("https://i.pinimg.com/originals/0d/9c/24/0d9c24b21e25f1ecd9f69026f692322e.png"),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                (CornerSize(
                                                    30.dp
                                                ))
                                            )
                                        ),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }

                    }

                }


            }
        }

    }

}