package com.appwrkassignment.utilities.extensions

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.appwrkassignment.data.DataItemModel
import com.appwrkassignment.databinding.DialogDetailScreenBinding
import androidx.core.graphics.drawable.toDrawable
import com.appwrkassignment.R

fun Context.showFullScreenDialog(
    dataModel: DataItemModel?,
    onClose: (DataItemModel?) -> Unit = {}
) {
    val dialog = Dialog(this)
    val binding = DialogDetailScreenBinding.inflate(LayoutInflater.from(this))

    dialog.setContentView(binding.root)

    // Fullscreen FLAG
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    // Transparent background
    dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

    // No default dialog frame
    dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

    // Set values
    binding.tvTitle.text = dataModel?.title ?: ""
    binding.tvMessage.text = dataModel?.description ?: ""
    binding.tvStatus.text =
        getString(R.string.def_status).plus(binding.tvStatus.context.getString(if (dataModel?.status == true) R.string.def_completed else R.string.def_pending))
    binding.btnChangeStatus.text = getString(
        R.string.def_mark_as,
        binding.tvStatus.context.getString(if (dataModel?.status == true) R.string.def_pending else R.string.def_completed)
    )
    binding.btnChangeStatus.setOnClickListener {
        dataModel?.status = dataModel.status?.not() ?: false
        onClose(dataModel)
        dialog.dismiss()
    }

    dialog.setCancelable(true)
    dialog.show()
}