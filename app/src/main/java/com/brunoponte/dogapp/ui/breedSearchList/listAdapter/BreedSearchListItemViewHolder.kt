package com.brunoponte.dogapp.ui.breedSearchList.listAdapter

import androidx.recyclerview.widget.RecyclerView
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.databinding.BreedSearchListItemBinding
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.helpers.Util
import com.bumptech.glide.Glide

class BreedSearchListItemViewHolder(
    private val binding: BreedSearchListItemBinding,
    private val interaction: BreedSearchListInteraction
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(breed: Breed, position: Int) {
        val notApplicableText = itemView.context.getString(R.string.not_applicable)

        binding.apply {
            if (breed.referenceImageId == null) {
                breedImage.setImageResource(R.drawable.ic_no_image)
            } else {
                Glide.with(itemView.context)
                    .load(String.format(Util.dogImageApiUrlTemplate, breed.referenceImageId))
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_no_image)
                    .centerCrop()
                    .into(breedImage)
            }

            breedNameText.text = breed.name ?: notApplicableText

            breedGroupText.text = breed.breedGroup ?: notApplicableText

            breedOriginText.text = breed.origin ?: notApplicableText

            root.setOnClickListener {
                interaction.onClick(position, breed)
            }
        }
    }
}