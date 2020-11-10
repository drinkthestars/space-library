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
    private val adapter = ImageListAdapter()

    init {
        binding.imageResults.adapter = adapter
        binding.imageResults.layoutManager = LinearLayoutManager(context)
    }

    fun setProps(props: Props) {
        adapter.onImageClick = props.onImageClick
        with(mainScope) {
            coroutineContext.cancelChildren()
            binding.searchInput
                .textChanges()
                .onEach { props.onSearch(it) }
                .launchIn(scope = this)
            binding.searchInput.setText(props.query)
            props.onSearch(props.query)
        }
    }

    fun setState(state: ImageResultsState) {
        binding.loading.isVisible = state.isLoading
        binding.error.isVisible = state.hasError
        binding.emptyState.isVisible = state.noResults
        binding.imageResults.isVisible = !state.noResults
        adapter.submitList(state.images)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mainScope.coroutineContext.cancelChildren()
    }

    data class Props(
        val query: String,
        val onSearch: (String) -> Unit,
        val onImageClick: (Image) -> Unit
    )
}
