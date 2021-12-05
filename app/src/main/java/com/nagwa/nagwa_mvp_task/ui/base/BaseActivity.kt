package com.nagwa.nagwa_mvp_task.ui.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.nagwa.nagwa_mvp_task.NagwaApp
import com.nagwa.nagwa_mvp_task.di.activity.ActivityComponent
import com.nagwa.nagwa_mvp_task.di.activity.ActivityModule
import com.nagwa.nagwa_mvp_task.di.activity.DaggerActivityComponent
import com.nagwa.nagwa_mvp_task.ui.activities.MainActivity
import com.nagwa.nagwa_mvp_task.utils.freezeB
import com.nagwa.nagwa_mvp_task.utils.unFreezeB
import javax.inject.Inject


abstract class BaseActivity<T : BaseMvpPresenter<*>, B : ViewBinding> : AppCompatActivity(),
    BaseMvpView, BaseFragment.Callback {

    @Inject
    lateinit var presenter: T

    lateinit var binding: B

    lateinit var activityComponent: ActivityComponent

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initBeforeView()
        binding = getContentBinding()

        setContentView(binding.root)


        activityComponent = DaggerActivityComponent.builder()
            .applicationComponent((applicationContext as NagwaApp).applicationComponent)
            .activityModule(ActivityModule(this)).build()

        injectDependency()

        init(savedInstanceState)

    }

    abstract fun injectDependency()

    protected abstract fun getContentBinding(): B
    protected abstract fun getContentResources(): Int

    protected abstract fun init(savedInstanceState: Bundle?)


    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(resId: Int) {
        showToast(getString(resId))
    }

    open fun initBeforeView() {

    }

    override fun restartTheApp() {
        val i = Intent(this, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finishAffinity()
        startActivity(i)
    }

    override fun onFragmentAttached() {

    }


    override fun onFragmentDetached(tag: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun startDimLoading() {
        this.freezeB()
    }

    override fun stopDimLoading() {
        this.unFreezeB()
    }


}