package com.example.adminoffice.ui.theme.Customer.Functions

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