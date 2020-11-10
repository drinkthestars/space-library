package com.goofy.goober.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.goofy.goober.databinding.SplashFragmentBinding
import com.goofy.goober.ui.util.activityArgs

class SplashFragment : Fragment() {

    interface FragmentArgs {
        val splashProps: Props
    }

    private val args by activityArgs<FragmentArgs>()
    private var animation: ViewPropertyAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { args.splashProps.onBack() }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return SplashFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                nasalogo.apply { alpha = 0f }
                animation = nasalogo.animate().apply {
                    duration = 800
                    startDelay = 1000
                    interpolator = AccelerateDecelerateInterpolator()
                    alpha(1f)
                    setListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                args.splashProps.onSplashDone()
                            }
                        }
                    )
                }
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animation?.start()
    }

    data class Props(
        val onSplashDone: () -> Unit,
        val onBack: () -> Unit
    )
}
