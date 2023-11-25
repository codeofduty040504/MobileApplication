package com.example.adminoffice.ui.theme.Utils.DataClasses.Bookings

data class Booking(
    val __v: Int,
    val _id: String,
    val adults: Int,
    val bookingDetails: String,
    val checkIn: String,
    val checkInDate: String,
    val checkOut: String,
    val checkOutDate: String,
    val childern: Int,
    val customer: Customer,
    val customerName: String,
    val hotel: Hotel,
    val hotelName: String,
    val roomCharges: Int,
    val rooms: List<Room>,
    val serviceCharges: Int,
    val status: String,
)
data class Customer(
    val _id: String,
    val userid: Userid
)
data class Hotel(
    val __v: Int,
    val _id: String,
    val city: String,
    val description: String,
    val name: String,
    val refundPolicy: RefundPolicy,
)

data class Refund(
    val _id: String,
    val days: Int,
    val percentage: Int
)

data class RefundPolicy(
    val _id: String,
    val description: String,
    val refunds: List<Refund>,
)

data class Userid(
    val _id: String,
    val cnic: String,
    val contactNo: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val profilePicture: String,
    val role: String,
    val status: String,
)

data class Room(
    val description: String,
    val images: List<String>,
    val roomNumber: String,
    val type: String,
    val  price : Int
)