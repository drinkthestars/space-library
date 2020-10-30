package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.databinding.DetailsFragmentBinding
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.ui.navigation.AstroNavArgsViewModel
import com.goofy.goober.ui.util.activityArgs
import com.goofy.goober.viewmodel.DetailsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    interface FragmentArgs {
        val detailsProps: Props
    }

    private val viewModel by viewModel<DetailsViewModel>()
    private val fragmentArgs by activityArgs<FragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DetailsFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                viewModel.state
                    .onEach {
                        // TODO: Handle state & set props
                    }
                    .launchIn(viewLifecycleOwner.lifecycleScope)

                viewModel.consumeIntent(fragmentArgs.detailsProps.initialIntent)
            }
            .root
    }

    data class Props(
        val onBackPressed: () -> Unit,
        val initialIntent: DetailsIntent
    )
}
