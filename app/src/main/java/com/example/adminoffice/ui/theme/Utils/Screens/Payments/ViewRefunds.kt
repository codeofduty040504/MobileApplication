package com.example.adminoffice.ui.theme.Utils.Screens.Payments


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
import com.example.adminoffice.ui.theme.Utils.DataClasses.Payments.Payment
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
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.AddRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Revenue.ViewRevenue
import com.example.adminoffice.ui.theme.Utils.Screens.Reviews.ViewReview
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.AddRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Rooms.ViewRoom
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.AddServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Services.EditServce
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getRoleFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import kotlinx.coroutines.launch
import org.json.JSONObject

object ViewRefunds  : Screen {
    var payments = mutableStateListOf<Payment>()
    var usersJsonArrayError = mutableStateOf("")
    val params = JSONObject()
    var deletemodalopen = mutableStateOf(false)
    var modalopen = mutableStateOf(false)
    lateinit var current : Payment
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
                DrawerUni(scope,drawerState)
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
                SortableTable(context = context, rows = payments, sortOrder = sortOrder, onSortOrderChange = { newSortOrder ->
                    sortOrder = newSortOrder
                } )
                if(modalopen.value){

                    CustomProgressDialog(current)
                }


            }
        }

        getCategories(context = context)
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SortableTable(
        context: Context,
        rows: List<Payment>,
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
                    .background(GlobalStrings.AdminColorMain)
                ){
                    Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center){
                        Text(text = "ID", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "type"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Refund ID", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(100.dp)
                        .clickable {
                            sortHeader = "charges"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "IBAN", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
                    }
                    Box(modifier = Modifier
                        .width(100.dp)
                        .clickable {
                            sortHeader = "charges"
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
                    if(sortHeader=="type"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.type }
                            SortOrder.Descending -> rows.sortedByDescending { it.type }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.type.contains(searchFilter, ignoreCase = true)
                                it.type.contains(searchFilter, ignoreCase = true) ||
                                        it.bookingID.contains(searchFilter, ignoreCase = true) ||
                                        it.amount.toString().contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="charges"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.amount.toString() }
                            SortOrder.Descending -> rows.sortedByDescending { it.amount.toString() }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.amount.toString().contains(searchFilter, ignoreCase = true)
                                it.amount.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.bookingID.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else{
                        val sortedRows = when (sortOrder) {

                            SortOrder.Ascending -> rows.sortedBy { it.id }
                            SortOrder.Descending -> rows.sortedByDescending { it.id }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.amount.toString().contains(searchFilter, ignoreCase = true)
                                it.amount.toString().contains(searchFilter, ignoreCase = true) ||
                                        it.bookingID.contains(searchFilter, ignoreCase = true) ||
                                        it.type.contains(searchFilter, ignoreCase = true)
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
    @Composable
    fun MyRow(row: Payment, context: Context){
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
                Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center){
                    Text(text = row._id.toString())
                }

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Text(text = row.type)
                }
                Box(modifier = Modifier.width(100.dp).height(40.dp).background(color = GlobalStrings.AdminColorMain).clip(shape = RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center){
                    Text(text = "Rs."+row.amount.toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Row{
                        IconButton(onClick = {
                            current = row
                            modalopen.value = true
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.eye),contentDescription = "View")
                        }
                    }
                }
            }
        }
    }
    // GET Categories Function
    fun getCategories(context: Context) {
        var role = getRoleFromLocalStorage(context)
        val url = "${GlobalStrings.baseURL}${role}/refunds/getPendingRefunds"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Payments...")
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
                    var categories  = response.getJSONArray("pendingRefunds")
                    payments.clear()
//                    for(i in 0 until categories.length()){
//                        var category = categories.getJSONObject(i)
//
//                        var id = category.getInt("id")
//                        var _id = category.getString("_id")
//                        var amount = category.getInt("amount")
//                        var type = category.getString("type")
//                        var booking = category.getJSONObject("booking")
//                        var booking_id = booking.getString("_id")
//
//                        payments.add(Payment(_id=_id, amount = amount, id = id, type =type , bookingID = booking_id))
//                    }
                    progressDialog.dismiss()
                },
                { error ->
                    payments.clear()
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

    @Composable
    fun CustomProgressDialog(current: Payment) {
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
                Text(text = "View Payment", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    painterResource(id = R.drawable.man),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color.Black
                                )
                            }
                            Column {
                                Text(text = "Type", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = current.type,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                Icon(
                                    painterResource(id = R.drawable.money),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color.Black
                                )
                            }
                            Column {
                                Text(text = "Amount", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = current.amount.toString(),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}