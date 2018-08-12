package cn.nexttec.tanementapi.bean


data class StationResponse(
        val success:Int,
        val count:Int,
        val message:String,
        val stations:List<Station>
)