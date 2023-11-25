package com.example.adminoffice.ui.theme.Utils.DataClasses

data class ServiceCategory(
    val _id: String,
    val title: String,
    val image: String,
    val isDeleted: Boolean,
    val deletedAt: String?,
    val createdAt: String,
    val updatedAt: String,
    val __v:String
)

data class Service(
    val _id: String,
    val serviceCategory: ServiceCategory,
    val type: String,
    val name: String,
    val description: String,
    val image: String,
    val priceRate: String,
    val price: Int,
    val addedByRole: String,
    val visible: Boolean,
    val isDeleted: Boolean,
    val deletedAt: String?,
    val createdAt: String,
    val updatedAt: String,
    val __v:String
)

data class Refund(
    val percentage: Int,
    val days: Int
)

data class RefundPolicy(
    val description: String,
    val refunds: List<Refund>
)

data class HotelData(
    val ownerid: String,
    val name: String,
    val description: String,
    val location: String,
    val lat: Double,
    val lng: Double,
    val city: String,
    val websiteURL: String,
    val instagramURL: String,
    val facebookURL: String,
    val phoneNo: String,
    val telephoneNo: String,
    val services: List<Service>,
    val images: List<String>,
    val videos: List<String>,
    val floors: List<String>,
    val refundPolicy: RefundPolicy
)

