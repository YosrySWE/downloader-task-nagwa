package com.nagwa.nagwa_mvp_task.ui.fragments.downloads


import com.nagwa.nagwa_mvp_task.data.DataManager
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
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
    override fun deleteFile(item: DownloadedAttachmentEntity) {
        item.isDownloaded = false
        compositeDisposable.add(
            dataManager.updateAttachment(attachment = item)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({

                },{
                    view?.emptyList()
                })
        )
    }

    override fun getDownloadedAttachmentFromCache() {
        view?.startDimLoading()
        compositeDisposable.add(
            dataManager.getCurrentDownloadedAttachments()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    view?.stopDimLoading()
                    if(it.isEmpty()){
                        view?.emptyList()
                    }else{
                        view?.loadDataFromCache(it)
                    }
                },{
                    view?.stopDimLoading()

                    view?.emptyList()
                })
        )
    }

}