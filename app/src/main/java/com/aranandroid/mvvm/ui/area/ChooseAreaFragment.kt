package com.aranandroid.mvvm.ui.area

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import com.aranandroid.mvvm.R
import com.aranandroid.mvvm.databinding.ChooseAreaBindingImpl
import com.aranandroid.mvvm.ui.MainActivity
import com.aranandroid.mvvm.base.vm.BaseFragment
import com.aranandroid.mvvm.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.choose_area.*

class ChooseAreaFragment :
    BaseFragment<ChooseAreaViewModel, ChooseAreaBindingImpl>(R.layout.choose_area) {
    private var progressDialog: ProgressDialog? = null
    private lateinit var adapter: ArrayAdapter<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ChooseAreaAdapter(context!!, R.layout.simple_item, viewModel.dataList)
        listView.adapter = adapter
        observe()
    }

    private fun observe() {
        // 通过观察当前水平的值  决定title的样式 （livedata）
        viewModel.currentLevel.observe(this, Observer { level ->
            when (level) {
                LEVEL_PROVINCE -> {
                    viewModel.title.value = "中国"
                    backButton.visibility = View.GONE
                }
                LEVEL_CITY -> {
                    viewModel.title.value = viewModel.selectedProvince?.provinceName
                    backButton.visibility = View.VISIBLE
                }
                LEVEL_COUNTY -> {
                    viewModel.title.value = viewModel.selectedCity?.cityName
                    backButton.visibility = View.VISIBLE
                }
            }
        })
        viewModel.dataChanged.observe(this, Observer {
            adapter.notifyDataSetChanged()
            listView.setSelection(0)
            closeProgressDialog()
        })
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) showProgressDialog()
            else closeProgressDialog()
        })
        viewModel.areaSelected.observe(this, Observer { selected ->
            if (selected && viewModel.selectedCounty != null) {
                if (activity is MainActivity) {
                    val intent = Intent(activity, WeatherActivity::class.java)
                    intent.putExtra("weather_id", viewModel.selectedCounty!!.weatherId)
                    startActivity(intent)
                    activity?.finish()
                } else if (activity is WeatherActivity) {
                    val weatherActivity = activity as WeatherActivity
                    weatherActivity.drawerLayout.closeDrawers()
                    weatherActivity.viewModel.weatherId = viewModel.selectedCounty!!.weatherId
                    weatherActivity.viewModel.refreshWeather()
                }
                viewModel.areaSelected.value = false
            }
        })
        if (viewModel.dataList.isEmpty()) {
            viewModel.getProvinces()
        }
    }

    /**
     * 显示进度对话框
     */
    private fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity)
            progressDialog?.setMessage("正在加载...")
            progressDialog?.setCanceledOnTouchOutside(false)
        }
        progressDialog?.show()
    }

    /**
     * 关闭进度对话框
     */
    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }

    // 伴生对象
    companion object {
        // 省 市 县
        const val LEVEL_PROVINCE = 0
        const val LEVEL_CITY = 1
        const val LEVEL_COUNTY = 2
    }

}