package com.nagwa.nagwa_mvp_task.ui.activities

import com.nagwa.nagwa_mvp_task.data.DataManager
import com.nagwa.nagwa_mvp_task.di.rx.SchedulerProvider
import com.nagwa.nagwa_mvp_task.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(
    val dataManager: DataManager,
    compositeDisposable: CompositeDisposable,
    val schedulerProvider: SchedulerProvider
) :
    BasePresenter<MainContract.View>(compositeDisposable), MainContract.Presenter {


}