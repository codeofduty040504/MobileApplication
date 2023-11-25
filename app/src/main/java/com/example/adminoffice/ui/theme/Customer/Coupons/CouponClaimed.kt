package com.example.adminoffice.ui.theme.Customer.Coupons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.adminoffice.ui.theme.Customer.LandingPage


data class ConfirmCoupon(
    val hotelname: String,
    val couponid: String,
    val discount: String,
) : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter("https://cdni.iconscout.com/illustration/premium/thumb/wallet-3378159-2810779.png"),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape((CornerSize(10.dp))))
            )
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.padding(20.dp, 0.dp)
            ) {
                Column {
                    Text(
                        "\n" +
                                "\uD83C\uDF89 Congratulations! \uD83C\uDF89",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(40.dp, 20.dp)
                    )
                    Text(
                        "You've Unlocked a Fabulous Discount Coupon at ${hotelname}! \uD83C\uDFE8",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(0.dp, 20.dp)
                    )
                    Text(
                        "Enjoy a Generous ${discount}% Discount on Your Stay and Delight in Unforgettable Moments of Luxury and Comfort. \uD83C\uDF1F",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(0.dp, 20.dp)
                    )
                    Text(
                        "Book Your Experience Today and Immerse Yourself in the Elegance of ${hotelname} at Its Absolute Finest! \uD83E\uDD42",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(0.dp, 20.dp)
                    )
                    Text(
                        "Avail discount under this Coupon Code ${couponid}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(0.dp, 20.dp)
                    )
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(50.dp, 10.dp)
                            .size(400.dp, 45.dp),
                        onClick = {
                            navigator.pop()
                            navigator.pop()
                            navigator.pop()
                            navigator.push(LandingPage)
                        },
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(0.dp, Color.Black)
                    ) {
                        Text(
                            text = "Check Wallet",
                            color = Color.White,
                            letterSpacing = 0.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

    }
}