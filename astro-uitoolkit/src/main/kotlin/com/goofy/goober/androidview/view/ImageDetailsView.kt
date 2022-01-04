package com.goofy.goober.androidview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import coil.load
import com.goofy.goober.common.model.DetailsState
import com.goofy.goober.databinding.ImageDetailsViewBinding

internal class ImageDetailsView(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding = ImageDetailsViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setState(detailsState: DetailsState) {
        val imageDetails = detailsState.imageDetails
        if (imageDetails != null) {
            binding.image.load(imageDetails.imageSizes.mediumSizeUrl) {
                listener(
                    onStart = { binding.loading.isVisible = true },
                    onError = { _, _ -> binding.error.isVisible = true }
                )
                crossfade(enable = true)
            }
        }
        binding.loading.isVisible = detailsState.isLoading
        binding.error.isVisible = detailsState.hasError
    }
}
