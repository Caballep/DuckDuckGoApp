package com.example.duckduckgoapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.duckduckgoapp.R
import com.example.duckduckgoapp.databinding.ActivityMainBinding
import com.example.duckduckgoapp.ext.deviceModeIsLandscape
import com.example.duckduckgoapp.ui.fragments.CharacterDetailsFragment
import com.example.duckduckgoapp.ui.fragments.CharacterListFragment
import com.example.duckduckgoapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private var isPortraitDetailsDisplaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStateFlows()

        if (deviceModeIsLandscape()) {
            displayCharacterListFragment()
            displayCharacterDetailsFragment()
        } else {
            displayCharacterListFragment()
        }

        mainViewModel.fetchCharacters()
    }

    override fun onBackPressed() {
        if (!deviceModeIsLandscape() && isPortraitDetailsDisplaying) {
            displayCharacterListFragment()
            isPortraitDetailsDisplaying = false
        } else {
            this.finish()
        }
    }

    private fun setStateFlows() {
        lifecycleScope.launchWhenCreated {
            mainViewModel.selectedCharacterState.collect { state ->
                when (state) {
                    is MainViewModel.SelectedCharacterState.SELECTED -> {
                        if (!deviceModeIsLandscape()) {
                            replaceCharacterFragment()
                        }
                    }
                    is MainViewModel.SelectedCharacterState.UNSELECTED -> { }
                }
            }
        }
    }

    private fun displayCharacterListFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.character_list_fragment_container, CharacterListFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun displayCharacterDetailsFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.character_details_fragment_container, CharacterDetailsFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun replaceCharacterFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.character_list_fragment_container, CharacterDetailsFragment())
        transaction.addToBackStack(null)
        transaction.commit()
        isPortraitDetailsDisplaying = true
    }
}