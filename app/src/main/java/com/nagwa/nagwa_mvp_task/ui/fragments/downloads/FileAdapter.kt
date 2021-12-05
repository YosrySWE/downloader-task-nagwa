package com.nagwa.nagwa_mvp_task.ui.fragments.downloads

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.nagwa.nagwa_mvp_task.R
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.utils.FetchUtils
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Status
import java.io.File
import java.util.*

class FileAdapter internal constructor(var dataSet: MutableList<DownloadedAttachmentEntity>,@NonNull actionListener: ActionListener) :
    RecyclerView.Adapter<FileAdapter.ViewHolder?>() {
    @NonNull
    private val downloads: MutableList<DownloadData> = ArrayList()

    @NonNull
    private val actionListener: ActionListener = actionListener
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_download_attachment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        holder.actionButton.setOnClickListener(null)
        holder.actionButton.isEnabled = true
        val downloadData = downloads[position]
        var url = ""
        if (downloadData.download != null) {
            url = downloadData.download!!.url
        }
        val uri = Uri.parse(url)
        val status: Status = downloadData.download?.status!!
        val context: Context = holder.itemView.context
        holder.titleTextView.text = uri.lastPathSegment
        holder.statusTextView.text = getStatusString(status)
        var progress: Int = downloadData.download!!.progress
        if (progress == -1) { // Download progress is undermined at the moment.
            progress = 0
        }
        holder.progressBar.progress = progress
        holder.progressTextView.text = context.getString(R.string.percent_progress, progress)
        if (downloadData.eta == -1L) {
            holder.timeRemainingTextView.text = ""
        } else {
            holder.timeRemainingTextView.text = FetchUtils.getETAString(context, downloadData.eta)
        }
        if (downloadData.downloadedBytesPerSecond == 0L) {
            holder.downloadedBytesPerSecondTextView.text = ""
        } else {
            holder.downloadedBytesPerSecondTextView.text = FetchUtils.getDownloadSpeedString(
                context,
                downloadData.downloadedBytesPerSecond
            )
        }
        when (status) {
            Status.COMPLETED -> {
                holder.actionButton.setText(R.string.view)
                holder.actionButton.setOnClickListener { view: View? ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(
                            context,
                            "Downloaded Path:" + downloadData.download!!.file,
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }
                    val file: File = File(downloadData.download!!.file)
                    val uri1 = Uri.fromFile(file)
                    val share = Intent(Intent.ACTION_VIEW)
                    share.setDataAndType(uri1, FetchUtils.getMimeType(context, uri1))
                    context.startActivity(share)
                }
            }
            Status.FAILED -> {
                holder.actionButton.setText(R.string.retry)
                holder.actionButton.setOnClickListener { view: View? ->
                    holder.actionButton.isEnabled = false
                    actionListener.onRetryDownload(downloadData.download!!.id)
                }
            }
            Status.PAUSED -> {
                holder.actionButton.setText(R.string.resume)
                holder.actionButton.setOnClickListener { view: View? ->
                    holder.actionButton.isEnabled = false
                    actionListener.onResumeDownload(downloadData.download!!.id)
                }
            }
            Status.DOWNLOADING, Status.QUEUED -> {
                holder.actionButton.setText(R.string.pause)
                holder.actionButton.setOnClickListener { view: View? ->
                    holder.actionButton.isEnabled = false
                    actionListener.onPauseDownload(downloadData.download!!.id)
                }
            }
            Status.ADDED -> {
                holder.actionButton.setText(R.string.download)
                holder.actionButton.setOnClickListener { view: View? ->
                    holder.actionButton.isEnabled = false
                    actionListener.onResumeDownload(downloadData.download!!.id)
                }
            }
            else -> {
            }
        }

        //Set delete action
        holder.itemView.setOnLongClickListener { v ->
            val uri12 = Uri.parse(downloadData.download?.url)
            AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.delete_title, uri12.lastPathSegment))
                .setPositiveButton(R.string.delete) { dialog, which ->
                    actionListener.onRemoveDownload(
                        downloadData.download!!.id
                    )
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
            true
        }
    }
    // add list into dataSet
    fun addNewData(newData: MutableList<DownloadedAttachmentEntity>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    fun addDownload(@NonNull download: Download) {
        var found = false
        var data: DownloadData? = null
        var dataPosition = -1
        for (i in downloads.indices) {
            val downloadData = downloads[i]
            if (downloadData.id == download.id) {
                data = downloadData
                dataPosition = i
                found = true
                break
            }
        }
        if (!found) {
            val downloadData = DownloadData()
            downloadData.id = download.id
            downloadData.download = download
            downloads.add(downloadData)
            notifyItemInserted(downloads.size - 1)
        } else {
            data!!.download = download
            notifyItemChanged(dataPosition)
        }
    }

    override fun getItemCount(): Int {
        return downloads.size
    }

    fun update(@NonNull download: Download, eta: Long, downloadedBytesPerSecond: Long) {
        for (position in downloads.indices) {
            val downloadData = downloads[position]
            if (downloadData.id == download.id) {
                when (download.status) {
                    Status.REMOVED, Status.DELETED -> {
                        downloads.removeAt(position)
                        notifyItemRemoved(position)
                    }
                    else -> {
                        downloadData.download = download
                        downloadData.eta = eta
                        downloadData.downloadedBytesPerSecond = downloadedBytesPerSecond
                        notifyItemChanged(position)
                    }
                }
                return
            }
        }
    }

    private fun getStatusString(status: Status): String {
        return when (status) {
            Status.COMPLETED -> "Done"
            Status.DOWNLOADING -> "Downloading"
            Status.FAILED -> "Error"
            Status.PAUSED -> "Paused"
            Status.QUEUED -> "Waiting in Queue"
            Status.REMOVED -> "Removed"
            Status.NONE -> "Not Queued"
            else -> "Unknown"
        }
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView
        val statusTextView: TextView
        val progressBar: ProgressBar
        val progressTextView: TextView
        val actionButton: Button
        val timeRemainingTextView: TextView
        val downloadedBytesPerSecondTextView: TextView

        init {
            titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
            statusTextView = itemView.findViewById<TextView>(R.id.status_TextView)
            progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
            actionButton = itemView.findViewById(R.id.actionButton)
            progressTextView = itemView.findViewById<TextView>(R.id.progress_TextView)
            timeRemainingTextView = itemView.findViewById<TextView>(R.id.remaining_TextView)
            downloadedBytesPerSecondTextView =
                itemView.findViewById<TextView>(R.id.downloadSpeedTextView)
        }
    }

    class DownloadData {
        var id = 0

        @Nullable
        var download: Download? = null
        var eta: Long = -1
        var downloadedBytesPerSecond: Long = 0
        override fun hashCode(): Int {
            return id
        }

        override fun toString(): String {
            return if (download == null) {
                ""
            } else download.toString()
        }

        override fun equals(obj: Any?): Boolean {
            return obj === this || obj is DownloadData && obj.id == id
        }
    }

}