package com.example.adminoffice.ui.theme.Customer

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.example.adminoffice.R
import com.example.adminoffice.ui.theme.Customer.Bookings.ViewBookingsCustomer
import com.example.adminoffice.ui.theme.Customer.CartFunctions.CartView
import com.example.adminoffice.ui.theme.Customer.Coupons.MapsCoupon
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Customer
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Refund
import com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy
import com.example.adminoffice.ui.theme.Customer.Functions.HotelScreen
import com.example.adminoffice.ui.theme.Customer.Profile.ViewProfile
import com.example.adminoffice.ui.theme.Customer.Wishlist.WishList
import com.example.adminoffice.ui.theme.DabsUser
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.ServiceCategory
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.AddHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Inventory.ViewInventory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getUserFromLocal
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.absoluteValue

object LandingPage  : Screen {
    var called = false
    data class CitySchema(
        val city:String,
        val hotels:String,
        val image: String
    )
    data class Review(
        val name: String,
        val date: String,
        val review: String,
        val rating: Int,
        val profilePicture: String,
        val position: String
    )

    val reviews = mutableStateListOf(
        Review(
            name = "Michael Brown",
            date = "25-10-2024",
            review = "The Grand Hotel provided me with an exceptional stay. The staff's warmth and willingness to help made my trip even better. The rooms were spacious and well-kept. I recommend this hotel.",
            rating = 5,
            profilePicture = "https://images.pexels.com/photos/846741/pexels-photo-846741.jpeg?cs=srgb&dl=pexels-andrea-piacquadio-846741.jpg&fm=jpg",
            position = "CEO at Microsoft"
        ),
        Review(
            name = "Alex Turner",
            date = "17-10-2024",
            review = "I had a wonderful stay at the Grand Hotel. The staff was friendly and helpful, the rooms were spacious and clean, and the location was perfect. I would recommend this hotel.",
            rating = 4,
            profilePicture = "https://t4.ftcdn.net/jpg/02/14/74/61/360_F_214746128_31JkeaP6rU0NzzzdFC4khGkmqc8noe6h.jpg",
            position = "Software Engineer"
        ),
        Review(
            name = "Ghulam Mohiuddin",
            date = "20-10-2024",
            review = "I enjoyed my stay at the Grand Hotel. The staff was friendly and helpful, rooms were clean, and the location was perfect. I recommend this hotel to anyone who loves eating.",
            rating = 4,
            profilePicture = "https://img.freepik.com/premium-photo/young-handsome-caucasian-man-isolated-blue-background-looking-side_1368-264985.jpg",
            position = "Head At Daraz"
        ),
        Review(
            name = "Emily Johnson",
            date = "15-09-2024",
            review = "I had a wonderful stay at the Grand Hotel. The staff was friendly and helpful, rooms were spacious and clean, and the location was perfect. I would definitely recommend this hotel.",
            rating = 5,
            profilePicture = "https://media.istockphoto.com/id/1167775238/photo/portrait-of-charming-college-person-putting-hands-palms-pockets-look-isolated-over-purple.jpg?s=612x612&w=0&k=20&c=SxRXuFWeBhmATaKEqjwW6LAVwALzuM6lRHRcNXvKKps=",
            position = "CEO at Google"
        ),
        Review(
            name = "David Smith",
            date = "03-11-2024",
            review = "I had an amazing time at the Grand Hotel. The staff's friendliness and attentiveness made my stay truly memorable. The rooms were spacious and well-maintained. I'm delighted to recommend this hotel.",
            rating = 4,
            profilePicture = "https://img.freepik.com/premium-photo/handsome-young-businessman-shirt-eyeglasses_85574-6228.jpg",
            position = "Governor Punjab"
        ),
        Review(
            name = "Sophia Lee",
            date = "08-12-2024",
            review = "I had a wonderful stay at the Grand Hotel. The staff was friendly and helpful, the rooms were spacious and clean, and the location was perfect. I would recommend this hotel.",
            rating = 5,
            profilePicture = "https://media.istockphoto.com/id/1152822574/video/happy-carefree-woman-dancing-on-blue-background.jpg?s=640x640&k=20&c=mpwnDKnpo-9Q52wRyKAGjNymsG0rj0acmAtBmi8DZR4=",
            position = "World Explorer"
        ),
        Review(
            name = "Jennifer Martinez",
            date = "12-11-2024",
            review = "I had a wonderful stay. I would definitely recommend this hotel to anyone looking for a great place to stay in the city.",
            rating = 4,
            profilePicture = "https://media.istockphoto.com/id/1281083606/photo/photo-of-attractive-charming-lady-cute-bobbed-hairdo-arms-crossed-self-confident-person.jpg?s=612x612&w=0&k=20&c=Pr3c_KCEQYE7jSDweq-mLbd-WVD-NdNSwzxG82BFwlw=",
            position = "Military Officer"
        ),
        Review(
            name = "Emma Davis",
            date = "30-09-2024",
            review = "I had a wonderful stay at the Grand Hotel. The staff was friendly and helpful, the rooms were spacious and clean, and the location was perfect. I would recommend this hotel.",
            rating = 5,
            profilePicture = "https://www.ukbusinessblog.co.uk/wp-content/uploads/2020/06/photo-of-woman-using-her-laptop-935756-1-scaled.jpg",
            position = "CTO at Doerz"
        )
    )
    var Cities = mutableStateListOf<CitySchema>()
    var UserDABS = mutableStateListOf<DabsUser>()
    var serviceCategories = mutableStateListOf<ViewInventory.TableRow>()
    var services = mutableStateListOf<Service>()
    var servicesRoom = mutableStateListOf<Service>()
    var servicesRoomselected = mutableStateListOf<String>()
    var serviceshotelSelected = mutableStateListOf<String>()
    var inventoryselected = mutableStateListOf<String>()
    var HotelJsonArrayError = mutableStateOf("")
    var Hotels = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel>()
    var HotelFilter = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel>()
   @OptIn(ExperimentalComposeUiApi::class)
    val keyboardController = LocalSoftwareKeyboardController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
        ExperimentalFoundationApi::class
    )
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val configuration = LocalConfiguration.current
        val screenWidthDp: Dp = configuration.screenWidthDp.dp
        val screenHeightDp: Dp = configuration.screenHeightDp.dp
        val searchHotel = remember {
            mutableStateOf("")
        }
        var user = getUserFromLocal(context)
        val pagerState = rememberPagerState()
        val pagerStatePopular = rememberPagerState()
        val pagerStateTestimonial = rememberPagerState()
        var sortOrder by remember { mutableStateOf(SortOrder.Ascending) }
        val minPrice = remember {
            mutableStateOf("300")
        }
        val maxPrice = remember {
            mutableStateOf("100000")
        }
        var isExpandedCity = remember {
            mutableStateOf(false)
        }
        var isExpandedRating = remember {
            mutableStateOf(false)
        }
        val radioOptions = listOf("All", "Yes", "No")
        var hotelservices by remember { mutableStateOf(false) }
        var roomservices by remember { mutableStateOf(false) }
        var roominvetory by remember { mutableStateOf(false) }
        var filters by remember { mutableStateOf(false) }
        var hotelservicemore by remember { mutableStateOf(false) }
        var roomservicemore by remember { mutableStateOf(false) }
        var inventorymore by remember { mutableStateOf(false) }
        val Page = remember {
            mutableStateOf(1)
        }
        var selectedOption by remember { mutableStateOf(radioOptions[0]) }
        var sliderPosition by remember { mutableStateOf(0f..100000f) }
        var city by remember { mutableStateOf("") }
        var rating by remember { mutableStateOf("") }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerState= drawerState,
            drawerContent = {
                ModalDrawerSheet{
                    Column(modifier = Modifier
                        .width(screenWidthDp - 100.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), horizontalArrangement = Arrangement.End){
                            Box(modifier = Modifier
                                .size(40.dp)
                                .background(Color.Red, RoundedCornerShape(10.dp))
                                .padding(10.dp)
                                .clickable {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }){
                                Icon(painterResource(id = R.drawable.close), contentDescription = null, modifier = Modifier.size(30.dp), tint = Color.White)
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            OutlinedTextField(
                                shape = RoundedCornerShape(5.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Gray,
                                    errorBorderColor = Color.Red,
                                    placeholderColor = Color.Gray,
                                    disabledPlaceholderColor = Color.Gray
                                ),
                                value = searchHotel.value,
                                onValueChange = {
                                    if(it.length<100)
                                        searchHotel.value=it

                                },
                                leadingIcon = {
                                    Icon(painterResource(id = R.drawable.search), contentDescription = "person", tint = Color.Black)
                                },
                                label = {
                                    Text(text = "Search Hotel", color = Color.Gray)
                                },
                                placeholder = {
                                    Text(text = "Search Hotel by any keyword", fontSize = 12.sp)
                                },
                                modifier = Modifier.width(screenWidthDp-140.dp),
                                singleLine = true,
                            )
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            Box(
                                modifier = Modifier
                                    .width(screenWidthDp - 140.dp)
                                    .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                    .padding(16.dp)
                                    .clickable { isExpandedCity.value = true }
                            ) {
                                Row {
                                    Icon(painterResource(id = R.drawable.location), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= Color(
                                        0xFF000000
                                    )
                                    )
                                    Text(text = if(city==""){
                                        "Select Hotel City"
                                    }
                                    else{
                                        city
                                    },
                                        color =  if(city==""){
                                            Color.Gray
                                        }
                                        else{
                                            Color.Black
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(100.dp))
                                  //  Icon(painterResource(id = R.drawable.chevrondown), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= Color.Gray)
                                }

                                DropdownMenu(
                                    expanded = isExpandedCity.value,
                                    onDismissRequest = {isExpandedCity.value=false },
                                    modifier = Modifier
                                        .width(screenWidthDp - 140.dp)
                                        .height(100.dp)
                                ) {

                                    val Cities = listOf(
                                        AddHotel.City(Name = "Islamabad", Image = "https://th.bing.com/th/id/OIP.zmWHViTrLiGswUseryASZwHaEr?pid=ImgDet&rs=1"),
                                        AddHotel.City(Name = "Karachi", Image = "https://th.bing.com/th/id/R.544ce936798e4de667bd8b58322c200b?rik=szhxqGw6LGn2UA&pid=ImgRaw&r=0"),
                                        AddHotel.City(Name = "Lahore", Image = "https://th.bing.com/th/id/R.9c40a75ee7de2a8105e3abdc5b1bf31b?rik=U7P1rfXkQ8a8rw&pid=ImgRaw&r=0")
                                    )
                                    Cities.forEach { option ->
                                        DropdownMenuItem(text = { Text(text = option.Name) } , leadingIcon = {
                                            Box(){
                                                Image(
                                                    painter = rememberAsyncImagePainter(option.Image, contentScale = ContentScale.FillBounds),
                                                    contentDescription = "image",
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .clip(RoundedCornerShape((CornerSize(20.dp))))
                                                )
                                            }
                                        }, onClick = {
                                            city = option.Name
                                            isExpandedCity.value = false
                                        })
                                    }
                                }
                            }
                        }
                        Text(text = "Price Range", modifier = Modifier.padding(top = 10.dp, start = 10.dp), fontSize = 14.sp, fontWeight = FontWeight.W800)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            RangeSlider(
                                value = sliderPosition,
                                steps = 50,
                                onValueChange = { range ->
                                    sliderPosition = range
                                                minPrice.value=sliderPosition.start.toInt().toString()
                                                maxPrice.value=sliderPosition.endInclusive.toInt().toString()
                                                },
                                valueRange = 0f..100000f,
                                onValueChangeFinished = {
                                    // launch some business logic update with the state you hold
                                    // viewModel.updateSelectedSliderValue(sliderPosition)
                                },
                                modifier = Modifier.width(screenWidthDp-160.dp),
                            )

                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            Row(
                                modifier = Modifier.width(screenWidthDp-140.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(

                                ) {
                                    OutlinedTextField(
                                        shape = RoundedCornerShape(5.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = Color.Black,
                                            unfocusedBorderColor = Color.Gray,
                                            errorBorderColor = Color.Red,
                                            placeholderColor = Color.Gray,
                                            disabledPlaceholderColor = Color.Gray
                                        ),
                                        value = minPrice.value,
                                        onValueChange = {
                                            if (it.length <= 10){
                                                if(it!=""){
                                                    if(it.toInt()<maxPrice.value.toInt()){
                                                        minPrice.value=it
                                                        var temp =(it.toFloat()..sliderPosition.endInclusive)
                                                        sliderPosition=temp
                                                    }
                                                }
                                                else{
                                                    minPrice.value="0"
                                                }
                                            }
                                        }, leadingIcon = {
                                                         Text(text = "Rs.")
                                        },
                                        label = {
                                            Text(text = "Min", color = Color.Gray)
                                        },
                                        placeholder = {
                                            Text(text = "Min Price")
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number
                                        ),
                                        modifier = Modifier.size(((screenWidthDp-140.dp)/2-10.dp),60.dp)
                                    )
                                }
                                Column(
                                ) {
                                    OutlinedTextField(
                                        shape = RoundedCornerShape(5.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = Color.Black,
                                            unfocusedBorderColor = Color.Gray,
                                            errorBorderColor = Color.Red,
                                            placeholderColor = Color.Gray,
                                            disabledPlaceholderColor = Color.Gray
                                        ),leadingIcon = {
                                            Text(text = "Rs.")
                                        },
                                        value = maxPrice.value,
                                        onValueChange = {
                                            if (it.length <= 10){
                                                if(it!=""){
                                                   if(it.toInt() > minPrice.value.toInt()){
                                                       maxPrice.value=it
                                                       var temp =(sliderPosition.start..it.toFloat())
                                                       sliderPosition=temp
                                                   }
                                                }
                                                else{
                                                    maxPrice.value="0"
                                                }
                                            }

//                            isErrorLastName = isValidLastName(LastName.value)
                                        },
                                        label = {
                                            Text(text = "Max", color = Color.Gray)
                                        },
                                        placeholder = {
                                            Text(text = "Max Price")
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number
                                        ),
                                        modifier = Modifier.size(((screenWidthDp-140.dp)/2-10.dp),60.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            Box(
                                modifier = Modifier
                                    .width(screenWidthDp - 140.dp)
                                    .border(0.6.dp, Color.Black, MaterialTheme.shapes.small)
                                    .padding(16.dp)
                                    .clickable { isExpandedRating.value = true }
                            ) {
                                Row {
                                    Icon(
                                        Icons.Outlined.Star, contentDescription =null, tint = Color(
                                            0xFFFFC107
                                        ), modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(text = if(rating==""){
                                        "Select Rating"
                                    }
                                    else{
                                        rating
                                    },
                                        color =  if(rating==""){
                                            Color.Gray
                                        }
                                        else{
                                            Color.Black
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(100.dp))
                                  //  Icon(painterResource(id = R.drawable.chevrondown), contentDescription = "person", modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp),tint= Color.Gray)
                                }

                                DropdownMenu(
                                    expanded = isExpandedRating.value,
                                    onDismissRequest = {isExpandedRating.value=false },
                                    modifier = Modifier
                                        .width(screenWidthDp - 140.dp)
                                        .height(100.dp)
                                ) {

                                    val Cities = listOf(
                                        AddHotel.City(Name = "All", Image = "https://th.bing.com/th/id/OIP.zmWHViTrLiGswUseryASZwHaEr?pid=ImgDet&rs=1"),
                                        AddHotel.City(Name = "1 Star", Image = "https://th.bing.com/th/id/R.544ce936798e4de667bd8b58322c200b?rik=szhxqGw6LGn2UA&pid=ImgRaw&r=0"),
                                        AddHotel.City(Name = "2 Star", Image = "https://th.bing.com/th/id/R.9c40a75ee7de2a8105e3abdc5b1bf31b?rik=U7P1rfXkQ8a8rw&pid=ImgRaw&r=0"),
                                        AddHotel.City(Name = "3 Star", Image = "https://th.bing.com/th/id/R.9c40a75ee7de2a8105e3abdc5b1bf31b?rik=U7P1rfXkQ8a8rw&pid=ImgRaw&r=0"),
                                        AddHotel.City(Name = "4 Star", Image = "https://th.bing.com/th/id/R.9c40a75ee7de2a8105e3abdc5b1bf31b?rik=U7P1rfXkQ8a8rw&pid=ImgRaw&r=0"),
                                        AddHotel.City(Name = "5 Star", Image = "https://th.bing.com/th/id/R.9c40a75ee7de2a8105e3abdc5b1bf31b?rik=U7P1rfXkQ8a8rw&pid=ImgRaw&r=0")
                                    )
                                    Cities.forEach { option ->
                                        DropdownMenuItem(text = { Text(text = option.Name) } , leadingIcon = {
                                            Icon(
                                                Icons.Outlined.Star, contentDescription =null, tint = Color(
                                                    0xFFFFC107
                                                ), modifier = Modifier.size(25.dp)
                                            )
                                        }, onClick = {
                                            rating = option.Name
                                            isExpandedRating.value = false
                                        })
                                    }
                                }
                            }
                        }
                        Text(text = "Meal Available", modifier = Modifier.padding(top = 10.dp, start = 10.dp), fontSize = 14.sp, fontWeight = FontWeight.W800)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){

                                Column {
                                    radioOptions.forEach { text ->
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp)
                                            ) {
                                                RadioButton(
                                                    selected = selectedOption == text,
                                                    onClick = {
                                                        selectedOption = text
                                                    }, modifier = Modifier.size(24.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(text = text, fontSize = 12.sp)
                                            }

                                    }
                                }

                        }
                        Text(text = "Hotel Services", modifier = Modifier.padding(top = 10.dp, start = 10.dp), fontSize = 14.sp, fontWeight = FontWeight.W800)
                        Row(modifier = Modifier.width(screenWidthDp-140.dp), verticalAlignment = Alignment.CenterVertically){
                            Checkbox(
                                checked = hotelservices,
                                onCheckedChange = { isChecked ->
                                    hotelservices = isChecked
                                    if(hotelservices==true){
                                        serviceshotelSelected.clear()
                                        for(serv in services){
                                            serviceshotelSelected.add(serv.name)
                                        }
                                    }
                                }
                            )
                            Text(text = "All", fontSize = 12.sp)
                        }
                            for(i in 0 until services.size){
                               Box(modifier = Modifier
                                   .padding(horizontal = 12.dp)
                                   .clickable {
                                       if (serviceshotelSelected.contains(services[i].name)) {
                                           serviceshotelSelected.remove(services[i].name)
                                       } else {
                                           serviceshotelSelected.add(services[i].name)
                                       }
                                   }){
                                   Row(modifier = Modifier.width(screenWidthDp-140.dp), verticalAlignment = Alignment.CenterVertically){
                                       Checkbox(
                                           checked = serviceshotelSelected.contains(services[i].name),
                                           onCheckedChange = null
                                       )
                                       Spacer(modifier = Modifier.size(4.dp))
                                       Text(text = services[i].name, fontSize = 12.sp)
                                   }
                               }
                            }
//                        Box(modifier = Modifier.clickable { hotelservicemore=!hotelservicemore }){
//                            Text(text = if(hotelservicemore==true){"Show More"}else{"Hide"}, color = GlobalStrings.CustomerColorMain, modifier = Modifier.padding(horizontal = 8.dp), fontSize = 10.sp)
//                        }
                        Text(text = "Room Services", modifier = Modifier.padding(top = 10.dp, start = 10.dp), fontSize = 14.sp, fontWeight = FontWeight.W800)
                        Row(modifier = Modifier.width(screenWidthDp-140.dp), verticalAlignment = Alignment.CenterVertically){
                            Checkbox(
                                checked = roomservices,
                                onCheckedChange = { isChecked ->
                                    roomservices = isChecked
                                    if(roomservices==true){
                                        servicesRoomselected.clear()
                                        for(serv in servicesRoom){
                                            servicesRoomselected.add(serv.name)
                                        }
                                    }
                                }
                            )
                            Text(text = "All", fontSize = 12.sp)
                        }
                        for(service in servicesRoom){
                            Box(modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .clickable {
                                    if (servicesRoomselected.contains(service.name)) {
                                        servicesRoomselected.remove(service.name)
                                    } else {
                                        servicesRoomselected.add(service.name)
                                    }
                                }){
                                Row(modifier = Modifier.width(screenWidthDp-140.dp), verticalAlignment = Alignment.CenterVertically){
                                    Checkbox(
                                        checked = servicesRoomselected.contains(service.name),
                                        onCheckedChange = null
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Text(text = service.name, fontSize = 12.sp)
                                }
                            }
                        }
                        Text(text = "Room Inventory", modifier = Modifier.padding(top = 10.dp, start = 10.dp), fontSize = 14.sp, fontWeight = FontWeight.W800)
                        Row(modifier = Modifier.width(screenWidthDp-140.dp), verticalAlignment = Alignment.CenterVertically){
                            Checkbox(
                                checked = roominvetory,
                                onCheckedChange = { isChecked ->
                                    roominvetory = isChecked
                                    if(roominvetory==true){
                                        inventoryselected.clear()
                                        for(serv in serviceCategories){
                                            inventoryselected.add(serv.serviceName)
                                        }
                                    }
                                }
                            )
                            Text(text = "All", fontSize = 12.sp)
                        }
                        for(service in serviceCategories){
                            Box(modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .clickable {
                                    if (inventoryselected.contains(service.serviceName)) {
                                        inventoryselected.remove(service.serviceName)
                                    } else {
                                        inventoryselected.add(service.serviceName)
                                    }
                                }){
                                Row(modifier = Modifier.width(screenWidthDp-140.dp), verticalAlignment = Alignment.CenterVertically){
                                    Checkbox(
                                        checked = inventoryselected.contains(service.serviceName),
                                        onCheckedChange = null
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Text(text = service.serviceName, fontSize = 12.sp)
                                }
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                            Box(modifier = Modifier
                                .clickable {
                                    scope.launch {
                                        filters = false
                                        servicesRoomselected.clear()
                                        serviceshotelSelected.clear()
                                        inventoryselected.clear()
                                        minPrice.value="300"
                                        maxPrice.value="100000"
                                        rating=""
                                        drawerState.close()

                                    }
                                }
                                .padding(0.dp, 5.dp)
                                .background(Color.Black, RoundedCornerShape(15.dp))
                                .size(((screenWidthDp - 140.dp) / 2) - 10.dp, 35.dp), contentAlignment = Alignment.Center){
                                Text(text = "Clear Filters", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.Bold)

                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Box(modifier = Modifier
                                .clickable {
                                    scope.launch {
                                        filters = true
                                        filterHotels(
                                            context = context,
                                            minPrice = minPrice.value.toInt(),
                                            maxPrice = maxPrice.value.toInt(),
                                            rating = rating,
                                            city = city,
                                            hotelservices = serviceshotelSelected,
                                            roomservices = servicesRoomselected,
                                            roominventory = inventoryselected,
                                            meals = selectedOption,
                                            page = Page.value
                                        )
                                        drawerState.close()
                                    }
                                }
                                .padding(0.dp, 5.dp)
                                .background(
                                    GlobalStrings.CustomerColorMain,
                                    RoundedCornerShape(15.dp)
                                )
                                .size(((screenWidthDp - 140.dp) / 2) - 10.dp, 35.dp), contentAlignment = Alignment.Center){
                                Text(text = "Apply Filters", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.Bold)

                            }
                            Spacer(modifier = Modifier.size(5.dp))
                        }
                    }
                }
            },
            gesturesEnabled=false
        ){
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {

                LazyColumn(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .height(screenHeightDp - 50.dp)
                ) {
                    item {
                        Box(modifier = Modifier
//                       .border(0.4.dp, Color.Gray, RoundedCornerShape(10.dp))
                            .padding(10.dp)){
                            Row (modifier= Modifier
                                .fillMaxWidth()
                                .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                Box(modifier = Modifier
                                    .width(screenWidthDp - 120.dp)
                                    .height(50.dp)
                                    .border(
                                        0.4.dp, Color.Gray,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .clickable {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }){
                                    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.height(45.dp)) {
                                        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                                            Row(verticalAlignment = Alignment.CenterVertically){
                                                Spacer(modifier = Modifier.size(10.dp))
                                                Icon(rememberAsyncImagePainter(model = R.drawable.search), contentDescription = null,Modifier.size(20.dp))
                                                Spacer(modifier = Modifier.size(10.dp))
                                                Column {
                                                    Text(text = "Where to?", fontSize = 12.sp, fontWeight = FontWeight.W800)
                                                    Text(text = "Anywhere - Any week - Add Guests", fontSize = 10.sp, fontWeight = FontWeight.Light,color=Color.Gray)
                                                }
                                            }
                                            Row {
                                                Icon(rememberAsyncImagePainter(model = R.drawable.filter), contentDescription = null,Modifier.size(20.dp))
                                                Spacer(modifier = Modifier.size(10.dp))
                                            }

                                        }
                                    }
                                }
                                Box(modifier = Modifier.clickable { navigator.push(CartView) }){
                                    Image(
                                        painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Fcart.png?alt=media&token=b086cbd2-0385-414d-9329-0bc6a796fe66&_gl=1*1tavxm5*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTU0Mjk5NC4zNC4xLjE2OTk1NDMwMzguMTYuMC4w"),
                                        contentDescription = "image",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    (CornerSize(
                                                        50.dp
                                                    ))
                                                )
                                            ),
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                                Box(modifier = Modifier.clickable { navigator.replace(ViewProfile) }){
                                    Image(
                                        painter = rememberAsyncImagePainter(if(user.profilePicture=="" || user.profilePicture=="user"){"https://icon-library.com/images/no-user-image-icon/no-user-image-icon-9.jpg"}else{
                                            user.profilePicture
                                        }),
                                        contentDescription = "image",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    (CornerSize(
                                                        50.dp
                                                    ))
                                                )
                                            ),
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                            }
                        }
                    }
                   if(filters==false){

                       item {
                           Text(
                               text = "Popular Hotels",
                               color = Color.Black,
                               textAlign = TextAlign.Start,
                               fontSize = 12.sp,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(10.dp),
                               fontWeight = FontWeight.W400,

                               )
                       }
                       item {
                           Row(modifier = Modifier.fillMaxWidth(),
                               verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.SpaceBetween,){
                               Box(modifier = Modifier.padding(5.dp)){
                                   IconButton(enabled = pagerStatePopular.canScrollBackward, modifier = Modifier.size(20.dp), onClick = {
                                       scope.launch {
                                           pagerStatePopular.animateScrollToPage(pagerStatePopular.currentPage - 1)
                                       }
                                   }) {
                                       Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "back")
                                   }
                               }
                               HorizontalPager(
                                   pageCount = Hotels.size,
                                   state = pagerStatePopular,
                                   contentPadding = PaddingValues(horizontal = 0.dp),
                                   modifier = Modifier
                                       .weight(1.5f)
                                       .fillMaxWidth()
                               ) { page ->
                                   val pageOffset =
                                       (pagerStatePopular.currentPage - page) + pagerStatePopular.currentPageOffsetFraction

                                   val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)


                                   Box(modifier = Modifier
//                                       .graphicsLayer {
//                                           scaleX = scaleFactor
//                                           scaleY = scaleFactor
//                                       }
//                                       .alpha(
//                                           scaleFactor.coerceIn(0f, 1f)
//                                       )
//                                       .padding(0.dp)
                                   ) {
                                       Box(modifier = Modifier.clickable { navigator.push(HotelScreen(Hotels[page])) }){
                                           HotelCardWithSlider(
                                               images = Hotels[page].images,
                                               name = Hotels[page].name,
                                               location = Hotels[page].location,
                                               price = Hotels[page].averagePrice.toString(),
                                               rating =Hotels[page].averageRating.toString()
                                           )
                                       }
                                       //CityCard(Citys[page].city,Citys[page].image,Citys[page].hotels)
                                   }
                               }
                               Box(modifier = Modifier.padding(5.dp)){
                                   IconButton(enabled = pagerStatePopular.currentPage != Hotels.size - 1,modifier = Modifier.size(20.dp), onClick = {
                                       scope.launch {
                                           pagerStatePopular.animateScrollToPage(pagerStatePopular.currentPage + 1)
                                       }
                                   }) {
                                       Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "forward")
                                   }
                               }
                           }
//                           Row(
//                               Modifier
//                                   .horizontalScroll(rememberScrollState())
//                                   .padding(horizontal = 10.dp)){
//                               for(hotel in Hotels){
//                                   Box(modifier = Modifier.clickable {
//                                       navigator.push(HotelScreen(hotel))
//                                   }){
//                                       HotelCardWithSlider(name = hotel.name, images = hotel.images, location = hotel.location, price = hotel.averagePrice.toString())                                   }
//                               }
//                           }
                       }
                       item{
                           Text(
                               text = "Top City Hotels",
                               color = Color.Black,
                               textAlign = TextAlign.Start,
                               fontSize = 12.sp,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(10.dp),
                               fontWeight = FontWeight.W400,

                               )
                       }
                       item {
                           Row(modifier = Modifier.fillMaxWidth(),
                               verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.SpaceBetween,){
                              Box(modifier = Modifier.padding(5.dp)){
                                  IconButton(enabled = pagerState.canScrollBackward, modifier = Modifier.size(20.dp), onClick = {
                                      scope.launch {
                                          pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                      }
                                  }) {
                                      Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "back")
                                  }
                              }
                               HorizontalPager(
                                   pageCount = Hotels.size,
                                   state = pagerState,
                                   contentPadding = PaddingValues(horizontal = 0.dp),
                                   modifier = Modifier
                                       .fillMaxWidth().weight(1.5f)
                               ) { page ->
                                   val pageOffset =
                                       (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                                   val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)


                                   Box(modifier = Modifier
//                                       .graphicsLayer {
//                                           scaleX = scaleFactor
//                                           scaleY = scaleFactor
//                                       }
//                                       .alpha(
//                                           scaleFactor.coerceIn(0f, 1f)
//                                       )
//                                       .padding(0.dp)
                                   ) {
                                       Box(modifier = Modifier.clickable {
                                           navigator.push(HotelScreen(Hotels[page]))
                                       }){
                                           HotelCardWithSlider(
                                               images = Hotels[page].images,
                                               name = Hotels[page].name,
                                               location = Hotels[page].location,
                                               price = Hotels[page].averagePrice.toString(),
                                               rating =Hotels[page].averageRating.toString()
                                           )
                                       }
                                       //CityCard(Citys[page].city,Citys[page].image,Citys[page].hotels)
                                   }
                               }
                               Box(modifier = Modifier.padding(5.dp)){
                                   IconButton(enabled = pagerState.currentPage != Hotels.size - 1, modifier = Modifier.size(20.dp), onClick = {
                                       scope.launch {
                                           pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                       }
                                   }) {
                                       Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "forward")
                                   }
                               }
                           }
//                           Row(
//                               Modifier
//                                   .horizontalScroll(rememberScrollState())
//                                   .padding(horizontal = 10.dp)){
//                               for(hotel in Hotels){
//                                   Box(modifier = Modifier.clickable {
//                                       navigator.push(HotelScreen(hotel))
//                                   }){
//                                       HotelCardWithSlider(name = hotel.name, images = hotel.images, location = hotel.location, price = hotel.averagePrice.toString())                                   }
//                               }
//                           }
                       }
                       item {
                           Text(
                               text = "Featured Cities",
                               color = Color.Black,
                               textAlign = TextAlign.Start,
                               fontSize = 12.sp,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(10.dp),
                               fontWeight = FontWeight.W400,

                               )
                       }
                       item{
                           CustomSliderCity(Citys = Cities)
                       }
                       item {
                           Text(
                               text = "View Hotel on Maps",
                               color = Color.Black,
                               textAlign = TextAlign.Start,
                               fontSize = 12.sp,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(10.dp),
                               fontWeight = FontWeight.W400,

                               )
                       }
                       item {
                          Box(modifier = Modifier
                              .fillMaxWidth()
                              .padding(10.dp)){
                              Box(modifier = Modifier
                                  .fillMaxWidth()
                                  .clickable {
                                      navigator.push(ViewHotelOnMaps(Hotels))
                                  }
                                  .background(
                                      color = Color(0xFF9D57FF),
                                      RoundedCornerShape(20.dp)
                                  )
                                  .padding(10.dp), contentAlignment = Alignment.Center){
                                  Row(
                                      modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                                  ){
                                      Box(modifier = Modifier.width((screenWidthDp/2)-10.dp)){
                                          Text(text = "View Hotel on Map and see what suits your interest.", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color.White)
                                      }
                                      Image(
                                          painter = rememberAsyncImagePainter("https://www.techjunkie.com/wp-content/uploads/2015/04/map-location-pin.jpg"),
                                          contentDescription = "image",
                                          modifier = Modifier
                                              .size(140.dp)
                                              .clip(
                                                  RoundedCornerShape(
                                                      (CornerSize(
                                                          20.dp
                                                      ))
                                                  )
                                              ),
                                          contentScale = ContentScale.FillBounds
                                      )
                                  }
                              }
                          }
                       }
                       item{
                           Row(
                               Modifier
                                   .fillMaxWidth()
                                   .horizontalScroll(rememberScrollState())
                                   .padding(10.dp), horizontalArrangement = Arrangement.Center){
                               Column(modifier = Modifier.width(350.dp)){
                                   Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                       Text(text = "Why Choose Us?", fontSize = 16.sp, fontWeight = FontWeight.W800, textAlign = TextAlign.Center, lineHeight = 17.sp)
                                   }
                                   Text(text = "Expertly curating comfort within budget - your go-to for affordable hotel bookings.", fontSize = 12.sp, fontWeight = FontWeight.Light, textAlign = TextAlign.Center, lineHeight = 13.sp)
                               }
                           }
                       }
                       item{
                           Row(
                               Modifier
                                   .horizontalScroll(rememberScrollState())
                               , horizontalArrangement = Arrangement.Center){
                               Box(modifier = Modifier
                                   .width(250.dp)
                                   .padding(5.dp)){
                                   Column (modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(10.dp)
                                       .width(250.dp)
                                       .height(200.dp)
                                       .background(
                                           Color(0xFFFF9E57),
                                           RoundedCornerShape(10.dp)
                                       )
//                               .border(
//                                   0.2.dp, Color.Gray,
//                                   RoundedCornerShape(10.dp)
//                               )
                                   ){
                                       Column(Modifier.padding(10.dp)) {
                                           Image(
                                               painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Fbooking.png?alt=media&token=3985ad23-e117-4518-8db6-128d8ad46329&_gl=1*1s23nwj*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTE4NjQ4OS4zMS4xLjE2OTkxODY1NzAuNjAuMC4w"),
                                               contentDescription = "image",
                                               modifier = Modifier
                                                   .size(30.dp)
                                                   .clip(
                                                       RoundedCornerShape(
                                                           (CornerSize(
                                                               50.dp
                                                           ))
                                                       )
                                                   ),
                                               contentScale = ContentScale.FillBounds
                                           )
                                           Text(text = "Booking Simplicity", fontSize = 16.sp, fontWeight = FontWeight.W600,color=Color.White)
                                           Text(text = "Say goodbye to complicated booking processes. Our user-friendly interface ensures that booking is as easy as a few clicks, ensuring you're getting value for money.", fontSize = 12.sp, fontWeight = FontWeight.W500, lineHeight = 13.sp,color=Color.White)

                                       }
                                   }
                               }
                               Box(modifier = Modifier
                                   .width(250.dp)
                                   .padding(5.dp)){
                                   Column (modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(10.dp)
                                       .width(250.dp)
                                       .height(200.dp)
                                       .background(
                                           Color(0xFF5787FF),
                                           RoundedCornerShape(10.dp)
                                       )){
                                       Column(Modifier.padding(10.dp)) {
                                           Image(
                                               painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Fcash.png?alt=media&token=0a40641a-3811-4123-8fa4-24449662041d&_gl=1*i4uc7x*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTE4NjQ4OS4zMS4xLjE2OTkxODY2MzEuNjAuMC4w"),
                                               contentDescription = "image",
                                               modifier = Modifier
                                                   .size(30.dp)
                                                   .clip(
                                                       RoundedCornerShape(
                                                           (CornerSize(
                                                               50.dp
                                                           ))
                                                       )
                                                   ),
                                               contentScale = ContentScale.FillBounds
                                           )
                                           Text(text = "Transparent Pricing", fontSize = 16.sp, fontWeight = FontWeight.W600,color=Color.White)
                                           Text(text = "Tailor your travel experience by filtering through a variety of options to match your preferences. Our platform ensures that you find accommodations for your comfort needs.", fontSize = 12.sp, fontWeight = FontWeight.W500, lineHeight = 13.sp,color=Color.White)

                                       }
                                   }
                               }
                               Box(modifier = Modifier
                                   .width(250.dp)
                                   .padding(5.dp)){
                                   Column (modifier = Modifier
                                       .fillMaxWidth()
                                       .width(250.dp)
                                       .height(220.dp)
                                       .padding(10.dp)
                                       .background(
                                           Color(0xFFFF57C1),
                                           RoundedCornerShape(10.dp)
                                       )){
                                       Column(Modifier.padding(10.dp)) {
                                           Image(
                                               painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logoog%2Fcomfort.png?alt=media&token=2d7a55f0-400c-41f7-bcc6-63cc5e4ea26e&_gl=1*1vuiv4s*_ga*MTgxOTgxNjI0NS4xNjg5MTczMDEz*_ga_CW55HF8NVT*MTY5OTE4NjQ4OS4zMS4xLjE2OTkxODY4MTguNjAuMC4w"),
                                               contentDescription = "image",
                                               modifier = Modifier
                                                   .size(30.dp)
                                                   .clip(
                                                       RoundedCornerShape(
                                                           (CornerSize(
                                                               50.dp
                                                           ))
                                                       )
                                                   ),
                                               contentScale = ContentScale.FillBounds
                                           )
                                           Text(text = "Personalized Comfort", fontSize = 16.sp, fontWeight = FontWeight.W600, color = Color.White)
                                           Text(text = "Experience hassle-free booking with upfront, affordable pricing. No hidden fees or surprises – just clear, straightforward rates that make planning your trip a breeze and make it wonderful", fontSize = 12.sp, fontWeight = FontWeight.W500, lineHeight = 13.sp, color = Color.White)

                                       }
                                   }
                               }
                           }
                       }
                       item{
                           Row(
                               Modifier
                                   .fillMaxWidth()
                                   .horizontalScroll(rememberScrollState())
                                   .padding(10.dp), horizontalArrangement = Arrangement.Center){
                               Column(modifier = Modifier.width(350.dp)){
                                   Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                       Text(text = "What our Client say?", fontSize = 16.sp, fontWeight = FontWeight.W800, textAlign = TextAlign.Center, lineHeight = 17.sp)
                                   }
                                   Text(text = "Expertly curating comfort within budget - your go-to for affordable hotel bookings.", fontSize = 12.sp, fontWeight = FontWeight.Light, textAlign = TextAlign.Center, lineHeight = 13.sp)
                               }
                           }
                       }

                       item {
                           Row(modifier = Modifier.fillMaxWidth(),
                               verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.SpaceBetween,){
                               IconButton(enabled = pagerStateTestimonial.canScrollBackward, onClick = {
                                   scope.launch {
                                       pagerStateTestimonial.animateScrollToPage(pagerStateTestimonial.currentPage - 1)
                                   }
                               }) {
                                   Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "back")
                               }
                               HorizontalPager(
                                   pageCount = reviews.size,
                                   state = pagerStateTestimonial,
                                   contentPadding = PaddingValues(horizontal = 0.dp),
                                   modifier = Modifier
                                       .width(screenWidthDp-100.dp)
                               ) { page ->
                                   val pageOffset =
                                       (pagerStateTestimonial.currentPage - page) + pagerStateTestimonial.currentPageOffsetFraction

                                   val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)


                                   Box(modifier = Modifier
                                       .graphicsLayer {
                                           scaleX = scaleFactor
                                           scaleY = scaleFactor
                                       }
                                       .alpha(
                                           scaleFactor.coerceIn(0f, 1f)
                                       )
                                       .padding(0.dp)
                                   ) {
                                       Box(modifier = Modifier.padding(horizontal=5.dp)){
                                           Box(modifier = Modifier
                                               .width(280.dp)
                                               .height(170.dp)
                                               // .background(Color(0xFFFEFEFE))
                                               .border(
                                                   0.2.dp,
                                                   Color(0xFFB6B4BB),
                                                   RoundedCornerShape(10.dp)
                                               )
                                           ){
                                               Column(
                                                   Modifier
                                                       .fillMaxWidth()){
                                                   Row (modifier = Modifier
                                                       .padding(5.dp)
                                                       .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                                       Row{
                                                           Image(
                                                               painter = rememberAsyncImagePainter(reviews[page].profilePicture),
                                                               contentDescription = "image",
                                                               modifier = Modifier
                                                                   .size(40.dp)
                                                                   .clip(
                                                                       RoundedCornerShape(
                                                                           (CornerSize(
                                                                               50.dp
                                                                           ))
                                                                       )
                                                                   ),
                                                               contentScale = ContentScale.FillBounds
                                                           )
                                                           Spacer(modifier = Modifier.size(10.dp))
                                                           Column {
                                                               Text(text = reviews[page].name, fontWeight = FontWeight.W700, fontSize = 14.sp)
                                                               Text(text = reviews[page].position, fontWeight = FontWeight.W400, fontSize = 11.sp, color = GlobalStrings.CustomerColorMain)
                                                               Text(text = reviews[page].date, fontWeight = FontWeight.W400, fontSize = 11.sp, color = Color.Gray)
                                                           }
                                                       }
                                                       Row(verticalAlignment = Alignment.CenterVertically){
                                                           Icon(
                                                               Icons.Outlined.Star, contentDescription =null, tint = Color(
                                                                   0xFFFFC107
                                                               ), modifier = Modifier.size(22.dp)
                                                           )
                                                           Text(text = reviews[page].rating.toString(), fontSize = 15.sp)
                                                       }

                                                   }
                                                   Column(modifier = Modifier
                                                       .padding(10.dp)
                                                       .height(50.dp)) {
                                                       Box(modifier = Modifier
                                                           .fillMaxWidth()){
                                                           Text(text = reviews[page].review, fontSize = 14.sp, fontWeight = FontWeight.W300, letterSpacing = 0.2.sp, color = Color.Black, lineHeight = 15.sp)
                                                       }
                                                   }
                                               }

                                           }
                                       }
                                       //CityCard(Citys[page].city,Citys[page].image,Citys[page].hotels)
                                   }
                               }
                               IconButton(enabled = pagerStateTestimonial.currentPage != Hotels.size - 1, onClick = {
                                   scope.launch {
                                       pagerStateTestimonial.animateScrollToPage(pagerStateTestimonial.currentPage + 1)
                                   }
                               }) {
                                   Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "forward")
                               }
                           }

//                           Row(
//                               Modifier
//                                   .horizontalScroll(rememberScrollState())
//                                   .padding(horizontal = 10.dp)){
//                               for(hotel in Hotels){
//                                   Box(modifier = Modifier.clickable {
//                                       navigator.push(HotelScreen(hotel))
//                                   }){
//                                       HotelCardWithSlider(name = hotel.name, images = hotel.images, location = hotel.location, price = hotel.averagePrice.toString())                                   }
//                               }
//                           }
                       }
                       item{
                           Spacer(modifier = Modifier.size(15.dp))
                       }

//                       item{
//                           Row(modifier = Modifier
//                               .fillMaxWidth()
//                               .padding(10.dp)
//                               .horizontalScroll(rememberScrollState())){
//                               for(review in reviews){
//                                   Box(modifier = Modifier.padding(horizontal=5.dp)){
//                                       Box(modifier = Modifier
//                                           .width(280.dp)
//                                           .height(170.dp)
//                                           // .background(Color(0xFFFEFEFE))
//                                           .border(
//                                               0.2.dp,
//                                               Color(0xFFB6B4BB),
//                                               RoundedCornerShape(10.dp)
//                                           )
//                                       ){
//                                           Column(
//                                               Modifier
//                                                   .fillMaxWidth()){
//                                               Row (modifier = Modifier
//                                                   .padding(5.dp)
//                                                   .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
//                                                   Row{
//                                                       Image(
//                                                           painter = rememberAsyncImagePainter(review.profilePicture),
//                                                           contentDescription = "image",
//                                                           modifier = Modifier
//                                                               .size(40.dp)
//                                                               .clip(
//                                                                   RoundedCornerShape(
//                                                                       (CornerSize(
//                                                                           50.dp
//                                                                       ))
//                                                                   )
//                                                               ),
//                                                           contentScale = ContentScale.FillBounds
//                                                       )
//                                                       Spacer(modifier = Modifier.size(10.dp))
//                                                       Column {
//                                                           Text(text = review.name, fontWeight = FontWeight.W700, fontSize = 14.sp)
//                                                           Text(text = review.position, fontWeight = FontWeight.W400, fontSize = 11.sp, color = GlobalStrings.CustomerColorMain)
//                                                           Text(text = review.date, fontWeight = FontWeight.W400, fontSize = 11.sp, color = Color.Gray)
//                                                       }
//                                                   }
//                                                   Row(verticalAlignment = Alignment.Bottom){
//                                                       Icon(
//                                                           Icons.Outlined.Star, contentDescription =null, tint = Color(
//                                                               0xFFFFC107
//                                                           ), modifier = Modifier.size(22.dp)
//                                                       )
//                                                       Text(text = review.rating.toString(), fontSize = 15.sp)
//                                                   }
//
//                                               }
//                                               Column(modifier = Modifier
//                                                   .padding(10.dp)
//                                                   .height(50.dp)) {
//                                                   Box(modifier = Modifier
//                                                       .fillMaxWidth()){
//                                                       Text(text = review.review, fontSize = 14.sp, fontWeight = FontWeight.W300, letterSpacing = 0.2.sp, color = Color.Black, lineHeight = 15.sp)
//                                                   }
//                                               }
//                                           }
//
//                                       }
//                                   }
//                               }
//                           }
//                       }
                   }
                    else{
                        item{
                            var searchFilter=searchHotel
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()){
                                }
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(
                                        rememberScrollState()
                                    )) {

                                    if(HotelFilter.isEmpty()){
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 50.dp)
                                            .height(screenHeightDp - 200.dp), contentAlignment = Alignment.Center){
                                            Column(horizontalAlignment = Alignment.CenterHorizontally){
                                                Image(
                                                    painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/kotlin-9839a.appspot.com/o/logo%2Fno-records.png?alt=media&token=d15e43c9-f619-47c5-8817-41ab00247cc4"),
                                                    contentDescription = "image",
                                                    modifier = Modifier
                                                        .size(300.dp)
                                                        .clip(
                                                            RoundedCornerShape(
                                                                (CornerSize(
                                                                    10.dp
                                                                ))
                                                            )
                                                        ),
                                                    contentScale = ContentScale.FillBounds
                                                )

                                                Text(text = "No Hotels Found", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                    else{
                                        val sortedRows = when (sortOrder) {
                                            SortOrder.Ascending -> HotelFilter.sortedBy { it.name }
                                            SortOrder.Descending -> HotelFilter.sortedByDescending { it.name}
                                        }
                                        sortedRows
                                            .filter {
                                                it.name.contains(searchFilter.value, ignoreCase = true)
                                            }
                                            .forEach { it ->
                                                Row(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(10.dp), horizontalArrangement = Arrangement.Center){
                                                    Box(modifier = Modifier.clickable {
                                                        navigator.push(HotelScreen(it))
                                                    }){
                                                        HotelCardWithSlider(
                                                            name = it.name, images = it.images, location = it.location,
                                                            rating =it.averageRating.toString(),
                                                            price = it.averagePrice.toString())}
                                                }
                                            }
                                        Row(modifier = Modifier
                                            .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ){
                                            Box(
                                                Modifier
                                                    .size(30.dp)
                                                    .background(
                                                        GlobalStrings.CustomerColorMain,
                                                        RoundedCornerShape(5.dp)
                                                    ), contentAlignment = Alignment.Center
                                            ){
                                                IconButton(
                                                    onClick = {
                                                        if(Page.value != 1){
                                                            Page.value = Page.value-1
                                                            filterHotels(context=context,minPrice=minPrice.value.toInt(),maxPrice=maxPrice.value.toInt(), rating = rating,
                                                                city=city, hotelservices = serviceshotelSelected, roomservices = servicesRoomselected,
                                                                roominventory = inventoryselected, meals = selectedOption, page = Page.value)
                                                        }
                                                    }, modifier = Modifier.padding(horizontal = 5.dp)
                                                ) {
                                                    Icon(painterResource(id = R.drawable.arrowleft), contentDescription = "Arrow Right", tint = Color.White)
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Box(
                                                Modifier
                                                    .size(30.dp)
                                                    .background(
                                                        GlobalStrings.CustomerColorMain,
                                                        RoundedCornerShape(5.dp)
                                                    ), contentAlignment = Alignment.Center
                                            ){
                                                IconButton(
                                                    onClick = {
                                                        if(HotelFilter.isNotEmpty()){
                                                            Page.value = Page.value+1
                                                            filterHotels(context=context,minPrice=minPrice.value.toInt(),maxPrice=maxPrice.value.toInt(), rating = rating,
                                                                city=city, hotelservices = serviceshotelSelected, roomservices = servicesRoomselected,
                                                                roominventory = inventoryselected, meals = selectedOption, page = Page.value)
                                                        }
                                                    }, modifier = Modifier.padding(horizontal = 5.dp)
                                                ) {
                                                    Icon(painterResource(id = R.drawable.arrowright), contentDescription = "Arrow Left",tint = Color.White)
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(5.dp))
                                        }
                                    }
                                }

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
                            Icon(painter = rememberVectorPainter(Icons.Outlined.Home), contentDescription = "Landing", tint = GlobalStrings.CustomerColorMain)
                        }
                        IconButton(onClick = {
                            navigator.replace(WishList)
                        }, modifier = Modifier.padding(10.dp)) {
                            Icon(painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder), contentDescription = "WishList", tint = Color.Black)
                        }
                        IconButton(onClick = {
                            navigator.replace(ViewProfile)
                        }, modifier = Modifier.padding(10.dp)) {
                            Icon(painter = rememberVectorPainter(Icons.Outlined.Person), contentDescription = "Account", tint = Color.Black)
                        }
                    }
                }


            }
        }

       if(called==false){
           getHotels(context)
           getServices(context)
           getServicesRoom(context)
           getInventory(context)

           getCities(context)
           called=true
       }
