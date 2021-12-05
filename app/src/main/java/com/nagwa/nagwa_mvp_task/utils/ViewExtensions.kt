package com.nagwa.nagwa_mvp_task.utils

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.nagwa.nagwa_mvp_task.R


fun View.secreteB() {
    if (!this.isGoneB()) {
        this.visibility = View.GONE
    }
}

fun String.toLongOrDefault(defaultValue: Long): Long {
    return try {
        toLong()
    } catch (_: NumberFormatException) {
        defaultValue
    }
}

fun View.showB() {
    if (!this.isVisibleB()) {
        this.visibility = View.VISIBLE
    }
}

fun View.hideB() {
    this.visibility = View.INVISIBLE
}


fun View.isVisibleB(): Boolean = visibility == View.VISIBLE

fun View.isGoneB(): Boolean = visibility == View.GONE

//fun View.isInvisibleB(): Boolean = visibility == View.INVISIBLE


//fun freezeB(progressBar: View, appCompatActivity: AppCompatActivity) {
//    appCompatActivity.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//    progressBar.showB()
//}

//fun freezeB(appCompatActivity: AppCompatActivity) {
//
//}

fun EditText.updateText(text: String?) {
    this.setText(text, TextView.BufferType.EDITABLE)
}

fun Fragment.freezeB() {
    val rootView = this.activity?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
    this.activity?.layoutInflater?.inflate(R.layout.layout_loading_dim, rootView, true)
    this.activity?.window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Fragment.unFreezeB() {
    val rootView = this.activity?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
    val loader = rootView?.findViewById<ConstraintLayout>(R.id.dimRoot)
    rootView?.removeView(loader)
    this.activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}


fun Activity.freezeB() {
    val rootView = this.window?.findViewById<ViewGroup>(android.R.id.content)
    this.layoutInflater.inflate(R.layout.layout_loading_dim, rootView, true)
    this.window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.unFreezeB() {
    val rootView = this.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
    val loader = rootView?.findViewById<ConstraintLayout>(R.id.dimRoot)
    if (loader != null) {
        rootView.removeView(loader)
    }
    this.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

//fun unFreezeB(progressBar: View, appCompatActivity: AppCompatActivity) {
//    appCompatActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//    progressBar.secreteB()
//}

//fun ScrollView.overScroll() {
////    OverScrollDecoratorHelper.setUpOverScroll(this)
//}
//
//
fun RecyclerView.overScrollVertical() {
//    OverScrollDecoratorHelper.setUpOverScroll(this, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
}


//fun RecyclerView.overScrollHorizontal() {
////    OverScrollDecoratorHelper.setUpOverScroll(this, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
//}

fun View.flash() {

    this.alpha = 1f
    this.scaleX = 1f
    this.scaleY = 1f

    val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f)
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, .95f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, .95f)

    val ass = ObjectAnimator.ofPropertyValuesHolder(this, alpha, scaleX, scaleY)
    ass.repeatCount = 3
    ass.duration = 200
    ass.repeatMode = ValueAnimator.REVERSE
    ass.start()

}

fun EditText.flash() {
    this.requestFocus()

    this.alpha = 1f
    this.scaleX = 1f
    this.scaleY = 1f

    val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f)
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, .95f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, .95f)

    val ass = ObjectAnimator.ofPropertyValuesHolder(this, alpha, scaleX, scaleY)
    ass.repeatCount = 3
    ass.duration = 200
    ass.repeatMode = ValueAnimator.REVERSE
    ass.start()
}


fun TextView.setUnderLine() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}


//fun Fragment.hideNetworkErrorLayout(@IdRes id: Int? = null) {
//    val rootView = this.activity?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
//    val subView = rootView.takeIf { id != null }?.findViewById<ConstraintLayout>(id!!)
//
//    val loader = subView ?: rootView?.findViewById<ConstraintLayout>(R.id.networkErrorRoot)
//    subView ?: rootView?.removeView(loader)
//}

fun TextView.itsText(): String {
    return this.text.toString().trim()
}


fun Fragment.changeStatusBarColor(
    @ColorRes colorToSet: Int = 0,
    @DrawableRes drawableToSet: Int? = null
) {
    this.activity?.changeStatusBarColor(colorToSet, drawableToSet)
}


fun Activity.changeStatusBarColor(
    @ColorRes colorToSet: Int,
    @DrawableRes drawableToSet: Int? = null
) {
    if (drawableToSet == null) {
        val window = this.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(this, colorToSet)
    } else {
        val window = this.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window?.setBackgroundDrawableResource(drawableToSet)
    }

}

fun ImageView.tintWithColor(@ColorRes colorToSet: Int) {
    this.setColorFilter(ContextCompat.getColor(context, colorToSet))
}


fun TextView.chageTextColor(@ColorRes colorToSet: Int) {
    setTextColor(ContextCompat.getColor(context, colorToSet))
}

fun ImageView.clearTint() {
    this.colorFilter = null
}

//fun Activity.delay(runnable: Runnable, seconds: Float) {
//    Handler(Looper.getMainLooper()).postDelayed(runnable, (seconds * 1000).toLong())
//}
//
//fun Fragment.delay(runnable: Runnable, seconds: Float) {
//    Handler(Looper.getMainLooper()).postDelayed(runnable, (seconds * 1000).toLong())
//}


//fun TextView.setHtmlText(text: String?) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
//    } else {
//        this.text = Html.fromHtml(text)
//    }
//}

fun View.setThisBackgroundDrawable(@DrawableRes drawable: Int) {
    background = ContextCompat.getDrawable(context, drawable)
}

//fun Array<View>.secrete() {
//    forEach {
//        it.secreteB()
//    }
//}


fun View.disableTemp() {
    isEnabled = false
    postDelayed({
        isEnabled = true
    }, 2000)
}

fun Fragment.restoreStatusBar() {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = activity?.window?.insetsController

        if (controller != null) {
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

fun Fragment.hideStatusBar() {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = activity?.window?.insetsController

        if (controller != null) {
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
    //activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Fragment.buildGetContentRequest(function: (Uri?) -> Unit): ActivityResultLauncher<String> =
    this.registerForActivityResult(ActivityResultContracts.GetContent()) {
        function(it)
    }


fun Fragment.buildOpenDocumentRequest(function: (Uri?) -> Unit): ActivityResultLauncher<Array<String>> {
    return this.registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        function(it)
    }
}

fun Fragment.buildTakePhotoRequest(function: (Boolean) -> Unit): ActivityResultLauncher<Uri> {
    return this.registerForActivityResult(ActivityResultContracts.TakePicture()) {
        function(it)
    }
}

fun Fragment.buildSelectMultipleContentRequest(function: (MutableList<Uri>?) -> Unit): ActivityResultLauncher<String> {
    return this.registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        function(it)
    }
}