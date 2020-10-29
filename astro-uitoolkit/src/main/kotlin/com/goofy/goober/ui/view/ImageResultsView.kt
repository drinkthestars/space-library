package com.goofy.goober.ui.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.goofy.goober.databinding.ImageResultsViewBinding
import com.goofy.goober.model.ImageResultsState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ImageResultsView(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding = ImageResultsViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val mainScope = MainScope()

    fun setProps(props: Props) {
        mainScope.coroutineContext.cancelChildren()
        binding.searchInput
            .queries()
            .onEach { props.onSearch(it) }
            .launchIn(mainScope)

        //TODO: Setup adapter
    }

    fun setImageResultsState(imageResultsState: ImageResultsState) {
        binding.loading.isVisible = imageResultsState.isLoading
        binding.error.isVisible = imageResultsState.hasError
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mainScope.coroutineContext.cancelChildren()
    }

    private fun TextView.queries(): Flow<String> {
        return callbackFlow<CharSequence?> {
            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    runCatching { sendBlocking(s) }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) = Unit
            }
            addTextChangedListener(textWatcher)
            awaitClose { removeTextChangedListener(textWatcher) }
        }
            .filterNotNull()
            .map { it.toString() }
            .filter { it.isNotBlank() }
            .buffer(Channel.CONFLATED)
            .debounce(300L)
    }

    data class Props(
        val onSearch: (String) -> Unit,
        val onImageClick: (String) -> Unit
    )

}
