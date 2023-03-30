package com.brunoponte.dogapp.presentation.breedList.listAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.brunoponte.dogapp.databinding.BreedListItemGridBinding
import com.brunoponte.dogapp.databinding.BreedListItemLinearBinding
import com.brunoponte.dogapp.presentation.BreedUiItem
import com.brunoponte.dogapp.presentation.breedList.ListMode

interface BreedListInteraction {
    fun onClick(position: Int, breedId: Int)

    fun onIndexReached(index: Int)
}

class BreedListAdapter(
    private val interaction: BreedListInteraction,
    var listMode: ListMode
) : ListAdapter<BreedUiItem, BreedListItemViewHolder>(BreedListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedListItemViewHolder {
        val view: View = if (listMode == ListMode.LINEAR) {
            BreedListItemLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
        } else {
            BreedListItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
        }

        return BreedListItemViewHolder(view, interaction)
    }

    override fun onBindViewHolder(holder: BreedListItemViewHolder, position: Int) {
        holder.bind(getItem(position), position)
        interaction.onIndexReached(position)
    }

    private companion object : DiffUtil.ItemCallback<BreedUiItem>() {

        override fun areItemsTheSame(oldItem: BreedUiItem, newItem: BreedUiItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BreedUiItem, newItem: BreedUiItem): Boolean {
            return oldItem == newItem
        }
    }
}