package com.goofy.goober.androidview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageResultsFragmentBinding
import com.goofy.goober.model.ImageResultsIntent
import com.goofy.goober.androidview.util.activityArgs
import com.goofy.goober.androidview.view.ImageResultsView
import com.goofy.goober.androidview.viewmodel.ImageSearchViewModel
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
                viewProps = with(viewModel) {
                    ImageResultsView.Props(
                        searchQuery = state.query,
                        onQueryClear = { state.query.value = "" },
                        onSearch = { consumeIntent(ImageResultsIntent.Search(it)) },
                        onImageClick = args.imageSearchProps.onImageClick,
                        lifecycleOwner = viewLifecycleOwner
                    )
                }
                viewState = viewModel.state.imageResultsState.asLiveData()
                lifecycleOwner = viewLifecycleOwner
            }
            .root
    }

    data class Props(
        val query: String,
        val onImageClick: (Image) -> Unit,
        val onBack: () -> Unit
    )
}
