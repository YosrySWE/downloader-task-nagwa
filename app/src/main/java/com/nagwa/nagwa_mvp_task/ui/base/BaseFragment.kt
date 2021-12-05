package com.nagwa.nagwa_mvp_task.ui.base

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nagwa.nagwa_mvp_task.utils.freezeB
import com.nagwa.nagwa_mvp_task.utils.unFreezeB
import javax.inject.Inject


abstract class BaseFragment<T : BaseMvpPresenter<*>> : Fragment(), BaseMvpView {

    var baseActivity: BaseActivity<*, *>? = null

    val hostActivity: AppCompatActivity by lazy {
        baseActivity as AppCompatActivity
    }

    val hostContext by lazy {
        baseActivity as Context
    }


    @Inject
    lateinit var presenter: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            baseActivity = context
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (setCustomStyle() != null) {
            val contextThemeWrapper = ContextThemeWrapper(baseActivity, setCustomStyle()!!)
            val localInflater = inflater.cloneInContext(contextThemeWrapper)
            localInflater.inflate(getContentResources(), container, false)
        } else {
            inflater.inflate(getContentResources(), container, false)
        }
    }

    open fun setCustomStyle(): Int? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()
        init(savedInstanceState, view)
    }


    protected abstract fun getContentResources(): Int


    protected abstract fun init(savedInstanceState: Bundle?, view: View)

    protected abstract fun injectDependencies()


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    override fun onDetach() {
        super.onDetach()
        baseActivity = null
    }

    override fun showToast(message: String) {
        baseActivity?.showToast(message)
    }

    override fun showToast(resId: Int) {
        showToast(getString(resId))
    }

    override fun restartTheApp() {
        baseActivity?.restartTheApp()
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }


    override fun startDimLoading() {
        this.freezeB()
    }

    override fun stopDimLoading() {
        this.unFreezeB()
    }


}