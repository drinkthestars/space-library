package com.goofy.goober.androidview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.R
import com.goofy.goober.api.model.Image
import com.goofy.goober.databinding.ImageDetailsFragmentBinding
import com.goofy.goober.model.DetailsIntent
import com.goofy.goober.androidview.util.activityArgs
import com.goofy.goober.viewmodel.DetailsViewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class NavGraphStartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return View(context).apply { setBackgroundColor(0x212735) }
    }
}
