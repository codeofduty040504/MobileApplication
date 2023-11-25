package com.example.adminoffice.ui.theme.Utils.Screens.Bookings

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Hotel
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.HotelOwner
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Refund
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.RefundPolicy
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Service
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.ServiceCategory
import com.example.adminoffice.ui.theme.Utils.DataClasses.Hotels.Userid
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.example.adminoffice.ui.theme.Utils.isInternetAvailable
import org.json.JSONObject

//// GET Hotels Function
//fun getHotels(context: Context) {
//    val url = "https://scs-backend-code.herokuapp.com/admin/hotels/getHotels"
//    val progressDialog = ProgressDialog(context)
//    val params = JSONObject()
//    progressDialog.setTitle("Loading Hotels...")
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
//            Request.Method.GET, url, params,
//            { response ->
//                AddBooking.Hotels.clear()
//                Log.d("HAPP",response.toString())
//                var hotels  = response.getJSONArray("hotels")
//                for(i in 0 until hotels.length()){
//                    try{
//
//                        var hotel = hotels.getJSONObject(i)
//                        var ServiceList = mutableStateListOf<Service>()
//                        var VideoList = mutableStateListOf<String>()
//                        var FloorList = mutableStateListOf<String>()
//                        var RulesList = mutableStateListOf<String>()
//                        var ReviewsList = mutableStateListOf<String>()
//                        var RefundList = mutableStateListOf<Refund>()
//                        var ImageList = mutableStateListOf<String>()
//
//                        var hotelOwner = hotel.getJSONObject("hotelOwner")
//                        var hotelOwner_id = hotelOwner.getString("_id")
//                        var hotelOwneruserid = hotelOwner.getJSONObject("userid")
//                        var hotelOwneruseriduserCategory= hotelOwneruserid.getString("userCategory")
//                        var hotelOwneruseriduser_id= hotelOwneruserid.getString("_id")
//                        var hotelOwneruseridfirstName = hotelOwneruserid.getString("firstName")
//                        var hotelOwneruseridlastName = hotelOwneruserid.getString("lastName")
//                        var hotelOwneruseridemail = hotelOwneruserid.getString("email")
//                        var hotelOwneruseridpassword = hotelOwneruserid.getString("password")
//                        var hotelOwneruseridcontactNo = hotelOwneruserid.getString("contactNo")
//                        var hotelOwneruseridcnic = hotelOwneruserid.getString("cnic")
//                        var hotelOwneruseridprofilePicture = hotelOwneruserid.getString("profilePicture")
//                        var hotelOwneruseridstatus = hotelOwneruserid.getString("status")
//                        var hotelOwneruseridrole = hotelOwneruserid.getString("role")
//                        var hotelOwneruseridverified = hotelOwneruserid.getBoolean("verified")
//                        var hotelOwneruseridisDeleted = hotelOwneruserid.getBoolean("isDeleted")
//                        var hotelOwneruseriddeletedAt = hotelOwneruserid.get("deletedAt")
//                        var hotelOwneruseridcreatedAt = hotelOwneruserid.getString("createdAt")
//                        var hotelOwneruseridupdatedAt = hotelOwneruserid.getString("updatedAt")
//                        var hotelOwneruserid__v = hotelOwneruserid.getInt("__v")
//                        var hotelOwnerisDeleted = hotelOwner.get("isDeleted")
//                        var hotelOwnerdeletedAt = hotelOwner.get("deletedAt")
//                        var hotelOwnercreatedAt = hotelOwner.getString("createdAt")
//                        var hotelOwnerupdatedAt = hotelOwner.getString("updatedAt")
//                        var hotelOwner__v = hotelOwner.getInt("__v")
//                        var userID = Userid(__v = hotelOwneruserid__v, _id = hotelOwneruseriduser_id, cnic = hotelOwneruseridcnic,
//                            contactNo = hotelOwneruseridcontactNo, createdAt = hotelOwneruseridcreatedAt, deletedAt = hotelOwneruseriddeletedAt,
//                            email = hotelOwneruseridemail, firstName = hotelOwneruseridfirstName, isDeleted = hotelOwneruseridisDeleted,
//                            lastName = hotelOwneruseridlastName, password = hotelOwneruseridpassword, profilePicture = hotelOwneruseridprofilePicture,
//                            role = hotelOwneruseridrole, status = hotelOwneruseridstatus, updatedAt = hotelOwneruseridupdatedAt, userCategory = hotelOwneruseriduserCategory,
//                            verified = hotelOwneruseridverified)
//                        var hotelOwnerOBJ = HotelOwner(__v = hotelOwner__v, _id = hotelOwner_id, createdAt = hotelOwnercreatedAt,
//                            deletedAt = hotelOwnerdeletedAt, isDeleted = hotelOwneruseridisDeleted, updatedAt = hotelOwnerupdatedAt,
//                            userid = userID)
//
//
//                        var _id = hotel.getString("_id")
//                        var id = hotel.getString("id")
//                        var hotelname = hotel.getString("name")
//                        var hoteldescription = hotel.getString("description")
//                        var hotellocation = hotel.getString("location")
//                        var hotellat = hotel.getDouble("lat")
//                        var hotellng = hotel.getDouble("lng")
//                        var hotelcity = hotel.getString("city")
//                        var hotelwebsiteURL = hotel.getString("websiteURL")
//                        var hotelstatus = hotel.getString("status")
//                        var hotelinstagramURL = hotel.getString("instagramURL")
//                        var hotelfacebookURL = hotel.getString("facebookURL")
//                        var hotelcreatedAt = hotel.getString("createdAt")
//                        var hotelupdatedAt = hotel.getString("updatedAt")
//                        var hotelisDeleted = hotel.getBoolean("isDeleted")
//                        var hoteldeletedAt = hotel.get("deletedAt")
//                        var hotel__v = hotel.getInt("__v")
//                        var hotelaverageRating = hotel.getInt("averageRating")
//                        var hoteltotalReviews = hotel.getInt("totalReviews")
//                        var hotelaveragePrice = hotel.getInt("averagePrice")
//                        var hotelid = hotel.getInt("id")
//                        var hotelownerName = hotel.getString("ownerName")
//                        var hotelphoneNo = hotel.getString("phoneNo")
//                        var hoteltelephoneNo = hotel.getString("telephoneNo")
//
//                        var hotelimages = hotel.getJSONArray("images")
//                        for(i in 0 until hotelimages.length()){
//                            var hotelImage = hotelimages.getString(i)
//                            ImageList.add(hotelImage)
//                        }
//
//                        var hotelvideos = hotel.getJSONArray("videos")
//                        for(i in 0 until hotelvideos.length()){
//                            var hotelVideoList = hotelvideos.getString(i)
//                            VideoList.add(hotelVideoList)
//                        }
//
//                        var hotelrules = hotel.getJSONArray("rules")
//                        for(i in 0 until hotelrules.length()){
//                            var hotelRulesList = hotelrules.getString(i)
//                            RulesList.add(hotelRulesList)
//                        }
//                        var hotelreviews = hotel.getJSONArray("reviews")
//                        for(i in 0 until hotelreviews.length()){
//                            var hotelReviewList = hotelreviews.getString(i)
//                            ReviewsList.add(hotelReviewList)
//                        }
//                        var hotelfloors = hotel.getJSONArray("floors")
//                        for(i in 0 until hotelfloors.length()){
//                            var hotelFloorList = hotelfloors.getString(i)
//                            FloorList.add(hotelFloorList)
//                        }
//
//                        var hotelservices = hotel.getJSONArray("services")
//                        for(i in 0 until hotelservices.length()){
//                            var serviceOBJ = hotelservices.getJSONObject(i)
//                            var hotelservices_id = serviceOBJ.getString("_id")
//                            var hotelservicestype = serviceOBJ.getString("type")
//                            var hotelservicesname = serviceOBJ.getString("name")
//                            var hotelservicesdescription = serviceOBJ.getString("description")
//                            var hotelservicesimage = serviceOBJ.getString("image")
//                            var hotelservicespriceRate = serviceOBJ.getString("priceRate")
//                            var hotelservicesprice = serviceOBJ.getInt("price")
//                            var hotelservicesaddedByRole = serviceOBJ.getString("addedByRole")
//                            var hotelservicesdeletedAt = serviceOBJ.get("deletedAt")
//                            var hotelservicesvisible = serviceOBJ.getBoolean("visible")
//                            var hotelservicesisDeleted = serviceOBJ.getBoolean("isDeleted")
//                            var hotelservicescreatedAt = serviceOBJ.getString("createdAt")
//                            var hotelservicesupdatedAt = serviceOBJ.getString("updatedAt")
//                            var hotelservices__v = serviceOBJ.getInt("__v")
//                            var hotelservicesserviceCategory = serviceOBJ.getJSONObject("serviceCategory")
//                            var hotelservicesserviceCategory_id = hotelservicesserviceCategory.getString("_id")
//                            var hotelservicesserviceCategorytitle = hotelservicesserviceCategory.getString("title")
//                            var hotelservicesserviceCategoryimage = hotelservicesserviceCategory.getString("image")
//                            var hotelservicesserviceCategoryisDeleted = hotelservicesserviceCategory.getBoolean("isDeleted")
//                            var hotelservicesserviceCategorydeletedAt = hotelservicesserviceCategory.get("deletedAt")
//                            var hotelservicesserviceCategorycreatedAt = hotelservicesserviceCategory.getString("createdAt")
//                            var hotelservicesserviceCategoryupdatedAt = hotelservicesserviceCategory.getString("updatedAt")
//                            var hotelservicesserviceCategory__v = hotelservicesserviceCategory.getInt("__v")
//                            var cat = ServiceCategory(__v = hotelservicesserviceCategory__v,_id=hotelservicesserviceCategory_id, createdAt = hotelservicesserviceCategorycreatedAt,
//                                deletedAt =hotelservicesserviceCategorydeletedAt, image = hotelservicesserviceCategoryimage, isDeleted = hotelservicesserviceCategoryisDeleted,
//                                title = hotelservicesserviceCategorytitle, updatedAt = hotelservicesserviceCategoryupdatedAt)
//                            var ser = Service(__v = hotelservices__v,_id=hotelservices_id, addedByRole = hotelservicesaddedByRole,
//                                createdAt = hotelservicescreatedAt, deletedAt = hotelservicesdeletedAt, description = hotelservicesdescription,
//                                image = hotelservicesimage,isDeleted = hotelservicesisDeleted, name = hotelservicesname,
//                                price = hotelservicesprice, priceRate = hotelservicespriceRate, type = hotelservicestype,
//                                updatedAt = hotelservicesupdatedAt, visible = hotelservicesvisible, serviceCategory = cat)
//                            ServiceList.add(ser)
//                        }
//
//
//                        var hotelrefundPolicy = hotel.getJSONObject("refundPolicy")
//                        var hotelrefundPolicy_id = hotelrefundPolicy.getString("_id")
//                        var hotelrefundPolicydescription = hotelrefundPolicy.getString("description")
//                        var hotelrefundPolicyrefunds = hotelrefundPolicy.getJSONArray("refunds")
//                        for(i in 0 until hotelrefundPolicyrefunds.length()){
//                            var refundsOBJ = hotelrefundPolicyrefunds.getJSONObject(i)
//                            var refundsOBJdays = refundsOBJ.getInt("days")
//                            var refundsOBJpercentage = refundsOBJ.getInt("percentage")
//                            var refundsOBJ_id = refundsOBJ.getString("_id")
//                            var refundsOBJisDeleted = refundsOBJ.getBoolean("isDeleted")
//                            var refundsOBJdeletedAt = refundsOBJ.get("deletedAt")
//                            var refundOBJ = Refund(_id=refundsOBJ_id, days = refundsOBJdays, deletedAt = refundsOBJdeletedAt,
//                                isDeleted = refundsOBJisDeleted, percentage = refundsOBJpercentage)
//                            RefundList.add(refundOBJ)
//
//                        }
//
//                        var hotelrefundPolicyisDeleted = hotelrefundPolicy.getBoolean("isDeleted")
//                        var hotelrefundPolicydeletedAt = hotelrefundPolicy.get("deletedAt")
//                        var hotelrefundPolicycreatedAt = hotelrefundPolicy.getString("createdAt")
//                        var hotelrefundPolicyupdatedAt = hotelrefundPolicy.getString("updatedAt")
//                        var hotelrefundPolicy__v = hotelrefundPolicy.getInt("__v")
//
//                        var RefundPolicy = RefundPolicy(__v = hotelrefundPolicy__v,_id=hotelrefundPolicy_id, createdAt = hotelrefundPolicycreatedAt,
//                            deletedAt = hotelrefundPolicydeletedAt, description = hotelrefundPolicydescription, isDeleted = hotelrefundPolicyisDeleted,
//                            refunds = RefundList, updatedAt = hotelrefundPolicyupdatedAt)
//
//
//                        var Hotel = Hotel(__v = hotel__v,_id=_id, averagePrice = hotelaveragePrice, averageRating = hotelaverageRating,
//                            city = hotelcity, createdAt = hotelcreatedAt, deletedAt = hoteldeletedAt, description = hoteldescription,
//                            facebookURL = hotelfacebookURL, floors = FloorList, hotelOwner = hotelOwnerOBJ, id = hotelid, images = ImageList,
//                            instagramURL = hotelinstagramURL, isDeleted = hotelisDeleted, lat = hotellat, lng = hotellng, location = hotellocation,
//                            name = hotelname, ownerName = hotelownerName, phoneNo = hotelphoneNo, refundPolicy =RefundPolicy, reviews = ReviewsList,
//                            rules = RulesList, services = ServiceList, status = hotelstatus, telephoneNo = hoteltelephoneNo, totalReviews = hoteltotalReviews,
//                            updatedAt = hotelupdatedAt, videos = VideoList, websiteURL = hotelwebsiteURL)
//
//                        AddBooking.Hotels.add(Hotel)
//                    }
//                    catch (e:Exception){
//                        e.printStackTrace()
//                    }
//                }
//                progressDialog.dismiss()
//            },
//            { error ->
//                Log.d("HASHDASDAS",error.toString())
//                AddBooking.Hotels.clear()
//                AddBooking.HotelJsonArrayError = mutableStateOf(error.toString())
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