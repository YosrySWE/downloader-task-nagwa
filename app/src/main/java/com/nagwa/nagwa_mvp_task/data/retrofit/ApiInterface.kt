package com.nagwa.nagwa_mvp_task.data.retrofit

import io.reactivex.Observable
import retrofit2.http.GET


interface ApiInterface {

    @GET("yosryswe/mockjson/results")
    fun getVideoAndBooks(): Observable<MutableList<String>>

}