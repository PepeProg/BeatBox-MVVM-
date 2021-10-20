package com.example.beatbox

import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.lang.Exception

private const val SOUND_FOLDER = "sample_sounds"
private const val TAG = "BeatBox"
private const val MAX_SOUNDS = 5


class BeatBox(private val assets: AssetManager) {
    val sounds: List<Sound>
    var rate = 1F
    private val soundPool = SoundPool.Builder()     //creating class, including all sound streams
        .setMaxStreams(MAX_SOUNDS)
        .build()
    init {
        sounds = loadSounds()
    }


    private fun loadSounds(): List<Sound> {
        val soundNames =  try {
           assets.list(SOUND_FOLDER)!!  //returns an array of all files in given directory
        } catch(e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            emptyArray<String>()
        }

        val soundList = mutableListOf<Sound>()
        soundNames.forEach { soundName->
            val assetPath = "$SOUND_FOLDER/$soundName"
            val sound = Sound(assetPath)
            try {
                load(sound)
                soundList.add(sound)
            }
            catch(ioe: IOException) {
                Log.e(TAG, "Could not load sound: $soundName", ioe)
            }
        }
        return soundList
    }

    private fun load(sound: Sound) {
        val assetFD = assets.openFd(sound.assetPath)    //returns file descriptor
        val soundId = soundPool.load(assetFD, 1)
        sound.soundId = soundId
    }

    fun release() {
        soundPool.release()     //releasing resources
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0F, 1.0F, 1, 0, rate)
        }
    }
}