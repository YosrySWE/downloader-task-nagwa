package com.nagwa.nagwa_mvp_task.data.local.repository

import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentDao
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DownloadAttachmentDataSource(downloadedAttachmentDao: DownloadedAttachmentDao) :
    DownloadAttachmentRepository {

    var downloadedAttachmentDao : DownloadedAttachmentDao = downloadedAttachmentDao
        @Inject set

    override fun insertAttachment(attachment: DownloadedAttachmentEntity): Completable {
        return downloadedAttachmentDao.insertAttachment(attachment)
    }

    override fun updateAttachment(attachment: DownloadedAttachmentEntity): Completable {
        return downloadedAttachmentDao.updateAttachment(attachment)
    }

    override fun deleteAttachment(attachment: DownloadedAttachmentEntity): Completable {
        return downloadedAttachmentDao.deleteAttachment(attachment)
    }

    override fun deleteAttachmentById(id: Int): Completable {
        return downloadedAttachmentDao.deleteAttachmentById(id)
    }

    override fun findById(id: Int): Flowable<DownloadedAttachmentEntity> {
        return downloadedAttachmentDao.findById(id)
    }

    override fun getCurrentDownloadedAttachments(): Flowable<MutableList<DownloadedAttachmentEntity>> {
        return downloadedAttachmentDao.getCurrentDownloadedAttachments()
    }

    override fun getAll(): Flowable<MutableList<DownloadedAttachmentEntity>> {
        return downloadedAttachmentDao.getAll()
    }
    override fun deleteAll(): Completable {
        return downloadedAttachmentDao.deleteAll()
    }

}