package com.brunoponte.dogapp.presentation.breedList.listAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.data.network.utils.Endpoints
import com.brunoponte.dogapp.presentation.BreedUiItem
import com.bumptech.glide.Glide

class BreedListItemViewHolder(
    private val view: View,
    private val interaction: BreedListInteraction
) : RecyclerView.ViewHolder(view) {

    fun bind(breed: BreedUiItem, position: Int) {
        val notApplicableText = itemView.context.getString(R.string.not_applicable)

        val breedImageView: ImageView = view.findViewById(R.id.breedImage)
        val breedNameTextView: TextView = view.findViewById(R.id.breedNameText)
        val breedRepoCardView: CardView = view.findViewById(R.id.breedCard)

        if (breed.referenceImageId == null) {
            breedImageView.setImageResource(R.drawable.ic_no_image)
        } else {
            Glide.with(itemView.context)
                .load(String.format(Endpoints.dogImageApiEndpointTemplate, breed.referenceImageId))
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_no_image)
                .centerCrop()
                .into(breedImageView)
        }

        breedNameTextView.text = breed.name ?: notApplicableText

        breedRepoCardView.setOnClickListener {
            interaction.onClick(position, breed.id)
        }
    }
}