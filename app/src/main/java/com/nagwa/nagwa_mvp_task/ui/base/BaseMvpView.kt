package com.nagwa.nagwa_mvp_task.ui.base

import androidx.annotation.StringRes

interface BaseMvpView {


    fun showToast(message: String)

    fun showToast(@StringRes resId: Int)

    fun restartTheApp()

    fun startDimLoading()

    fun stopDimLoading()

}