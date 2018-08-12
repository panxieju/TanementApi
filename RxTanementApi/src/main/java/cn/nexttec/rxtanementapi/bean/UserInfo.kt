package cn.nexttec.tanementapi.bean


/**
 * Created by Centros on 2017/10/23.
 * 用户信息，记录登录时手机信息，型号及当前版本和所在位置
 */

data class UserInfo(
        var imei: String?, var imsi: String?, var model: String?, var version: String?, var login_location: String?, var lat: Double, var lon: Double)
