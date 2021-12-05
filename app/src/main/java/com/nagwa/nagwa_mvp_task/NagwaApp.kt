package com.nagwa.nagwa_mvp_task

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.nagwa.nagwa_mvp_task.data.DataManager
import com.nagwa.nagwa_mvp_task.di.application.ApplicationComponent
import com.nagwa.nagwa_mvp_task.di.application.RoomModule
import com.nagwa.nagwa_mvp_task.di.application.ApplicationModule
import com.nagwa.nagwa_mvp_task.di.application.DaggerApplicationComponent
import javax.inject.Inject


class NagwaApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: NagwaApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

//    companion object {
//        private var instance: App? = null
//
//        fun applicationContext() : Context {
//            return instance!!.applicationContext
//        }
//    }

    @Inject
    lateinit var dataManager: DataManager


    lateinit var applicationComponent: ApplicationComponent


    private var context: Context? = null

    override fun onCreate() {
        super.onCreate()


        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .build()
        applicationComponent.inject(this)

        context = applicationContext;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }


    override fun onTerminate() {
        super.onTerminate()
        //Stop network callback
    }


}