//        AuthorizationDABS(context){
//
//        }

    }


    enum class SortOrder {
        Ascending,
        Descending;

        fun toggle(): SortOrder {
            return if (this == Ascending) Descending else Ascending
        }
    }
    @Composable
    fun MyRow(row: com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel, context: Context){
        val navigatorOut = LocalNavigator.currentOrThrow
        Box(modifier = Modifier
            .padding(vertical = 10.dp)
            .border(0.3.dp, Color.Gray, RoundedCornerShape(5.dp))){
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ){
                Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){
                    Text(text = row.id.toString())
                }

                Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                    if(row.images[0]==""){
                        Image(
                            painter = rememberAsyncImagePainter("https://cdn4.iconfinder.com/data/icons/social-messaging-ui-color-squares-01/3/30-512.png"),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(
                                    RoundedCornerShape(
                                        (CornerSize(
                                            10.dp
                                        ))
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    else{
                        Image(
                            painter = rememberAsyncImagePainter(row.images[0]),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(
                                    RoundedCornerShape(
                                        (CornerSize(
                                            10.dp
                                        ))
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                }

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.name)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.name)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.description)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){

                }
            }
        }
    }

    // GET Categories Function
    fun getInventory(context: Context) {
        if(serviceCategories.isNotEmpty()){
            return
        }
        val url = "${GlobalStrings.baseURL}customer/inventories/getInventories"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Inventories...")
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
                Request.Method.GET, url, ViewInventory.params,
                { response ->
                    Log.d("HAPP",response.toString())
                    var categories  = response.getJSONArray("inventories")
                    serviceCategories.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)

                    //    var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var serviceName = category.getString("name")
                        var servicecategory = category.getString("category")
                        var servicedescription = category.getString("description")
                        var servicecolor = category.getString("color")
                        var serviceCa = category.getJSONObject("inventoryCategory")
                        var serviceCategoryID = serviceCa.getString("_id")

                        serviceCategories.add(
                            ViewInventory.TableRow(
                                _id = _id,
                                id = 0,
                                serviceImage = serviceImage,
                                serviceName = serviceName,
                                serviceType = servicecolor,
                                serviceRate = "servicepriceRate",
                                servicePrice = "serviceprice",
                                serviceDescription = servicedescription,
                                serviceCategory = servicecategory,
                                serviceCategoryID = serviceCategoryID
                            )
                        )
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    serviceCategories.clear()
                    Log.d("HASHDASDAS11",error.toString())
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
    // GET Categories Function
    fun getServices(context: Context) {
        if(services.isNotEmpty()){
            return
        }
        val url = "${GlobalStrings.baseURL}customer/services/getHotelServices"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Services...")
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
                Request.Method.GET, url, ViewService.params,
                { response ->
                    Log.d("HAPP",response.toString())
                    var categories  = response.getJSONArray("services")
                    services.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)

                       // var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var serviceName = category.getString("name")
                        //var servicecategory = category.getString("category")
                        var servicedescription = category.getString("description")
                        var serviceprice = category.getInt("price")
                        var servicepriceRate = category.getString("priceRate")
                        var serviceaddedByRole = category.getString("addedByRole")
                        var servicevisible = category.getBoolean("visible")
                        var serviceisDeleted = category.getBoolean("isDeleted")
                        var servicedeletedAt = category.getString("deletedAt")
                        var servicecreatedAt = category.getString("createdAt")
                        var serviceupdatedAt = category.getString("updatedAt")
                        var service__v = category.getInt("__v")
                        var serviceType = category.getString("type")
                        var serviceCa = category.getJSONObject("serviceCategory")
                        var serviceCategoryID = serviceCa.getString("_id")
                        var serviceCategoryTitle = serviceCa.getString("title")
                        var serviceCategoryImage = serviceCa.getString("image")
                        var serviceCategoryDeletedAt = serviceCa.getString("deletedAt")
                        var serviceCategoryIsDeleted = serviceCa.getBoolean("isDeleted")
                        var serviceCategoryCreatedAt = serviceCa.getString("createdAt")
                        var serviceCategoryUpdatedAt = serviceCa.getString("updatedAt")
                        var serviceCategory__V = serviceCa.getInt("__v")
                        var serviceCategoryObject =
                            ServiceCategory(
                                _id = serviceCategoryID,
                                title = serviceCategoryTitle,
                                image = serviceCategoryImage,
                                isDeleted = serviceCategoryIsDeleted,
                                deletedAt = serviceCategoryDeletedAt,
                                createdAt = serviceCategoryCreatedAt,
                                updatedAt = serviceCategoryUpdatedAt,
                                __v = serviceCategory__V
                            )
                        services.add(
                            Service(
                                _id = _id,
                                serviceCategory = serviceCategoryObject,
                                type = serviceType,
                                name = serviceName,
                                description = servicedescription,
                                image = serviceImage,
                                priceRate = servicepriceRate,
                                price = serviceprice,
                                addedByRole = serviceaddedByRole,
                                visible = servicevisible,
                                isDeleted = serviceisDeleted,
                                deletedAt = servicedeletedAt,
                                createdAt = servicecreatedAt,
                                updatedAt = serviceupdatedAt,
                                __v = service__v
                            )
                        )
                    }
                    progressDialog.dismiss()

                },
                { error ->
                    services.clear()
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

    // GET Categories Function
    fun getServicesRoom(context: Context) {
        if(servicesRoom.isNotEmpty()){
            return
        }
        val url = "${GlobalStrings.baseURL}customer/services/getRoomServices"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Services...")
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
                Request.Method.GET, url, ViewService.params,
                { response ->
                    Log.d("HAPP",response.toString())
                    var categories  = response.getJSONArray("services")
                    servicesRoom.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)

                        // var id = category.getInt("id")
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("image")
                        var serviceName = category.getString("name")
                        //var servicecategory = category.getString("category")
                        var servicedescription = category.getString("description")
                        var serviceprice = category.getInt("price")
                        var servicepriceRate = category.getString("priceRate")
                        var serviceaddedByRole = category.getString("addedByRole")
                        var servicevisible = category.getBoolean("visible")
                        var serviceisDeleted = category.getBoolean("isDeleted")
                        var servicedeletedAt = category.getString("deletedAt")
                        var servicecreatedAt = category.getString("createdAt")
                        var serviceupdatedAt = category.getString("updatedAt")
                        var service__v = category.getInt("__v")
                        var serviceType = category.getString("type")
                        var serviceCa = category.getJSONObject("serviceCategory")
                        var serviceCategoryID = serviceCa.getString("_id")
                        var serviceCategoryTitle = serviceCa.getString("title")
                        var serviceCategoryImage = serviceCa.getString("image")
                        var serviceCategoryDeletedAt = serviceCa.getString("deletedAt")
                        var serviceCategoryIsDeleted = serviceCa.getBoolean("isDeleted")
                        var serviceCategoryCreatedAt = serviceCa.getString("createdAt")
                        var serviceCategoryUpdatedAt = serviceCa.getString("updatedAt")
                        var serviceCategory__V = serviceCa.getInt("__v")
                        var serviceCategoryObject =
                            ServiceCategory(
                                _id = serviceCategoryID,
                                title = serviceCategoryTitle,
                                image = serviceCategoryImage,
                                isDeleted = serviceCategoryIsDeleted,
                                deletedAt = serviceCategoryDeletedAt,
                                createdAt = serviceCategoryCreatedAt,
                                updatedAt = serviceCategoryUpdatedAt,
                                __v = serviceCategory__V
                            )
                        servicesRoom.add(
                            Service(
                                _id = _id,
                                serviceCategory = serviceCategoryObject,
                                type = serviceType,
                                name = serviceName,
                                description = servicedescription,
                                image = serviceImage,
                                priceRate = servicepriceRate,
                                price = serviceprice,
                                addedByRole = serviceaddedByRole,
                                visible = servicevisible,
                                isDeleted = serviceisDeleted,
                                deletedAt = servicedeletedAt,
                                createdAt = servicecreatedAt,
                                updatedAt = serviceupdatedAt,
                                __v = service__v
                            )
                        )
                    }
                    progressDialog.dismiss()

                },
                { error ->
                    servicesRoom.clear()
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
    // GET Hotels Function
    fun getHotels(context: Context) {
        if(Hotels.isNotEmpty()){
            return
        }
        val url = "${GlobalStrings.baseURL}customer/hotels/getHotels"
        val progressDialog = ProgressDialog(context)
        val params = JSONObject()
        progressDialog.setTitle("Loading Hotels...")
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
                Request.Method.GET, url, params,
                { response ->
                    Hotels.clear()
                    Log.d("HAPP",response.toString())
                    var hotels  = response.getJSONArray("hotels")
                    for(i in 0 until hotels.length()){
                        var hotel = hotels.getJSONObject(i)
                        var ServiceList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service>()
                        var VideoList = mutableStateListOf<String>()
                        var FloorList = mutableStateListOf<String>()
                        var RulesList = mutableStateListOf<String>()
                        var ReviewsList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review>()
                        var RefundList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Refund>()
                        var ImageList = mutableStateListOf<String>()
                        var RoomsList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room>()
                        var userID = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Userid(__v = 0, _id = "hotelOwner_id", cnic = "hotelOwneruseridcnic",
                            contactNo = "hotelOwneruseridcontactNo", createdAt = "hotelOwneruseridcreatedAt", deletedAt = "hotelOwneruseriddeletedAt",
                            email = "hotelOwneruseridemail", firstName = "hotelOwneruseridfirstName", isDeleted = false,
                            lastName = "hotelOwneruseridlastName", password = "hotelOwneruseridpassword", profilePicture = "hotelOwneruseridprofilePicture",
                            role = "hotelOwneruseridrole", status = "hotelOwneruseridstatus", updatedAt = "hotelOwneruseridupdatedAt", userCategory = "hotelOwneruseriduserCategory",
                            verified = true)
                        var hotelOwnerOBJ = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.HotelOwner(__v = 0, _id = "hotelOwner_id", createdAt = "hotelOwnercreatedAt",
                            deletedAt = "hotelOwnerdeletedAt", isDeleted = false, updatedAt = "hotelOwnerupdatedAt",
                            userid = userID)


                        var _id = hotel.getString("_id")
                       // var id = hotel.getString("id")
                        var hotelname = hotel.getString("name")
                        var hoteldescription = hotel.getString("description")
                        var hotellocation = hotel.getString("location")
                        var hotellat = hotel.getDouble("lat")
                        var hotellng = hotel.getDouble("lng")
                        var hotelcity = hotel.getString("city")
                        var hotelwebsiteURL = hotel.getString("websiteURL")
                        var hotelstatus = hotel.getString("status")
                        var hotelinstagramURL = hotel.getString("instagramURL")
                        var hotelfacebookURL = hotel.getString("facebookURL")
                        var hotelcreatedAt = hotel.getString("createdAt")
                        var hotelupdatedAt = hotel.getString("updatedAt")
                        var hotelisDeleted = hotel.getBoolean("isDeleted")
                        var hoteldeletedAt = hotel.get("deletedAt")
                        var hotel__v = hotel.getInt("__v")
                        var hotelaverageRating = hotel.get("averageRating").toString()
                        var hoteltotalReviews = hotel.getInt("totalReviews")
                        var hotelaveragePrice = hotel.getInt("averagePrice")
                      //  var hotelid = hotel.getInt("id")
                        //var hotelownerName = hotel.getString("ownerName")
                        var hotelphoneNo = hotel.getString("phoneNo")
                        var hoteltelephoneNo = hotel.getString("telephoneNo")

                        var hotelimages = hotel.getJSONArray("images")
                        for(i in 0 until hotelimages.length()){
                            var hotelImage = hotelimages.getString(i)
                            ImageList.add(hotelImage)
                        }

                        var hotelvideos = hotel.getJSONArray("videos")
                        for(i in 0 until hotelvideos.length()){
                            var hotelVideoList = hotelvideos.getString(i)
                            VideoList.add(hotelVideoList)
                        }

                        var hotelrules = hotel.getJSONArray("rules")
                        for(i in 0 until hotelrules.length()){
                            var hotelRulesList = hotelrules.getString(i)
                            RulesList.add(hotelRulesList)
                        }
                        var hotelreviews = hotel.getJSONArray("reviews")
                        for(i in 0 until hotelreviews.length()){
                            var reviewOne = hotelreviews.getJSONObject(i)
                            var review_id = reviewOne.getString("_id")
                            var customer = reviewOne.getString("customer")
                            var CC = Customer(name = "AA", profile = "aaas")
                            var title = reviewOne.getString("title")
                            var description = reviewOne.getString("description")
                            var isDeleted = reviewOne.getBoolean("isDeleted")
                            var deletedAt = reviewOne.get("deletedAt")
                            var stars = reviewOne.getInt("stars")
                            var createdAt = reviewOne.getString("createdAt")
                            var updatedAt = reviewOne.getString("updatedAt")
                            var __v = reviewOne.getInt("__v")
                            var Revieww = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review(__v = __v,_id=review_id,
                                createdAt=createdAt,customer=CC,deletedAt=deletedAt, description = description, isDeleted = isDeleted, stars = stars,title=title, updatedAt = updatedAt)
                            ReviewsList.add(Revieww)
                        }
                        var hotelfloors = hotel.getJSONArray("floors")
                        for(i in 0 until hotelfloors.length()){
                            var hotelFloorList = hotelfloors.getString(i)
                            FloorList.add(hotelFloorList)
                        }

                        var hotelservices = hotel.getJSONArray("services")
                        for(i in 0 until hotelservices.length()){
                            var serviceOBJ = hotelservices.getJSONObject(i)
                            var hotelservices_id = serviceOBJ.getString("_id")
                            var hotelservicestype = serviceOBJ.getString("type")
                            var hotelservicesname = serviceOBJ.getString("name")
                            var hotelservicesdescription = serviceOBJ.getString("description")
                            var hotelservicesimage = serviceOBJ.getString("image")
                            var hotelservicespriceRate = serviceOBJ.getString("priceRate")
                            var hotelservicesprice = serviceOBJ.getInt("price")
                            var hotelservicesaddedByRole = serviceOBJ.getString("addedByRole")
                            var hotelservicesdeletedAt = serviceOBJ.get("deletedAt")
                            var hotelservicesvisible = serviceOBJ.getBoolean("visible")
                            var hotelservicesisDeleted = serviceOBJ.getBoolean("isDeleted")
                            var hotelservicescreatedAt = serviceOBJ.getString("createdAt")
                            var hotelservicesupdatedAt = serviceOBJ.getString("updatedAt")
                            var hotelservices__v = serviceOBJ.getInt("__v")
//                            var hotelservicesserviceCategory = serviceOBJ.getJSONObject("serviceCategory")
//                            var hotelservicesserviceCategory_id = hotelservicesserviceCategory.getString("_id")
//                            var hotelservicesserviceCategorytitle = hotelservicesserviceCategory.getString("title")
//                            var hotelservicesserviceCategoryimage = hotelservicesserviceCategory.getString("image")
//                            var hotelservicesserviceCategoryisDeleted = hotelservicesserviceCategory.getBoolean("isDeleted")
//                            var hotelservicesserviceCategorydeletedAt = hotelservicesserviceCategory.get("deletedAt")
//                            var hotelservicesserviceCategorycreatedAt = hotelservicesserviceCategory.getString("createdAt")
//                            var hotelservicesserviceCategoryupdatedAt = hotelservicesserviceCategory.getString("updatedAt")
//                            var hotelservicesserviceCategory__v = hotelservicesserviceCategory.getInt("__v")
                            var cat = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.ServiceCategory(__v = 0,_id="hotelservicesserviceCategory_id", createdAt = "hotelservicesserviceCategorycreatedAt",
                                deletedAt ="hotelservicesserviceCategorydeletedAt", image = "hotelservicesserviceCategoryimage", isDeleted = false,
                                title = "hotelservicesserviceCategorytitle", updatedAt = "hotelservicesserviceCategoryupdatedAt")
                            var ser = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service(__v = hotelservices__v,_id=hotelservices_id, addedByRole = hotelservicesaddedByRole,
                                createdAt = hotelservicescreatedAt, deletedAt = hotelservicesdeletedAt, description = hotelservicesdescription,
                                image = hotelservicesimage,isDeleted = hotelservicesisDeleted, name = hotelservicesname,
                                price = hotelservicesprice, priceRate = hotelservicespriceRate, type = hotelservicestype,
                                updatedAt = hotelservicesupdatedAt, visible = hotelservicesvisible, serviceCategory = cat)
                            ServiceList.add(ser)
                        }


                        var hotelrefundPolicy = hotel.getJSONObject("refundPolicy")
                        var hotelrefundPolicy_id = hotelrefundPolicy.getString("_id")
                        var hotelrefundPolicydescription = hotelrefundPolicy.getString("description")
                        var hotelrefundPolicyrefunds = hotelrefundPolicy.getJSONArray("refunds")
                        for(i in 0 until hotelrefundPolicyrefunds.length()){
                            var refundsOBJ = hotelrefundPolicyrefunds.getJSONObject(i)
                            var refundsOBJdays = refundsOBJ.getInt("days")
                            var refundsOBJpercentage = refundsOBJ.getInt("percentage")
                            var refundsOBJ_id = refundsOBJ.getString("_id")
                            var refundsOBJisDeleted = refundsOBJ.getBoolean("isDeleted")
                            var refundsOBJdeletedAt = refundsOBJ.get("deletedAt")
                            var refundOBJ = Refund(_id=refundsOBJ_id, days = refundsOBJdays, deletedAt = refundsOBJdeletedAt,
                                isDeleted = refundsOBJisDeleted, percentage = refundsOBJpercentage)
                            RefundList.add(refundOBJ)

                        }

                        var hotelrefundPolicyisDeleted = hotelrefundPolicy.getBoolean("isDeleted")
                        var hotelrefundPolicydeletedAt = hotelrefundPolicy.get("deletedAt")
                        var hotelrefundPolicycreatedAt = hotelrefundPolicy.getString("createdAt")
                        var hotelrefundPolicyupdatedAt = hotelrefundPolicy.getString("updatedAt")
                        var hotelrefundPolicy__v = hotelrefundPolicy.getInt("__v")
//                        var RefundPolicy = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy(__v = 0,_id="hotelrefundPolicy_id", createdAt = "hotelrefundPolicycreatedAt",
//                            deletedAt = "hotelrefundPolicydeletedAt", description = "hotelrefundPolicydescription", isDeleted = false,
//                            refunds = RefundList, updatedAt = "hotelrefundPolicyupdatedAt")
                        var RefundPolicy = RefundPolicy(__v = hotelrefundPolicy__v,_id=hotelrefundPolicy_id, createdAt = hotelrefundPolicycreatedAt,
                            deletedAt = hotelrefundPolicydeletedAt, description = hotelrefundPolicydescription, isDeleted = hotelrefundPolicyisDeleted,
                            refunds = RefundList, updatedAt = hotelrefundPolicyupdatedAt)
                        try{
                            var rooms = hotel.getJSONArray("room")
                            for(i in 0 until rooms.length()){
                                var room = rooms.getJSONObject(i)
                                var _id = room.getString("_id")
                                var floor = room.getString("floor")
                                var roomNumber = room.getString("roomNumber")
                                var type = room.getString("type")
                                var description = room.getString("description")
                                var images = room.getJSONArray("images")
                                var imglist = mutableStateListOf<String>()
                                for(i in 0 until images.length()){

                                    var img = images.getString(i)
                                    imglist.add(img)
                                }
                                var videos = room.getJSONArray("videos")
                                var vidlist = mutableStateListOf<String>()
                                for(i in 0 until videos.length()){

                                    var img = videos.getString(i)
                                    vidlist.add(img)
                                }
                                var adults = room.getInt("adults")
                                var children = room.getInt("children")
                                var price = room.getInt("price")
                                var size = room.getString("size")
                                var Room = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room(_id=_id,
                                    hotel="asd",floor=floor,roomNumber=roomNumber,type=type,description=description,
                                    images=imglist,videos=vidlist,adults=adults,children=children,price=price,size=size,
                                    panoramicView = null, roomServices = imglist, inventories =imglist,isDeleted = false,deletedAt = null, models = imglist, createdAt = "asdasd", updatedAt = "Asd", __v = 0 )
                                RoomsList.add(Room)
                            }

                        }
                        catch(e:Exception){
                            e.printStackTrace()
                        }
                        var avgrat = 0
                        if(hotelaverageRating=="null"){
                            avgrat=0
                        }
                        else{
                            avgrat=hotelaverageRating.toInt()
                        }

                        var Hotel = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel(__v = hotel__v,_id=_id, averagePrice = hotelaveragePrice, averageRating = avgrat,
                            city = hotelcity, createdAt = hotelcreatedAt, deletedAt = hoteldeletedAt, description = hoteldescription,
                            facebookURL = hotelfacebookURL, floors = FloorList, hotelOwner = hotelOwnerOBJ, id = 0, images = ImageList,
                            instagramURL = hotelinstagramURL, isDeleted = hotelisDeleted, lat = hotellat, lng = hotellng, location = hotellocation,
                            name = hotelname, ownerName = "hotelownerName", phoneNo = hotelphoneNo, refundPolicy =RefundPolicy, reviews = ReviewsList,
                            rules = RulesList, services = ServiceList, status = hotelstatus, telephoneNo = hoteltelephoneNo, totalReviews = hoteltotalReviews,
                            updatedAt = hotelupdatedAt, videos = VideoList, websiteURL = hotelwebsiteURL, rooms = RoomsList)

                        Hotels.add(Hotel)

                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS222",error.toString())
                    Hotels.clear()
                    HotelJsonArrayError = mutableStateOf(error.toString())
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

    // GET Hotels Function
    fun getCities(context: Context) {
        if(Hotels.isNotEmpty()){
            return
        }
        val url = "${GlobalStrings.baseURL}customer/city/getCityWiseHotels"
        val progressDialog = ProgressDialog(context)
        val params = JSONObject()
        progressDialog.setTitle("Loading Hotels...")
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
                Request.Method.GET, url, params,
                { response ->
                    Log.d("HAPP",response.toString())
                    var hotels  = response.getJSONArray("hotels")
                    for(i in 0 until hotels.length()){
                        var hotel = hotels.getJSONObject(i)
                        var city = hotel.getString("_id")
                        var image = hotel.getString("image")
                        var count = hotel.getInt("count")
                        var obj = CitySchema(city=city,hotels=count.toString(),image=image)
                        Cities.add(obj)
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS222",error.toString())
                    Cities.clear()
                    HotelJsonArrayError = mutableStateOf(error.toString())
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

    fun AuthorizationDABS(context: Context, callback: (Boolean) -> Unit) {
        if(UserDABS.isNotEmpty()){
//            if(UserDABS[0].profilePicture=="")
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

    // GET Hotels Function
    fun filterHotels(context: Context,minPrice:Int,maxPrice:Int,rating:String,city: String,hotelservices:MutableList<String>,roomservices:MutableList<String>,
                     roominventory:MutableList<String>,meals:String,page:Int) {
        val search = JSONObject()
        val filters = JSONObject()
        val hotelServices = JSONArray()
        val roomServices = JSONArray()
        val inventories = JSONArray()
        if(minPrice<299){
            filters.put("minPrice",300)
        }
        else{
            filters.put("minPrice",minPrice)
        }
        filters.put("maxPrice",maxPrice)
        if(rating=="All"){
            filters.put("rating",rating)
        }
        else{
            filters.put("rating",rating.substring(0,1).toInt())
        }
        if(meals=="Yes"){
            filters.put("menu",true)
        }
        else if(meals=="No"){
            filters.put("menu",false)
        }
        for(a in hotelservices){
            hotelServices.put(a)
        }
        for(a in roomservices){
            roomServices.put(a)
        }
        for(a in roominventory){
            inventories.put(a)
        }
        if(hotelservices.isNotEmpty()){
            filters.put("hotelServices",hotelServices)
        }
        if(roomservices.isNotEmpty()){
            filters.put("roomServices",roomServices)
        }
        if(roominventory.isNotEmpty()){
            filters.put("inventories",inventories)
        }
        search.put("city",city)
        val sort = JSONObject()
        Log.d("HAPP",filters.toString())
        val url = "${GlobalStrings.baseURL}customer/hotels/searchHotels/${search}/${filters}/${sort}/${page}"
        Log.d("HAPP",url.toString())
        val progressDialog = ProgressDialog(context)
        val params = JSONObject()
        progressDialog.setTitle("Loading Hotels...")
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
                Request.Method.GET, url, params,
                { response ->
                    HotelFilter.clear()
                    Log.d("HAPP",response.toString())
                    var hotels  = response.getJSONArray("hotels")
                    for(i in 0 until hotels.length()){
                        var hotel = hotels.getJSONObject(i)
                        var ServiceList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service>()
                        var VideoList = mutableStateListOf<String>()
                        var FloorList = mutableStateListOf<String>()
                        var RulesList = mutableStateListOf<String>()
                        var ReviewsList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review>()
                        var RefundList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Refund>()
                        var ImageList = mutableStateListOf<String>()
                        var RoomsList = mutableStateListOf<com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room>()

//                        var hotelOwner = hotel.getJSONObject("hotelOwner")
//                        var hotelOwner_id = hotelOwner.getString("_id")
//                        var hotelOwneruserid = hotelOwner.getJSONObject("userid")
//                        var hotelOwneruseriduserCategory= hotelOwneruserid.getString("userCategory")
//                        var hotelOwneruseriduser_id= hotelOwneruserid.getString("_id")
//                        var hotelOwneruseridfirstName = hotelOwner.getString("firstName")
//                        var hotelOwneruseridlastName = hotelOwner.getString("lastName")
//                        var hotelOwneruseridemail = hotelOwneruserid.getString("email")
//                        var hotelOwneruseridpassword = hotelOwneruserid.getString("password")
//                        var hotelOwneruseridcontactNo = hotelOwneruserid.getString("contactNo")
//                        var hotelOwneruseridcnic = hotelOwneruserid.getString("cnic")
//                        var hotelOwneruseridprofilePicture = hotelOwneruserid.getString("profilePicture")
//                        var hotelOwneruseridstatus = hotelOwneruserid.getString("status")
//                        var hotelOwneruseridrole = hotelOwneruserid.getString("role")
//                        var hotelOwneruseridverified = hotelOwneruserid.getBoolean("verified")
//                        var hotelOwneruseridisDeleted = hotelOwneruserid.getBoolean("isDeleted")
//                        var hotelOwneruseriddeletedAt = hotelOwneruserid.get("deletedAt")
//                        var hotelOwneruseridcreatedAt = hotelOwneruserid.getString("createdAt")
//                        var hotelOwneruseridupdatedAt = hotelOwneruserid.getString("updatedAt")
//                        var hotelOwneruserid__v = hotelOwneruserid.getInt("__v")
//                        var hotelOwnerisDeleted = hotelOwner.get("isDeleted")
//                        var hotelOwnerdeletedAt = hotelOwner.get("deletedAt")
//                        var hotelOwnercreatedAt = hotelOwner.getString("createdAt")
//                        var hotelOwnerupdatedAt = hotelOwner.getString("updatedAt")
//                        var hotelOwner__v = hotelOwner.getInt("__v")
                        var userID = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Userid(__v = 0, _id = "hotelOwner_id", cnic = "hotelOwneruseridcnic",
                            contactNo = "hotelOwneruseridcontactNo", createdAt = "hotelOwneruseridcreatedAt", deletedAt = "hotelOwneruseriddeletedAt",
                            email = "hotelOwneruseridemail", firstName = "hotelOwneruseridfirstName", isDeleted = false,
                            lastName = "hotelOwneruseridlastName", password = "hotelOwneruseridpassword", profilePicture = "hotelOwneruseridprofilePicture",
                            role = "hotelOwneruseridrole", status = "hotelOwneruseridstatus", updatedAt = "hotelOwneruseridupdatedAt", userCategory = "hotelOwneruseriduserCategory",
                            verified = true)
                        var hotelOwnerOBJ = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.HotelOwner(__v = 0, _id = "hotelOwner_id", createdAt = "hotelOwnercreatedAt",
                            deletedAt = "hotelOwnerdeletedAt", isDeleted = false, updatedAt = "hotelOwnerupdatedAt",
                            userid = userID)


                        var _id = hotel.getString("_id")
                        // var id = hotel.getString("id")
                        var hotelname = hotel.getString("name")
                        var hoteldescription = hotel.getString("description")
                        var hotellocation = hotel.getString("location")
                        var hotellat = hotel.getDouble("lat")
                        var hotellng = hotel.getDouble("lng")
                        var hotelcity = hotel.getString("city")
                        var hotelwebsiteURL = hotel.getString("websiteURL")
                        var hotelstatus = hotel.getString("status")
                        var hotelinstagramURL = hotel.getString("instagramURL")
                        var hotelfacebookURL = hotel.getString("facebookURL")
                        var hotelcreatedAt = hotel.getString("createdAt")
                        var hotelupdatedAt = hotel.getString("updatedAt")
                        var hotelisDeleted = hotel.getBoolean("isDeleted")
                        var hoteldeletedAt = hotel.get("deletedAt")
                        var hotel__v = hotel.getInt("__v")
                          var hotelaverageRating = hotel.get("averageRating").toString()
                        var hoteltotalReviews = hotel.getInt("totalReviews")
                        var hotelaveragePrice = hotel.getInt("averagePrice")
                        //  var hotelid = hotel.getInt("id")
                        //var hotelownerName = hotel.getString("ownerName")
                        var hotelphoneNo = hotel.getString("phoneNo")
                        var hoteltelephoneNo = hotel.getString("telephoneNo")

                        var hotelimages = hotel.getJSONArray("images")
                        for(i in 0 until hotelimages.length()){
                            var hotelImage = hotelimages.getString(i)
                            ImageList.add(hotelImage)
                        }

                        var hotelvideos = hotel.getJSONArray("videos")
                        for(i in 0 until hotelvideos.length()){
                            var hotelVideoList = hotelvideos.getString(i)
                            VideoList.add(hotelVideoList)
                        }

                        var hotelrules = hotel.getJSONArray("rules")
                        for(i in 0 until hotelrules.length()){
                            var hotelRulesList = hotelrules.getString(i)
                            RulesList.add(hotelRulesList)
                        }
                        var hotelreviews = hotel.getJSONArray("reviews")
                        for(i in 0 until hotelreviews.length()){
                            var reviewOne = hotelreviews.getJSONObject(i)
                            var review_id = reviewOne.getString("_id")
                            var customer = reviewOne.getString("customer")
                            var CC = Customer(name = "AA", profile = "aaas")
                            var title = reviewOne.getString("title")
                            var description = reviewOne.getString("description")
                            var isDeleted = reviewOne.getBoolean("isDeleted")
                            var deletedAt = reviewOne.get("deletedAt")
                            var stars = reviewOne.getInt("stars")
                            var createdAt = reviewOne.getString("createdAt")
                            var updatedAt = reviewOne.getString("updatedAt")
                            var __v = reviewOne.getInt("__v")
                            var Revieww = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Review(__v = __v,_id=review_id,
                                createdAt=createdAt,customer=CC,deletedAt=deletedAt, description = description, isDeleted = isDeleted, stars = stars,title=title, updatedAt = updatedAt)
                            ReviewsList.add(Revieww)
                        }
                        var hotelfloors = hotel.getJSONArray("floors")
                        for(i in 0 until hotelfloors.length()){
                            var hotelFloorList = hotelfloors.getString(i)
                            FloorList.add(hotelFloorList)
                        }

                        var hotelservices = hotel.getJSONArray("services")
                        for(i in 0 until hotelservices.length()){
                            var serviceOBJ = hotelservices.getJSONObject(i)
                            var hotelservices_id = serviceOBJ.getString("_id")
                            var hotelservicestype = serviceOBJ.getString("type")
                            var hotelservicesname = serviceOBJ.getString("name")
                            var hotelservicesdescription = serviceOBJ.getString("description")
                            var hotelservicesimage = serviceOBJ.getString("image")
                            var hotelservicespriceRate = serviceOBJ.getString("priceRate")
                            var hotelservicesprice = serviceOBJ.getInt("price")
                            var hotelservicesaddedByRole = serviceOBJ.getString("addedByRole")
                            var hotelservicesdeletedAt = serviceOBJ.get("deletedAt")
                            var hotelservicesvisible = serviceOBJ.getBoolean("visible")
                            var hotelservicesisDeleted = serviceOBJ.getBoolean("isDeleted")
                            var hotelservicescreatedAt = serviceOBJ.getString("createdAt")
                            var hotelservicesupdatedAt = serviceOBJ.getString("updatedAt")
                            var hotelservices__v = serviceOBJ.getInt("__v")


//                            var hotelservicesserviceCategory = serviceOBJ.getJSONObject("serviceCategory")
//                            var hotelservicesserviceCategory_id = hotelservicesserviceCategory.getString("_id")
//                            var hotelservicesserviceCategorytitle = hotelservicesserviceCategory.getString("title")
//                            var hotelservicesserviceCategoryimage = hotelservicesserviceCategory.getString("image")
//                            var hotelservicesserviceCategoryisDeleted = hotelservicesserviceCategory.getBoolean("isDeleted")
//                            var hotelservicesserviceCategorydeletedAt = hotelservicesserviceCategory.get("deletedAt")
//                            var hotelservicesserviceCategorycreatedAt = hotelservicesserviceCategory.getString("createdAt")
//                            var hotelservicesserviceCategoryupdatedAt = hotelservicesserviceCategory.getString("updatedAt")
//                            var hotelservicesserviceCategory__v = hotelservicesserviceCategory.getInt("__v")
                            var cat = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.ServiceCategory(__v = 0,_id="hotelservicesserviceCategory_id", createdAt = "hotelservicesserviceCategorycreatedAt",
                                deletedAt ="hotelservicesserviceCategorydeletedAt", image = "hotelservicesserviceCategoryimage", isDeleted = false,
                                title = "hotelservicesserviceCategorytitle", updatedAt = "hotelservicesserviceCategoryupdatedAt")
                            var ser = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Service(__v = hotelservices__v,_id=hotelservices_id, addedByRole = hotelservicesaddedByRole,
                                createdAt = hotelservicescreatedAt, deletedAt = hotelservicesdeletedAt, description = hotelservicesdescription,
                                image = hotelservicesimage,isDeleted = hotelservicesisDeleted, name = hotelservicesname,
                                price = hotelservicesprice, priceRate = hotelservicespriceRate, type = hotelservicestype,
                                updatedAt = hotelservicesupdatedAt, visible = hotelservicesvisible, serviceCategory = cat)
                            ServiceList.add(ser)
                        }


                        var hotelrefundPolicy = hotel.getJSONObject("refundPolicy")
                        var hotelrefundPolicy_id = hotelrefundPolicy.getString("_id")
                        var hotelrefundPolicydescription = hotelrefundPolicy.getString("description")
                        var hotelrefundPolicyrefunds = hotelrefundPolicy.getJSONArray("refunds")
                        for(i in 0 until hotelrefundPolicyrefunds.length()){
                            var refundsOBJ = hotelrefundPolicyrefunds.getJSONObject(i)
                            var refundsOBJdays = refundsOBJ.getInt("days")
                            var refundsOBJpercentage = refundsOBJ.getInt("percentage")
                            var refundsOBJ_id = refundsOBJ.getString("_id")
                            var refundsOBJisDeleted = refundsOBJ.getBoolean("isDeleted")
                            var refundsOBJdeletedAt = refundsOBJ.get("deletedAt")
                            var refundOBJ = Refund(_id=refundsOBJ_id, days = refundsOBJdays, deletedAt = refundsOBJdeletedAt,
                                isDeleted = refundsOBJisDeleted, percentage = refundsOBJpercentage)
                            RefundList.add(refundOBJ)

                        }

                        var hotelrefundPolicyisDeleted = hotelrefundPolicy.getBoolean("isDeleted")
                        var hotelrefundPolicydeletedAt = hotelrefundPolicy.get("deletedAt")
                        var hotelrefundPolicycreatedAt = hotelrefundPolicy.getString("createdAt")
                        var hotelrefundPolicyupdatedAt = hotelrefundPolicy.getString("updatedAt")
                        var hotelrefundPolicy__v = hotelrefundPolicy.getInt("__v")
//                        var RefundPolicy = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.RefundPolicy(__v = 0,_id="hotelrefundPolicy_id", createdAt = "hotelrefundPolicycreatedAt",
//                            deletedAt = "hotelrefundPolicydeletedAt", description = "hotelrefundPolicydescription", isDeleted = false,
//                            refunds = RefundList, updatedAt = "hotelrefundPolicyupdatedAt")
                        var RefundPolicy = RefundPolicy(__v = hotelrefundPolicy__v,_id=hotelrefundPolicy_id, createdAt = hotelrefundPolicycreatedAt,
                            deletedAt = hotelrefundPolicydeletedAt, description = hotelrefundPolicydescription, isDeleted = hotelrefundPolicyisDeleted,
                            refunds = RefundList, updatedAt = hotelrefundPolicyupdatedAt)
                        try{
                            var rooms = hotel.getJSONArray("room")
                            for(i in 0 until rooms.length()){
                                var room = rooms.getJSONObject(i)
                                var _id = room.getString("_id")
                                var floor = room.getString("floor")
                                var roomNumber = room.getString("roomNumber")
                                var type = room.getString("type")
                                var description = room.getString("description")
                                var images = room.getJSONArray("images")
                                var imglist = mutableStateListOf<String>()
                                for(i in 0 until images.length()){

                                    var img = images.getString(i)
                                    imglist.add(img)
                                }
                                var videos = room.getJSONArray("videos")
                                var vidlist = mutableStateListOf<String>()
                                for(i in 0 until videos.length()){

                                    var img = videos.getString(i)
                                    vidlist.add(img)
                                }
                                var adults = room.getInt("adults")
                                var children = room.getInt("children")
                                var price = room.getInt("price")
                                var size = room.getString("size")
                                var Room = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Room(_id=_id,
                                    hotel="asd",floor=floor,roomNumber=roomNumber,type=type,description=description,
                                    images=imglist,videos=vidlist,adults=adults,children=children,price=price,size=size,
                                    panoramicView = null, roomServices = imglist, inventories =imglist,isDeleted = false,deletedAt = null, models = imglist, createdAt = "asdasd", updatedAt = "Asd", __v = 0 )
                                RoomsList.add(Room)
                            }

                        }
                        catch(e:Exception){
                            e.printStackTrace()
                        }
                        var avgrat = 0
                        if(hotelaverageRating=="null"){
                            avgrat=0
                        }
                        else{
                            avgrat=hotelaverageRating.toInt()
                        }
                        var Hotel = com.example.adminoffice.ui.theme.Customer.DataClassesCustomer.Hotel(__v = hotel__v,_id=_id, averagePrice = hotelaveragePrice, averageRating = avgrat,
                            city = hotelcity, createdAt = hotelcreatedAt, deletedAt = hoteldeletedAt, description = hoteldescription,
                            facebookURL = hotelfacebookURL, floors = FloorList, hotelOwner = hotelOwnerOBJ, id = 0, images = ImageList,
                            instagramURL = hotelinstagramURL, isDeleted = hotelisDeleted, lat = hotellat, lng = hotellng, location = hotellocation,
                            name = hotelname, ownerName = "hotelownerName", phoneNo = hotelphoneNo, refundPolicy =RefundPolicy, reviews = ReviewsList,
                            rules = RulesList, services = ServiceList, status = hotelstatus, telephoneNo = hoteltelephoneNo, totalReviews = hoteltotalReviews,
                            updatedAt = hotelupdatedAt, videos = VideoList, websiteURL = hotelwebsiteURL, rooms = RoomsList)

                        HotelFilter.add(Hotel)
                        progressDialog.dismiss()

                    }
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HASHDASDAS",error.toString())
                    Log.d("HASHDASDAS",error.networkResponse.statusCode.toString())
                    HotelFilter.clear()
                    progressDialog.dismiss()
                    HotelJsonArrayError = mutableStateOf(error.toString())
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