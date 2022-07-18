package com.example.uidemoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.uidemoactivity.databinding.ActivityMainBinding
import com.example.uidemoactivity.retrofitBuilder.RetrofitService
import com.example.uidemoactivity.viewModelFactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mFactory: MainViewModelFactory
    private lateinit var mViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository
    private val retrofitService: RetrofitService by lazy { RetrofitService.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainRepository = MainRepository(retrofitService)
        mFactory = MainViewModelFactory.getInstance(this.application, mainRepository)
        mViewModel = generateViewModel()
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this
        mViewModel.startRequestAirPollutionDataSource()
        initObserver()
    }

    private fun initObserver() {
        mViewModel.downVerticalInfoList.observe(this, {

        })

        mViewModel.topHorizontalInfoList.observe(this,{

        })
    }

    private inline fun <reified T : AndroidViewModel> generateViewModel(): T {
        return ViewModelProvider(this, mFactory).get(T::class.java)
    }
}