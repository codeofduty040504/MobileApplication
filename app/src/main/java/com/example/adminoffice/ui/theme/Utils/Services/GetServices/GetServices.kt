package com.example.adminoffice.ui.theme.Utils.Services.GetServices

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.ui.theme.Utils.DataClasses.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.ServiceCategory
import com.example.adminoffice.ui.theme.Utils.Screens.Hotels.AddHotel
import com.example.adminoffice.ui.theme.Utils.Screens.Services.ViewService
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable

//// GET Categories Function
//fun getServices(context: Context) {
//    val url = "https://scs-backend-code.herokuapp.com/admin/services/getServices"
//    val progressDialog = ProgressDialog(context)
//    progressDialog.setTitle("Loading Services...")
//    progressDialog.show()
//    if(!isInternetAvailable(context)){
//        Toast
//            .makeText(
//                context,
//                "Internet not available.",
//                Toast.LENGTH_SHORT
//            )
//            .show()
//    }
//    else{
//        val request = object : JsonObjectRequest(
//            Request.Method.GET, url, ViewService.params,
//            { response ->
//                Log.d("HASHDASDAS",response.toString())
//                var categories  = response.getJSONArray("services")
//                AddHotel.services.clear()
//                for(i in 0 until categories.length()){
//                    var category = categories.getJSONObject(i)
//
//                    var id = category.getInt("id")
//                    var _id = category.getString("_id")
//                    var serviceImage = category.getString("image")
//                    var serviceName = category.getString("name")
//                    var servicecategory = category.getString("category")
//                    var servicedescription = category.getString("description")
//                    var serviceprice = category.getInt("price")
//                    var servicepriceRate = category.getString("priceRate")
//                    var serviceaddedByRole = category.getString("addedByRole")
//                    var servicevisible = category.getBoolean("visible")
//                    var serviceisDeleted = category.getBoolean("isDeleted")
//                    var servicedeletedAt = category.getString("deletedAt")
//                    var servicecreatedAt = category.getString("createdAt")
//                    var serviceupdatedAt = category.getString("updatedAt")
//                    var service__v = category.getString("__v")
//                    var serviceType = category.getString("type")
//                    var serviceCa = category.getJSONObject("serviceCategory")
//                    var serviceCategoryID = serviceCa.getString("_id")
//                    var serviceCategoryTitle = serviceCa.getString("title")
//                    var serviceCategoryImage = serviceCa.getString("image")
//                    var serviceCategoryDeletedAt = serviceCa.getString("deletedAt")
//                    var serviceCategoryIsDeleted = serviceCa.getBoolean("isDeleted")
//                    var serviceCategoryCreatedAt = serviceCa.getString("createdAt")
//                    var serviceCategoryUpdatedAt = serviceCa.getString("updatedAt")
//                    var serviceCategory__V = serviceCa.getString("__v")
//                    var serviceCategoryObject = ServiceCategory(_id=serviceCategoryID, title = serviceCategoryTitle, image = serviceCategoryImage, isDeleted = serviceCategoryIsDeleted, deletedAt = serviceCategoryDeletedAt, createdAt = serviceCategoryCreatedAt, updatedAt = serviceCategoryUpdatedAt, __v = serviceCategory__V)
//                    AddHotel.services.add(
//                        Service(_id=_id, serviceCategory = serviceCategoryObject, type = serviceType, name = serviceName, description = servicedescription, image = serviceImage, priceRate = servicepriceRate, price = serviceprice, addedByRole = serviceaddedByRole,visible = servicevisible, isDeleted = serviceisDeleted, deletedAt = servicedeletedAt, createdAt = servicecreatedAt, updatedAt = serviceupdatedAt,
//                            __v = service__v)
//                    )
//                }
//                progressDialog.dismiss()
//            },
//            { error ->
//                AddHotel.services.clear()
//                AddHotel.usersJsonArrayErrorForServices = mutableStateOf(error.toString())
//                progressDialog.dismiss()
//            }) {
//
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Content-Type"] = "application/json"
//                headers["Authorization"] = "${getTokenFromLocalStorage(context)}"
//                return headers
//            }
//        }
//
//        // Add the request to the RequestQueue.
//        val requestQueue = Volley.newRequestQueue(context)
//        requestQueue.add(request)
//    }
//
//}

