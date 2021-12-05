package com.nagwa.nagwa_mvp_task.data

import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.data.local.repository.DownloadAttachmentDataSource
import com.nagwa.nagwa_mvp_task.data.local.repository.DownloadAttachmentRepository
import com.nagwa.nagwa_mvp_task.data.remote.models.AttachmentResponse
import com.nagwa.nagwa_mvp_task.data.local.sharedHelper.AppSharedHelper
import com.nagwa.nagwa_mvp_task.data.remote.AppApiHelper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

class AppDataManager(var appApiHelper: AppApiHelper, var repository: DownloadAttachmentDataSource,private var appSharedHelper: AppSharedHelper) :
    DataManager {
    override fun getVideoAndBooks(): Observable<AttachmentResponse>{
        return appApiHelper.getVideoAndBooks()
    }

    override fun insertAttachment(attachment: DownloadedAttachmentEntity): Completable {
        return repository.insertAttachment(attachment)
    }

    override fun updateAttachment(attachment: DownloadedAttachmentEntity): Completable {
        return repository.updateAttachment(attachment)
    }

    override fun deleteAttachment(attachment: DownloadedAttachmentEntity): Completable {
        return repository.deleteAttachment(attachment)
    }

    override fun deleteAttachmentById(id: Int): Completable {
        return repository.deleteAttachmentById(id)
    }

    override fun findById(id: Int): Flowable<DownloadedAttachmentEntity> {
        return repository.findById(id)
    }

    override fun getCurrentDownloadedAttachments(): Flowable<MutableList<DownloadedAttachmentEntity>> {
        return repository.getCurrentDownloadedAttachments()
    }

    override fun deleteAll(): Completable {
        return repository.deleteAll()
    }

    override fun getAll(): Flowable<MutableList<DownloadedAttachmentEntity>> {
        return repository.getAll()
    }

}