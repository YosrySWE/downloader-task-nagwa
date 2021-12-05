@file:Suppress("DEPRECATION")

package com.nagwa.nagwa_mvp_task.di.application

import android.app.Application
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.nagwa.nagwa_mvp_task.data.AppApiHelper
import com.nagwa.nagwa_mvp_task.data.AppDataManager
import com.nagwa.nagwa_mvp_task.data.DataManager
import com.nagwa.nagwa_mvp_task.data.sharedHelper.AppSharedHelper
import com.nagwa.nagwa_mvp_task.di.qualifiers.NormalRetro
import com.nagwa.nagwa_mvp_task.di.qualifiers.ServerUrl
import com.nagwa.nagwa_mvp_task.utils.Var
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApplicationModule(var application: Application) {

    @Provides
    @Singleton
    @ServerUrl
    fun provideBaseUrl(): String {
        return Var.BASE_URL
    }


    @Provides
    @Singleton
    @NormalRetro
    fun provideNormalRetrofit(@ServerUrl serverUrl: String): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(serverUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()

    }


    @Provides
    @Singleton
    fun provideDataManager(
        appApiHelper: AppApiHelper,
        appSharedHelper: AppSharedHelper
    ): DataManager {
        return AppDataManager(appApiHelper, appSharedHelper)
    }


    @Provides
    @Singleton
    fun provideSharedHelper(): AppSharedHelper {
        val shared = PreferenceManager.getDefaultSharedPreferences(application)
        return AppSharedHelper(shared)
    }


    @Provides
    @Singleton
    fun provideApiHelper(

        @NormalRetro normalRetrofit: Retrofit,
    ): AppApiHelper {
        return AppApiHelper(
            normalRetrofit,
        )
    }


}