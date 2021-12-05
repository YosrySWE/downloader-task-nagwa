package com.nagwa.nagwa_mvp_task.ui.fragments.downloads


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nagwa.nagwa_mvp_task.R
import com.nagwa.nagwa_mvp_task.databinding.FragmentDownloadsBinding
import com.nagwa.nagwa_mvp_task.ui.base.BaseFragment

class DownloadsFragment : BaseFragment<DownloadsContract.Presenter>(), DownloadsContract.View {
    private var _binding: FragmentDownloadsBinding? = null

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


    }


    override fun getContentResources(): Int {
        return R.layout.fragment_downloads
    }

    override fun injectDependencies() {
        baseActivity?.activityComponent?.inject(this)
    }


}
