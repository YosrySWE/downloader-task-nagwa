package com.nagwa.nagwa_mvp_task.ui.fragments.home

import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.data.remote.models.AttachmentModel
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpPresenter
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpView
import com.nagwa.nagwa_mvp_task.ui.base.LoadingListener


interface HomeContract {

    interface View : BaseMvpView , LoadingListener{
        fun onResponseFetchedSuccessfully(results: MutableList<AttachmentModel>)
        fun onResponseFetchedFailed(message: String)
        fun updateList(item: DownloadedAttachmentEntity, position: Int)
        fun loadDataFromCache(results: MutableList<DownloadedAttachmentEntity>)
    }


    interface Presenter : BaseMvpPresenter<View> {

        fun updateItemFromCache(item: DownloadedAttachmentEntity, position: Int)
        fun fetchFakeResponse()
        fun setCacheDataList(results: MutableList<DownloadedAttachmentEntity>)
        fun getDownloadedAttachmentFromCache()
    }


}