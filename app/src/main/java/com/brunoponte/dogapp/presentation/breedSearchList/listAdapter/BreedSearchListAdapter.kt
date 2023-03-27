package com.brunoponte.dogapp.presentation.breedSearchList.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.brunoponte.dogapp.databinding.BreedSearchListItemBinding
import com.brunoponte.dogapp.presentation.BreedItemViewState

interface BreedSearchListInteraction {
    fun onClick(position: Int, breedId: Int)
}

class BreedSearchListAdapter(
    private val interaction: BreedSearchListInteraction
) : ListAdapter<BreedItemViewState, BreedSearchListItemViewHolder>(BreedSearchListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedSearchListItemViewHolder {
        val binding = BreedSearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedSearchListItemViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: BreedSearchListItemViewHolder, position: Int) =
        holder.bind(getItem(position), position)

    private companion object : DiffUtil.ItemCallback<BreedItemViewState>() {

        override fun areItemsTheSame(oldItem: BreedItemViewState, newItem: BreedItemViewState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BreedItemViewState, newItem: BreedItemViewState): Boolean {
            return oldItem == newItem
        }
    }
}