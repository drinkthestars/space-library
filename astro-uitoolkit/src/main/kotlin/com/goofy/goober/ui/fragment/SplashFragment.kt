package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goofy.goober.databinding.SplashFragmentBinding
import com.goofy.goober.ui.state.bindState

class SplashFragment : Fragment() {

    // TODO: Eventually replace with StateFlow
    interface FragmentProp {
        fun prop(): Prop
    }

    private val fragmentProp: FragmentProp by bindState()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return SplashFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply { prop = fragmentProp.prop() }
            .root
    }

    data class Prop(
        val onSplashDone: () -> Unit
    )
}
