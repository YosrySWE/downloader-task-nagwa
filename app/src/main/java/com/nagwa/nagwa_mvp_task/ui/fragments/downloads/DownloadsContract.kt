package com.nagwa.nagwa_mvp_task.ui.fragments.downloads

import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpPresenter
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpView


interface DownloadsContract {

    interface View : BaseMvpView {
        fun loadDataFromCache(results: MutableList<DownloadedAttachmentEntity>)


        fun emptyList()
    }


    interface Presenter : BaseMvpPresenter<View> {

        fun deleteFile(item: DownloadedAttachmentEntity)
        fun getDownloadedAttachmentFromCache()
    }


}