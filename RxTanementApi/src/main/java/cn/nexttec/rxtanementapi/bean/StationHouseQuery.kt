package cn.nexttec.tanementapi.bean


data class StationHouseQuery(val token: String,
                             val stations: List<Station>,
                             val lat: Double,
                             val lon: Double,
                             val city: String
)
