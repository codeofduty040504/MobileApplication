package com.example.adminoffice.ui.theme.Customer

data class HotelDataClassCustomer(
    val name:String,
    val location:String,
    val description:String,
    val images:String,
    val phoneNo:String,
    val telephoneNo:String,
    val service : List<Service>
)
data class Service(
    val name: String,
    val price:String,
    val image:String
)