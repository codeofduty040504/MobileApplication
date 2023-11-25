package com.example.adminoffice.ui.theme.Customer.Profile


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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import com.example.adminoffice.ui.theme.Utils.saveTokenToLocalStorage
import io.getstream.chat.android.client.models.User
import org.json.JSONObject

object ViewProfile  : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController
    var UserDABS = mutableStateListOf<DabsUser>()
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.size(30.dp))
                Image(
                    painter = rememberAsyncImagePainter(if(UserDABS.isEmpty() || UserDABS[0].profilePicture=="user"){"https://icon-library.com/images/no-user-image-icon/no-user-image-icon-9.jpg"}else{
                        UserDABS[0].profilePicture}),
                    contentDescription = "image",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(
                            RoundedCornerShape(
                                (CornerSize(
                                    200.dp
                                ))
                            )
                        ),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.size(15.dp))
                Text(text = if(UserDABS.isEmpty()){""}else{
                    UserDABS[0].firstname+" "+ UserDABS[0].lastName}, fontSize = 18.sp, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.size(30.dp))
                Box(
                    modifier = Modifier.background(Color.White),
                    contentAlignment = Alignment.TopCenter
                ) {
                    LazyColumn(
                        //  contentPadding = padding,
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .background(Color.White),
                    ) {
                        item {
                            CategoryItem(
                                title = "Manage Account",
                                icon = Icons.Outlined.AccountCircle
                            ) {
                                navigator.push(ManageProfile(UserDABS[0]))
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
                            ) { /*TODO*/ }
                        }

                        item {
                            CategoryItem(
                                title = "Wishlist",
                                icon = Icons.Outlined.Favorite
                            ) { /*TODO*/ }
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
                        item { Divider(modifier = Modifier.padding(vertical = 12.dp)) }
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
                        item { Divider(modifier = Modifier.padding(vertical = 12.dp)) }
                        item {
                            CategoryItem(
                                title = "Log Out",
                                icon = Icons.Outlined.ArrowBack,
                                color = Color.Red
                            ) {
                                navigator.popUntilRoot()
                                saveTokenToLocalStorage(context, "")
                            }
                        }
//                item {
//                    @Composable
//                    fun rememberDescription(): ImageVector {
//                        return remember {
//                            ImageVector.Builder(
//                                name = "description",
//                                defaultWidth = 24.0.dp,
//                                defaultHeight = 24.0.dp,
//                                viewportWidth = 24.0f,
//                                viewportHeight = 24.0f
//                            ).apply {
//                                path(
//                                    fill = SolidColor(Color.Black),
//                                    fillAlpha = 1f,
//                                    stroke = null,
//                                    strokeAlpha = 1f,
//                                    strokeLineWidth = 1.0f,
//                                    strokeLineCap = StrokeCap.Butt,
//                                    strokeLineJoin = StrokeJoin.Miter,
//                                    strokeLineMiter = 1f,
//                                    pathFillType = PathFillType.NonZero
//                                ) {
//                                    moveTo(9f, 18f)
//                                    horizontalLineToRelative(6f)
//                                    quadToRelative(0.425f, 0f, 0.713f, -0.288f)
//                                    quadTo(16f, 17.425f, 16f, 17f)
//                                    reflectiveQuadToRelative(-0.287f, -0.712f)
//                                    quadTo(15.425f, 16f, 15f, 16f)
//                                    horizontalLineTo(9f)
//                                    quadToRelative(-0.425f, 0f, -0.712f, 0.288f)
//                                    quadTo(8f, 16.575f, 8f, 17f)
//                                    reflectiveQuadToRelative(0.288f, 0.712f)
//                                    quadTo(8.575f, 18f, 9f, 18f)
//                                    close()
//                                    moveToRelative(0f, -4f)
//                                    horizontalLineToRelative(6f)
//                                    quadToRelative(0.425f, 0f, 0.713f, -0.288f)
//                                    quadTo(16f, 13.425f, 16f, 13f)
//                                    reflectiveQuadToRelative(-0.287f, -0.713f)
//                                    quadTo(15.425f, 12f, 15f, 12f)
//                                    horizontalLineTo(9f)
//                                    quadToRelative(-0.425f, 0f, -0.712f, 0.287f)
//                                    quadTo(8f, 12.575f, 8f, 13f)
//                                    reflectiveQuadToRelative(0.288f, 0.712f)
//                                    quadTo(8.575f, 14f, 9f, 14f)
//                                    close()
//                                    moveToRelative(-3f, 8f)
//                                    quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
//                                    quadTo(4f, 20.825f, 4f, 20f)
//                                    verticalLineTo(4f)
//                                    quadToRelative(0f, -0.825f, 0.588f, -1.413f)
//                                    quadTo(5.175f, 2f, 6f, 2f)
//                                    horizontalLineToRelative(7.175f)
//                                    quadToRelative(0.4f, 0f, 0.763f, 0.15f)
//                                    quadToRelative(0.362f, 0.15f, 0.637f, 0.425f)
//                                    lineToRelative(4.85f, 4.85f)
//                                    quadToRelative(0.275f, 0.275f, 0.425f, 0.637f)
//                                    quadToRelative(0.15f, 0.363f, 0.15f, 0.763f)
//                                    verticalLineTo(20f)
//                                    quadToRelative(0f, 0.825f, -0.587f, 1.413f)
//                                    quadTo(18.825f, 22f, 18f, 22f)
//                                    close()
//                                    moveToRelative(7f, -14f)
//                                    verticalLineTo(4f)
//                                    horizontalLineTo(6f)
//                                    verticalLineToRelative(16f)
//                                    horizontalLineToRelative(12f)
//                                    verticalLineTo(9f)
//                                    horizontalLineToRelative(-4f)
//                                    quadToRelative(-0.425f, 0f, -0.712f, -0.288f)
//                                    quadTo(13f, 8.425f, 13f, 8f)
//                                    close()
//                                    moveTo(6f, 4f)
//                                    verticalLineToRelative(5f)
//                                    verticalLineToRelative(-5f)
//                                    verticalLineToRelative(16f)
//                                    verticalLineTo(4f)
//                                    close()
//                                }
//                            }.build()
//                        }
//                    }
//                    CategoryItem(
//                        title = "Legal",
//                        icon = rememberDescription(),
//                        onClick = { /*TODO*/ }
//                    )
//                }
//                item {
//                    @Composable
//                    fun rememberHandshake(): ImageVector {
//                        return remember {
//                            ImageVector.Builder(
//                                name = "handshake",
//                                defaultWidth = 24.0.dp,
//                                defaultHeight = 24.0.dp,
//                                viewportWidth = 24.0f,
//                                viewportHeight = 24.0f
//                            ).apply {
//                                path(
//                                    fill = SolidColor(Color.Black),
//                                    fillAlpha = 1f,
//                                    stroke = null,
//                                    strokeAlpha = 1f,
//                                    strokeLineWidth = 1.0f,
//                                    strokeLineCap = StrokeCap.Butt,
//                                    strokeLineJoin = StrokeJoin.Miter,
//                                    strokeLineMiter = 1f,
//                                    pathFillType = PathFillType.NonZero
//                                ) {
//                                    moveTo(11.875f, 20f)
//                                    quadToRelative(0.1f, 0f, 0.2f, -0.05f)
//                                    reflectiveQuadToRelative(0.15f, -0.1f)
//                                    lineToRelative(8.2f, -8.2f)
//                                    quadToRelative(0.3f, -0.3f, 0.438f, -0.675f)
//                                    quadToRelative(0.137f, -0.375f, 0.137f, -0.75f)
//                                    quadToRelative(0f, -0.4f, -0.137f, -0.763f)
//                                    quadToRelative(-0.138f, -0.362f, -0.438f, -0.637f)
//                                    lineToRelative(-4.25f, -4.25f)
//                                    quadToRelative(-0.275f, -0.3f, -0.637f, -0.438f)
//                                    quadTo(15.175f, 4f, 14.775f, 4f)
//                                    quadToRelative(-0.375f, 0f, -0.75f, 0.137f)
//                                    quadToRelative(-0.375f, 0.138f, -0.675f, 0.438f)
//                                    lineToRelative(-0.275f, 0.275f)
//                                    lineToRelative(1.85f, 1.875f)
//                                    quadToRelative(0.375f, 0.35f, 0.55f, 0.8f)
//                                    quadToRelative(0.175f, 0.45f, 0.175f, 0.95f)
//                                    quadToRelative(0f, 1.05f, -0.712f, 1.762f)
//                                    quadToRelative(-0.713f, 0.713f, -1.763f, 0.713f)
//                                    quadToRelative(-0.5f, 0f, -0.962f, -0.175f)
//                                    quadToRelative(-0.463f, -0.175f, -0.813f, -0.525f)
//                                    lineTo(9.525f, 8.4f)
//                                    lineTo(5.15f, 12.775f)
//                                    quadToRelative(-0.075f, 0.075f, -0.113f, 0.163f)
//                                    quadToRelative(-0.037f, 0.087f, -0.037f, 0.187f)
//                                    quadToRelative(0f, 0.2f, 0.15f, 0.362f)
//                                    quadToRelative(0.15f, 0.163f, 0.35f, 0.163f)
//                                    quadToRelative(0.1f, 0f, 0.2f, -0.05f)
//                                    reflectiveQuadToRelative(0.15f, -0.1f)
//                                    lineToRelative(3.4f, -3.4f)
//                                    lineToRelative(1.4f, 1.4f)
//                                    lineToRelative(-3.375f, 3.4f)
//                                    quadToRelative(-0.075f, 0.075f, -0.113f, 0.162f)
//                                    quadToRelative(-0.037f, 0.088f, -0.037f, 0.188f)
//                                    quadToRelative(0f, 0.2f, 0.15f, 0.35f)
//                                    quadToRelative(0.15f, 0.15f, 0.35f, 0.15f)
//                                    quadToRelative(0.1f, 0f, 0.2f, -0.05f)
//                                    reflectiveQuadToRelative(0.15f, -0.1f)
//                                    lineToRelative(3.4f, -3.375f)
//                                    lineToRelative(1.4f, 1.4f)
//                                    lineToRelative(-3.375f, 3.4f)
//                                    quadToRelative(-0.075f, 0.05f, -0.112f, 0.15f)
//                                    quadToRelative(-0.038f, 0.1f, -0.038f, 0.2f)
//                                    quadToRelative(0f, 0.2f, 0.15f, 0.35f)
//                                    quadToRelative(0.15f, 0.15f, 0.35f, 0.15f)
//                                    quadToRelative(0.1f, 0f, 0.188f, -0.038f)
//                                    quadToRelative(0.087f, -0.037f, 0.162f, -0.112f)
//                                    lineToRelative(3.4f, -3.375f)
//                                    lineToRelative(1.4f, 1.4f)
//                                    lineToRelative(-3.4f, 3.4f)
//                                    quadToRelative(-0.075f, 0.075f, -0.112f, 0.162f)
//                                    quadToRelative(-0.038f, 0.088f, -0.038f, 0.188f)
//                                    quadToRelative(0f, 0.2f, 0.163f, 0.35f)
//                                    quadToRelative(0.162f, 0.15f, 0.362f, 0.15f)
//                                    close()
//                                    moveToRelative(-0.025f, 2f)
//                                    quadToRelative(-0.925f, 0f, -1.637f, -0.613f)
//                                    quadToRelative(-0.713f, -0.612f, -0.838f, -1.537f)
//                                    quadToRelative(-0.85f, -0.125f, -1.425f, -0.7f)
//                                    quadToRelative(-0.575f, -0.575f, -0.7f, -1.425f)
//                                    quadToRelative(-0.85f, -0.125f, -1.412f, -0.712f)
//                                    quadToRelative(-0.563f, -0.588f, -0.688f, -1.413f)
//                                    quadToRelative(-0.95f, -0.125f, -1.55f, -0.825f)
//                                    quadToRelative(-0.6f, -0.7f, -0.6f, -1.65f)
//                                    quadToRelative(0f, -0.5f, 0.188f, -0.963f)
//                                    quadToRelative(0.187f, -0.462f, 0.537f, -0.812f)
//                                    lineToRelative(5.8f, -5.775f)
//                                    lineTo(12.8f, 8.85f)
//                                    quadToRelative(0.05f, 0.075f, 0.15f, 0.112f)
//                                    quadToRelative(0.1f, 0.038f, 0.2f, 0.038f)
//                                    quadToRelative(0.225f, 0f, 0.375f, -0.137f)
//                                    quadToRelative(0.15f, -0.138f, 0.15f, -0.363f)
//                                    quadToRelative(0f, -0.1f, -0.037f, -0.2f)
//                                    quadToRelative(-0.038f, -0.1f, -0.113f, -0.15f)
//                                    lineTo(9.95f, 4.575f)
//                                    quadToRelative(-0.275f, -0.3f, -0.638f, -0.438f)
//                                    quadTo(8.95f, 4f, 8.55f, 4f)
//                                    quadToRelative(-0.375f, 0f, -0.75f, 0.137f)
//                                    quadToRelative(-0.375f, 0.138f, -0.675f, 0.438f)
//                                    lineTo(3.6f, 8.125f)
//                                    quadToRelative(-0.225f, 0.225f, -0.375f, 0.525f)
//                                    quadToRelative(-0.15f, 0.3f, -0.2f, 0.6f)
//                                    quadToRelative(-0.05f, 0.3f, 0f, 0.612f)
//                                    quadToRelative(0.05f, 0.313f, 0.2f, 0.588f)
//                                    lineToRelative(-1.45f, 1.45f)
//                                    quadToRelative(-0.425f, -0.575f, -0.625f, -1.263f)
//                                    quadTo(0.95f, 9.95f, 1f, 9.25f)
//                                    quadToRelative(0.05f, -0.7f, 0.35f, -1.363f)
//                                    quadToRelative(0.3f, -0.662f, 0.825f, -1.187f)
//                                    lineTo(5.7f, 3.175f)
//                                    quadTo(6.3f, 2.6f, 7.038f, 2.3f)
//                                    quadTo(7.775f, 2f, 8.55f, 2f)
//                                    reflectiveQuadToRelative(1.512f, 0.3f)
//                                    quadToRelative(0.738f, 0.3f, 1.313f, 0.875f)
//                                    lineToRelative(0.275f, 0.275f)
//                                    lineToRelative(0.275f, -0.275f)
//                                    quadToRelative(0.6f, -0.575f, 1.337f, -0.875f)
//                                    quadTo(14f, 2f, 14.775f, 2f)
//                                    quadToRelative(0.775f, 0f, 1.513f, 0.3f)
//                                    quadToRelative(0.737f, 0.3f, 1.312f, 0.875f)
//                                    lineTo(21.825f, 7.4f)
//                                    quadToRelative(0.575f, 0.575f, 0.875f, 1.325f)
//                                    quadToRelative(0.3f, 0.75f, 0.3f, 1.525f)
//                                    quadToRelative(0f, 0.775f, -0.3f, 1.512f)
//                                    quadToRelative(-0.3f, 0.738f, -0.875f, 1.313f)
//                                    lineToRelative(-8.2f, 8.175f)
//                                    quadToRelative(-0.35f, 0.35f, -0.813f, 0.55f)
//                                    quadToRelative(-0.462f, 0.2f, -0.962f, 0.2f)
//                                    close()
//                                    moveTo(9.375f, 8f)
//                                    close()
//                                }
//                            }.build()
//                        }
//                    }
//                    CategoryItem(
//                        title = "Licenses",
//                        icon = rememberHandshake(),
//                        onClick = { /*TODO*/ }
//                    )
//                }
//                item { Divider(modifier = Modifier.padding(vertical = 12.dp)) }
//                item {
//                    AppVersion(
//                        versionText = "Version 1.0.0",
//                        copyrights = "© 2023 Your Company",
//                        onClick = { /* TODO Add easter egg after 8 times is clicked */ }
//                    )
//                }
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
                        Icon(painter = rememberVectorPainter(Icons.Filled.Home), contentDescription = "Landing", tint = Color.Black)
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
        AuthorizationDABS(context){

        }
        }
    fun AuthorizationDABS(context: Context, callback: (Boolean) -> Unit) {
        if(UserDABS.isNotEmpty()){
            return
        }
        val url = "${GlobalStrings.baseURL}auth/getAuthroizedUser"
        // Request parameters
        val params = JSONObject()
        params.put("token", getTokenFromLocalStorage(context))
        Log.d("LOOOO",params.toString())
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
                Request.Method.POST, url, params,
                { response ->
                    UserDABS.clear()
                    // Handle successful login response
                    Log.d("LOOOO", response.toString())
                    var user = response.getJSONObject("user")
                    var _id = user.getString("_id")
                    var firstname = user.getString("firstName")
                    var lastname = user.getString("lastName")
                    var email = user.getString("email")
                    var contactNo = user.getString("contactNo")
                    var cnic = user.getString("cnic")
                    var profilePicture = user.getString("profilePicture")
                    var role = user.getString("role")
                    var userD = DabsUser(_id,  firstname, lastname,email, contactNo, cnic, profilePicture, role)
                    UserDABS.add(userD)
                    progressDialog.dismiss()
                    // Assuming the API returns a JSON object with a field "valid" indicating user validity

                    callback(true)
                },
                { error ->
                    UserDABS.clear()
                    // Handle error response
                    Log.e("LOOOO Error", error.toString())
                    Log.e("LOOOO Error", error.networkResponse.data.toString())
                    Log.e("LOOOO Error", error.networkResponse.statusCode.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Connection Error or try with different Credentials",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    callback(false)
                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
//                    headers["Authorization"] = "${getTokenFromLocalStorage(context)}"
                    headers["Authorization"] = ""
                    return headers
                }
            }


            // Add the request to the RequestQueue.
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
        }
    }

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = color
            )
            Text(title, style = MaterialTheme.typography.bodyLarge, color = color)
        }
    }
}
