package cn.nexttec.tanementapi.bean


data class HouseResponse(
        val success: Int,
        val count: Int,
        val message: String,
        val houses: List<House>
)