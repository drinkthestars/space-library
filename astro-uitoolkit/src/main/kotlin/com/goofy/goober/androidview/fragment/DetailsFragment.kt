package com.goofy.goober.androidview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.snapshotFlow
import androidx.fragment.app.Fragment
import com.goofy.goober.androidview.navigation.activityArgs
import com.goofy.goober.androidview.navigation.collectWhenStarted
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageDetailsFragmentBinding
import com.goofy.goober.model.DetailsAction
import com.goofy.goober.viewmodel.DetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class DetailsFragment : Fragment() {

    interface FragmentArgs {
        val detailsProps: Props
    }

    private val viewModel by viewModel<DetailsViewModel>()
    private val args by activityArgs<FragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                args.detailsProps.onBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ImageDetailsFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                collectWhenStarted(snapshotFlow { viewModel.state }) { viewState = it }
                viewModel.dispatch(DetailsAction.LoadContent(args.detailsProps.image))
            }
            .root
    }

    data class Props(
        val onBack: () -> Unit,
        val image: Image
    )
}
