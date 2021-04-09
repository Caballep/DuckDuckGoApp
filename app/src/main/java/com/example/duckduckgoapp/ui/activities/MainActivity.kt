package com.example.duckduckgoapp.ui.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.duckduckgoapp.R
import com.example.duckduckgoapp.databinding.ActivityMainBinding
import com.example.duckduckgoapp.ui.fragments.CharacterListFragment
import com.example.duckduckgoapp.ui.viewmodels.MainViewModel
import com.example.duckduckgoapp.ui.viewmodels.MainViewModel.MainState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViews()

        setMainStateCollector()
        mainViewModel.fetchCharacters()
    }

    private fun setViews() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, CharacterListFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //TODO: https://www.youtube.com/watch?v=KJGKj078Qag

    private fun setMainStateCollector() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.mainState.collect { state ->
                when(state) {
                    is MainState.SUCCESS -> {

                    }
                    is MainState.ERROR -> {

                    }
                    is MainState.LOADING -> {

                    }
                    else -> {
                        // BLANK or any other unhandled state
                    }
                }
            }
        }
    }
}