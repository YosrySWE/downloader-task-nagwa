package com.nagwa.nagwa_mvp_task.ui.fragments.downloads

interface ActionListener {
    fun onPauseDownload(id: Int)
    fun onResumeDownload(id: Int)
    fun onRemoveDownload(id: Int)
    fun onRetryDownload(id: Int)
}