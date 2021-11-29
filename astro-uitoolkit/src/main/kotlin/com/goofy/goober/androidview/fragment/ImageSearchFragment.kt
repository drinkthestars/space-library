package com.goofy.goober.androidview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.snapshotFlow
import androidx.fragment.app.Fragment
import com.goofy.goober.R
import com.goofy.goober.androidview.navigation.activityArgs
import com.goofy.goober.androidview.navigation.backStackEntryViewModel
import com.goofy.goober.androidview.navigation.collectWhenStarted
import com.goofy.goober.androidview.view.ImageResultsView
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageResultsFragmentBinding
import com.goofy.goober.model.ImageResultsAction
import com.goofy.goober.viewmodel.ImageSearchViewModel
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
                val query = snapshotFlow { viewModel.state.query }
                val imageResultsState = snapshotFlow { viewModel.state.imageResultsState }
                viewProps = viewProps(query)
                collectWhenStarted(imageResultsState) { viewState = it }
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
