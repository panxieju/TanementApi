package cn.nexttec.tanementapi.bean


import cn.nexttec.rxtanementapi.bean.BusLine
import java.util.HashSet

/**
 * Created by Centros on 2017/9/11.
 */

class House(
        val md5: String,
        val title: String,
        val time: String,
        val date: String,
        val rental: Float,
        val campus: String,
        val rent_type: String,
        val house_type: String,
        val area: String,
        val floor: String,
        val district: String,
        val city: String,
        val address: String,
        val url: String,
        val image_url: String,
        val source: String,
        val rooms: Int,
        val lat: Double,
        val lon: Double,
        val contact: String,
        val phone: String,
        val is_straight_reach:Boolean,
        val lines:List<BusLine>,
        val distance:Float,
        val walk_distance:Float,
        val duration:Float

)
