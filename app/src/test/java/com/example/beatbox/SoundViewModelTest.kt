package com.example.beatbox

import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {
    private lateinit var subject: SoundViewModel
    private lateinit var sound: Sound
    private lateinit var beatBox: BeatBox

    @Before
    fun setUp() {           //calls before all tests to tune the class
        sound = Sound("assetPath")
        beatBox = mock(BeatBox::class.java)  //creating an emulation of real beatbox object
        subject = SoundViewModel(beatBox)
        subject.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle()  {
        MatcherAssert.assertThat(subject.title, `is`(sound.name))   //hamcrest's method, helping with doing different compares,
                                                                    //requires any count of matchers, that will be used to first argument
    }

    @Test
    fun callsBeatBoxPlayOnButtonClicked() {
        subject.onButtonClicked()
        verify(beatBox).play(sound)    //hear we are checking if func `play(sound)` has been called
    }
}