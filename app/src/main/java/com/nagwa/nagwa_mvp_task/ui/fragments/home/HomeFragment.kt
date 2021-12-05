package com.nagwa.nagwa_mvp_task.ui.fragments.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nagwa.nagwa_mvp_task.R
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.data.remote.models.AttachmentModel
import com.nagwa.nagwa_mvp_task.databinding.FragmentHomeBinding
import com.nagwa.nagwa_mvp_task.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeContract.Presenter>(), HomeContract.View,
    AttachmentsAdapter.Callback {
    private var _binding: FragmentHomeBinding? = null


    val cachedData : MutableList<DownloadedAttachmentEntity> = mutableListOf()
    lateinit var adapter: AttachmentsAdapter
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun init(savedInstanceState: Bundle?, view: View) {
        presenter.attach(this)

        presenter.getDownloadedAttachmentFromCache()

        initRecyclerView()

        binding.swipeRefresher.setOnRefreshListener {

            if(cachedData.isEmpty()){
                presenter.fetchFakeResponse()
            }else{
                binding.swipeRefresher.isRefreshing = false
            }
        }


    }

    private fun initRecyclerView() {
        binding.resultsRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = AttachmentsAdapter(mutableListOf(), this)
        binding.resultsRv.adapter = adapter

    }


    override fun getContentResources(): Int {
        return R.layout.fragment_home
    }

    override fun injectDependencies() {
        baseActivity?.activityComponent?.inject(this)
    }

    override fun onResponseFetchedSuccessfully(results: MutableList<AttachmentModel>) {
        if(cachedData.size == results.size){
            adapter.addNewData(cachedData)

            return
        }
        if (cachedData.size < results.size && cachedData.isNotEmpty()){
            results.zip(cachedData).filter { item -> item.first.id != item.second.id}.forEach {
                cachedData.add(DownloadedAttachmentEntity(it.first))
            }
            presenter.setCacheDataList(cachedData)

            adapter.addNewData(cachedData)
        }else{
            results.forEach {
                cachedData.add(DownloadedAttachmentEntity(it))
            }
            presenter.setCacheDataList(cachedData)

            adapter.addNewData(cachedData)
        }


    }

    override fun onResponseFetchedFailed(message: String) {
        showToast(message)
    }

    override fun startLoading() {
        if(binding.swipeRefresher.isRefreshing){
            binding.swipeRefresher.isRefreshing = false
        }
        startDimLoading()
    }

    override fun stopLoading() {
        if(binding.swipeRefresher.isRefreshing){
            binding.swipeRefresher.isRefreshing = false
        }
        stopDimLoading()
    }

    override fun onFileDownload(item: DownloadedAttachmentEntity, position: Int) {
        item.isDownloaded = true
        presenter.updateItemFromCache(item, position)
    }

    override fun onFileDeleted(item: DownloadedAttachmentEntity, position: Int) {
        item.isDownloaded = false
        presenter.updateItemFromCache(item, position)
    }

    override fun updateList(item: DownloadedAttachmentEntity, position: Int) {
        adapter.updateItem(item, position)
    }




    override fun loadDataFromCache(results: MutableList<DownloadedAttachmentEntity>) {
        if(results.isEmpty()){
            presenter.fetchFakeResponse()
        }else{
            cachedData.addAll(results)
            adapter.addNewData(results)
        }
    }



}
