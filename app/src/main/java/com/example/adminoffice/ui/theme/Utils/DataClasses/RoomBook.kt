package com.example.adminoffice.ui.theme.Utils.DataClasses

import com.example.adminoffice.ui.theme.Utils.DataClasses.Rooms.Service

data class RoomBook(
    val _id: String,
    val adults: Int,
    val children: Int,
    val deletedAt: Any,
    val isDeleted: Boolean,
    val price: Int,
    val roomNumber: String,
    val roomType: String,
    val services: List<String>,
    val size: String,
    val type: String
)