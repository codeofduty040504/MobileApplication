package com.example.adminoffice.ui.theme.Utils.DataClasses.Inventories

data class Inventory(
    val __v: Int,
    val _id: String,
    val addedByRole: String,
    val category: String,
    val color: String,
    val createdAt: String,
    val deletedAt: Any,
    val description: String,
    val id: Int,
    val image: String,
    val inventoryCategory: InventoryCategory,
    val isDeleted: Boolean,
    val name: String,
    val updatedAt: String
)

data class InventoryCategory(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val deletedAt: Any,
    val image: String,
    val isDeleted: Boolean,
    val title: String,
    val updatedAt: String
)