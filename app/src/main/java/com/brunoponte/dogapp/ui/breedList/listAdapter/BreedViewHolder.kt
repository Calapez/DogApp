package com.brunoponte.dogapp.ui.breedList.listAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.brunoponte.dogapp.R
import com.brunoponte.dogapp.domainModels.Breed
import com.brunoponte.dogapp.helpers.Util
import com.bumptech.glide.Glide


class BreedViewHolder(
    private val view: View,
    private val interaction: BreedListInteraction
) : RecyclerView.ViewHolder(view) {

    fun bind(breed: Breed, position: Int) {
        val notApplicableText = itemView.context.getString(R.string.not_applicable)

        val breedImageView: ImageView = view.findViewById(R.id.breedImage)
        val breedNameTextView: TextView = view.findViewById(R.id.breedNameText)
        val breedRepoCardView: CardView = view.findViewById(R.id.repoCard)

        breed.referenceImageId?.let { referenceImageId ->
            val imageUrl = String.format(Util.dogImageApiUrlTemplate, referenceImageId)

            Glide.with(itemView.context)
                .load(imageUrl)
                .into(breedImageView)
        }

        breedNameTextView.text = breed.name ?: notApplicableText

        breedRepoCardView.setOnClickListener {
            interaction.onClick(position, breed)
        }
    }
}