package com.goofy.goober.androidview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.goofy.goober.androidview.util.textChanges
import com.goofy.goober.databinding.SearchInputViewBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchInputView(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val binding = SearchInputViewBinding.inflate(LayoutInflater.from(context), this)
    private val mainScope = MainScope()

    fun setProps(props: Props) {
        with(mainScope) {
            coroutineContext.cancelChildren()
            binding.searchEditText
                .textChanges()
                .onEach { props.onSearch(it) }
                .launchIn(mainScope)
            binding.clearSearchTextButton.setOnClickListener { props.onQueryClear() }
        }
    }

    fun setState(text: String?) {
        if (binding.searchEditText.text.toString() != text) {
            binding.searchEditText.setText(text)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mainScope.coroutineContext.cancelChildren()
    }

    data class Props(
        val onQueryClear: () -> Unit,
        val onSearch: (String) -> Unit
    )
}
