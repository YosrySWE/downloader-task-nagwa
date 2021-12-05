package com.nagwa.nagwa_mvp_task.ui.fragments.downloads

import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpPresenter
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpView


interface DownloadsContract {

    interface View : BaseMvpView {
    }


    interface Presenter : BaseMvpPresenter<View> {

    }


}