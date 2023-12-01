package com.example.adminoffice.ui.theme.Customer.DataClassesCustomer

data class Review(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val customer: Customer,
    val deletedAt: Any,
    val description: String,
    val isDeleted: Boolean,
    val stars: Int,
    val title: String,
    val updatedAt: String
)

data class Customer(
    val name : String,
    val profile: String
)