package com.example.uidemoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableField
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

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                mViewModel.searchText.set(text.toString())
            }
        })

        mBinding.searchText.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, focus: Boolean) {
                if (focus) {
                    mBinding.filterRecyclerView.visibility = View.VISIBLE
                    mBinding.searchingText.visibility = View.VISIBLE
                    mBinding.horizontalRecyclerView.visibility = View.GONE
                    mBinding.verticalRecyclerView.visibility = View.GONE
                } else {
                    mBinding.filterRecyclerView.visibility = View.GONE
                    mBinding.searchingText.visibility = View.GONE
                    mBinding.horizontalRecyclerView.visibility = View.VISIBLE
                    mBinding.verticalRecyclerView.visibility = View.VISIBLE
                }
            }
        }

        mBinding.back.setOnClickListener {
            mBinding.searchText.clearFocus()
        }
    }

    private fun initView() {
        mBinding.searchingText.text = mBinding.root.context.getString(R.string.initial_search_text)
        mBinding.horizontalRecyclerView.setInitial(HORIZONTAL_TYPE)
        mBinding.verticalRecyclerView.setInitial(VERTICAL_TYPE)
        mBinding.filterRecyclerView.setInitial(VERTICAL_TYPE)
    }

    private fun RecyclerView.setInitial(currentType: Int) {
        val mLayoutManager = LinearLayoutManager(this@MainActivity)
        mLayoutManager.orientation = if (currentType == HORIZONTAL_TYPE) {
            LinearLayoutManager.HORIZONTAL
        } else {
            LinearLayoutManager.VERTICAL
        }
        this.layoutManager = mLayoutManager
        this.adapter = RecyclerAdapter(currentType, object : RecyclerAdapter.IClickListener {
            override fun onClick(msg: String) {
                Toast.makeText(
                    mBinding.root.context, msg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initObserver() {
        mViewModel.downVerticalInfoList.observe(this, {
            (mBinding.verticalRecyclerView.adapter as? RecyclerAdapter)?.infoList = it
        })

        mViewModel.topHorizontalInfoList.observe(this, {
            (mBinding.horizontalRecyclerView.adapter as? RecyclerAdapter)?.infoList = it
        })

        mViewModel.searchText.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val searchingText = sender as ObservableField<String>
                showFilterUIStatus(searchingText.get().toString())
            }
        })
    }

    private fun showFilterUIStatus(searchingText: String) {
        if(searchingText.isEmpty()) {
            mBinding.filterRecyclerView.visibility = View.GONE
            mBinding.searchingText.text = "輸入「站名」查詢該地區空污資訊"
        } else {
            val list =  mViewModel.originVerticalInfoList.filter {
                it.siteName.contains(searchingText)
            }
            if(list.isEmpty()) {
                mBinding.searchingText.text = "找不到「$searchingText」相關的空污資料"
            } else {
                mBinding.filterRecyclerView.visibility = View.VISIBLE
                (mBinding.filterRecyclerView.adapter as? RecyclerAdapter)?.infoList = list
            }
        }
    }


    private inline fun <reified T : ViewModel> generateViewModel(): T {
        return ViewModelProvider(this, mFactory).get(T::class.java)
    }

    override fun onBackPressed() {
        if (mBinding.searchText.isFocused) {
            mBinding.searchText.clearFocus()
        } else {
            super.onBackPressed()
        }
    }
}