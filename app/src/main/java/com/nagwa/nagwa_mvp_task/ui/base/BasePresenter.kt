package com.nagwa.nagwa_mvp_task.ui.base

import androidx.annotation.StringRes
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : BaseMvpView>(var compositeDisposable: CompositeDisposable) :
    BaseMvpPresenter<V> {


    var view: V? = null

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        compositeDisposable.clear()
        view?.stopDimLoading()
        view = null
    }

    override fun isAttached(): Boolean {
        return view != null
    }

    fun toast(@StringRes resId: Int) {
        view?.showToast(resId)
    }


    fun toast(string: String) {
        view?.showToast(string)
    }

    fun dimScreen() {
        view?.startDimLoading()
    }

    fun unDimScreen() {
        view?.stopDimLoading()
    }


    open var networkFailed = false

}