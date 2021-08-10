package com.aranandroid.mvvm.ui

import android.Manifest
import android.os.Bundle
import com.aranandroid.mvvm.R
import com.aranandroid.mvvm.ui.weather.WeatherActivity
import android.content.Intent
import android.widget.Toast
import com.aranandroid.mvvm.base.vm.BaseActivity
import com.aranandroid.mvvm.databinding.ActivityMainBinding
import com.aranandroid.mvvm.permission.PermissonDialog
import com.aranandroid.mvvm.ui.area.ChooseAreaFragment
import com.permissionx.guolindev.PermissionX


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {

    //测试
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //请求权限
        registerPermissions()
        if (viewModel.isWeatherCached()) {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            supportFragmentManager.beginTransaction().replace(binding.container.id, ChooseAreaFragment()).commit()
        }

    }

    private fun registerPermissions() {
        PermissionX.init(this).permissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                val message = "PermissionX需要您同意以下权限才能正常使用"
                val dialog = PermissonDialog(this, message, deniedList)
                scope.showRequestReasonDialog(dialog)
            }.onForwardToSettings { scope, deniedList ->
                val message = "您需要去设置中手动开启以下权限"
                val dialog = PermissonDialog(this, message, deniedList)
                scope.showForwardToSettingsDialog(dialog)
            }.request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
