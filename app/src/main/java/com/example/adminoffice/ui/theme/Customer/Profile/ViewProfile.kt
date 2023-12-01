package com.example.adminoffice.ui.theme.Customer.Profile


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
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
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Customer.Bookings.ViewBookingsCustomer
import com.example.adminoffice.ui.theme.Customer.Coupons.CouponsList
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.LandingPage
import com.example.adminoffice.ui.theme.Customer.Settings.AboutUSCustomer
import com.example.adminoffice.ui.theme.Customer.Wishlist.WishList
import com.example.adminoffice.ui.theme.DabsUser
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Hotel
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.HotelOwner
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Room
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.ServiceCategory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Userid
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Profiling.Login
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.saveTokenToLocalStorage
import io.getstream.chat.android.client.models.User
import org.json.JSONObject

object ViewProfile  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var UserDABS = mutableStateListOf<DabsUser>()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        var user = getUserFromLocal(context)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                        Box(
                            modifier = Modifier.background(Color.White),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            LazyColumn(
                                //  contentPadding = padding,
                                modifier = Modifier
                                    .widthIn(max = 600.dp).padding(15.dp)
                                    .background(Color.White),
                            ) {
                                item{
                                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically){
                                        Image(
                                            painter = rememberAsyncImagePainter(if(user.profilePicture=="user"){"https://icon-library.com/images/no-user-image-icon/no-user-image-icon-9.jpg"}else{
                                                user.profilePicture}),
                                            contentDescription = "image",
                                            modifier = Modifier
                                                .size(80.dp)
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
                                            Text(text =user.firstname+" "+user.lastName, fontSize = 18.sp, fontWeight = FontWeight.Light)
                                            Text(text = user.email, fontSize = 12.sp, fontWeight = FontWeight.Light, color = Color.Gray
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(30.dp))
                                    }
                                }
                                item {
                                    CategoryItem(
                                        title = "Manage Account",
                                        icon = Icons.Outlined.AccountCircle
                                    ) {
                                        navigator.push(ManageProfile(user))
                                    }
                                }

                                item {
                                    CategoryItem(
                                        title = "Change Password",
                                        icon = Icons.Outlined.Lock,
                                        color = Color.Black
                                    ) {
                                        navigator.push(ChangePassword)
                                    }
                                }

                                item {
                                    CategoryItem(
                                        title = "Policy",
                                        icon = Icons.Outlined.Lock
                                    ) {
                                        navigator.push(Policy)
                                    }
                                }

                                item {
                                    CategoryItem(
                                        title = "Wishlist",
                                        icon = Icons.Outlined.FavoriteBorder
                                    ) { navigator.replace(WishList) }
                                }

                                item {
                                    /**
                                     * Icon generated from https://composables.com/icons
                                     */
                                    /**
                                     * Icon generated from https://composables.com/icons
                                     */
                                    @Composable
                                    fun rememberStyle(): ImageVector {
                                        return remember {
                                            ImageVector.Builder(
                                                name = "style",
                                                defaultWidth = 24.0.dp,
                                                defaultHeight = 24.0.dp,
                                                viewportWidth = 24.0f,
                                                viewportHeight = 24.0f
                                            ).apply {
                                                path(
                                                    fill = SolidColor(Color.Black),
                                                    fillAlpha = 1f,
                                                    stroke = null,
                                                    strokeAlpha = 1f,
                                                    strokeLineWidth = 1.0f,
                                                    strokeLineCap = StrokeCap.Butt,
                                                    strokeLineJoin = StrokeJoin.Miter,
                                                    strokeLineMiter = 1f,
                                                    pathFillType = PathFillType.NonZero
                                                ) {
                                                    moveTo(3.975f, 19.8f)
                                                    lineToRelative(-0.85f, -0.35f)
                                                    quadToRelative(-0.775f, -0.325f, -1.037f, -1.125f)
                                                    quadToRelative(-0.263f, -0.8f, 0.087f, -1.575f)
                                                    lineToRelative(1.8f, -3.9f)
                                                    close()
                                                    moveToRelative(4f, 2.2f)
                                                    quadToRelative(-0.825f, 0f, -1.413f, -0.587f)
                                                    quadToRelative(-0.587f, -0.588f, -0.587f, -1.413f)
                                                    verticalLineToRelative(-6f)
                                                    lineToRelative(2.65f, 7.35f)
                                                    quadToRelative(0.075f, 0.175f, 0.15f, 0.338f)
                                                    quadToRelative(0.075f, 0.162f, 0.2f, 0.312f)
                                                    close()
                                                    moveToRelative(5.15f, -0.1f)
                                                    quadToRelative(-0.8f, 0.3f, -1.55f, -0.075f)
                                                    reflectiveQuadToRelative(-1.05f, -1.175f)
                                                    lineToRelative(-4.45f, -12.2f)
                                                    quadToRelative(-0.3f, -0.8f, 0.05f, -1.563f)
                                                    quadToRelative(0.35f, -0.762f, 1.15f, -1.037f)
                                                    lineToRelative(7.55f, -2.75f)
                                                    quadToRelative(0.8f, -0.3f, 1.55f, 0.075f)
                                                    reflectiveQuadToRelative(1.05f, 1.175f)
                                                    lineToRelative(4.45f, 12.2f)
                                                    quadToRelative(0.3f, 0.8f, -0.05f, 1.563f)
                                                    quadToRelative(-0.35f, 0.762f, -1.15f, 1.037f)
                                                    close()
                                                    moveTo(10.975f, 10f)
                                                    quadToRelative(0.425f, 0f, 0.713f, -0.288f)
                                                    quadToRelative(0.287f, -0.287f, 0.287f, -0.712f)
                                                    reflectiveQuadToRelative(-0.287f, -0.713f)
                                                    quadTo(11.4f, 8f, 10.975f, 8f)
                                                    reflectiveQuadToRelative(-0.712f, 0.287f)
                                                    quadToRelative(-0.288f, 0.288f, -0.288f, 0.713f)
                                                    reflectiveQuadToRelative(0.288f, 0.712f)
                                                    quadToRelative(0.287f, 0.288f, 0.712f, 0.288f)
                                                    close()
                                                    moveToRelative(1.45f, 10f)
                                                    lineToRelative(7.55f, -2.75f)
                                                    lineTo(15.525f, 5f)
                                                    lineToRelative(-7.55f, 2.75f)
                                                    close()
                                                    moveTo(7.975f, 7.75f)
                                                    lineTo(15.525f, 5f)
                                                    close()
                                                }
                                            }.build()
                                        }
                                    }
//                    CategoryItem(
//                        title = "Look & Feel",
//                        icon = rememberStyle(),
//                        onClick = { /*TODO*/ }
//                    )
                                }
//                                item { Divider(modifier = Modifier.padding(vertical = 12.dp)) }
                                item {
                                    @Composable
                                    fun rememberQuestionMark(): ImageVector {
                                        return remember {
                                            ImageVector.Builder(
                                                name = "question_mark",
                                                defaultWidth = 24.0.dp,
                                                defaultHeight = 24.0.dp,
                                                viewportWidth = 24.0f,
                                                viewportHeight = 24.0f
                                            ).apply {
                                                path(
                                                    fill = SolidColor(Color.Black),
                                                    fillAlpha = 1f,
                                                    stroke = null,
                                                    strokeAlpha = 1f,
                                                    strokeLineWidth = 1.0f,
                                                    strokeLineCap = StrokeCap.Butt,
                                                    strokeLineJoin = StrokeJoin.Miter,
                                                    strokeLineMiter = 1f,
                                                    pathFillType = PathFillType.NonZero
                                                ) {
                                                    moveTo(12.025f, 16f)
                                                    quadToRelative(-0.6f, 0f, -1.012f, -0.425f)
                                                    quadToRelative(-0.413f, -0.425f, -0.363f, -1f)
                                                    quadToRelative(0.075f, -1.05f, 0.5f, -1.825f)
                                                    quadToRelative(0.425f, -0.775f, 1.35f, -1.6f)
                                                    quadToRelative(1.025f, -0.9f, 1.562f, -1.563f)
                                                    quadToRelative(0.538f, -0.662f, 0.538f, -1.512f)
                                                    quadToRelative(0f, -1.025f, -0.687f, -1.7f)
                                                    quadTo(13.225f, 5.7f, 12f, 5.7f)
                                                    quadToRelative(-0.8f, 0f, -1.362f, 0.337f)
                                                    quadToRelative(-0.563f, 0.338f, -0.913f, 0.838f)
                                                    quadToRelative(-0.35f, 0.5f, -0.862f, 0.675f)
                                                    quadToRelative(-0.513f, 0.175f, -0.988f, -0.025f)
                                                    quadToRelative(-0.575f, -0.25f, -0.787f, -0.825f)
                                                    quadToRelative(-0.213f, -0.575f, 0.087f, -1.075f)
                                                    quadTo(7.9f, 4.5f, 9.125f, 3.75f)
                                                    reflectiveQuadTo(12f, 3f)
                                                    quadToRelative(2.625f, 0f, 4.038f, 1.463f)
                                                    quadToRelative(1.412f, 1.462f, 1.412f, 3.512f)
                                                    quadToRelative(0f, 1.25f, -0.537f, 2.138f)
                                                    quadToRelative(-0.538f, 0.887f, -1.688f, 2.012f)
                                                    quadToRelative(-0.85f, 0.8f, -1.2f, 1.3f)
                                                    reflectiveQuadToRelative(-0.475f, 1.15f)
                                                    quadToRelative(-0.1f, 0.625f, -0.525f, 1.025f)
                                                    quadToRelative(-0.425f, 0.4f, -1f, 0.4f)
                                                    close()
                                                    moveTo(12f, 22f)
                                                    quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                                                    quadTo(10f, 20.825f, 10f, 20f)
                                                    quadToRelative(0f, -0.825f, 0.588f, -1.413f)
                                                    quadTo(11.175f, 18f, 12f, 18f)
                                                    reflectiveQuadToRelative(1.413f, 0.587f)
                                                    quadTo(14f, 19.175f, 14f, 20f)
                                                    quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                                                    quadTo(12.825f, 22f, 12f, 22f)
                                                    close()
                                                }
                                            }.build()
                                        }
                                    }
                                    CategoryItem(
                                        title = "FAQ",
                                        icon = rememberQuestionMark()
                                    ) {
                                        navigator.push(FAQs)
                                    }
                                }

                                item {
                                    CategoryItem(
                                        title = "Contact Us",
                                        icon = Icons.Outlined.Email
                                    ) {
                                        navigator.push(ContactUs)
                                    }
                                }
                                item {
                                    @Composable
                                    fun rememberAutoAwesome(): ImageVector {
                                        return remember {
                                            ImageVector.Builder(
                                                name = "auto_awesome",
                                                defaultWidth = 24.0.dp,
                                                defaultHeight = 24.0.dp,
                                                viewportWidth = 24.0f,
                                                viewportHeight = 24.0f
                                            ).apply {
                                                path(
                                                    fill = SolidColor(Color.Black),
                                                    fillAlpha = 1f,
                                                    stroke = null,
                                                    strokeAlpha = 1f,
                                                    strokeLineWidth = 1.0f,
                                                    strokeLineCap = StrokeCap.Butt,
                                                    strokeLineJoin = StrokeJoin.Miter,
                                                    strokeLineMiter = 1f,
                                                    pathFillType = PathFillType.NonZero
                                                ) {
                                                    moveTo(19f, 8.3f)
                                                    quadToRelative(-0.125f, 0f, -0.262f, -0.075f)
                                                    quadTo(18.6f, 8.15f, 18.55f, 8f)
                                                    lineToRelative(-0.8f, -1.75f)
                                                    lineToRelative(-1.75f, -0.8f)
                                                    quadToRelative(-0.15f, -0.05f, -0.225f, -0.188f)
                                                    quadTo(15.7f, 5.125f, 15.7f, 5f)
                                                    reflectiveQuadToRelative(0.075f, -0.263f)
                                                    quadTo(15.85f, 4.6f, 16f, 4.55f)
                                                    lineToRelative(1.75f, -0.8f)
                                                    lineToRelative(0.8f, -1.75f)
                                                    quadToRelative(0.05f, -0.15f, 0.188f, -0.225f)
                                                    quadToRelative(0.137f, -0.075f, 0.262f, -0.075f)
                                                    reflectiveQuadToRelative(0.263f, 0.075f)
                                                    quadToRelative(0.137f, 0.075f, 0.187f, 0.225f)
                                                    lineToRelative(0.8f, 1.75f)
                                                    lineToRelative(1.75f, 0.8f)
                                                    quadToRelative(0.15f, 0.05f, 0.225f, 0.187f)
                                                    quadToRelative(0.075f, 0.138f, 0.075f, 0.263f)
                                                    reflectiveQuadToRelative(-0.075f, 0.262f)
                                                    quadTo(22.15f, 5.4f, 22f, 5.45f)
                                                    lineToRelative(-1.75f, 0.8f)
                                                    lineToRelative(-0.8f, 1.75f)
                                                    quadToRelative(-0.05f, 0.15f, -0.187f, 0.225f)
                                                    quadToRelative(-0.138f, 0.075f, -0.263f, 0.075f)
                                                    close()
                                                    moveToRelative(0f, 14f)
                                                    quadToRelative(-0.125f, 0f, -0.262f, -0.075f)
                                                    quadToRelative(-0.138f, -0.075f, -0.188f, -0.225f)
                                                    lineToRelative(-0.8f, -1.75f)
                                                    lineToRelative(-1.75f, -0.8f)
                                                    quadToRelative(-0.15f, -0.05f, -0.225f, -0.188f)
                                                    quadToRelative(-0.075f, -0.137f, -0.075f, -0.262f)
                                                    reflectiveQuadToRelative(0.075f, -0.262f)
                                                    quadToRelative(0.075f, -0.138f, 0.225f, -0.188f)
                                                    lineToRelative(1.75f, -0.8f)
                                                    lineToRelative(0.8f, -1.75f)
                                                    quadToRelative(0.05f, -0.15f, 0.188f, -0.225f)
                                                    quadToRelative(0.137f, -0.075f, 0.262f, -0.075f)
                                                    reflectiveQuadToRelative(0.263f, 0.075f)
                                                    quadToRelative(0.137f, 0.075f, 0.187f, 0.225f)
                                                    lineToRelative(0.8f, 1.75f)
                                                    lineToRelative(1.75f, 0.8f)
                                                    quadToRelative(0.15f, 0.05f, 0.225f, 0.188f)
                                                    quadToRelative(0.075f, 0.137f, 0.075f, 0.262f)
                                                    reflectiveQuadToRelative(-0.075f, 0.262f)
                                                    quadToRelative(-0.075f, 0.138f, -0.225f, 0.188f)
                                                    lineToRelative(-1.75f, 0.8f)
                                                    lineToRelative(-0.8f, 1.75f)
                                                    quadToRelative(-0.05f, 0.15f, -0.187f, 0.225f)
                                                    quadToRelative(-0.138f, 0.075f, -0.263f, 0.075f)
                                                    close()
                                                    moveTo(9f, 18.575f)
                                                    quadToRelative(-0.275f, 0f, -0.525f, -0.15f)
                                                    reflectiveQuadTo(8.1f, 18f)
                                                    lineToRelative(-1.6f, -3.5f)
                                                    lineTo(3f, 12.9f)
                                                    quadToRelative(-0.275f, -0.125f, -0.425f, -0.375f)
                                                    quadToRelative(-0.15f, -0.25f, -0.15f, -0.525f)
                                                    reflectiveQuadToRelative(0.15f, -0.525f)
                                                    quadToRelative(0.15f, -0.25f, 0.425f, -0.375f)
                                                    lineToRelative(3.5f, -1.6f)
                                                    lineTo(8.1f, 6f)
                                                    quadToRelative(0.125f, -0.275f, 0.375f, -0.425f)
                                                    quadToRelative(0.25f, -0.15f, 0.525f, -0.15f)
                                                    reflectiveQuadToRelative(0.525f, 0.15f)
                                                    quadToRelative(0.25f, 0.15f, 0.375f, 0.425f)
                                                    lineToRelative(1.6f, 3.5f)
                                                    lineToRelative(3.5f, 1.6f)
                                                    quadToRelative(0.275f, 0.125f, 0.425f, 0.375f)
                                                    quadToRelative(0.15f, 0.25f, 0.15f, 0.525f)
                                                    reflectiveQuadToRelative(-0.15f, 0.525f)
                                                    quadToRelative(-0.15f, 0.25f, -0.425f, 0.375f)
                                                    lineToRelative(-3.5f, 1.6f)
                                                    lineTo(9.9f, 18f)
                                                    quadToRelative(-0.125f, 0.275f, -0.375f, 0.425f)
                                                    quadToRelative(-0.25f, 0.15f, -0.525f, 0.15f)
                                                    close()
                                                    moveToRelative(0f, -3.425f)
                                                    lineTo(10f, 13f)
                                                    lineToRelative(2.15f, -1f)
                                                    lineTo(10f, 11f)
                                                    lineTo(9f, 8.85f)
                                                    lineTo(8f, 11f)
                                                    lineToRelative(-2.15f, 1f)
                                                    lineTo(8f, 13f)
                                                    close()
                                                    moveTo(9f, 12f)
                                                    close()
                                                }
                                            }.build()
                                        }
                                    }
                                    CategoryItem(
                                        title = "About Us",
                                        icon = rememberAutoAwesome()
                                    ) { navigator.push(AboutUSCustomer) }
                                }
//                                item { Divider(modifier = Modifier.padding(vertical = 12.dp)) }
                                item {
                                    CategoryItem(
                                        title = "Complaints",
                                        icon = Icons.Outlined.Warning,
                                    ) {
                                        navigator.push(ComplaintsCustomer)
                                    }
                                }
//                                item {
//                                    CategoryItem(
//                                        title = "Reviews",
//                                        icon = Icons.Outlined.Star,
//                                    ) {
//                                        navigator.push(ComplaintsCustomer)
//                                    }
//                                }
                                item {
                                    CategoryItem(
                                        title = "Log Out",
                                        icon = Icons.Outlined.ArrowBack,
                                        color = Color.Red
                                    ) {
                                        navigator.popUntilRoot()
                                        navigator.replace(Login)
                                        saveTokenToLocalStorage(context, "")
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
                                Icon(painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder), contentDescription = "WishList", tint = Color.Black)
                            }
                            IconButton(onClick = {
                                navigator.replace(ViewProfile)
                            }, modifier = Modifier.padding(10.dp)) {
                                Icon(painter = rememberVectorPainter(Icons.Outlined.Person), contentDescription = "Account", tint = GlobalStrings.CustomerColorMain)
                            }
                        }
                    }
                }
