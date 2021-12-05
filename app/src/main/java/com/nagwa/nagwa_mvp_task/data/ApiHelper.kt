package com.nagwa.nagwa_mvp_task.data

import com.nagwa.nagwa_mvp_task.data.models.AttachmentResponse
import io.reactivex.Observable

interface ApiHelper {

    fun getVideoAndBooks(): Observable<AttachmentResponse>


}