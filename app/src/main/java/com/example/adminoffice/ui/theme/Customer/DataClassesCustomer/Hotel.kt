package com.example.adminoffice.ui.theme.Customer.DataClassesCustomer

data class Hotel(
    val __v: Int,
    val _id: String,
    val averagePrice: Int,
    val averageRating: Int,
    val city: String,
    val createdAt: String,
    val deletedAt: Any,
    val description: String,
    val facebookURL: String,
    val floors: List<String>,
    val hotelOwner: HotelOwner,
    val id: Int,
    val images: List<String>,
    val instagramURL: String,
    val isDeleted: Boolean,
    val lat: Double,
    val lng: Double,
    val location: String,
    val name: String,
    val ownerName: String,
    val phoneNo: String,
    val refundPolicy: RefundPolicy,
    val reviews: List<Any>,
    val rules: List<Any>,
    val services: List<Service>,
    val status: String,
    val telephoneNo: String,
    val totalReviews: Int,
    val updatedAt: String,
    val videos: List<String>,
    val websiteURL: String,
    val rooms: List<Room>
)
data class HotelOwner(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val deletedAt: Any,
    val isDeleted: Boolean,
    val updatedAt: String,
    val userid: Userid
)

data class Refund(
    val _id: String,
    val days: Int,
    val deletedAt: Any,
    val isDeleted: Boolean,
    val percentage: Int
)

data class RefundPolicy(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val deletedAt: Any,
    val description: String,
    val isDeleted: Boolean,
    val refunds: List<Refund>,
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

data class Userid(
    val __v: Int,
    val _id: String,
    val cnic: String,
    val contactNo: String,
    val createdAt: String,
    val deletedAt: Any,
    val email: String,
    val firstName: String,
    val isDeleted: Boolean,
    val lastName: String,
    val password: String,
    val profilePicture: String,
    val role: String,
    val status: String,
    val updatedAt: String,
    val userCategory: String,
    val verified: Boolean
)
data class Room(
    val _id: String,
    val hotel: String,
    val floor: String,
    val roomNumber: String,
    val type: String,
    val description: String,
    val images: List<String>,
    val videos: List<String>,
    val adults: Int,
    val children: Int,
    val price: Int,
    val size: String,
    val panoramicView: String?,
    val roomServices: List<String>,
    val inventories: List<String>,
    val isDeleted: Boolean,
    val deletedAt: String?,
    val models: List<String>,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)