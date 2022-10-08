package com.brunoponte.dogapp.ui.breedSearchList.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.brunoponte.dogapp.databinding.BreedListItemLinearBinding
import com.brunoponte.dogapp.databinding.BreedSearchListItemBinding
import com.brunoponte.dogapp.domainModels.Breed


interface BreedSearchListInteraction {
    fun onClick(position: Int, breed: Breed)
}

class BreedSearchListAdapter(
    private val interaction: BreedSearchListInteraction
) : ListAdapter<Breed, BreedSearchListItemViewHolder>(BreedSearchListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedSearchListItemViewHolder {
        val binding = BreedSearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedSearchListItemViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: BreedSearchListItemViewHolder, position: Int) =
        holder.bind(getItem(position), position)

    private companion object : DiffUtil.ItemCallback<Breed>() {

        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem == newItem
        }
    }
}