package com.example.beatbox

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel:BaseObservable() {
    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }

    @get:Bindable
    val title
        get() = sound?.name
}
