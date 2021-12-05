package com.nagwa.nagwa_mvp_task.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedAttachmentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAttachment( attachment: DownloadedAttachmentEntity): Completable

    @Update
    fun updateAttachment( attachment: DownloadedAttachmentEntity): Completable

    @Delete
    fun deleteAttachment( attachment: DownloadedAttachmentEntity): Completable

    @Query("DELETE FROM attachment WHERE id = :id")
    fun deleteAttachmentById(id: Int): Completable

    @Query("SELECT * FROM attachment WHERE id = :id")
    fun findById(id: Int): Flowable<DownloadedAttachmentEntity>


    @Query("SELECT * FROM attachment WHERE 1")
    fun getAll(): Flowable<MutableList<DownloadedAttachmentEntity>>

    @Query("SELECT * FROM attachment WHERE isDownloaded")
    fun getCurrentDownloadedAttachments(): Flowable<MutableList<DownloadedAttachmentEntity>>

    @Query("DELETE FROM attachment")
    fun deleteAll(): Completable
}