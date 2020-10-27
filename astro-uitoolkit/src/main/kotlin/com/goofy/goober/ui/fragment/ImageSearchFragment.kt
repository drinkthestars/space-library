package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.databinding.ImageResultsFragmentBinding
import com.goofy.goober.ui.state.bindState
import com.goofy.goober.ui.view.ImageResultsView
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ImageSearchFragment : Fragment() {

    // TODO: Eventually replace with StateFlow
    interface FragmentState {
        fun imageResults(): StateFlow<ImageResultsView.State>
    }

    private val fragmentState: FragmentState by bindState()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ImageResultsFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                fragmentState.imageResults()
                    .onEach { imageResultsState = it }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            }
            .root
    }
}
