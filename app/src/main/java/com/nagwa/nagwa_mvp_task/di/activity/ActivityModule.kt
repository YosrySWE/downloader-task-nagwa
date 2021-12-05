package com.nagwa.nagwa_mvp_task.di.activity


import androidx.appcompat.app.AppCompatActivity
import com.nagwa.nagwa_mvp_task.data.DataManager
import com.nagwa.nagwa_mvp_task.di.rx.AppSchedulerProvider
import com.nagwa.nagwa_mvp_task.di.rx.SchedulerProvider
import com.nagwa.nagwa_mvp_task.ui.activities.MainContract
import com.nagwa.nagwa_mvp_task.ui.activities.MainPresenter
import com.nagwa.nagwa_mvp_task.ui.fragments.home.HomeContract
import com.nagwa.nagwa_mvp_task.ui.fragments.home.HomePresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
class ActivityModule(var mActivity: AppCompatActivity) {

    @Provides
    @PerActivity
    fun provideMainPresenter(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider
    ): MainContract.Presenter {
        return MainPresenter(dataManager, compositeDisposable, schedulerProvider)
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }


    @Provides
    fun provideHomePresenter(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable,
        schedulerProvider: SchedulerProvider
    ): HomeContract.Presenter {
        return HomePresenter(dataManager, compositeDisposable, schedulerProvider)
    }


}