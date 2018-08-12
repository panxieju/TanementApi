package cn.nexttec.tanementapi.api

import cn.nexttec.tanementapi.Urls.COMMON_STATIONS_URL
import cn.nexttec.tanementapi.Urls.REACH_CAMPUS_URL
import cn.nexttec.tanementapi.Urls.REACH_STATIONS_URL
import cn.nexttec.tanementapi.bean.CampusResponse
import cn.nexttec.tanementapi.bean.StationResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TrafficApi{
    //获取可达的小区列表
    @POST(REACH_CAMPUS_URL)
    fun getReachCampus(@Body requestBody: RequestBody): Observable<CampusResponse>

    @POST(REACH_STATIONS_URL)
    fun getReachStations(@Body requestBody: RequestBody):Observable<StationResponse>

    @POST(COMMON_STATIONS_URL)
    fun getCommonStation(@Body requestBody: RequestBody):Observable<StationResponse>
}