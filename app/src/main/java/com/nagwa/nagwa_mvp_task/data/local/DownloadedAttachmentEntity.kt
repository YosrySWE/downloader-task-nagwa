package com.nagwa.nagwa_mvp_task.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nagwa.nagwa_mvp_task.data.remote.models.AttachmentModel


@Entity(tableName = "attachment")
data class DownloadedAttachmentEntity (
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "isDownloaded")
    var isDownloaded: Boolean


){
    constructor(id: Int, name: String, type: String, url: String)
    :this(id, name, type, url, false)
    constructor(attachmentModel: AttachmentModel):
            this(attachmentModel.id, attachmentModel.name, attachmentModel.type, attachmentModel.url, attachmentModel.isDownloaded)
}
