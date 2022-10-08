package com.brunoponte.dogapp.ui.breedList.listAdapter

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.brunoponte.dogapp.databinding.BreedListItemGridBinding
import com.brunoponte.dogapp.databinding.BreedListItemLinearBinding
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.ui.breedList.ListMode


interface BreedListInteraction {
    fun onClick(position: Int, breed: Breed)

    fun onIndexReached(index: Int)
}

class BreedListAdapter(
    private val interaction: BreedListInteraction,
    var listMode: ListMode
) : ListAdapter<Breed, BreedViewHolder>(BreedListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view: View = if (listMode == ListMode.LINEAR) {
            BreedListItemLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
        } else {
            BreedListItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
        }

        return BreedViewHolder(view, interaction)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position), position)
        interaction.onIndexReached(position)
    }

    private companion object : DiffUtil.ItemCallback<Breed>() {

        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem == newItem
        }
    }
}