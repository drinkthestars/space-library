package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.databinding.DetailsFragmentBinding
import com.goofy.goober.ui.state.bindState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailsFragment : Fragment() {

    // TODO: Eventually replace with StateFlow
    interface FragmentState {
        fun details(): StateFlow<State>
    }

    private val fragmentState: FragmentState by bindState()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DetailsFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                fragmentState.details()
                    .onEach { state = it }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            }
            .root
    }

    data class State(val description: String)
}
