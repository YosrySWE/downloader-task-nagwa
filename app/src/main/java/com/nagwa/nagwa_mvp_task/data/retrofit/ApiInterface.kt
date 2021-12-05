package com.nagwa.nagwa_mvp_task.data.retrofit

import com.nagwa.nagwa_mvp_task.data.models.AttachmentResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface ApiInterface {

    @GET("yosryswe/mockjson/db")
    fun getVideoAndBooks(): Observable<AttachmentResponse>

}