package com.example.duckduckgoapp.local.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.duckduckgoapp.databinding.ItemCharacterListBinding
import com.example.duckduckgoapp.local.models.Character

class CharacterListAdapter(
    private val characterList: List<Character>,
    private val characterListListener: CharacterListListener,
    private val textFilering: String?
) : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    class ViewHolder(private val itemBinding: ItemCharacterListBinding, private val characterListListener: CharacterListListener) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(character: Character) {
            with(itemBinding) {
                characterTextView.text = character.name
                characterListItemLayout.setOnClickListener {
                    characterListListener.onCharacterSelected(character)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemCharacterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, characterListListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(characterList[position])
    }

    override fun getItemCount() = characterList.size
}

interface CharacterListListener {
    fun onCharacterSelected(character: Character)
}
