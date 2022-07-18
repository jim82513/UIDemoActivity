package com.example.uidemoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uidemoactivity.adapter.RecyclerAdapter
import com.example.uidemoactivity.databinding.ActivityMainBinding
import com.example.uidemoactivity.retrofitBuilder.RetrofitService
import com.example.uidemoactivity.viewModelFactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mFactory: MainViewModelFactory
    private lateinit var mViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository
    private lateinit var retrofitService: RetrofitService
    private val HORIZONTAL_TYPE = 0
    private val VERTICAL_TYPE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        retrofitService = RetrofitService.create()
        mainRepository = MainRepository(retrofitService)
        mFactory = MainViewModelFactory.getInstance(this.application, mainRepository)
        mViewModel = generateViewModel()
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this
        mViewModel.startRequestAirPollutionDataSource()
        initObserver()
        initView()
    }

    private fun initView() {
        mBinding.horizontalRecyclerView.setInitial(HORIZONTAL_TYPE)
        mBinding.verticalRecyclerView.setInitial(VERTICAL_TYPE)
    }

    private fun RecyclerView.setInitial(currentType:Int){
        val mLayoutManager = LinearLayoutManager(this@MainActivity)
        mLayoutManager.orientation = if(currentType == HORIZONTAL_TYPE) {
            LinearLayoutManager.HORIZONTAL
        } else {
            LinearLayoutManager.VERTICAL
        }
        this.layoutManager = mLayoutManager
        this.adapter = RecyclerAdapter(currentType)
    }

    private fun initObserver() {
        mViewModel.downVerticalInfoList.observe(this, {
            (mBinding.verticalRecyclerView.adapter as RecyclerAdapter).infoList = it
        })

        mViewModel.topHorizontalInfoList.observe(this,{
            (mBinding.horizontalRecyclerView.adapter as RecyclerAdapter).infoList = it
        })
    }

    private inline fun <reified T : AndroidViewModel> generateViewModel(): T {
        return ViewModelProvider(this, mFactory).get(T::class.java)
    }
}