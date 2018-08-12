package cn.nexttec.tanementapi.api

import cn.nexttec.tanementapi.Urls.FEEDBACK_URL
import cn.nexttec.tanementapi.Urls.LOGIN_URL
import cn.nexttec.tanementapi.bean.LoginResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST


interface AppApi{
    @POST(LOGIN_URL)
    fun login(@Body requestBody: RequestBody):Observable<LoginResponse>

    @POST(FEEDBACK_URL)
    fun feedback(@Body requestBody: RequestBody):Observable<ResponseBody>
}