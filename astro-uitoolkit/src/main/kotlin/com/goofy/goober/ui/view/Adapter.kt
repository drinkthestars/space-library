package com.goofy.goober.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageListItemBinding
import com.goofy.goober.ui.view.ImageListAdapter.*

class ImageListAdapter() : ListAdapter<Image, ImageViewHolder>(UserItemDiffCallback()) {

    var onImageClick: (Image) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ImageViewHolder {
        return ImageViewHolder(
            ImageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position), onImageClick)
    }

    class ImageViewHolder(
        private val binding: ImageListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image, onImageClick: (Image) -> Unit) {
            binding.image.load(image.thumbUrl) { crossfade(enable = true) }
            binding.title.text = image.title
            binding.root.setOnClickListener { onImageClick(image) }
        }
    }

    private class UserItemDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem == newItem
    }
}
