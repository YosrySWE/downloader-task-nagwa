package com.nagwa.nagwa_mvp_task.ui.fragments.home

import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpPresenter
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpView


interface HomeContract {

    interface View : BaseMvpView {
    }


    interface Presenter : BaseMvpPresenter<View> {

    }


}