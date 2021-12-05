package com.nagwa.nagwa_mvp_task.di.application

import android.app.Application

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(/*application: Application*/) {
//
//    private var database: AppDatabase = Room.databaseBuilder(application, AppDatabase::class.java,
//        Var.DATABASE_NAME).build()
//
//    @Singleton
//    @Provides
//    fun providesAppDatabase(): AppDatabase {
//        return database
//    }
//    @Singleton
//    @Provides
//    fun providesStateDao(appDatabase: AppDatabase): StateDao{
//        return appDatabase.stateDao()
//    }
//
//    @Singleton
//    @Provides
//    fun providesStateDataSource(stateDao: StateDao): StateRepository {
//        return StateDataSource(stateDao)
//    }
//

}