//        AuthorizationDABS(context)
        }
//    fun AuthorizationDABS(context: Context) {
//        Log.d("KKKK","Yes")
//        if(UserDABS.isNotEmpty()){
//            return
//        }
//        Log.d("KKKK","Yes")
//        val url = "${GlobalStrings.baseURL}auth/getAuthroizedUser"
//        // Request parameters
//        val params = JSONObject()
//        params.put("token", getTokenFromLocalStorage(context))
//        Log.d("LOOOO",params.toString())
//        if(!isInternetAvailable(context)){
//            Toast
//                .makeText(
//                    context,
//                    "Internet is not Available",
//                    Toast.LENGTH_SHORT
//                )
//                .show()
//        }
//        else{
//            val request = object : JsonObjectRequest(
//                Request.Method.POST, url, params,
//                { response ->
//                    UserDABS.clear()
//                    // Handle successful login response
//                    Log.d("LOOOO", response.toString())
//                    var user = response.getJSONObject("user")
//                    var _id = user.getString("_id")
//                    var firstname = user.getString("firstName")
//                    var lastname = user.getString("lastName")
//                    var email = user.getString("email")
//                    var contactNo = user.getString("contactNo")
//                    var cnic = user.getString("cnic")
//                    var profilePicture = user.getString("profilePicture")
//                    var role = user.getString("role")
//                    var userD = DabsUser(_id,  firstname, lastname,email, contactNo, cnic, profilePicture, role)
//                    UserDABS.add(userD)
//                    // Assuming the API returns a JSON object with a field "valid" indicating user validity
//
//                   // callback(true)
//                },
//                { error ->
//                    UserDABS.clear()
//                    // Handle error response
//                    Log.e("LOOOO Error", error.toString())
//                    Log.e("LOOOO Error", error.networkResponse.data.toString())
//                    Log.e("LOOOO Error", error.networkResponse.statusCode.toString())
//
//                    Toast
//                        .makeText(
//                            context,
//                            "Connection Error.",
//                            Toast.LENGTH_SHORT
//                        )
//                        .show()
//                 //   callback(false)
//                }) {
//
//                @Throws(AuthFailureError::class)
//                override fun getHeaders(): MutableMap<String, String> {
//                    val headers = HashMap<String, String>()
//                    headers["Content-Type"] = "application/json"
////                    headers["Authorization"] = "${getTokenFromLocalStorage(context)}"
//                    headers["Authorization"] = ""
//                    return headers
//                }
//            }
//
//
//            // Add the request to the RequestQueue.
//            val requestQueue = Volley.newRequestQueue(context)
//            requestQueue.add(request)
//        }
//    }

    }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    title: String,
    icon: ImageVector,color: Color=Color.Black,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.background(Color.White)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(30.dp)){
                    Icon(
                        icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = color
                    )
                    Text(title, fontSize = 14.sp, fontWeight = FontWeight.ExtraLight, color = color)
                }
                if(title !="Log Out"){
                    Icon(
                        Icons.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = color
                    )
                }
            }
            if(title != "Log Out"){
                Box(modifier = Modifier.fillMaxWidth().background(Color.White)){
                    Divider(
                        Modifier
                    )
                }
            }
        }
    }
}
