package com.aranandroid.mvvm.data.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

open class BaseNetwork {

    /**
     * enqueue 是retrofit2网络请求的回调方法
     * 添加suspendCoroutine添加协程作用域（将回调函数 转换 协程/挂起函数）
     */
    suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    // 异常返回值 continuation.resumeWithException
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    // 正常返回值 continuation.resume
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

}