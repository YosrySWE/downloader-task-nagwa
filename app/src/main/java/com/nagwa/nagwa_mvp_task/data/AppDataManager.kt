package com.nagwa.nagwa_mvp_task.data

import com.nagwa.nagwa_mvp_task.data.models.AttachmentResponse
import com.nagwa.nagwa_mvp_task.data.sharedHelper.AppSharedHelper
import io.reactivex.Observable

class AppDataManager(var appApiHelper: AppApiHelper, private var appSharedHelper: AppSharedHelper) :
    DataManager {
    override fun getVideoAndBooks(): Observable<AttachmentResponse>{
        return appApiHelper.getVideoAndBooks()
    }


}