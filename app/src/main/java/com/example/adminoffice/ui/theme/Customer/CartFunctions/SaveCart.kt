package com.example.adminoffice.ui.theme.Customer.CartFunctions

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import com.example.adminoffice.ui.theme.Customer.Functions.RoomCustomer

fun saveHotelIDInToken(context: Context, hotelId: String) {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString("hotelId", hotelId)
    editor.apply()
}


fun saveHotelName(context: Context, name: String) {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString("hotelName", name)
    editor.apply()
}

fun AddRoomInCart(context: Context,hotelId: String,roomID: String,name:String){
    var localHotelId =  getHotelID(context)
    if(localHotelId.isEmpty()){
        saveHotelIDInToken(context, hotelId)
        saveHotelName(context,name)
    }
    localHotelId = getHotelID(context)
    if(localHotelId==hotelId){
        var CartRooms = getRoomsInCart(context)
        var alreadyExist = false
        CartRooms.forEach { room->
            if(room==roomID){
                alreadyExist=true
                Toast
                    .makeText(
                        context,
                        "Room Already Added.",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
        if(alreadyExist==false){
            addRoomToCart(context, roomID)
            Toast
                .makeText(
                    context,
                    "Room is Added in Cart.",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }
    else{
        Toast
            .makeText(
                context,
                "You cannot add Rooms from different Hotels in a Single Cart.",
                Toast.LENGTH_SHORT
            )
            .show()
    }
}

fun addRoomToCart(context: Context,roomID: String){
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val roomsList = mutableSetOf<String>()
    var ExistingRoomList = getRoomsInCart(context)
    ExistingRoomList.forEach { it->
        Log.d("HUJIA",it)
        roomsList.add(it)
    }
    roomsList.add(roomID)
    editor.putStringSet("rooms",roomsList)
    editor.apply()
}

fun ClearCart(context: Context){
    saveHotelIDInToken(context,"")
    saveHotelName(context,"")
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val roomsList = mutableSetOf<String>()
    editor.putStringSet("rooms",roomsList)
    editor.apply()
}

fun getRoomsInCart(context: Context): Set<String> {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Retrieve the token from SharedPreferences, defaulting to an empty string if not found
    return sharedPreferences.getStringSet("rooms", emptySet()) ?: emptySet()
}

fun getHotelID(context: Context): String {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Retrieve the token from SharedPreferences, defaulting to an empty string if not found
    return sharedPreferences.getString("hotelId", "") ?: ""
}

fun getHotelName(context: Context): String {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Retrieve the token from SharedPreferences, defaulting to an empty string if not found
    return sharedPreferences.getString("hotelName", "") ?: ""
}
fun getRoomsIdinCart(context: Context): MutableSet<String> {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val roomsList = mutableSetOf<String>()
    var ExistingRoomList = getRoomsInCart(context)
    ExistingRoomList.forEach { it->
        Log.d("HUJIA",it)
        roomsList.add(it)
    }
    return roomsList
}

fun removeRoom(context: Context,roomID: String): MutableSet<String> {
    // Get the SharedPreferences object
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    // Save the token to SharedPreferences
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val roomsList = mutableSetOf<String>()
    var ExistingRoomList = getRoomsInCart(context)
    ExistingRoomList.forEach { it->
        Log.d("HUJIA",it)
        roomsList.add(it)
    }
    if(roomsList.isEmpty()){
        saveHotelName(context,"")
        saveHotelIDInToken(context,"")
    }
    else{
        roomsList.remove(roomID)
    }
    editor.putStringSet("rooms",roomsList)
    editor.apply()
    return roomsList
}