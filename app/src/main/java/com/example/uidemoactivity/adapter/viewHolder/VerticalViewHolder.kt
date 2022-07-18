package com.example.uidemoactivity.adapter.viewHolder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uidemoactivity.R
import com.example.uidemoactivity.adapter.RecyclerAdapter
import com.example.uidemoactivity.dataEntity.AirPollutionInfoEntity
import com.example.uidemoactivity.databinding.VerticalItemViewBinding

class VerticalViewHolder(private val mBinding: VerticalItemViewBinding, private val listener: RecyclerAdapter.IClickListener) : RecyclerView.ViewHolder(mBinding.root){
    private lateinit var entity: AirPollutionInfoEntity
    fun setEntity(mEntity: AirPollutionInfoEntity){
        entity = mEntity
        mBinding.entity = mEntity
        setUIStatus()
        setClickButtonListener()
    }

    private fun setClickButtonListener() {
        mBinding.clickButton.setOnClickListener{
            listener.onClick("${entity.mSiteID} ${entity.mSiteName}")
        }
    }

    private fun setUIStatus() {
        if(entity.mStatus.equals(mBinding.root.context.getString(R.string.good))){
            mBinding.status.text = mBinding.root.context.getString(R.string.go_out)
            mBinding.clickButton.visibility = View.GONE
        } else {
            mBinding.status.text = entity.mStatus
            mBinding.clickButton.visibility = View.VISIBLE
        }
    }
}