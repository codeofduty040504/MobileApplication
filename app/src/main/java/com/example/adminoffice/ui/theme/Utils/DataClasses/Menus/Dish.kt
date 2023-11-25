package com.example.adminoffice.ui.theme.Utils.DataClasses.Menus

data class Dish(
    val _id: String,
    val addedByRole: String,
    val categoryTitle: String,
    val description: String,
    val dishCategory: DishCategory,
    val id: Int,
    val images: List<String>,
    val name: String,
    val price: Int,
    val servings: Int
)
data class DishCategory(
    val _id: String,
    val description: String,
    val image: String,
    val title: String
)