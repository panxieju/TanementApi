package cn.nexttec.tanementapi.api

import cn.nexttec.tanementapi.Urls.GET_NEARBY_HOUSE_URL
import cn.nexttec.tanementapi.Urls.GET_REACHABLE_HOUSE_URL
import cn.nexttec.tanementapi.bean.HouseResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface HouseApi{
    //获取周边的房源
    @POST(GET_NEARBY_HOUSE_URL)
    fun getNearbyHouse(@Body requestBody: RequestBody):Observable<HouseResponse>

    //获取可达房源
    @POST(GET_REACHABLE_HOUSE_URL)
    fun getReachableHouse(@Body requestBody: RequestBody):Observable<HouseResponse>

}