package com.nagwa.nagwa_mvp_task.ui.base

interface BaseMvpPresenter<V : BaseMvpView> {

    fun attach(view: V)

    fun detach()

    fun isAttached(): Boolean

}