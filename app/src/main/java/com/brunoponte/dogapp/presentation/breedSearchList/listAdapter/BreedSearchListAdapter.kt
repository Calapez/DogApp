package com.brunoponte.dogapp.presentation.breedSearchList.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.brunoponte.dogapp.databinding.BreedSearchListItemBinding
import com.brunoponte.dogapp.presentation.BreedUiItem

interface BreedSearchListInteraction {
    fun onClick(position: Int, breedId: Int)
}

class BreedSearchListAdapter(
    private val interaction: BreedSearchListInteraction
) : ListAdapter<BreedUiItem, BreedSearchListItemViewHolder>(BreedSearchListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedSearchListItemViewHolder {
        val binding = BreedSearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedSearchListItemViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: BreedSearchListItemViewHolder, position: Int) =
        holder.bind(getItem(position), position)

    private companion object : DiffUtil.ItemCallback<BreedUiItem>() {

        override fun areItemsTheSame(oldItem: BreedUiItem, newItem: BreedUiItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BreedUiItem, newItem: BreedUiItem): Boolean {
            return oldItem == newItem
        }
    }
}