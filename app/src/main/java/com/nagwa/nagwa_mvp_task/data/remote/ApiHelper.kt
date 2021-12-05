package com.nagwa.nagwa_mvp_task.data.remote

import com.nagwa.nagwa_mvp_task.data.remote.models.AttachmentResponse
import io.reactivex.Observable

interface ApiHelper {

    fun getVideoAndBooks(): Observable<AttachmentResponse>


}