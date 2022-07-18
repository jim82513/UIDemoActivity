package com.example.uidemoactivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uidemoactivity.adapter.viewHolder.HorizontalViewHolder
import com.example.uidemoactivity.adapter.viewHolder.VerticalViewHolder
import com.example.uidemoactivity.dataEntity.AirPollutionInfo
import com.example.uidemoactivity.databinding.HorizonItemViewBinding
import com.example.uidemoactivity.databinding.VerticalItemViewBinding

class RecyclerAdapter(private val currentType: Int, private val _listener: IClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ITEM_HORIZONTAL = 0
        private const val ITEM_VERTICAL = 1
    }

    var infoList: List<AirPollutionInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    interface IClickListener{
        fun onClick(msg: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_HORIZONTAL) {
            HorizontalViewHolder(HorizonItemViewBinding.inflate(layoutInflater, parent, false))
        } else {
            VerticalViewHolder(VerticalItemViewBinding.inflate(layoutInflater, parent, false), _listener)
        }
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HorizontalViewHolder) {
            holder.setEntity(infoList[position])
        } else if (holder is VerticalViewHolder) {
            holder.setEntity(infoList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentType == ITEM_HORIZONTAL) {
            ITEM_HORIZONTAL
        } else {
            ITEM_VERTICAL
        }
    }

}