package com.example.beatbox

import android.content.res.AssetManager
import android.util.Log
import java.lang.Exception

private const val SOUND_FOLDER = "sample_sounds"
private const val TAG = "BeatBox"



class BeatBox(private val assets: AssetManager) {
    val sounds: List<Sound>

    init {
        sounds = loadSounds()
    }

    private fun loadSounds(): List<Sound> {
        val soundNames =  try {
           assets.list(SOUND_FOLDER)!!
        } catch(e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            emptyArray<String>()
        }

        val soundList = mutableListOf<Sound>()
        soundNames.forEach { soundName->
            val assetPath = "$SOUND_FOLDER/$soundName"
            soundList.add(Sound(assetPath))
        }
        return soundList
    }
}