package com.example.adminoffice.ui.theme.Utils.DataClasses.Payments

data class Payment(
    val _id: String,
    val amount: Int,
    val id: Int,
    val type: String,
    val bookingID: String
)