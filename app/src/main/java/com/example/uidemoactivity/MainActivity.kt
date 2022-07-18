package com.example.uidemoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uidemoactivity.adapter.RecyclerAdapter
import com.example.uidemoactivity.databinding.ActivityMainBinding
import com.example.uidemoactivity.retrofitBuilder.AirPollutionService
import com.example.uidemoactivity.viewModelFactory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    companion object {
        private const val HORIZONTAL_TYPE = 0
        private const val VERTICAL_TYPE = 1
    }

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mFactory: MainViewModelFactory
    private lateinit var mViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository
    private lateinit var airPollutionService: AirPollutionService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        airPollutionService = AirPollutionService.create()
        mainRepository = MainRepository(airPollutionService)
        mFactory = MainViewModelFactory.getInstance(mainRepository)
        mViewModel = ViewModelProvider(this, mFactory).get(MainViewModel::class.java).apply {
            this.startRequestAirPollutionDataSource()
        }
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this
        initObserver()
        initListener()
        initView()
    }

    private fun initListener() {
        mBinding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }
        })
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
        this.adapter = RecyclerAdapter(currentType, object : RecyclerAdapter.IClickListener {
            override fun onClick(msg: String) {
                Toast.makeText(mBinding.root.context, msg ,
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initObserver() {
        mViewModel.downVerticalInfoList.observe(this, {
            (mBinding.verticalRecyclerView.adapter as? RecyclerAdapter)?.infoList = it
        })

        mViewModel.topHorizontalInfoList.observe(this,{
            (mBinding.horizontalRecyclerView.adapter as? RecyclerAdapter)?.infoList = it
        })
    }

    private inline fun <reified T : ViewModel> generateViewModel(): T {
        return ViewModelProvider(this, mFactory).get(T::class.java)
    }
}