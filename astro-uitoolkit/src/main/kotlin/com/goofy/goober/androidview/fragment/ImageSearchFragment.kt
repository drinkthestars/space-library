package com.goofy.goober.androidview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.Snapshot
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.R
import com.goofy.goober.androidview.util.activityArgs
import com.goofy.goober.androidview.util.backStackEntryViewModel
import com.goofy.goober.androidview.view.ImageResultsView
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageResultsFragmentBinding
import com.goofy.goober.model.ImageResultsAction
import com.goofy.goober.viewmodel.ImageSearchViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ImageSearchFragment : Fragment() {

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
                println("WARP, query = $query")
                println("WARP,   imageResultsState = $imageResultsState")
                viewProps = with(viewModel) {
                    ImageResultsView.Props(
                        searchQuery = query,
                        onQueryClear = {
                            Snapshot.withMutableSnapshot {
                                viewModel.state.query = ""
                            }
                        },
                        onSearch = {
                            Snapshot.withMutableSnapshot {
                                viewModel.state.query = it
                            }
                            dispatch(ImageResultsAction.Search(it))
                        },
                        onImageClick = args.imageSearchProps.onImageClick,
                        lifecycleOwner = viewLifecycleOwner
                    )
                }
                query.collectWhenStarted {
                    println("WARP, query collect = $it")
                }
                imageResultsState.collectWhenStarted {
                    println("WARP, imageResultsState collect = $it")
                    viewState = it
                }
                lifecycleOwner = viewLifecycleOwner
            }
            .root
    }

    private inline fun <T> Flow<T>.collectWhenStarted(
        crossinline action: suspend (value: T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            collect(action)
        }
    }

    data class Props(
        val onImageClick: (Image) -> Unit,
        val onBack: () -> Unit
    )
}
