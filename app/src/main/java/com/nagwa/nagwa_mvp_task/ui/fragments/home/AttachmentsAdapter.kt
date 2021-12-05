package com.nagwa.nagwa_mvp_task.ui.fragments.home

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.nagwa.nagwa_mvp_task.R
import com.nagwa.nagwa_mvp_task.data.local.DownloadedAttachmentEntity
import com.nagwa.nagwa_mvp_task.data.remote.models.AttachmentModel
import com.nagwa.nagwa_mvp_task.databinding.RowAttachmentBinding
import com.nagwa.nagwa_mvp_task.ui.base.GeneralAdapter
import com.nagwa.nagwa_mvp_task.utils.secreteB
import com.nagwa.nagwa_mvp_task.utils.showB

class AttachmentsAdapter(var dataSet: MutableList<DownloadedAttachmentEntity>, var callback: Callback) :
    GeneralAdapter(dataSet, R.layout.row_attachment, null) {


    //    val dataImages = mutableListOf<String>()
    override fun onBindNormalView(position: Int, view: View) = with(view) {
        val binding = RowAttachmentBinding.bind(view)

        dataSet[position].let { item ->
            if(item.isDownloaded){
                binding.downloadLayout.isEnabled = false
                binding.downloadTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                binding.downloadTv.text = "Added"
                binding.deleteBtn.showB()
            }else{
                binding.downloadLayout.isEnabled = true
                binding.downloadTv.text = "+ Add to Queue"
                binding.downloadTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_downward_24, 0)
                binding.deleteBtn.secreteB()

            }
            when (dataSet[position].type) {
                    "PDF" -> {
                        binding.iconView.setImageDrawable(
                            AppCompatResources.getDrawable(
                                context,
                                R.drawable.ic_pdf_white
                            )
                        )
                    }
                    "VIDEO" -> {
                        binding.iconView.setImageDrawable(
                            AppCompatResources.getDrawable(
                                context,
                                R.drawable.ic_video
                            )
                        )

                    }
                }

            binding.titleTv.text = item.name
            binding.typeTv.text = item.type

            binding.deleteBtn.setOnClickListener {
                callback.onFileDeleted(item, position)
            }
            binding.downloadLayout.setOnClickListener {
                callback.onFileDownload(item, position)
            }
        }

    }

    override fun onBindEmptyView(position: Int, view: View) = with(view) {

    }


    // add list into dataSet
    fun addNewData(newData: MutableList<DownloadedAttachmentEntity>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    // add only one item into the list (dataSet)
    fun addItem(newItem: DownloadedAttachmentEntity) {
        dataSet.add(newItem)
        notifyDataSetChanged()
    }

    fun updateItem(updatedItem: DownloadedAttachmentEntity, position: Int){
        dataSet[position] = updatedItem
        notifyItemChanged(position)
    }

//    // Remove Item
//    fun removeFile(position: Int) {
//        dataSet.removeAt(position)
//        notifyItemRemoved(position)
//        notifyItemRangeChanged(position, itemCount)
//        if (dataSet.size == 0) {
//            callback.onAllFilesDeleted()
//        }
//
//    }


    override fun getItemCount(): Int {
        return dataSet.size
    }

    interface Callback {
        fun onFileDownload(item: DownloadedAttachmentEntity, position: Int)
        fun onFileDeleted(item: DownloadedAttachmentEntity, position: Int)
    }
}