package com.example.duckduckgoapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.duckduckgoapp.R
import com.example.duckduckgoapp.databinding.FragmentCharacterDetailsBinding
import com.example.duckduckgoapp.ui.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStateFlows()
    }

    private fun setStateFlows() {
        lifecycleScope.launchWhenStarted {
            activityViewModel.selectedCharacterState.collect { state ->
                when (state) {
                    is MainViewModel.SelectedCharacterState.SELECTED -> {
                        with(binding) {
                            characterNameTextView.text = state.character.name
                            characterDescriptionTextView.text = state.character.description
                            duckDuckGoLinkTextView.text = state.character.duckDuckLink
                            duckDuckGoLinkTextView.tag = state.character.duckDuckLink
                            Glide.with(this.root).load(state.character.imageURL)
                                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                                .error(R.drawable.ic_baseline_image_not_supported_24)
                                .into(characterImageTextView)
                            fragmentCharacterDetailsLayout.isVisible = true
                        }
                    }
                    is MainViewModel.SelectedCharacterState.UNSELECTED -> {

                    }
                }
            }
        }
    }
}