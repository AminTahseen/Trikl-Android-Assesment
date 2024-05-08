package com.example.triklandroidassessment.view.fragments.highScore

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.triklandroidassessment.R
import com.example.triklandroidassessment.databinding.FragmentHighScoreBinding
import kotlinx.coroutines.launch


class HighScoreFragment : Fragment() {
    private val highScoreViewModel: HighScoreViewModel by viewModels()

    private lateinit var highScoreBinding: FragmentHighScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        highScoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_high_score, container, false)
        highScoreBinding.viewModel = highScoreViewModel

        getBundle()
        handleBackPress()
        observeEvents()
        return highScoreBinding.root
    }

    private fun handleBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    try {
                        findNavController().navigate(R.id.action_highScoreFragment_to_mainMenuFragment)
                    } catch (e: Exception) {
                        Log.d("error", e.toString())
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            highScoreViewModel.events.observe(viewLifecycleOwner) { events ->
                when (events) {
                    HighScoreEvents.NavigateToMainMenu -> {
                        try {
                            findNavController().navigate(R.id.action_highScoreFragment_to_mainMenuFragment)
                        } catch (e: Exception) {
                            Log.d("error", e.toString())
                        }
                    }
                }
            }
        }
    }

    private fun getBundle() {
        val totalScore = arguments?.getInt("totalScore", 0)
        highScoreBinding.totalPoints.text = "$totalScore points"
    }
}