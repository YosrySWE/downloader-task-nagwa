package com.nagwa.nagwa_mvp_task.ui.fragments.home

import com.nagwa.nagwa_mvp_task.data.models.AttachmentModel
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpPresenter
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpView
import com.nagwa.nagwa_mvp_task.ui.base.LoadingListener


interface HomeContract {

    interface View : BaseMvpView , LoadingListener{
        fun onResponseFetchedSuccessfully(results: MutableList<AttachmentModel>)
        fun onResponseFetchedFailed(message: String)
    }


    interface Presenter : BaseMvpPresenter<View> {

        fun fetchFakeResponse()
    }


}