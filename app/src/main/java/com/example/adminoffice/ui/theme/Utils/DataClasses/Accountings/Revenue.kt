package com.example.adminoffice.ui.theme.Utils.DataClasses.Accountings

data class Revenue(
    val _id: String,
    val amount: Int,
    val date: String,
    val details: String,
    val hotelName: String,
    val id: Int,
    val image: String,
    val referenceTitle: String,
    val hotelID: String
)

data class Expense(
    val _id: String,
    val amount: Int,
    val date: String,
    val details: String,
    val hotelName: String,
    val type: String,
    val id: Int,
    val image: String,
    val referenceTitle: String,
    val hotelID: String
)