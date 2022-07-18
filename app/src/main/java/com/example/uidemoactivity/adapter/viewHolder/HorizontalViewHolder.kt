package com.example.uidemoactivity.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.uidemoactivity.dataEntity.AirPollutionInfo
import com.example.uidemoactivity.databinding.HorizonItemViewBinding

class HorizontalViewHolder(private val mBinding: HorizonItemViewBinding) : RecyclerView.ViewHolder(mBinding.root){
    fun setEntity(mEntity: AirPollutionInfo){
        mBinding.entity = mEntity
    }
}