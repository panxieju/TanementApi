package cn.nexttec.tanementapi.bean

import com.google.gson.Gson

/**
 * Created by Centros on 2017/10/23.
 */

data class LoginRequest(
        val imei: String?,
        val imsi: String?,
        val model: String?,
        val manufacturer: String?,
        val version: Int,
        val login_location: String?,
        val lat: Double,
        val lon: Double,
        val city: String?)


