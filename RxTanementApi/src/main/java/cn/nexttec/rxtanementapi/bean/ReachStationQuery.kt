package cn.nexttec.tanementapi.bean


data class ReachStationQuery(
    val token:String,
    val lat:Double,
    val lon:Double,
    val city:String
)