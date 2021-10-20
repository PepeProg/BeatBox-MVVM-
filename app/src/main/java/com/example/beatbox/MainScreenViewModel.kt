package com.example.beatbox

import android.content.Context
import android.view.View
import android.widget.SeekBar
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

class MainScreenViewModel(private val context: Context, private val beatBox: BeatBox): BaseObservable() {
    init {
        liveResourceName.observeForever{    //here we're signing up for getting seekBar's str pattern
            seekBarStr = getSeekBarStr(it)
            notifyPropertyChanged(BR.seekBarStr)
        }
    }

    var speedInPerCents = 100
        set(speed: Int) {
            field = speed
            seekBarStr = getSeekBarStr(liveResourceName.value!!)
            beatBox.rate = speed.toFloat() / 100
            notifyPropertyChanged(BR.seekBarStr)
        }

    fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        speedInPerCents = progress
    }

    @get:Bindable
    var seekBarStr: String = ""     //this str is showing above seekBar

    private fun getSeekBarStr(resourceName: String): String {   //here we're building string with resources' pattern and playback speed
        val strID = context.resources.getIdentifier(
            resourceName,
            "string",
            context.packageName
        )
        return context.resources.getString(strID, speedInPerCents)
    }

    companion object {
        val liveResourceName: MutableLiveData<String> = MutableLiveData()
    }
}
@BindingAdapter("seek_bar_str_resource")
fun setSeekBarResource(view: View, str: String) {   //this func can be called from xml using `app::seek_bar_str_resource="str"` from any View
    MainScreenViewModel.liveResourceName.value = str
}