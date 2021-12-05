package com.nagwa.nagwa_mvp_task.ui.fragments.home


import com.nagwa.nagwa_mvp_task.data.DataManager
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.data.remote.models.AttachmentModel
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

    override fun updateItemFromCache(item: DownloadedAttachmentEntity, position: Int) {
        compositeDisposable.add(
            dataManager.updateAttachment(item)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                           view?.updateList(item, position)
                },{
                    item.isDownloaded = !item.isDownloaded
                    view?.updateList(item, position)
                })
        )
    }

    override fun setCacheDataList(results: MutableList<DownloadedAttachmentEntity>) {
        results.forEach {
            compositeDisposable.add(
                dataManager.insertAttachment(it)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({

                    },{

                    })
            )
        }
    }

    override fun getDownloadedAttachmentFromCache() {
        compositeDisposable.add(
            dataManager.getAll()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view?.loadDataFromCache(it)
                },{
                    fetchFakeResponse()
                })
        )
    }

}