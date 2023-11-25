package com.example.adminoffice.ui.theme.Utils.Screens.Bookings

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Booking
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Customer
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Hotel
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Room
import com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings.Userid
import com.example.adminoffice.ui.theme.Utils.GlobalStrings
import com.example.adminoffice.ui.theme.Utils.Header
import com.example.adminoffice.ui.theme.Utils.Logo
import com.example.adminoffice.ui.theme.Utils.Menu
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
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
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.convertDateFormat
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import kotlinx.coroutines.launch
import org.json.JSONObject

object ViewBookings  : Screen {
    var deletemodalopen = mutableStateOf(false)
    val params = JSONObject()
    var usersJsonArrayError = mutableStateOf("")
    var modalopen = mutableStateOf(false)
   lateinit var current: Booking
    var serviceCategories = mutableStateListOf<Booking>()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var sortOrder by remember { mutableStateOf(SortOrder.Ascending) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItem by remember{ mutableStateOf(-1) }
        var selectedSubItem by remember { mutableStateOf(-1) }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Logo(scope = scope, drawerState = drawerState)
                    Menu().forEachIndexed{
                            index, data ->
                        NavigationDrawerItem(
                            modifier = Modifier.height(45.dp),
                            label = { Header(first = data.first, second = data.second) },
                            selected = selectedItem==index,
                            onClick = {
                                selectedItem=index
                                selectedSubItem = -1
                                if(selectedItem==0){
                                    scope.launch {
                                        drawerState.close()
                                        navigator.pop()
                                    }

                                }
                                else if(selectedItem==10){
                                    scope.launch {
                                        drawerState.close()
                                        navigator.replace(Chat)
                                    }

                                }
                            })
                        if (selectedItem == index) {
                            val subMenuItems = data.third
                            Column {
                                subMenuItems.forEachIndexed { index, subItem ->
                                    NavigationDrawerItem(
                                        modifier = Modifier.height(45.dp),
                                        label = {
                                            SubHeader(subItem=subItem)
                                        },
                                        selected = selectedSubItem == index,
                                        onClick = {
                                            //onSubItemClick()
                                            scope.launch {
                                                drawerState.close()
                                                if (selectedItem == 1) {
                                                    if (index == 1) {
                                                        navigator.replace(ViewUsers)
                                                    }
                                                    if (index == 0) {
                                                        navigator.replace(Home)
                                                    }
                                                } else if (selectedItem == 2) {
                                                    if (index == 1) {
                                                        navigator.replace(ViewServiceCategory)
                                                    }
                                                    if (index == 0) {
                                                        navigator.replace(AddServiceCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddService)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewService)
                                                    }
                                                } else if (selectedItem == 3) {
                                                    if (index == 0) {
                                                        navigator.replace(AddHotel)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewHotel)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddRoom)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewRoom)
                                                    }
                                                } else if (selectedItem == 4) {
                                                    if (index == 0) {
                                                        navigator.replace(AddInventoryCategory)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewInventoryCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddInventory)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewInventory)
                                                    }
                                                }
                                                else if (selectedItem == 5) {
                                                    if (index == 0) {
                                                        navigator.replace(AddCoupon)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewCoupons)
                                                    }
                                                }else if (selectedItem == 6) {
                                                    if (index == 0) {
                                                        navigator.replace(AddBooking)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewBookings)
                                                    }
                                                } else if (selectedItem == 7) {
                                                    if (index == 0) {
                                                        navigator.replace(ViewPayments)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewRefunds)
                                                    }
                                                } else if (selectedItem == 8) {
                                                    if (index == 0) {
                                                        navigator.replace(AddDishCategory)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewDishCategory)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddDish)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewDish)
                                                    }
                                                    if (index == 4) {
                                                        navigator.replace(AddMenu)
                                                    }
                                                    if (index == 5) {
                                                        navigator.replace(ViewMenu)
                                                    }
                                                } else if (selectedItem == 9) {
                                                    if (index == 0) {
                                                        navigator.replace(AddReview)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewReview)
                                                    }
                                                } else if (selectedItem == 11) {
                                                    if (index == 0) {
                                                        navigator.replace(AddRevenue)
                                                    }
                                                    if (index == 1) {
                                                        navigator.replace(ViewRevenue)
                                                    }
                                                    if (index == 2) {
                                                        navigator.replace(AddExpense)
                                                    }
                                                    if (index == 3) {
                                                        navigator.replace(ViewExpense)
                                                    }
                                                    if (index == 4) {
                                                        navigator.replace(ViewProfit)
                                                    }
                                                }
                                                else if (selectedItem == 12) {
                                                    if (index == 1) {
                                                        navigator.replace(FAQ)
                                                    }
                                                    if (index == 0) {
                                                        navigator.push(AboutUs)
                                                    }
                                                    if (index == 2) {
                                                        navigator.push(Policy)
                                                    }
                                                }
                                            }


                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            },
            drawerState= drawerState,
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
                SortableTable(context = context, rows = serviceCategories, sortOrder = sortOrder, onSortOrderChange = { newSortOrder ->
                    sortOrder = newSortOrder
                } )
                if(modalopen.value){

                    CustomProgressDialog(current)
                }
                if (deletemodalopen.value) {

                    DeleteBox(current,context)
                }


            }
        }

        getCategories(context = context)
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SortableTable(
        context: Context,
        rows: List<Booking>,
        sortOrder: SortOrder,
        onSortOrderChange: (SortOrder) -> Unit
    ) {
        var currentPage by remember { mutableStateOf(0) }
        val itemsPerPage = 5
        val startIndex = currentPage * itemsPerPage
        val endIndex = minOf((currentPage + 1) * itemsPerPage, rows.size)
        var sortHeader by remember {
            mutableStateOf("")
        }
        var searchFilter by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 60.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()){
                OutlinedTextField(
                    value = searchFilter,
                    onValueChange = { filter ->
                        searchFilter = filter

                    },
                    modifier = Modifier
                        .padding(5.dp),
                    placeholder = {
                        Text(text = "Search")
                    },
                )

            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )) {

                // Table header
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                ){
                    Box(modifier = Modifier.width(250.dp), contentAlignment = Alignment.Center){
                        Text(text = "ID", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                        Text(text = "Image", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "name"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Hotel Name", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "checkin"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Check In", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "checkout"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Check Out", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "adults"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Adults", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "children"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Children", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "charges"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Charges", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "status"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Status", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                        Text(text = "Action", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }

                }

                if(rows.isEmpty()){
                    Text(text = "NO Records Found.", fontSize = 12.sp, fontWeight = FontWeight.W800, modifier = Modifier.padding(20.dp))
                }
                else{
                    if(sortHeader=="name"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.hotelName }
                            SortOrder.Descending -> rows.sortedByDescending { it.hotelName }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.hotelName.contains(searchFilter, ignoreCase = true)
                            }
                            .filter {
                                it.hotelName.contains(searchFilter, ignoreCase = true)
                                it.hotelName.contains(searchFilter, ignoreCase = true) ||
                                        it.childern.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.adults.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.checkIn.contains(searchFilter, ignoreCase = true)||
                                        it.checkOut.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="checkin"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.checkIn }
                            SortOrder.Descending -> rows.sortedByDescending { it.checkIn }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.checkIn.contains(searchFilter, ignoreCase = true)
                            }
                            .filter {
                                it.checkIn.contains(searchFilter, ignoreCase = true)
                                it.checkIn.contains(searchFilter, ignoreCase = true) ||
                                        it.childern.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.adults.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.hotelName.contains(searchFilter, ignoreCase = true)||
                                        it.checkOut.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="checkout"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.checkOut }
                            SortOrder.Descending -> rows.sortedByDescending { it.checkOut }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.checkOut.contains(searchFilter, ignoreCase = true)
                            }
                            .filter {
                                it.checkOut.contains(searchFilter, ignoreCase = true)
                                it.checkOut.contains(searchFilter, ignoreCase = true) ||
                                        it.childern.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.adults.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.hotelName.contains(searchFilter, ignoreCase = true)||
                                        it.checkIn.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="adults"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.adults }
                            SortOrder.Descending -> rows.sortedByDescending { it.adults }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.adults.toString().contains(searchFilter, ignoreCase = true)
                            }
                            .filter {
                                it.adults.toString().contains(searchFilter, ignoreCase = true)
                                it.adults.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.childern.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.checkOut.contains(searchFilter, ignoreCase = true) ||
                                        it.hotelName.contains(searchFilter, ignoreCase = true)||
                                        it.checkIn.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="children"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.childern }
                            SortOrder.Descending -> rows.sortedByDescending { it.childern }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.childern.toString().contains(searchFilter, ignoreCase = true)
                            }
                            .filter {
                                it.childern.toString().contains(searchFilter, ignoreCase = true)
                                it.childern.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.checkOut.contains(searchFilter, ignoreCase = true) ||
                                        it.adults.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.hotelName.contains(searchFilter, ignoreCase = true)||
                                        it.checkIn.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else{
                        val sortedRows = when (sortOrder) {

                            SortOrder.Ascending -> rows.sortedBy { it._id }
                            SortOrder.Descending -> rows.sortedByDescending { it._id }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.childern.toString().contains(searchFilter, ignoreCase = true)
                            }
                            .filter {
                                it.childern.toString().contains(searchFilter, ignoreCase = true)
                                it.childern.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.checkOut.contains(searchFilter, ignoreCase = true) ||
                                        it.adults.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.hotelName.contains(searchFilter, ignoreCase = true)||
                                        it.checkIn.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }

                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))){
                    IconButton(
                        onClick = {
                            if (currentPage>0) {
                                currentPage--
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.arrowleft), contentDescription = "Arrow Right")
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))){
                    IconButton(
                        onClick = {
                            if ((currentPage + 1) * itemsPerPage < rows.size) {
                                currentPage++
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.arrowright), contentDescription = "Arrow Right")
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }

    enum class SortOrder {
        Ascending,
        Descending;

        fun toggle(): SortOrder {
            return if (this == Ascending) Descending else Ascending
        }
    }
    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun MyRow(row: Booking, context: Context){
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
                Box(modifier = Modifier.width(250.dp), contentAlignment = Alignment.Center){
                    Text(text = row._id)
                }

                Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center){
                    if(row.rooms[0].images[0]==""){
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
                            painter = rememberAsyncImagePainter(row.rooms[0].images[0]),
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
                    Text(text = row.hotelName)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = convertDateFormat(row.checkIn))
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = convertDateFormat(row.checkOut))
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.adults.toString())
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.childern.toString())
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = (row.roomCharges+row.serviceCharges).toString())
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    var status by remember { mutableStateOf("") }
                    val statusAvailable = listOf("new", "cancelled", "refunded","checked-in","checked-out")
                    var isExpandedStatus = remember {
                        mutableStateOf(false)
                    }
                        Box(modifier = Modifier
                            .width(100.dp)
                            .background(
                                color =
                                (if (row.status == "new") {
                                    Color(0x90355E3B)
                                } else if (row.status == "new") {
                                    Color(0x90EB0325)
                                } else if (row.status == "refunded") {
                                    Color(0x901435D3)
                                } else if (row.status == "checked-in") {
                                    Color(0x906215CF)
                                } else if (row.status == "checked-out") {
                                    Color(0x90ECAF1C)
                                } else {
                                    Color(0x90E10AF8)
                                }) as Color,
                                shape = RoundedCornerShape(1.dp)
                            )
                            .clickable {
                                isExpandedStatus.value = true
                            }
                            .padding(7.dp),
                            contentAlignment = Alignment.Center){
                            DropdownMenu(
                                expanded = isExpandedStatus.value,
                                onDismissRequest = {isExpandedStatus.value=false },
                                modifier = Modifier
                                    .width(150.dp)
                            ) {
                                statusAvailable.forEach { option ->
                                    DropdownMenuItem(text = { Text(text = option) } ,
                                        onClick = {
                                        status = option
                                        isExpandedStatus.value = false
                                            updateStatus(
                                                context,
                                                id = row._id,
                                                status = status
                                            ) { it ->
                                                getCategories(context = context)
                                            }
                                    }
                                    )
                                }
                            }
                            Text(text = row.status.toUpperCase(), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                        }
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Row{
                        IconButton(onClick = {
                            current = row
                            modalopen.value = true

                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.eye),contentDescription = "View")
                        }
                        IconButton(onClick = {
                              navigatorOut.push(CancelBooking(row))
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.cancel),contentDescription = "edit")
                        }
                        IconButton(onClick = {
                            navigatorOut.push(ExtendBooking(row))
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.extend),contentDescription = "edit")
                        }
                        IconButton(onClick = {
                            deletemodalopen.value =true
                            current = row


                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.delete),contentDescription = "delete")
                        }
                    }
                }
            }
        }
    }
    // GET Categories Function
    fun getCategories(context: Context) {
        val url = "${GlobalStrings.baseURL}admin/bookings/getBookings"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Bookings...")
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
                    Log.d("HASHDASDAS",response.toString())
                    var categories  = response.getJSONArray("bookings")
                    serviceCategories.clear()
                        for(i in 0 until categories.length()){
                            try{
                                var category = categories.getJSONObject(i)
                                var RefundList = mutableStateListOf<Refund>()
                                var RoomList = mutableStateListOf<Room>()
                                var _id = category.getString("_id")
                                var bookingDetails = category.getString("bookingDetails")
                                var checkIn = category.getString("checkIn")
                                var checkInDate = category.getString("checkInDate")
                                var checkOutDate = category.getString("checkOutDate")
                                var checkOut = category.getString("checkOut")
                                var hotell = category.getJSONObject("hotel")
                                var hotelName = hotell.getString("name")
                                var __v = category.getInt("__v")
                                var adults = category.getInt("adults")
                                var roomCharges = category.getInt("roomCharges")
                                var childern = category.getInt("childern")
//                            var customerName = category.getString("customerName")
                                var serviceCharges = category.getInt("serviceCharges") /// Issue beacuse of Customer Diferent
                                var status = category.getString("status")
                                var rooms = category.getJSONArray("rooms")
                                for(i in 0 until rooms.length()){
                                    var tyu = rooms.getJSONObject(i)
                                    var description = tyu.getString("description")
                                    var roomNumber = tyu.getString("roomNumber")
                                    var price = tyu.getInt("price")
                                    var images = tyu.getJSONArray("images")
                                    var imagesList = mutableStateListOf<String>()
                                    for(i in 0 until images.length()){
                                        var img = images.getString(i)
                                        imagesList.add(img)
                                    }
                                    var type = tyu.getString("type")
                                    RoomList.add(Room(description = description, images = imagesList,roomNumber=roomNumber, type = type,price=price))
                                }
                                var hotel = category.getJSONObject("hotel")
                                var hotel__v = hotel.getInt("__v")
                                var hotel_id = hotel.getString("_id")
                                var hotelcity= hotel.getString("city")
                                var hoteldescription= hotel.getString("description")
                                var hotelname= hotel.getString("name")
                                var hotelrefundPolicy= hotel.getJSONObject("refundPolicy")
                                var hotelrefundPolicy_id= hotelrefundPolicy.getString("_id")
                                var hotelrefundPolicydescription= hotelrefundPolicy.getString("description")
                                var hotelrefundPolicyrefunds= hotelrefundPolicy.getJSONArray("refunds")
                                for (i in 0 until hotelrefundPolicyrefunds.length()){
                                    var rr = hotelrefundPolicyrefunds.getJSONObject(i)
                                    var refunddays= rr.getInt("days")
                                    var refundpercentage= rr.getInt("percentage")
                                    var refund_id= rr.getString("_id")
                                    var refund = Refund(_id = refund_id, days = refunddays, percentage = refundpercentage)
                                    RefundList.add(refund)
                                }
                                var refundPolicy = RefundPolicy(_id = hotelrefundPolicy_id, description =hotelrefundPolicydescription,
                                    refunds = RefundList)
                                var customerName = "Customer could not be loaded"
                                var customeremail = "Customer could not be loaded"
                                var customernumber = "Customer could not be loaded"
                                var customerprofilePicture = ""
                                if(category.has("customer")){
                                    var customer = category.getJSONObject("customer")
//                                   var customeruseridcnic = customer.getString("cnic")
//                                   var customeruseridcontactNo = customer.getString("contactNo")
//                                   var customeruseridemail = customer.getString("email")
                                    var customeruseridfirstName = customer.getString("firstName")
                                    var customeruseridlastName = customer.getString("lastName")
                                    customeremail = customer.getString("email")
                                    customernumber = customer.getString("contactNo")
                                    customerprofilePicture = customer.getString("profilePicture")
//                                   var customeruseridprofilePicture = customer.getString("profilePicture")
//                                   var customeruseridrole = customer.getString("role")
//                                   var customeruseridstatus = customer.getString("status")
//                                   var customer_id = category.getString("_id")
                                    customerName=customeruseridfirstName+" "+customeruseridlastName
                                }
                                else{
                                    var name = category.getString("name")
                                    customeremail = category.getString("email")
                                    customernumber = category.getString("contactNo")
                                    customerName=name
                                }
//                               try{
//                                   var customer = category.getJSONObject("customer")
////                                   var customeruseridcnic = customer.getString("cnic")
////                                   var customeruseridcontactNo = customer.getString("contactNo")
////                                   var customeruseridemail = customer.getString("email")
//                                   var customeruseridfirstName = customer.getString("firstName")
//                                   var customeruseridlastName = customer.getString("lastName")
////                                   var customeruseridprofilePicture = customer.getString("profilePicture")
////                                   var customeruseridrole = customer.getString("role")
////                                   var customeruseridstatus = customer.getString("status")
////                                   var customer_id = category.getString("_id")
//                                   customerName=customeruseridfirstName+" "+customeruseridlastName
//                               }
//                               catch (e:Exception){
//                                   e.printStackTrace()
////                                   var email =category.getString("email")
////                                   var name =category.getString("name")
////                                   var contactNo =category.getString("contactNo")
////                                   customerName=email
//                               }



                                var userid= Userid(_id ="Customer could not be loaded", cnic =  "Customer could not be loaded", contactNo =customernumber,
                                    email = customeremail, firstName =customerName, lastName = "",
                                    profilePicture = customerprofilePicture, role = "Customer could not be loaded", status = "Customer could not be loaded")
                                var customerOBJ = Customer(_id="customer_id", userid = userid)


                                var hotelOBJ = Hotel(__v =hotel__v, _id =  hotel_id, city =hotelcity, description = hoteldescription,
                                    name = hotelname, refundPolicy = refundPolicy)
                                serviceCategories.add(Booking(__v = __v,_id=_id, adults =adults, bookingDetails =bookingDetails,
                                    checkIn = checkIn, checkInDate = checkInDate, checkOut = checkOut, checkOutDate =checkOutDate,
                                    childern =childern, customer = customerOBJ, customerName = customerName,
                                    hotel = hotelOBJ, hotelName = hotelName, roomCharges =roomCharges, rooms =RoomList ,
                                    serviceCharges = serviceCharges, status =status ))

                            }
                            catch(e:Exception){
                                e.printStackTrace()
                            }
                        }
                    progressDialog.dismiss()
                },
                { error ->
                    serviceCategories.clear()
                    usersJsonArrayError = mutableStateOf(error.toString())
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

    // DELETE Category Function
    fun deleteCategory(context: Context,id:String, callback: (Boolean) -> Unit) {
        val url = "${GlobalStrings.baseURL}admin/bookings/deleteBooking/${id}"
        // Request parameters
        val params = JSONObject()
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
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Deleting...")
            progressDialog.show()
            val request = object : JsonObjectRequest(
                Request.Method.DELETE, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("HEHHEHEHE", response.toString())

                    val isCouponClaimed = response.optBoolean("valid", false)
                    callback(isCouponClaimed)
                    progressDialog.dismiss()
                },
                { error ->
                    // Handle error response
                    Log.d("HEHHEHEHE", error.toString())
                    Toast
                        .makeText(
                            context,
                            "An Error occurred please check your Internet.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    callback(false)
                    progressDialog.dismiss()
                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): MutableMap<String, String> {
                    val token = getTokenFromLocalStorage(context = context)
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["Authorization"] = "${token}"
                    return headers
                }
            }


            // Add the request to the RequestQueue.
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
        }
    }

    @Composable
    fun CustomProgressDialog(current: Booking) {
        Dialog(
            onDismissRequest = { modalopen.value =false}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = rememberAsyncImagePainter(current.rooms[0].images[0]),

                    contentDescription = "image",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Column {
                                    Text(text = "Booking ID", fontSize = 10.sp, color = Color.Gray)
                                    Text(
                                        text = current._id,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Column {
                                    Text(text = "Booking Details", fontSize = 10.sp, color = Color.Gray)
                                    Text(
                                        text = current.bookingDetails,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Text(text = "Room", fontSize = 10.sp, color = Color.Gray)
                            for (room in current.rooms){
                                Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                                    Text(
                                        text = room.roomNumber,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = room.type,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Column {
                                    Text(text = "Hotel", fontSize = 10.sp, color = Color.Gray)
                                    Text(
                                        text = current.hotelName,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Column {
                                    Text(text = "Charges", fontSize = 10.sp, color = Color.Gray)
                                    Text(
                                        text = (current.roomCharges+current.serviceCharges).toString(),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Column {
                                    Text(text = "Customer Name", fontSize = 10.sp, color = Color.Gray)
                                    Text(
                                        text = current.customerName,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Column {
                                    Text(text = "Booking Status", fontSize = 10.sp, color = Color.Gray)
                                    Text(
                                        text = current.status,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    @Composable
    fun DeleteBox(current: Booking,context:Context) {
        Dialog(
            onDismissRequest = { deletemodalopen.value = false }
        ) {
            Column(modifier = Modifier
                .padding(all = 16.dp)
                .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                .background(Color.White)) {

                Column(modifier = Modifier
                    .padding(all = 16.dp)
                    .background(Color.White)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 6.dp,
                            alignment = Alignment.Start
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "Delete Icon",
                            tint = Color.Black
                        )

                        Text(
                            text = "Delete Booking?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 20.dp),
                        text = "Are you sure you want to delete this Booking?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.End
                        )
                    ) {

                        // Cancel button
                        Box(
                            modifier = Modifier
                                .clickable {
                                    deletemodalopen.value = false
                                }
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.Black
                            )
                        }

                        // Delete button
                        Box(
                            modifier = Modifier
                                .clickable {
                                    deleteCategory(
                                        context = context,
                                        id = current._id
                                    ) { Succes ->
                                        getCategories(context)
                                        deletemodalopen.value = false
                                    }
                                }
                                .background(
                                    color = Color.Red,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                        ) {
                            Text(
                                text = "Delete",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                        }

                    }
                }
            }
        }
    }

    fun updateStatus(context: Context, id: String, status: String, callback: (Boolean) -> Unit) {
        val url = "${GlobalStrings.baseURL}admin//bookings/updateStatus/${id}"
        // Request parameters
        val params = JSONObject()
        params.put("status", status)
        params.put("cancellationReason", "null")
        params.put("iban", "null")
        params.put("amount", "null")
        Log.d("HAPP",params.toString())
        if (!isInternetAvailable(context)) {
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        } else {
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Updating...")
            progressDialog.show()
            val request = object : JsonObjectRequest(
                Request.Method.PUT, url, params,
                { response ->
                    Log.d("HAPP",response.toString())
                    callback(true)
                    progressDialog.dismiss()
                },
                { error ->
                    Log.d("HAPP",error.toString())
                    Toast
                        .makeText(
                            context,
                            "An Error occurred please check your Internet.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    callback(false)
                    progressDialog.dismiss()
                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): MutableMap<String, String> {
                    val token = getTokenFromLocalStorage(context = context)
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["Authorization"] = "${token}"
                    return headers
                }
            }
            // Add the request to the RequestQueue.
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(request)
        }
    }

}