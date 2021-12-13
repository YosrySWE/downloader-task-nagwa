package com.nagwa.nagwa_mvp_task.ui.fragments.downloads


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nagwa.nagwa_mvp_task.R
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.databinding.FragmentDownloadsBinding
import com.nagwa.nagwa_mvp_task.ui.base.BaseFragment
import com.nagwa.nagwa_mvp_task.ui.fragments.home.AttachmentsAdapter
import com.nagwa.nagwa_mvp_task.utils.secreteB
import com.nagwa.nagwa_mvp_task.utils.showB
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2.Fetch.Impl.getInstance
import com.tonyodev.fetch2core.Downloader.FileDownloaderType

import com.tonyodev.fetch2core.Func

import okhttp3.OkHttpClient
import java.util.*


class DownloadsFragment : BaseFragment<DownloadsContract.Presenter>(), DownloadsContract.View, ActionListener {
    private var _binding: FragmentDownloadsBinding? = null
    private val UNKNOWN_REMAINING_TIME: Long = -1
    private val UNKNOWN_DOWNLOADED_BYTES_PER_SECOND: Long = 0
    private val GROUP_ID = "listGroup".hashCode()


    val cachedData : MutableList<DownloadedAttachmentEntity> = mutableListOf()
    lateinit var adapter: FileAdapter
    lateinit var fetch: Fetch

    // This property is only valid between onCreateView and
    //
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View {
        _binding = FragmentDownloadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun init(savedInstanceState: Bundle?, view: View) {
        presenter.attach(this)

        presenter.getDownloadedAttachmentFromCache()

        initRecyclerView()

        binding.swipeRefresher.setOnRefreshListener {

            if(cachedData.isEmpty()){
                presenter.getDownloadedAttachmentFromCache()
            }else{
                binding.swipeRefresher.isRefreshing = false
            }
        }

        binding.startDownloadBtn.setOnClickListener {
            checkStoragePermissions()
        }

        val fetchConfiguration: FetchConfiguration = FetchConfiguration.Builder(requireContext())
            .setDownloadConcurrentLimit(3)
            .setNamespace("Local")
            .build()
        fetch = getInstance(fetchConfiguration)
        checkStoragePermissions()

    }

    override fun onResume() {
        super.onResume()
        fetch.getDownloadsInGroup(
            GROUP_ID
        ) { downloads: List<Download>? ->
            val list = ArrayList(downloads)
            list.sortWith(Comparator { first: Download, second: Download ->
                first.created.compareTo(second.created)
            })
            for (download in list) {
                adapter.addDownload(download)
            }
        }.addListener(fetchListener)
    }

    override fun onPause() {
        super.onPause()
        fetch.removeListener(fetchListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        fetch.close()
    }

    private val fetchListener: FetchListener = object : AbstractFetchListener() {
        override fun onAdded(download: Download) {
            adapter.addDownload(download)
        }

        override fun onQueued(download: Download, waitingOnNetwork: Boolean) {
            adapter.update(
                download,
                UNKNOWN_REMAINING_TIME,
                UNKNOWN_DOWNLOADED_BYTES_PER_SECOND
            )
        }

        override fun onCompleted(download: Download) {
            adapter.update(
                download,
                UNKNOWN_REMAINING_TIME,
                UNKNOWN_DOWNLOADED_BYTES_PER_SECOND
            )
        }

        override fun onError(download: Download, error: Error, throwable: Throwable?) {
            super.onError(download, error, throwable)
            adapter.update(
                download,
                UNKNOWN_REMAINING_TIME,
                UNKNOWN_DOWNLOADED_BYTES_PER_SECOND
            )
        }

        override fun onProgress(
            download: Download,
            etaInMilliseconds: Long,
            downloadedBytesPerSecond: Long
        ) {
            adapter.update(download, etaInMilliseconds, downloadedBytesPerSecond)
        }

        override fun onPaused(download: Download) {
            adapter.update(
                download,
                UNKNOWN_REMAINING_TIME,
                UNKNOWN_DOWNLOADED_BYTES_PER_SECOND
            )
        }

        override fun onResumed(download: Download) {
            adapter.update(
                download,
                UNKNOWN_REMAINING_TIME,
                UNKNOWN_DOWNLOADED_BYTES_PER_SECOND
            )
        }

        override fun onCancelled(download: Download) {
            adapter.update(
                download,
                UNKNOWN_REMAINING_TIME,
                UNKNOWN_DOWNLOADED_BYTES_PER_SECOND
            )
        }

        override fun onRemoved(download: Download) {
            adapter.update(
                download,
                UNKNOWN_REMAINING_TIME,
                UNKNOWN_DOWNLOADED_BYTES_PER_SECOND
            )
            val obj : DownloadedAttachmentEntity = cachedData.find { it.url == download.url }!!

            presenter.deleteFile(obj)
        }

        override fun onDeleted(download: Download) {
            adapter.update(
                download,
                UNKNOWN_REMAINING_TIME,
                UNKNOWN_DOWNLOADED_BYTES_PER_SECOND
            )

            val obj : DownloadedAttachmentEntity = cachedData.find { it.url == download.url }!!

            presenter.deleteFile(obj)
        }
    }

    private fun checkStoragePermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            200
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enqueueDownloads()
        } else {
            Snackbar.make(binding.root, R.string.permission_not_enabled, Snackbar.LENGTH_INDEFINITE)
                .show()
        }
    }

    private fun enqueueDownloads() {
        val requests = Data.getFetchRequestWithGroupId(
            GROUP_ID,
            cachedData,
            requireContext()
        )
        fetch.enqueue(requests){

        }
    }

    private fun initRecyclerView() {
        binding.resultsRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = FileAdapter(mutableListOf(), this)
        binding.resultsRv.adapter = adapter
    }


    override fun getContentResources(): Int {
        return R.layout.fragment_downloads
    }

    override fun injectDependencies() {
        baseActivity?.activityComponent?.inject(this)
    }

    override fun emptyList() {
        binding.page2.showB()
        binding.swipeRefresher.secreteB()
        binding.startDownloadBtn.secreteB()
    }

    override fun loadDataFromCache(results: MutableList<DownloadedAttachmentEntity>) {
        binding.page2.secreteB()
        binding.swipeRefresher.showB()
        binding.startDownloadBtn.showB()
        adapter.addNewData(results)
        cachedData.clear()
        cachedData.addAll(results)

        fetch.getDownloadsInGroup(
            GROUP_ID
        ) { downloads: List<Download>? ->
            val list = ArrayList(downloads)
            list.sortWith(Comparator { first: Download, second: Download ->
                first.created.compareTo(second.created)
            })
            for (download in list) {
                adapter.addDownload(download)
            }
        }.addListener(fetchListener)
    }

    override fun onPauseDownload(id: Int) {
        fetch.pause(id)
    }

    override fun onResumeDownload(id: Int) {
        fetch.resume(id)
    }

    override fun onRemoveDownload(id: Int) {
        fetch.remove(id)
    }

    override fun onRetryDownload(id: Int) {
        fetch.retry(id)
    }


}
