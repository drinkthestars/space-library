package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageResultsFragmentBinding
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.ui.util.activityArgs
import com.goofy.goober.ui.view.ImageResultsView
import com.goofy.goober.viewmodel.ImageSearchViewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class ImageSearchFragment : Fragment() {

    interface FragmentArgs {
        val imageSearchProps: Props
    }

    private val viewModel by viewModel<ImageSearchViewModel>()
    private val args by activityArgs<FragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { args.imageSearchProps.onBack() }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ImageResultsFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                viewProps = ImageResultsView.Props(
                    query = args.imageSearchProps.query,
                    onSearch = { viewModel.consumeIntent(ImageResultsIntent.Search(it)) },
                    onImageClick = args.imageSearchProps.onImageClick
                )
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    viewModel.state.collect { viewState = it }
                }
            }
            .root
    }

    data class Props(
        val query: String,
        val onImageClick: (Image) -> Unit,
        val onBack: () -> Unit
    )
}
