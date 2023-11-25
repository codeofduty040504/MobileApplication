package com.example.adminoffice.ui.theme.Customer

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.adminoffice.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.adminoffice.ui.theme.Customer.Functions.CustomSlider
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun HotelCardWithSlider(
    images : List<String>,
    name: String,
    location: String,
    price:String
){
    Box(modifier = Modifier.padding(horizontal=15.dp)){
        Box(modifier = Modifier
            .width(300.dp)
            // .background(Color(0xFFFEFEFE))
            .border(
                0.2.dp,
                Color(0xFFB6B4BB),
                RoundedCornerShape(15.dp)
            )
        ){
            Column(
                Modifier
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                CustomSliderHotel(sliderList = images, imageModifier = Modifier
                    .height(180.dp).padding(10.dp)
                    .clip(
                        RoundedCornerShape(
                            (CornerSize(
                                15.dp
                            ))
                        )
                    ))
                Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                    Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.W700, letterSpacing = 0.2.sp)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                        Row(verticalAlignment = Alignment.Bottom){
//                                                Icon(
//                                                    painterResource(id = R.drawable.location), contentDescription = null,
//                                                    Modifier.size(15.dp))
                            Text(text = location, color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.ExtraLight)

                        }
                        Row(verticalAlignment = Alignment.Top, modifier = Modifier.height(20.dp)){
                            Icon(
                                Icons.Outlined.Star, contentDescription =null, tint = Color(
                                    0xFFFFC107
                                ), modifier = Modifier.size(18.dp)
                            )
                            Text(text = "3.5", fontSize = 12.sp)
                            //  Text(text = "("+"141"+") reviews", fontSize = 8.sp)
                        }
                    }
                    Spacer(modifier = Modifier.size(7.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Rs. "+price+"/Night", fontSize = 12.sp, fontWeight = FontWeight.W600)
                        Box(modifier = Modifier
                            .background(
                                GlobalStrings.CustomerColorMain,
                                RoundedCornerShape(5.dp)
                            )
                            .padding(5.dp)
                        ){
                            Row(verticalAlignment = Alignment.Bottom, modifier = Modifier){
                                Text(text = "Book Now", fontSize = 10.sp, fontWeight = FontWeight.W500,color=Color.White)
//                                                Text(text = "night",fontSize = 12.sp, fontWeight = FontWeight.Thin,color=Color.White)
                            }
                        }
                    }


                    Spacer(modifier = Modifier.size(10.dp))
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSliderHotel(
    modifier: Modifier = Modifier,
    imageModifier: Modifier=modifier.height(350.dp),
    sliderList: List<String>,
    backwardIcon: ImageVector = Icons.Default.KeyboardArrowLeft,
    forwardIcon: ImageVector = Icons.Default.KeyboardArrowRight,
    dotsActiveColor: Color = GlobalStrings.CustomerColorMain,
    dotsInActiveColor: Color = Color.LightGray,
    dotsSize: Dp = 10.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 0.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
) {


    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(

    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

//            IconButton(enabled = pagerState.canScrollBackward, onClick = {
//                scope.launch {
//                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
//                }
//            }) {
//                Icon(imageVector = backwardIcon, contentDescription = "back")
//            }
            HorizontalPager(
                pageCount = sliderList.size,
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)


                Box(modifier = modifier
//                    .graphicsLayer {
//                        scaleX = scaleFactor
//                        scaleY = scaleFactor
//                    }
//                    .alpha(
//                        scaleFactor.coerceIn(0f, 1f)
//                    )
//                    .padding(0.dp)
//                    .clip(RoundedCornerShape(imageCornerRadius))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                            .crossfade(true).data(sliderList[page]).build(),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.image),
                        modifier = imageModifier
//                            .alpha(if (pagerState.currentPage == page) 1f else 0.5f)
                    )
                }
            }
//            IconButton(enabled = pagerState.currentPage != sliderList.size - 1, onClick = {
//                scope.launch {
//                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
//                }
//            }) {
//                Icon(imageVector = forwardIcon, contentDescription = "forward")
//            }
        }
        Row(
            modifier.padding(10.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter), horizontalArrangement = Arrangement.Center
        ) {
            repeat(sliderList.size) {
                val color = if (pagerState.currentPage == it) dotsActiveColor else dotsInActiveColor
                Box(modifier = modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .size(dotsSize)
                    .background(color)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    })
            }
        }

    }
}

