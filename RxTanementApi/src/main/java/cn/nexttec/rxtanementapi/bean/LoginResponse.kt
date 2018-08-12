package cn.nexttec.tanementapi.bean

/**
 * Created by Centros on 2017/10/23.
 */

data class LoginResponse(
        var token: String,
        var success: Int = 0,
        var version: String?,
        var descrpition: String?,
        var url: String?
)

