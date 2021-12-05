package com.nagwa.nagwa_mvp_task.ui.activities.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import com.nagwa.nagwa_mvp_task.R
import com.nagwa.nagwa_mvp_task.databinding.ActivityMainBinding
import com.nagwa.nagwa_mvp_task.ui.base.BaseActivity
import com.nagwa.nagwa_mvp_task.utils.clearTint
import com.nagwa.nagwa_mvp_task.utils.disableTemp
import com.nagwa.nagwa_mvp_task.utils.tintWithColor

class MainActivity : BaseActivity<MainContract.Presenter, ActivityMainBinding>() ,
    MainContract.View {


    override fun injectDependency() {
        activityComponent.inject(this)
    }

    override fun getContentBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getContentResources(): Int {
        return R.layout.activity_main
    }

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    override fun init(savedInstanceState: Bundle?) {
        presenter.attach(this)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        pressBtn( binding.homeBtn, binding.homeTitle)
        binding.homeBtnLayout.isSelected = true

        binding.homeBtnLayout.setOnClickListener {

            if(it.isSelected){
                return@setOnClickListener
            }
            pressBtn(binding.homeBtn, binding.homeTitle)
            unPressBtn(binding.downloadBtn, binding.downloadsTitle)
            navController.navigate(R.id.action_downloads_to_home)
            it.disableTemp()
            it.isSelected = true
            binding.downloadsBtnLayout.isSelected = false

        }

        binding.downloadsBtnLayout.setOnClickListener {
            if(it.isSelected){
                return@setOnClickListener
            }

            unPressBtn( binding.homeBtn, binding.homeTitle)
            pressBtn(binding.downloadBtn, binding.downloadsTitle)
            navController.navigate(R.id.action_home_to_downloads)

            it.disableTemp()
            it.isSelected = true
            binding.homeBtnLayout.isSelected = false

        }

    }

    private fun pressBtn( view: AppCompatImageView, textView: TextView) {
        view.isEnabled = false
        view.tintWithColor(R.color.colorPrimary)
        //view.animate().scaleY(1.2f).scaleX(1.2f).setInterpolator(OvershootInterpolator()).setDuration(250).start()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            textView.setTextColor(getColor(R.color.colorPrimary))
        } else {
            @Suppress("DEPRECATION")
            textView.setTextColor(this.resources.getColor(R.color.colorPrimary))
        }
        //textView.animate().scaleY(1.2f).scaleX(1.2f).setInterpolator(OvershootInterpolator()).setDuration(250).start()
    }

    private fun unPressBtn(view: AppCompatImageView, textView: TextView) {
        view.isEnabled = true
        view.clearTint()
        view.clearAnimation()
        // view.animate().scaleY(0f).scaleX(0f).setDuration(250).setInterpolator(OvershootInterpolator()).start()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            textView.setTextColor(getColor(R.color.offWhite))
        } else {
            @Suppress("DEPRECATION")
            textView.setTextColor(this.resources.getColor(R.color.offWhite))
        }
        //textView.animate().scaleY(1f).scaleX(1f).setDuration(250).setInterpolator(OvershootInterpolator()).start()


    }



}