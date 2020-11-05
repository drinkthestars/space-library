package com.goofy.goober.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import coil.load
import com.goofy.goober.databinding.ImageDetailsViewBinding
import com.goofy.goober.model.DetailsState

class ImageDetailsView(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding = ImageDetailsViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setState(detailsState: DetailsState) {
        val imageDetails = detailsState.imageDetails
        if (imageDetails != null) {
            binding.image.load(imageDetails.imageSizes.mediumSizeUrl) { crossfade(enable = true) }
        }
        binding.loading.isVisible = detailsState.isLoading
        binding.error.isVisible = detailsState.hasError
    }
}
