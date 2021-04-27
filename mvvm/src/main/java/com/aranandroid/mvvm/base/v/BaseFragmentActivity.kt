package com.aranandroid.mvvm.base.v


import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.aranandroid.mvvm.base.vm.BaseViewModel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


open class BaseFragmentActivity<VM : BaseViewModel,ViewDataBinding : androidx.databinding.ViewDataBinding?>(
    layoutId: Int
) : FragmentActivity() {
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
