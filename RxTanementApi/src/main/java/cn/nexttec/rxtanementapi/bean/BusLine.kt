package cn.nexttec.rxtanementapi.bean

import java.time.Duration

data class BusLine(
        val line_name:String,
        val depart_station:String,
        val arrival_station:String,
        val duration: String
)