@Composable
fun HotelCardWithSliderSort(){
    val sliderList = remember {
        mutableListOf(
            "https://www.gstatic.com/webp/gallery/1.webp",
            "https://www.gstatic.com/webp/gallery/2.webp",
            "https://www.gstatic.com/webp/gallery/3.webp",
            "https://www.gstatic.com/webp/gallery/4.webp",
            "https://www.gstatic.com/webp/gallery/5.webp",
        )
    }
    Box(modifier = Modifier.padding(horizontal=15.dp)){
        Box(modifier = Modifier
            .width(350.dp)
            // .background(Color(0xFFFEFEFE))
            .border(
                0.2.dp,
                Color(0xFFB6B4BB),
                RoundedCornerShape(15.dp)
            )
        ){
            Column(
                Modifier
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                CustomSlider(sliderList = sliderList, imageModifier = Modifier
                    .height(180.dp).padding(10.dp)
                    .clip(
                        RoundedCornerShape(
                            (CornerSize(
                                15.dp
                            ))
                        )
                    ))
                Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                    Text(text = "Royal Castle", fontSize = 15.sp, fontWeight = FontWeight.W700, letterSpacing = 0.2.sp)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                        Row(verticalAlignment = Alignment.Bottom){
//                                                Icon(
//                                                    painterResource(id = R.drawable.location), contentDescription = null,
//                                                    Modifier.size(15.dp))
                            Text(text = "Romania", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.ExtraLight)

                        }
                        Row(verticalAlignment = Alignment.Bottom){
                            Icon(
                                Icons.Outlined.Star, contentDescription =null, tint = Color(
                                    0xFFFFC107
                                ), modifier = Modifier.size(18.dp)
                            )
                            Text(text = "3.5", fontSize = 12.sp)
                            //  Text(text = "("+"141"+") reviews", fontSize = 8.sp)
                        }
                    }
                    Spacer(modifier = Modifier.size(7.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Rs. 35000/Night", fontSize = 12.sp, fontWeight = FontWeight.W600)
                        Box(modifier = Modifier
                            .background(
                                GlobalStrings.CustomerColorMain,
                                RoundedCornerShape(5.dp)
                            )
                            .padding(5.dp)
                        ){
                            Row(verticalAlignment = Alignment.Bottom, modifier = Modifier){
                                Text(text = "Book Now", fontSize = 10.sp, fontWeight = FontWeight.W500,color=Color.White)
//                                                Text(text = "night",fontSize = 12.sp, fontWeight = FontWeight.Thin,color=Color.White)
                            }
                        }
                    }


                    Spacer(modifier = Modifier.size(10.dp))
                }
            }

        }
    }
}

@Composable
fun CityCard(
    city:String="as",
    image: String="",
    hotels:String=""
){
    // Create a brush to darken the image
    val darkBrush = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color(0xFFE7E7E7)),
        startY = 0.0f,
        endY = 0.5f // Adjust the endY value for the desired darkness
    )
    val configuration = LocalConfiguration.current
    val screenWidthDp: Dp = configuration.screenWidthDp.dp
    val screenHeightDp: Dp = configuration.screenHeightDp.dp
    // Apply a ColorFilter to the Image with reduced alpha
    val colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 1f)) // Adjust the alpha value for the desired darkness


    Box(modifier = Modifier.padding(5.dp)){
        Box(contentAlignment = Alignment.BottomStart){
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = "image",
                modifier = Modifier
                    .size(screenWidthDp-40.dp, 200.dp)
                    .clip(
                        RoundedCornerShape(
                            (CornerSize(
                                10.dp
                            ))
                        )
                    ),colorFilter = ColorFilter.tint(Color(0xFF797979), BlendMode.Multiply),
                contentScale = ContentScale.FillBounds
            )
            Column( modifier = Modifier.padding(10.dp) ){
                Row{
                   // Icon(painterResource(id = R.drawable.location), contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
                    Text(text = city, fontSize = 14.sp, fontWeight = FontWeight.W200, color = Color.White,)

                }
                Row(verticalAlignment = Alignment.Bottom){
                    Icon(painterResource(id = R.drawable.home), contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Text(text = "${hotels} Hotels", fontSize = 12.sp, fontWeight = FontWeight.W600, color = Color.White,)
                }


            }
        }
    }
}



@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSliderCity(
    modifier: Modifier = Modifier,
    imageModifier: Modifier=modifier.height(350.dp),
    backwardIcon: ImageVector = Icons.Default.KeyboardArrowLeft,
    forwardIcon: ImageVector = Icons.Default.KeyboardArrowRight,
    dotsActiveColor: Color = GlobalStrings.CustomerColorMain,
    dotsInActiveColor: Color = Color.LightGray,
    dotsSize: Dp = 10.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 0.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
    Citys:MutableList<LandingPage.CitySchema>
) {


    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(

    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            HorizontalPager(
                pageCount = Citys.size,
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)


                Box(modifier = modifier
                    .graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    .alpha(
                        scaleFactor.coerceIn(0f, 1f)
                    )
                    .padding(0.dp)
                    .clip(RoundedCornerShape(imageCornerRadius))
                ) {
                    CityCard(Citys[page].city,Citys[page].image,Citys[page].hotels)
                }
            }
        }

    }
}