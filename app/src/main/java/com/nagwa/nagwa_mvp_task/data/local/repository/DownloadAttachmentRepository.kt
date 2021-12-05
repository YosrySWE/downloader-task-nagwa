package com.nagwa.nagwa_mvp_task.data.local.repository

import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface DownloadAttachmentRepository {

    fun insertAttachment(attachment: DownloadedAttachmentEntity): Completable

    fun updateAttachment(attachment: DownloadedAttachmentEntity): Completable

    fun deleteAttachment(attachment: DownloadedAttachmentEntity): Completable

    fun deleteAttachmentById(id: Int): Completable

    fun findById(id: Int): Flowable<DownloadedAttachmentEntity>

    fun getAll(): Flowable<MutableList<DownloadedAttachmentEntity>>

    fun getCurrentDownloadedAttachments(): Flowable<MutableList<DownloadedAttachmentEntity>>

    fun deleteAll(): Completable
}