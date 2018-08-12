package cn.nexttec.tanementapi.bean

data class CommonStationQuery(
        val token:String,
        val lat1:Double,
        val lon1:Double,
        val lat2:Double,
        val lon2:Double,
        val city:String
)