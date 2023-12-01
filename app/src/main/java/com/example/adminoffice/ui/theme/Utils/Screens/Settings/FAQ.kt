package com.example.adminoffice.ui.theme.Utils.Screens.Settings

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
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

object FAQ  : Screen {
    data class TableRow(
        val _id: String,
        val question: String,
        val answer: String,)
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
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 60.dp)
                ){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Frequently Asked Questions", fontWeight = FontWeight.W800, fontSize = 16.sp, color = GlobalStrings.AdminColorMain)
                        OutlinedButton(onClick = {
                            modalopenFAQ.value = true
                        },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White,
                                containerColor = GlobalStrings.AdminColorMain,
                            ),
                            border= BorderStroke(1.dp,GlobalStrings.AdminColorMain),
                            shape = RoundedCornerShape(CornerSize(3.dp))) {
                            Text(text = "Add FAQ")
                        }
                    }
                    SortableTable(context = context, rows = serviceCategories, sortOrder = sortOrder, onSortOrderChange = { newSortOrder ->
                        sortOrder = newSortOrder
                    } )
                }

                if(modalopen.value){

                    CustomProgressDialog(current)
                }
                if(modalopenFAQ.value){

                    CustomProgressDialogFAQ(context)
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
        rows: List<TableRow>,
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
                    Box(modifier = Modifier
                        .width(250.dp)
                        .clickable {
                            sortHeader = "name"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Question", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 10.dp))
                    }
                    Box(modifier = Modifier
                        .width(250.dp)
                        .clickable {
                            sortHeader = "answer"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center){
                        Text(text = "Answer", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
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
                            SortOrder.Ascending -> rows.sortedBy { it.question }
                            SortOrder.Descending -> rows.sortedByDescending { it.question }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.question.contains(searchFilter, ignoreCase = true) ||
                                        it.answer.contains(searchFilter, ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }
                    else if(sortHeader=="answer"){
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.answer }
                            SortOrder.Descending -> rows.sortedByDescending { it.answer }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.answer.contains(searchFilter, ignoreCase = true) ||
                                        it.question.contains(searchFilter, ignoreCase = true)
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
                                it.question.contains(searchFilter, ignoreCase = true) ||
                                        it.answer.contains(searchFilter, ignoreCase = true)
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
    fun MyRow(row: TableRow, context: Context){
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
                    Text(text = row.question)
                }
                Box(modifier = Modifier.width(250.dp), contentAlignment = Alignment.Center){
                    Text(text = row.answer)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center){
                    Row{
                        IconButton(onClick = {
                            current =row
                            modalopen.value = true

                            //navigatorOut.push(ViewUserSpecific(row))
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.eye),contentDescription = "View")
                        }
                        IconButton(onClick = {
                           // navigatorOut.push(EditServiceCategory(row))
                        }, modifier = Modifier.width(30.dp)) {
                            //Icon(painterResource(id = R.drawable.edit),contentDescription = "edit")
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
        val url = "${GlobalStrings.baseURL}admin/settings/getSettings"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading FAQ...")
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
                    var settinngs  = response.getJSONObject("setting")
                    var categories  = settinngs.getJSONArray("faqs")
                    serviceCategories.clear()
                    for(i in 0 until categories.length()){
                        var category = categories.getJSONObject(i)
                        var _id = category.getString("_id")
                        var serviceImage = category.getString("question")
                        var serviceName = category.getString("answer")

                        serviceCategories.add(
                            TableRow(
                                _id = _id,
                                question = serviceImage,
                                answer = serviceName,
                            )
                        )
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
        val url = "${GlobalStrings.baseURL}admin/settings/deleteFaq/${id}"
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
                Request.Method.PUT, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("HEHHEHEHE", response.toString())

                    callback(true)
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
    fun CustomProgressDialog(current: TableRow) {
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = current.question, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = current.answer, fontSize = 10.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomProgressDialogFAQ(context:Context) {
        val hotelName = remember {
            mutableStateOf("")
        }
        val hotelDescription = remember {
            mutableStateOf("")
        }
        var isErrorHotelName by remember { mutableStateOf(false) }
        var isErrorDescription by remember { mutableStateOf(false) }
        Dialog(
            onDismissRequest = { modalopenFAQ.value =false}
        ) {

           Box(modifier = Modifier.fillMaxWidth()
               .padding(16.dp).clip(RoundedCornerShape(5.dp))
               .background(Color.White)){
               Box(modifier = Modifier
                   .padding(10.dp)
                   .width(300.dp).height(400.dp)){
                   Column(
                       modifier = Modifier
                           .padding(16.dp)
                           .width(250.dp)
                           .clip(RoundedCornerShape(5.dp))
                           .background(Color.White)
                           .verticalScroll(rememberScrollState()),
                       horizontalAlignment = Alignment.CenterHorizontally,
                   ) {
                       Spacer(modifier = Modifier.height(16.dp))
                       OutlinedTextField(
                           shape = RoundedCornerShape(5.dp),
                           colors = TextFieldDefaults.outlinedTextFieldColors(
                               focusedBorderColor = Color.Black,
                               unfocusedBorderColor = Color.Gray,
                               errorBorderColor = Color.Red,
                               placeholderColor = Color.Gray,
                               disabledPlaceholderColor = Color.Gray
                           ),
                           value = hotelName.value,
                           onValueChange = {
                               if (it.length <= 70)
                                   hotelName.value=it
                           },
                           leadingIcon = {
                               Icon(painterResource(id = R.drawable.house), contentDescription = "add", tint = GlobalStrings.AdminColorMain)
                           },
                           label = {
                               Text(text = "Question",color = Color.Gray)
                           },
                           placeholder = {
                               Text(text = "Enter Question")
                           },
                           singleLine = true
                       )
                       if (isErrorHotelName) {
                           Text(
                               text = "Please enter a valid Question",
                               color = Color.Red,
                               fontSize = 11.sp
                           )
                       }
                       Spacer(modifier = Modifier.height(10.dp))
                       OutlinedTextField(
                           shape = RoundedCornerShape(5.dp),
                           colors = TextFieldDefaults.outlinedTextFieldColors(
                               focusedBorderColor = Color.Black,
                               unfocusedBorderColor = Color.Gray,
                               errorBorderColor = Color.Red,
                               placeholderColor = Color.Gray,
                               disabledPlaceholderColor = Color.Gray
                           ),
                           value = hotelDescription.value,
                           onValueChange = {
                               if (it.length <= 300)
                                   hotelDescription.value=it
                           },
                           leadingIcon = {
                               Icon(painterResource(id = R.drawable.description), contentDescription = "add", tint = GlobalStrings.AdminColorMain)
                           },
                           label = {
                               Text(text = "Answer",color = Color.Gray)
                           },
                           placeholder = {
                               Text(text = "Enter Answer")
                           },
                           modifier = Modifier
                               .height(200.dp),
                       )
                       if (isErrorDescription) {
                           Text(
                               text = "Please provide an Answer.",
                               color = Color.Red,
                               fontSize = 11.sp
                           )
                       }
                       OutlinedButton(
                           border = BorderStroke(2.dp, GlobalStrings.AdminColorMain),
                           colors = ButtonDefaults.buttonColors(
                               containerColor = GlobalStrings.AdminColorMain,
                               contentColor = Color.White),
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(0.dp, 10.dp)
                               .size(400.dp, 45.dp),
                           onClick = {
                               isErrorHotelName = hotelName.value==""
                               isErrorDescription = hotelDescription.value==""
                               AddFAQ(context=context, question=hotelName.value, answer=hotelDescription.value){
                                   if(it){
                                       modalopenFAQ.value=false
                                   }
                               }


                           },
                           shape = RoundedCornerShape(15.dp),
                       ) {
                           Text(text = "Add FAQ", color = Color.White, letterSpacing = 0.sp, fontWeight = FontWeight.SemiBold)
                       }
                   }
               }
           }
        }
    }
    @Composable
    fun DeleteBox(current: TableRow,context:Context) {
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
                            text = "Delete FAQ?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 20.dp),
                        text = "Are you sure you want to delete this FAQ?",
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

    // Add Category Function
    fun AddFAQ(context: Context, question: String, answer: String, callback: (Boolean) -> Unit){
        val url = "${GlobalStrings.baseURL}admin/settings/addFaq"

        // Request parameters
        val params = JSONObject()
        params.put("question", question)
        params.put("answer", answer)
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait")
        Log.d("ASDWFVSA", params.toString())
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
                Request.Method.PUT, url, params,
                { response ->
                    // Handle successful login response
                    Log.d("ASDWFVSA", response.toString())
                    Toast
                        .makeText(
                            context,
                            "FAQ Added.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    progressDialog.dismiss()

                    callback(true)
                },
                { error ->
                    // Handle error response
                    Log.e("ASDWFVSA", error.toString())
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "There is some error.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    callback(false)
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