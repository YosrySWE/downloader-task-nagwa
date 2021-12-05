package com.nagwa.nagwa_mvp_task.data.remote.retrofit

import com.nagwa.nagwa_mvp_task.data.remote.models.AttachmentResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface ApiInterface {

    @GET("yosryswe/mockjson/db")
    fun getVideoAndBooks(): Observable<AttachmentResponse>

}