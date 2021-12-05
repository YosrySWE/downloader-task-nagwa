package com.nagwa.nagwa_mvp_task.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class GeneralAdapter(
    val data: MutableList<*>,
    @LayoutRes var normalLayout: Int,
    @LayoutRes var emptyLay: Int? = null
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {

        return if (emptyLay == null) {
            ViewHolder(
                LayoutInflater.from(p0.context).inflate(normalLayout, p0, false)
            )
        } else {
            when (p1) {
                VIEW_TYPE_NORMAL -> ViewHolder(
                    LayoutInflater.from(p0.context).inflate(normalLayout, p0, false)
                )
                VIEW_TYPE_EMPTY -> EmptyViewHolder(
                    LayoutInflater.from(p0.context).inflate(emptyLay!!, p0, false)
                )
                else -> EmptyViewHolder(
                    LayoutInflater.from(p0.context).inflate(emptyLay!!, p0, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {

        return if (emptyLay == null) {
            data.size
        } else {
            if (data.isNotEmpty()) {
                data.size
            } else {
                1
            }
        }
    }


    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return if (emptyLay == null) {
            VIEW_TYPE_NORMAL
        } else {
            if (data.isNotEmpty()) {
                VIEW_TYPE_NORMAL
            } else {
                VIEW_TYPE_EMPTY
            }
        }
    }

    override fun onBindViewHolder(p0: BaseViewHolder, p1: Int) {
        if (emptyLay == null) {
            (p0 as ViewHolder).onBind(p1)
        } else {
            p0.onBind(p1)
        }
    }


    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun clear() = with(itemView) {
            onClearNormalView(this, currentPosition)
        }


        override fun onBind(position: Int): Unit = with(itemView) {
            super.onBind(position)
            onBindNormalView(position, this)
        }
    }


    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun clear() = with(itemView) {
            onClearEmptyView(this)
        }


        override fun onBind(position: Int): Unit = with(itemView) {
            super.onBind(position)

            onBindEmptyView(position, this)
        }
    }

    abstract fun onBindNormalView(position: Int, view: View)
    open fun onClearNormalView(view: View, position: Int) {

    }

    abstract fun onBindEmptyView(position: Int, view: View)
    open fun onClearEmptyView(view: View) {

    }

}