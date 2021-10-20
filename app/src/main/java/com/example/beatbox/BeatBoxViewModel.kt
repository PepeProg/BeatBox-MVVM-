package com.example.beatbox

import android.content.Context
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class BeatBoxViewModel(assets: AssetManager, context: Context): ViewModel() {
    val beatBox = BeatBox(assets)   //here we're saving beatBox class in order not to stop sounds
    val mainBindingViewModel = MainScreenViewModel(context, beatBox)    //we're saving main screen's ViewModel here to save sound's speed

    override fun onCleared() {
        super.onCleared()
        beatBox.release()   //clearing sounds' resources
    }
}

class BeatBoxViewModelFactory(private val assets: AssetManager, private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {     //it is ViewModel's factory, helping us creating ViewModels with args
        if (modelClass.isAssignableFrom(BeatBoxViewModel::class.java))
            return BeatBoxViewModel(assets, context) as T
        else
            throw IllegalStateException("ViewModel was not found")
    }
}