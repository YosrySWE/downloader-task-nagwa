package com.nagwa.nagwa_mvp_task.ui.fragments.downloads


import com.nagwa.nagwa_mvp_task.data.DataManager
import com.nagwa.nagwa_mvp_task.di.rx.SchedulerProvider
import com.nagwa.nagwa_mvp_task.ui.base.BasePresenter
import com.nagwa.nagwa_mvp_task.ui.fragments.home.HomeContract
import io.reactivex.disposables.CompositeDisposable

class DownloadsPresenter(
    var dataManager: DataManager,
    compositeDisposable: CompositeDisposable,
    val schedulerProvider: SchedulerProvider
) :
    BasePresenter<DownloadsContract.View>(compositeDisposable), DownloadsContract.Presenter {

}