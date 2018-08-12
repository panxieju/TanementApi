package cn.nexttec.tanementtest

import TanementLib.Providers.AppProvider
import TanementLib.Providers.HouseProvider
import android.Manifest
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import cn.nexttec.rxamap.RxAMap
import cn.nexttec.rxpermission.RxPermissions
import cn.nexttec.tanementtest.Utils.gson
import cn.nexttec.tanementtest.Utils.requestBody
import cn.nexttec.tanementapi.api.AppApi
import cn.nexttec.tanementapi.api.HouseApi
import cn.nexttec.tanementapi.api.TrafficApi
import cn.nexttec.tanementapi.bean.*

import cn.nexttec.tanementlib.Providers.TrafficProvider
import cn.nexttec.tanementtest.Utils.div
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    private lateinit var token: String
    private lateinit var city: String
    private var lat: Double = 0.toDouble()
    private var lon: Double = 0.toDouble()
    private lateinit var address: String
    private lateinit var district: String
    private lateinit var stations: List<Station>

    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button

    private lateinit var appApi: AppApi
    private lateinit var trafficApi: TrafficApi
    private lateinit var houseApi: HouseApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appApi = AppProvider.service
        houseApi = HouseProvider.service
        trafficApi = TrafficProvider.service

        RxPermissions(this@MainActivity).requestEachCombined(
                listOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
        )
                .flatMap {
                    if (it.granted) {
                        RxAMap.locate(this@MainActivity)
                                .subscribeOn(Schedulers.newThread())
                    } else {
                        Observable.empty()
                    }
                }
                .subscribe {
                    lat = it.latitude
                    lon = it.longitude
                    city = it.city
                    district = it.district
                    address = it.address
                    log("定位成功>$lat,$lon,$address")
                }

        btn1 = findViewById(R.id.button1) as Button
        with(btn1) {
            setOnClickListener {
                login()
            }
        }

        btn2 = findViewById(R.id.button2) as Button
        with(btn2) {
            setOnClickListener {
                getNeatbyCampus()
            }
        }

        btn3 = findViewById(R.id.button3) as Button
        with(btn3) {
            setOnClickListener {
                getReachableStations()
            }
        }

        btn4 = findViewById(R.id.button4) as Button
        with(btn4) {
            setOnClickListener {
                getReachableCampus()
            }
        }

        btn5 = findViewById(R.id.button5) as Button
        with(btn5) {
            setOnClickListener {
                getHouseByStations()
            }
        }

        btn6 = findViewById(R.id.button6) as Button
        with(btn6) {
            setOnClickListener {
                getCommonStation()
            }
        }
    }

    private fun getReachableCampus() {


    }

    private fun getHouseByStations() {
        stations?.let {
            Observable.just(it)
                    .flatMap {
                        val newlist = it / 10
                        Observable.fromIterable(newlist)
                    }
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        val query = StationHouseQuery(
                                token, it, lat, lon, city
                        )
                        log("站点房源请求>${gson(query)}")
                        houseApi.getReachableHouse(requestBody(query))
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.success == 1 && it.count > 0) {
                            it.houses.map { log("可达房源搜索结果>${it.title},${it.campus}") }
                        }
                    }

        }

    }

    fun getReachableHouse() {
        RxAMap.geoSearch(this@MainActivity, "天河城广场", "广州市")
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(Observable.empty())
                .flatMap {
                    log("${it.geocodeAddressList[0].latLonPoint.toString()}")
                    val stationQuery = ReachStationQuery(token,
                            it.geocodeAddressList[0].latLonPoint.latitude,
                            it.geocodeAddressList[0].latLonPoint.longitude,
                            city)
                    trafficApi.getReachStations(requestBody(stationQuery))
                }
                .onErrorResumeNext(Observable.empty())
                .flatMap {
                    if (it.success == 1 && it.count > 0) {
                        val newlist = it.stations / 10
                        Observable.fromIterable(newlist)
                    } else {
                        Observable.empty()
                    }
                }
                .flatMap {
                    val query = StationHouseQuery(
                            token, it, lat, lon, city
                    )
                    log("站点房源请求>${gson(query)}")
                    houseApi.getReachableHouse(requestBody(query))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.success == 1 && it.count > 0) {
                        it.houses.map { log("可达房源搜索结果>${it.title},${it.campus}") }
                    }
                }
    }

    private fun getCommonStation() {
        token?.let {
            val lat2 = 22.934005
            val lon2 = 113.3843

            val query = CommonStationQuery(token,
                    lat,
                    lon,
                    lat2,
                    lon2,
                    city)
            log("查找共有的车站>${gson(query)}")
            trafficApi.getCommonStation(requestBody(query))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.success == 1 && it.count > 0) {
                            it.stations.map { log("共有的车站>${it.station}") }
                        }
                    }

        }
    }

    //获取可到达的车站列表
    private fun getReachableStations() {
        token?.let {
            var query = ReachStationQuery(it, lat, lon, city)
            log(gson(query))
            trafficApi.getReachStations(requestBody(query))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(Observable.empty())
                    .subscribe {
                        if (it.success == 1 && it.count > 0) {
                            var stationstr = it.stations.map { it.station }.reduce { acc, s -> "$acc,$s" }
                            stations = it.stations
                            log("查询可达车站成功>$stationstr")
                        } else {
                            log("查询可达车站失败")
                        }
                    }
        }
    }

    //获取周边房源--OK
    private fun getNeatbyCampus() {
        token?.let {

            val query = NearbyQuery(
                    city, address, district, lat, lon, it
            )
            houseApi.getNearbyHouse(requestBody(query))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        log("${it.success},${it.count}")
                        if (it.count > 0) {
                            val str = it.houses.map { it.title }.reduce { s, acc -> "$s,$acc" }
                            log("获取周边房源>$str")
                        }
                    }
        }

    }

    //登录到服务器获取Token -- OK
    private fun login() {

        val login = LoginRequest(
                imei = "a0000059765744",
                imsi = "",
                city = "广州",
                lat = 22.965691,
                lon = 113.490884,
                login_location = "广东省广州市番禺区黄河路靠近番禺区石楼镇保利公馆2010北苑",
                model = "HUAWEI NXT-CL00",
                manufacturer = "HUAWEI",
                version = 1
        )
        appApi.login(requestBody(login))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    log("登录成功>${it.token}")
                    token = it.token
                }
    }
}

infix fun Activity.log(message: String) {
    val tag = this::class.java.simpleName
    Log.i(tag, message)
}




