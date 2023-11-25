package com.example.adminoffice.ui.theme.Customer.Coupons

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.adminoffice.ui.theme.Utils.getTokenFromLocalStorage
import com.google.ar.core.Config
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode
import org.json.JSONObject


data class ARCoupon(
    val hotelname: String,
    val couponid: String,
    val discount:String,
    val latitude:String,
    val longitude:String,
    val coinModel:String
) : Screen {
    val couponIDD =mutableStateOf("")

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        var nodes = remember {
            mutableListOf<ArNode>()
        }
        var lifecycleScope = rememberCoroutineScope()
        val modelNode = remember {
            mutableStateOf<ArModelNode?>(null)
        }
        val placeModelButton = remember {
            mutableStateOf(false)
        }

        Column {
            Box(modifier = Modifier.fillMaxSize()){
                ARScene(
                    modifier = Modifier.fillMaxSize(),
                    nodes = nodes,
                    planeRenderer = true,
                    onCreate = {arSceneView ->
                        arSceneView.lightEstimationMode = Config.LightEstimationMode.DISABLED
                        arSceneView.planeRenderer.isShadowReceiver = false
                        modelNode.value = ArModelNode(arSceneView.engine, PlacementMode.INSTANT).apply {
                            loadModelGlbAsync(
                                glbFileLocation = coinModel,
                                scaleToUnits = 0.4f
                            )
                            {

                            }
                            onAnchorChanged = {
                                placeModelButton.value = !isAnchored
                            }
                            onHitResult = {node, hitResult ->
                                placeModelButton.value = node.isTracking
                            }

                        }
                        nodes.add(modelNode.value!!)
                    },
                    onSessionCreate = {
                        planeRenderer.isVisible = false
                    },

                    )
                FloatingActionButton(
                    onClick = {
                        claimMarker(context) { isSuccess ->
                            if(isSuccess){
                                nodes.clear()
                                 navigator.push(ConfirmCoupon(hotelname=hotelname,couponid=couponIDD.value,discount=discount))
                            }

                        }
                    },
                    modifier = Modifier
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(
                                    x = 350,
                                    y = 2000
                                )
                            }
                        }
                        .height(50.dp)
                        .width(150.dp),
                    contentColor = Color.White,
                    containerColor = Color.Black
                ) {
                    Text(text = "Claim Coupon", color = Color.White)
                }


                // Places the coin on the plane/Surface with a 3 second delay
                Handler().postDelayed({
                    modelNode.value?.anchor()
                }, 3000)
            }
        }

    }
    // claim Coupon Function
    fun claimMarker(context: Context, callback: (Boolean) -> Unit) {
        val url = "https://scs-backend-code.herokuapp.com/customer/coupons/claimCoupon/$couponid"

        // Request parameters
        val params = JSONObject()
        params.put("lat", latitude)
        params.put("lng", longitude)

        val request = object : JsonObjectRequest(
            Request.Method.PUT, url, params,
            { response ->
                // Handle successful login response
                Log.d("rerrtr", response.toString())
                couponIDD.value = response.getString("code")
                //val isCouponClaimed = response.optBoolean("valid", false)
                callback(true)
            },
            { error ->
                // Handle error response
                Log.d("rerrtr", error.toString())
                callback(false)
            }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val token = getTokenFromLocalStorage(context = context)
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "${token}"
                return headers
            }
        }


        // Add the request to the RequestQueue.
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

}