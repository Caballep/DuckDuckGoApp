package com.example.duckduckgoapp.ui.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.duckduckgoapp.R
import com.example.duckduckgoapp.ui.viewmodels.MainViewModel
import com.example.duckduckgoapp.ui.viewmodels.MainViewModel.MainState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setMainStateCollector()
        mainViewModel.fetchCharacters()
    }

    private fun setMainStateCollector() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.mainState.collect { state ->
                when(state) {
                    is MainState.SUCCESS -> {
                        findViewById<TextView>(R.id.textView).text = state.characters.first().name
                    }
                    is MainState.ERROR -> {
                        findViewById<TextView>(R.id.textView).text = state.message
                    }
                    is MainState.LOADING -> {
                        findViewById<TextView>(R.id.textView).text = "Loading..."
                    }
                    else -> {
                        // BLANK or any other unhandled state
                    }
                }
            }
        }
    }
}