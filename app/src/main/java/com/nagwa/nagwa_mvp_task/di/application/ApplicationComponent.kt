package com.nagwa.nagwa_mvp_task.di.application

import com.nagwa.nagwa_mvp_task.NagwaApp
import com.nagwa.nagwa_mvp_task.data.DataManager
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(ApplicationModule::class), (RoomModule::class)])
interface ApplicationComponent {

    fun inject(app: NagwaApp)

    fun dataManager(): DataManager

}