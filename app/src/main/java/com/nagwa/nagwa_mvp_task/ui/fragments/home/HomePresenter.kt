package com.nagwa.nagwa_mvp_task.ui.fragments.home


import com.nagwa.nagwa_mvp_task.data.DataManager
import com.nagwa.nagwa_mvp_task.di.rx.SchedulerProvider
import com.nagwa.nagwa_mvp_task.ui.base.BasePresenter
import com.nagwa.nagwa_mvp_task.ui.fragments.home.HomeContract
import io.reactivex.disposables.CompositeDisposable

class HomePresenter(
    var dataManager: DataManager,
    compositeDisposable: CompositeDisposable,
    val schedulerProvider: SchedulerProvider
) :
    BasePresenter<HomeContract.View>(compositeDisposable), HomeContract.Presenter {
    override fun fetchFakeResponse() {
        view?.startLoading()
        compositeDisposable.add(
            dataManager.getVideoAndBooks()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view?.stopLoading()
                           if(it != null){
                               view?.onResponseFetchedSuccessfully(it.results)
                           }else{
                               view?.onResponseFetchedFailed("error")
                           }
                },{
                    view?.stopLoading()
                    view?.onResponseFetchedFailed("exception")
                })
        )
    }

}