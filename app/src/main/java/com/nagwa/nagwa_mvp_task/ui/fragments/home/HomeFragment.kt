package com.nagwa.nagwa_mvp_task.ui.fragments.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nagwa.nagwa_mvp_task.R
import com.nagwa.nagwa_mvp_task.databinding.FragmentHomeBinding
import com.nagwa.nagwa_mvp_task.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeContract.Presenter>(), HomeContract.View {
    private var _binding: FragmentHomeBinding? = null

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


    }


    override fun getContentResources(): Int {
        return R.layout.fragment_home
    }

    override fun injectDependencies() {
        baseActivity?.activityComponent?.inject(this)
    }


}
