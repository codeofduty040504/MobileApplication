package com.example.adminoffice.ui.theme.Utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddBooking
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.AddReview
import com.example.adminoffice.ui.theme.Utils.Screens.Bookings.ViewBookings
import com.example.adminoffice.ui.theme.Utils.Screens.Chat.Chat
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.AddCoupon
import com.example.adminoffice.ui.theme.Utils.Screens.Coupons.ViewCoupons
import com.example.adminoffice.ui.theme.Utils.Screens.Dashboard
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
import com.example.adminoffice.ui.theme.Utils.Screens.Users.Home
import com.example.adminoffice.ui.theme.Utils.Screens.Users.ViewUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerUni(scope: CoroutineScope, drawerState: DrawerState){
    var selectedItem by remember{ mutableStateOf(-1) }
    var selectedSubItem by remember { mutableStateOf(-1) }
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
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
                            navigator.replace(Dashboard)
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
                                        if(getRoleFromLocalStorage(context)=="admin"){
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
                                        }
                                        else{
                                            if (index == 0) {
                                                navigator.replace(ViewServiceCategory)
                                            }
                                            if (index == 1) {
                                                navigator.replace(AddService)
                                            }
                                            if (index == 2) {
                                                navigator.replace(ViewService)
                                            }
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
                                        if(getRoleFromLocalStorage(context)=="admin"){
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
                                        else{
                                            if (index == 0) {
                                                navigator.replace(ViewInventoryCategory)
                                            }
                                            if (index == 1) {
                                                navigator.replace(AddInventory)
                                            }
                                            if (index == 2) {
                                                navigator.replace(ViewInventory)
                                            }
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
                                        if(getRoleFromLocalStorage(context)=="admin"){
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
                                        }
                                        else{
                                            if (index == 0) {
                                                navigator.replace(ViewDishCategory)
                                            }
                                            if (index == 1) {
                                                navigator.replace(AddDish)
                                            }
                                            if (index == 2) {
                                                navigator.replace(ViewDish)
                                            }
                                            if (index == 3) {
                                                navigator.replace(AddMenu)
                                            }
                                            if (index == 4) {
                                                navigator.replace(ViewMenu)
                                            }
                                        }

                                    } else if (selectedItem == 9) {
                                        if(getRoleFromLocalStorage(context)=="admin"){
                                            if (index == 0) {
                                                navigator.replace(AddReview)
                                            }
                                            if (index == 1) {
                                                navigator.replace(ViewReview)
                                            }
                                        }
                                        else{
                                            if (index == 0) {
                                                navigator.replace(ViewReview)
                                            }
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
                                            navigator.replace(AboutUs)
                                        }
                                        if (index == 2) {
                                            navigator.replace(Policy)
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
}