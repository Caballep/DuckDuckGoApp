package com.example.duckduckgoapp.local.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duckduckgoapp.databinding.FragmentCharacterListBinding
import com.example.duckduckgoapp.local.models.Character
import com.example.duckduckgoapp.local.ui.adapters.CharacterListAdapter
import com.example.duckduckgoapp.local.ui.adapters.CharacterListListener
import com.example.duckduckgoapp.local.ui.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class CharacterListFragment : Fragment(), CharacterListListener {

    private lateinit var binding: FragmentCharacterListBinding
    private val activityViewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStateFlows()
        setViews()
    }

    private fun setStateFlows() {
        lifecycleScope.launchWhenStarted {
            activityViewModel.charactersState.collect { state ->
                when (state) {
                    is MainViewModel.CharactersState.SUCCESS -> {
                        setSuccessStateViews(state.characters)
                    }
                    is MainViewModel.CharactersState.ERROR -> {
                        setErrorStateViews()
                    }
                    is MainViewModel.CharactersState.LOADING -> {
                        setLoadingStateViews()
                    }
                    is MainViewModel.CharactersState.FILTERED -> {
                        setSuccessStateViews(state.characters)
                    }
                    else -> {
                        // BLANK or any other unhandled state
                    }
                }
            }
        }
    }

    private fun setSuccessStateViews(characterList: List<Character>) {
        with(binding) {
            sportsRecycler.adapter =
                CharacterListAdapter(characterList, this@CharacterListFragment, null)
        }
    }

    private fun setErrorStateViews() {
        // Hide views, show others, show error dialog, log errors, do stuff
    }

    private fun setLoadingStateViews() {
        // Load stuff
    }

    private fun setViews() {
        with(binding) {

            sportsRecycler.layoutManager = LinearLayoutManager(context)
//            searchCharacterEditText.hint = activityViewModel.lastSeach
            searchCharacterEditText.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    sequence: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    activityViewModel.filterAndRemakeCharacterList(sequence.toString())
//                    activityViewModel.lastSeach = sequence.toString()
                }
            })
        }

//        if (activityViewModel.lastSeach != activityViewModel.defaultLastSearch
//            && activityViewModel.hasCharactersData()
//        ) {
//            activityViewModel.filterAndRemakeCharacterList(
//                activityViewModel.defaultLastSearch
//            )
//        }
    }

    override fun onCharacterSelected(character: Character) {
        activityViewModel.selectCharacter(
            Character(
                name = character.name,
                description = character.description,
                imageURL = character.imageURL,
                duckDuckLink = character.duckDuckLink
            )
        )
    }
}