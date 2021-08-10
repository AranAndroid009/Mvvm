package com.aranandroid.mvvm.base.vm


import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.aranandroid.mvvm.base.m.BaseViewModel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


open class BaseActivity<VM : BaseViewModel, ViewDataBinding : androidx.databinding.ViewDataBinding?>(
    layoutId: Int
) : AppCompatActivity() {
    val viewModel by lazy {
        ViewModelProviders.of(this)
            .get(getClass() as Class<VM>)
    }

    val binding by lazy {
        DataBindingUtil.setContentView<ViewDataBinding>(
            this,
            layoutId
        )
    }



    fun getClass(): Type? {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM?>
    }


}
