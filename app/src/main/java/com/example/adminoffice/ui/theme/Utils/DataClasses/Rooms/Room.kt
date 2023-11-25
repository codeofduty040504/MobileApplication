package com.example.adminoffice.ui.theme.Utils.DataClasses.Rooms

data class Room(
    val __v: Int,
    val _id: String,
    val adults: Int,
    val children: Int,
    val createdAt: String,
    val deletedAt: Any,
    val description: String,
    val floor: String,
    val hotel: Hotel,
    val hotelName: String,
    val id: Int,
    val images: List<String>,
    val inventories: List<Inventory>,
    val isDeleted: Boolean,
    val models: List<Any>,
    val price: Int,
    val roomNumber: String,
    val services: List<Service>,
    val size: String,
    val type: String,
    val updatedAt: String,
    val videos: List<Any>
)

data class Hotel(
    val _id: String,
    val floors: List<Any>,
    val name: String,
)

data class Inventory(
    val __v: Int,
    val _id: String,
    val addedByRole: String,
    val color: String,
    val createdAt: String,
    val deletedAt: Any,
    val description: String,
    val image: String,
    val inventoryCategory: String,
    val isDeleted: Boolean,
    val name: String,
    val updatedAt: String
)

data class Service(
    val __v: Int,
    val _id: String,
    val addedByRole: String,
    val createdAt: String,
    val deletedAt: Any,
    val description: String,
    val image: String,
    val isDeleted: Boolean,
    val name: String,
    val price: Int,
    val priceRate: String,
    val serviceCategory: ServiceCategory,
    val type: String,
    val updatedAt: String,
    val visible: Boolean
)


data class ServiceCategory(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val deletedAt: Any,
    val image: String,
    val isDeleted: Boolean,
    val title: String,
    val updatedAt: String
)