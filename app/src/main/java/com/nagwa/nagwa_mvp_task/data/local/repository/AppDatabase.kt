package com.nagwa.nagwa_mvp_task.data.local.repository

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentDao
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.utils.Var

@Database(entities = [DownloadedAttachmentEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadedAttachmentDao(): DownloadedAttachmentDao

    companion object {
        @Volatile //Marks the JVM backing field of the annotated property as volatile, meaning that writes to this field are immediately made visible to other threads
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(app: Application) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(app).also { instance = it }
        }
        private fun buildDatabase(application: Application) = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            Var.DATABASE_NAME
        )
            .build()
    }
}
