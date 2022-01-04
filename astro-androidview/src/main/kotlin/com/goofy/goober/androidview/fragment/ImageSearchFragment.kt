package com.goofy.goober.androidview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.goofy.goober.R
import com.goofy.goober.androidview.view.ImageResultsView
import com.goofy.goober.api.model.Image
import com.goofy.goober.common.flow.ImageSearchViewModel
import com.goofy.goober.common.model.ImageResultsAction
import com.goofy.goober.databinding.ImageResultsFragmentBinding
import kotlinx.coroutines.flow.Flow

internal class ImageSearchFragment : Fragment() {

    interface FragmentArgs {
        val imageSearchProps: Props
    }

    private val viewModel by backStackEntryViewModel<ImageSearchViewModel>(R.id.imageSearchFragment)
    private val args by activityArgs<FragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                args.imageSearchProps.onBack()
            }
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
                viewProps = viewProps(viewModel.state.query)
                collectWhenStarted(viewModel.state.imageResultsState) { viewState = it }
                lifecycleOwner = viewLifecycleOwner
            }
            .root
    }

    private fun viewProps(query: Flow<String>) = with(viewModel) {
        ImageResultsView.Props(
            searchQuery = query,
            onQueryClear = { viewModel.state.update(query = "") },
            onSearch = {
                viewModel.state.update(query = it)
                dispatch(ImageResultsAction.Search(it))
            },
            onImageClick = args.imageSearchProps.onImageClick,
            lifecycleOwner = viewLifecycleOwner
        )
    }

    data class Props(
        val onImageClick: (Image) -> Unit,
        val onBack: () -> Unit
    )
}
