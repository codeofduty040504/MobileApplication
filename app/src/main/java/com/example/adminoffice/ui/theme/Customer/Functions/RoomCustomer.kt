package com.example.adminoffice.ui.theme.Customer.Functions

import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service

data class RoomCustomer(
    val _id: String,
    val adults: Int,
    val children: Int,
    val description: String,
    val floor: String,
    val images: List<String>,
    val inventories: List<Any>,
    val price: Int,
    val roomNumber: String,
    val services: List<Any>,
    val size: String,
    val type: String,
    val videos: List<String>,
)


data class RoomCustomerBooking(
    val _id: String,
    val adults: Int,
    val children: Int,
    val description: String,
    val floor: String,
    val images: List<String>,
    val inventories: List<Any>,
    val price: Int,
    val roomNumber: String,
    val services: List<Service>,
    val size: String,
    val type: String,
    val videos: List<String>,
)