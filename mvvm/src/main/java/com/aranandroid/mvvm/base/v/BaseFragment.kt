package com.aranandroid.mvvm.base.v

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.aranandroid.mvvm.base.vm.BaseViewModel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

open class BaseFragment<VM : BaseViewModel,ViewDataBinding : androidx.databinding.ViewDataBinding?>(
    private val layoutId : Int) : Fragment(){
    // lifecycle
    val viewModel by lazy {
        ViewModelProviders.of(this).get(
            getClass() as Class<VM>) }

    var binding : ViewDataBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        binding = DataBindingUtil.bind<ViewDataBinding>(view)
        return view
    }

    fun getClass(): Type? {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM?>
    }


}