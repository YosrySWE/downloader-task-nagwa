package com.nagwa.nagwa_mvp_task.ui.activities

import android.os.Bundle
import com.nagwa.nagwa_mvp_task.R
import com.nagwa.nagwa_mvp_task.databinding.ActivityMainBinding
import com.nagwa.nagwa_mvp_task.ui.base.BaseActivity

class MainActivity : BaseActivity<MainContract.Presenter, ActivityMainBinding>() , MainContract.View{


    override fun injectDependency() {
        activityComponent.inject(this)
    }

    override fun getContentBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getContentResources(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        presenter.attach(this)
    }
}