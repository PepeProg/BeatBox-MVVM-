package com.example.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {
    private val beatBoxViewModel: BeatBoxViewModel by lazy{ //here we are using view model with args(ViewModelFactory allows us to do it)
        ViewModelProvider(this, BeatBoxViewModelFactory(assets, this))
            .get(BeatBoxViewModel::class.java)
    }
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(  //set view for activity with possibility of data-binding
            this,
            R.layout.activity_main
        )
        mainBinding.viewModel = beatBoxViewModel.mainBindingViewModel   //set ViewModel for main screen

        mainBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBoxViewModel.beatBox.sounds)
        }
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewModel = SoundViewModel(beatBoxViewModel.beatBox)
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings()    //executes bindings immediately
            }
        }
    }

    private inner class SoundAdapter(private val soundsList: List<Sound>)
        : RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding: ListItemSoundBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            holder.bind(soundsList[position])
        }

        override fun getItemCount(): Int {
            return soundsList.size
        }
    }

}