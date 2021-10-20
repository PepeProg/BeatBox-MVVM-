package com.example.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox):BaseObservable() {
    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()    //notify all bound properties about all changes
        }

    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

    @get:Bindable    //we need to anno all bound properties, that will get updates
    val title
        get() = sound?.name
}
