package cn.nexttec.tanementapi.bean


data class CampusResponse(
        val success :Int,
        val count :Int,
        val message:String,
        val campuses:List<Campus>
)