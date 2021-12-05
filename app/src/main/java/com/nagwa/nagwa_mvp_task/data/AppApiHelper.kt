package com.nagwa.nagwa_mvp_task.data

import com.nagwa.nagwa_mvp_task.data.models.AttachmentResponse
import com.nagwa.nagwa_mvp_task.data.retrofit.ApiInterface
import com.nagwa.nagwa_mvp_task.di.qualifiers.NormalRetro
import io.reactivex.Observable
import retrofit2.Retrofit


class AppApiHelper(
    @NormalRetro var normalRetro: Retrofit,
) : ApiHelper {
    override fun getVideoAndBooks(): Observable<AttachmentResponse>{
        return normalRetro.create(ApiInterface::class.java).getVideoAndBooks()
    }


}