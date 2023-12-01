package com.example.adminoffice.ui.theme.Utils.Screens.Users

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import androidx.compose.material3.Icon
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.AboutUs
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.FAQ
import com.example.adminoffice.ui.theme.Utils.Screens.Settings.Policy
import com.example.adminoffice.ui.theme.Utils.SubHeader
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

object ViewUsers  : Screen {
    data class TableRow(
        val _id: String,
        val id: Int,
        val profilePicture: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phoneNumber: String,
        val cnic: String,
        val role: String,
        val status: String
    )

    lateinit var Users: JSONArray
    lateinit var userId: String
    var modalopen = mutableStateOf(false)
    var deletemodalopen = mutableStateOf(false)
    var current = TableRow(
        _id = "",
        id = 0,
        firstName = "",
        lastName = "",
        profilePicture = "",
        email = "",
        phoneNumber = "",
        cnic = "",
        role = "",
        status = ""
    )
    var usersData = mutableStateListOf<TableRow>()
    val params = JSONObject()
    var usersJsonArrayError = mutableStateOf("")

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current

        val navigator = LocalNavigator.currentOrThrow
        var sortOrder by remember { mutableStateOf(SortOrder.Ascending) }
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
                SortableTable(
                    context = context,
                    rows = usersData,
                    sortOrder = sortOrder,
                    onSortOrderChange = { newSortOrder ->
                        sortOrder = newSortOrder
                    })
                if (modalopen.value) {

                    CustomProgressDialog(current)
                }
                if (deletemodalopen.value) {

                    DeleteBox(current,context)
                }
            }
        }

        GetUsers(context)

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
        var role by remember { mutableStateOf("") }
        val rolesAvailable = listOf("customer", "owner", "accountant")
        var isExpandedRole = remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .padding(vertical = 60.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
//                BasicTextField(
//                    value = searchFilter,
//                    onValueChange = { filter ->
//                            searchFilter = filter.trim()
//
//                    },
//                    cursorBrush = SolidColor(Color.Black), // Change cursor color here
//                    textStyle = TextStyle(fontSize = 12.sp),
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .height(30.dp)
//                        .width(200.dp)
//                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp)),
//                    singleLine = true,
//                    decorationBox = { innerTextField ->
//                        Column(modifier = Modifier.padding(7.dp)) {
//                            if (searchFilter == "") {
//                                Text("Search", fontSize = 12.sp, color = Color.Gray)
//                            } else {
//                                innerTextField()
//                            }
//                        }
//                    }
//                )
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
                Box(modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        isExpandedRole.value = true
                    }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .background(Color.Red, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row {
                                Icon(
                                    painterResource(id = R.drawable.filter),
                                    contentDescription = "filter",
                                    tint = Color.White
                                )
                            }
                            DropdownMenu(
                                expanded = isExpandedRole.value,
                                onDismissRequest = { isExpandedRole.value = false },
                                modifier = Modifier
                                    .size(300.dp, 150.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                            ) {
                                rolesAvailable.forEach { option ->
                                    DropdownMenuItem(text = { Text(text = option) }, onClick = {
                                        role = option
                                        isExpandedRole.value = false
                                    })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier
                            .size(35.dp)
                            .background(
                                Color.Black, RoundedCornerShape(10.dp)
                            )
                            .clickable { role = "" }, contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(id = R.drawable.filteroff),
                                contentDescription = "filter",
                                tint = Color.White
                            )
                        }
                    }
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        rememberScrollState()
                    )
            ) {

                // Table header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GlobalStrings.AdminColorMain)
                ) {
                    Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "ID",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Image",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "name"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "First Name",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "lname"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Last Name",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "email"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Email",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Box(modifier = Modifier
                        .width(150.dp)
                        .clickable {
                            sortHeader = "role"
                            onSortOrderChange(sortOrder.toggle())
                        }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Role",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "CNIC",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Phone",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Status",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Action",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                }

                if (rows.isEmpty()) {
                    Text(
                        text = "NO Records Found.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W800,
                        modifier = Modifier.padding(20.dp)
                    )
                } else {
                    if (sortHeader == "name") {
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.firstName }
                            SortOrder.Descending -> rows.sortedByDescending { it.firstName }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                            }
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                                it.role.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.firstName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.lastName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.email.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.phoneNumber.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.cnic.contains(searchFilter.trim(), ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    } else if (sortHeader == "lname") {
                        val sortedRows = when (sortOrder) {
                            SortOrder.Ascending -> rows.sortedBy { it.lastName }
                            SortOrder.Descending -> rows.sortedByDescending { it.lastName }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                            }
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                                it.role.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.firstName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.lastName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.email.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.phoneNumber.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.cnic.contains(searchFilter.trim(), ignoreCase = true)
                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    } else if (sortHeader == "email") {
                        val sortedRows = when (sortOrder) {

                            SortOrder.Ascending -> rows.sortedBy { it.email }
                            SortOrder.Descending -> rows.sortedByDescending { it.email }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                            }
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                                it.role.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.firstName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.lastName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.email.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.phoneNumber.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.cnic.contains(searchFilter.trim(), ignoreCase = true)

                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    } else if (sortHeader == "role") {
                        val sortedRows = when (sortOrder) {

                            SortOrder.Ascending -> rows.sortedBy { it.role }
                            SortOrder.Descending -> rows.sortedByDescending { it.role }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                            }
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                                it.role.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.firstName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.lastName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.email.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.phoneNumber.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.cnic.contains(searchFilter.trim(), ignoreCase = true)

                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    } else {
                        val sortedRows = when (sortOrder) {

                            SortOrder.Ascending -> rows.sortedBy { it.id }
                            SortOrder.Descending -> rows.sortedByDescending { it.id }
                        }
                        sortedRows
                            .subList(startIndex, endIndex)
                            .filter {
                                it.role.contains(role, ignoreCase = true)
                            }
                            .filter {
                                it.role.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.firstName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.lastName.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.email.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.phoneNumber.contains(searchFilter.trim(), ignoreCase = true) ||
                                        it.cnic.contains(searchFilter.trim(), ignoreCase = true)

                            }
                            .forEach { row ->
                                MyRow(row = row, context = context)
                            }
                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))
                ) {
                    IconButton(
                        onClick = {
                            if (currentPage > 0) {
                                currentPage--
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.arrowleft),
                            contentDescription = "Arrow Right"
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    Modifier
                        .size(30.dp)
                        .border(0.4.dp, Color.Gray, RoundedCornerShape(5.dp))
                ) {
                    IconButton(
                        onClick = {
                            if ((currentPage + 1) * itemsPerPage < rows.size) {
                                currentPage++
                            }
                        }, modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.arrowright),
                            contentDescription = "Arrow Right"
                        )
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
    fun MyRow(row: TableRow, context: Context) {
        val navigatorOut = LocalNavigator.currentOrThrow
        Box(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .border(0.3.dp, Color.Gray, RoundedCornerShape(5.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center) {
                    Text(text = row.id.toString())
                }

                Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center) {
                    if (row.profilePicture == "") {
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
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(row.profilePicture),
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

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                    Text(text = row.firstName)
                }

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                    Text(text = row.lastName)
                }

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                    Text(text = row.email)
                }

                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                    if (row.role == "customer") {
                        Text(
                            text = row.role.toUpperCase(),
                            color = Color(0x900000FF),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    } else if (row.role == "owner") {
                        Text(
                            text = row.role.toUpperCase(),
                            color = Color(0x90FF0000),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                    } else {
                        Text(
                            text = row.role.toUpperCase(),
                            color = Color(0xFF1BCA1B),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                    Text(text = row.cnic)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                    Text(text = row.phoneNumber)
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                    if (row.status == "active") {
                        Box(modifier = Modifier
                            .width(100.dp)
                            .background(
                                Color(0x90355E3B),
                                shape = RoundedCornerShape(1.dp)
                            )
                            .clickable {
                                updateStatus(context, id = row._id, status = "blocked") { it ->
                                    GetUsers(context)
                                }
                            }
                            .padding(7.dp),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = row.status.toUpperCase(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } else if (row.status == "blocked") {
                        Box(modifier = Modifier
                            .width(100.dp)
                            .background(
                                Color(0x90FF3131),
                                shape = RoundedCornerShape(1.dp)
                            )
                            .clickable {
                                updateStatus(context, id = row._id, status = "active") { it ->
                                    GetUsers(context)
                                }
                            }
                            .padding(7.dp),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = row.status.toUpperCase(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                    }
                }
                Box(modifier = Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                    Row {
                        IconButton(onClick = {
                            //navigatorOut.push(ViewUserSpecific(row))
                            current = TableRow(
                                id = row.id,
                                _id = row._id,
                                firstName = row.firstName,
                                lastName = row.lastName,
                                email = row.email,
                                phoneNumber = row.phoneNumber,
                                role = row.role,
                                status = row.status,
                                profilePicture = row.profilePicture,
                                cnic = row.cnic
                            )
                            modalopen.value = true
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.eye), contentDescription = "View")
                        }
                        IconButton(onClick = {
                            navigatorOut.push(EditUser(row))
                        }, modifier = Modifier.width(30.dp)) {
                            Icon(painterResource(id = R.drawable.edit), contentDescription = "edit")
                        }
                        IconButton(onClick = {
                            var id = row._id
                            userId = id
                            deletemodalopen.value = true
                            current = TableRow(
                                id = row.id,
                                _id = row._id,
                                firstName = row.firstName,
                                lastName = row.lastName,
                                email = row.email,
                                phoneNumber = row.phoneNumber,
                                role = row.role,
                                status = row.status,
                                profilePicture = row.profilePicture,
                                cnic = row.cnic
                            )


                        }, modifier = Modifier.width(30.dp)) {
                            Icon(
                                painterResource(id = R.drawable.delete),
                                contentDescription = "delete"
                            )
                        }
                    }
                }

            }
        }
    }

    // DELETE USER Function
    fun deleteUser(context: Context, id: String, callback: (Boolean) -> Unit) {
        val url = "${GlobalStrings.baseURL}admin/users/deleteUser/${id}"
        // Request parameters
        val params = JSONObject()
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

    // GET USERS Function
    fun GetUsers(context: Context) {
        val url = "${GlobalStrings.baseURL}admin/users/getUsers"
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading Users...")
        progressDialog.show()
        if (!isInternetAvailable(context)) {
            Toast
                .makeText(
                    context,
                    "Internet not available.",
                    Toast.LENGTH_SHORT
                )
                .show()
        } else {
            val request = object : JsonObjectRequest(
                Request.Method.GET, url, params,
                { response ->
                    Log.d("HAPP",response.toString())
                    var users = response.getJSONArray("users")
                    usersData.clear()
                    Users = users
                    for (i in 0 until Users.length()) {
                        var user = Users.getJSONObject(i)

                        var id = user.getInt("id")
                        var _id = user.getString("_id")
                        var profilePicture = ""
                        if(user.has("profilePicture")){
                            profilePicture = user.getString("profilePicture")
                        }
                        var firstName = user.getString("firstName")
                        var lastName = user.getString("lastName")
                        var email = user.getString("email")
                        var userCategory = user.getString("userCategory").toString()
                        var cnic = user.getString("cnic")
                        var contactNo = user.getString("contactNo")
                        var status = user.getString("status")

                        usersData.add(
                            TableRow(
                                _id = _id,
                                id = id,
                                profilePicture = profilePicture,
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                role = userCategory,
                                cnic = cnic,
                                phoneNumber = contactNo,
                                status = status
                            )
                        )
                    }
                    progressDialog.dismiss()
                },
                { error ->
                    usersData.clear()
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

    fun updateStatus(context: Context, id: String, status: String, callback: (Boolean) -> Unit) {
        val url = "${GlobalStrings.baseURL}admin/users/updateUser/${id}"
        // Request parameters
        val params = JSONObject()
        params.put("status", status)
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
                    val isCouponClaimed = response.optBoolean("valid", false)
                    callback(isCouponClaimed)
                    progressDialog.dismiss()
                },
                { error ->
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
            onDismissRequest = { modalopen.value = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Spacer(modifier = Modifier.height(15.dp))
                    var profilePicture = current.profilePicture
                    if (profilePicture != "") {
                        Image(
                            painter = rememberAsyncImagePainter(profilePicture),

                            contentDescription = "image",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape((CornerSize(200.dp)))),
                            contentScale = ContentScale.FillBounds
                        )
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter("https://cdn4.iconfinder.com/data/icons/social-messaging-ui-color-squares-01/3/30-512.png"),
                            contentDescription = "image",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape((CornerSize(200.dp)))),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .background(
                                    if (current.status == "active") {
                                        Color(0x90355E3B)
                                    } else {
                                        Color(0x90FF3131)
                                    },
                                    shape = RoundedCornerShape(1.dp)
                                )
                                .padding(7.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = current.status.toUpperCase(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    var firstName = current.firstName
                    var lastName = current.lastName
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
                                    tint = Color(
                                        0x900000FF
                                    )
                                )
                            }
                            Column {
                                Text(text = "Name", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = firstName + " " + lastName,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    var email = current.email
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
                                    painterResource(id = R.drawable.email),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color(
                                        0x900000FF
                                    )
                                )
                            }
                            Column {
                                Text(text = "Email", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = email,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    var userCategory = current.role
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
                                    painterResource(id = R.drawable.role),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color(
                                        0x900000FF
                                    )
                                )
                            }
                            Column {
                                Text(text = "Role", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = userCategory,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    var cnic = current.cnic
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
                                    painterResource(id = R.drawable.card),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color(
                                        0x900000FF
                                    )
                                )
                            }
                            Column {
                                Text(text = "CNIC Number", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = cnic,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    var contactNo = current.phoneNumber
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
                                    painterResource(id = R.drawable.phone),
                                    contentDescription = "asdasd",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint = Color(
                                        0x900000FF
                                    )
                                )
                            }
                            Column {
                                Text(text = "Phone Number", fontSize = 10.sp, color = Color.Gray)
                                Text(
                                    text = contactNo,
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
                            text = "Delete User?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 20.dp),
                        text = "Are you sure you want to delete this User?",
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
                                    deleteUser(context = context, id = current._id) { Succes ->
                                        GetUsers(context)
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
}