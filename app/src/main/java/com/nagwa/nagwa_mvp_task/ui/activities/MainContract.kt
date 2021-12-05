package com.nagwa.nagwa_mvp_task.ui.activities

import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpPresenter
import com.nagwa.nagwa_mvp_task.ui.base.BaseMvpView

interface MainContract {

    interface View : BaseMvpView {


    }

    interface Presenter : BaseMvpPresenter<View> {

    }

}