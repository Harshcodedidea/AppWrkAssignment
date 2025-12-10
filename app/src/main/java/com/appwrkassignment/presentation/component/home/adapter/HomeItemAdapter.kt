package com.appwrkassignment.presentation.component.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appwrkassignment.R
import com.appwrkassignment.data.DataItemModel
import com.appwrkassignment.databinding.ItemLayoutBinding

class HomeItemAdapter(
    var list: MutableList<DataItemModel>?,
    private val onItemClick: (DataItemModel?, Int) -> Unit
) : RecyclerView.Adapter<HomeItemAdapter.DataViewHolder>() {

    inner class DataViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: DataItemModel?, position: Int) {
            binding.tvItemTitle.text = "Item ".plus(position + 1).plus(" ").plus(item?.title ?: "")
            binding.tvItemDescription.text = "Description: ".plus(item?.description ?: "")
            binding.tvItemStatus.text =
                binding.tvItemStatus.context.getString(if (item?.status == true) R.string.def_completed else R.string.def_pending)
            binding.root.setOnClickListener {
                onItemClick(item, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(list?.get(position), position)
    }

    override fun getItemCount(): Int = list?.size ?: 0
    fun updateAdapterData(updatedItem: DataItemModel, pos: Int) {
        if (list?.indices?.contains(pos) == true) {
            list?.set(pos, updatedItem)
            notifyItemChanged(pos)  // Notify RecyclerView to refresh this item
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshList(filteredData: MutableList<DataItemModel>?) {
        list = filteredData
        notifyDataSetChanged()
    }
}