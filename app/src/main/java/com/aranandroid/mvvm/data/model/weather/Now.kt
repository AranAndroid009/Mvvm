package com.aranandroid.mvvm.data.model.weather

import com.google.gson.annotations.SerializedName

class Now {
    @SerializedName("tmp")
    var temperature = ""
    @SerializedName("cond")
    lateinit var more: More

    fun degree() = "$temperature℃"

    inner class More {
        @SerializedName("txt")
        var info = ""
    }
}