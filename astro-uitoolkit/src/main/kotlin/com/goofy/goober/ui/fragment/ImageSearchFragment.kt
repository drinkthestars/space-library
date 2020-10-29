package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.databinding.ViewImageResultsBinding
import com.goofy.goober.viewmodel.ImageSearchViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

class ImageSearchFragment : Fragment() {

    interface FragmentArgs {
        val imageSearchProps: Props
    }

    private val viewModel by viewModel<ImageSearchViewModel>()
    private val fragmentArgs by initArgs<FragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ViewImageResultsBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                viewModel.state
                    .onEach {
                        // TODO: Handle state set props
                    }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            }
            .root
    }

    data class Props(val onImageClick: (String) -> Unit)
}
