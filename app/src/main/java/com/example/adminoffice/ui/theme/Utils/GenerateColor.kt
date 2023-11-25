package com.example.adminoffice.ui.theme.Utils

import android.graphics.Color
import kotlin.random.Random

fun generateRandomColor(): Int {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color.rgb(red, green, blue)
}