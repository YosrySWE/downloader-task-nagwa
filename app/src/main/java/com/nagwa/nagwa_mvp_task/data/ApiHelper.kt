package com.nagwa.nagwa_mvp_task.data

import io.reactivex.Observable

interface ApiHelper {

    fun getVideoAndBooks(): Observable<MutableList<String>>


}