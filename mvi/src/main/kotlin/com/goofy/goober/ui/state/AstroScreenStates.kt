package com.goofy.goober.ui.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goofy.goober.ui.fragment.DetailsFragment
import com.goofy.goober.ui.fragment.ImageSearchFragment
import com.goofy.goober.ui.fragment.SplashFragment
import com.goofy.goober.ui.view.ImageResultsView

class AstroScreenStates : PizzaChildFragmentStates {

    private val welcomeState = MutableLiveData<SplashFragment.Prop>()
    private val questionState = MutableLiveData<ImageResultsView.State>()
    private val endState = MutableLiveData<DetailsFragment.State>()

    fun updateWelcomeState(newProp: SplashFragment.Prop) {
        welcomeState.value = newProp
    }

    fun updateQuestionState(newState: ImageResultsView.State) {
        questionState.value = newState
    }

    fun updateEndState(newState: DetailsFragment.State) {
        endState.value = newState
    }

    override fun state(): LiveData<SplashFragment.Prop> = welcomeState
    override fun imageResults(): LiveData<ImageResultsView.State> = questionState
    override fun endState(): LiveData<DetailsFragment.State> = endState
}

interface PizzaChildFragmentStates :
    SplashFragment.FragmentProp,
    DetailsFragment.FragmentState,
    ImageSearchFragment.FragmentState
