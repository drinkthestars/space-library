package com.goofy.goober.androidview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.goofy.goober.androidview.util.textChanges
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageResultsViewBinding
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.StateFlow
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
            binding.searchEditText
                .textChanges()
                .onEach { props.onSearch(it) }
                .launchIn(props.lifecycleOwner.lifecycleScope)
            binding.searchQuery = props.searchQuery.asLiveData()
            binding.clearSearchInputButton.setOnClickListener { props.onQueryClear() }
            binding.lifecycleOwner = props.lifecycleOwner
            props.onSearch(props.searchQuery.value)
        }
    }

    fun setState(state: ImageResultsState?) {
        state ?: return
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
        val searchQuery: StateFlow<String>,
        val lifecycleOwner: LifecycleOwner,
        val onQueryClear: () -> Unit,
        val onSearch: (String) -> Unit,
        val onImageClick: (Image) -> Unit
    )
}
