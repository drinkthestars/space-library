package com.goofy.goober.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageResultsViewBinding
import com.goofy.goober.model.ImageResultsState
import com.goofy.goober.ui.util.textChanges
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ImageResultsView(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding = ImageResultsViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val mainScope = MainScope()
    private lateinit var adapter: ImageListAdapter

    fun setProps(props: Props) {
        mainScope.coroutineContext.cancelChildren()
        binding.searchInput
            .textChanges(debounceMillis = 300L)
            .onEach { props.onSearch(it) }
            .launchIn(mainScope)

        adapter = ImageListAdapter(props.onImageClick)
        binding.imageResults.adapter = adapter
        binding.imageResults.layoutManager = LinearLayoutManager(context)
    }

    fun setImageResultsState(imageResultsState: ImageResultsState) {
        binding.loading.isVisible = imageResultsState.isLoading
        binding.error.isVisible = imageResultsState.hasError
        binding.emptyState.isVisible = imageResultsState.noResults
        binding.imageResults.isVisible = !imageResultsState.noResults
        adapter.submitList(imageResultsState.images)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mainScope.coroutineContext.cancelChildren()
    }

    data class Props(
        val onSearch: (String) -> Unit,
        val onImageClick: (Image) -> Unit
    )
}
