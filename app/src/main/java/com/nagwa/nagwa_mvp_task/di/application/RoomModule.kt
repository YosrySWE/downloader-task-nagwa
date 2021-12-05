package com.nagwa.nagwa_mvp_task.di.application

import android.app.Application
import androidx.room.Room
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentDao
import com.nagwa.nagwa_mvp_task.data.local.repository.AppDatabase
import com.nagwa.nagwa_mvp_task.data.local.repository.DownloadAttachmentDataSource
import com.nagwa.nagwa_mvp_task.data.local.repository.DownloadAttachmentRepository
import com.nagwa.nagwa_mvp_task.utils.Var

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {

    private var database: AppDatabase = Room.databaseBuilder(application, AppDatabase::class.java,
        Var.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providesAppDatabase(): AppDatabase {
        return database
    }
    @Singleton
    @Provides
    fun providesDownloadedAttachmentDao(appDatabase: AppDatabase): DownloadedAttachmentDao{
        return appDatabase.downloadedAttachmentDao()
    }

    @Singleton
    @Provides
    fun providesDownloadedAttachmentDataSource(stateDao: DownloadedAttachmentDao): DownloadAttachmentRepository {
        return DownloadAttachmentDataSource(stateDao)
    }


}