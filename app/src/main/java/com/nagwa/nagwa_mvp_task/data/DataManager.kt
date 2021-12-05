package com.nagwa.nagwa_mvp_task.data

import com.nagwa.nagwa_mvp_task.data.local.repository.DownloadAttachmentRepository
import com.nagwa.nagwa_mvp_task.data.local.sharedHelper.SharedHelper
import com.nagwa.nagwa_mvp_task.data.remote.ApiHelper


interface DataManager : ApiHelper, SharedHelper , DownloadAttachmentRepository{
}