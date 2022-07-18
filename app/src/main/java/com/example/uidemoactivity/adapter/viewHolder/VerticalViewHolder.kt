package com.example.uidemoactivity.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.uidemoactivity.R
import com.example.uidemoactivity.dataEntity.AirPollutionInfoEntity
import com.example.uidemoactivity.databinding.VerticalItemViewBinding

class VerticalViewHolder(private val mBinding: VerticalItemViewBinding) : RecyclerView.ViewHolder(mBinding.root){
    fun setEntity(mEntity: AirPollutionInfoEntity){
        mBinding.entity = mEntity
        mBinding.status.text = if(mEntity.mStatus.equals(mBinding.root.context.getString(R.string.good))){
            "The status is good, we want to go out to have fun"
        } else {
            mEntity.mStatus
        }
    }
}