package com.example.triklandroidassessment.view.fragments.mainMenu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.example.triklandroidassessment.R
import com.example.triklandroidassessment.databinding.FragmentMainMenuBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.triklandroidassessment.utils.gone
import com.example.triklandroidassessment.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class MainMenuFragment : Fragment() {
    private val mainMenuViewModel: MainMenuViewModel by viewModels()
    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)
        binding.viewModel = mainMenuViewModel
        // Inflate the layout for this fragment
        handleBackPress()
        observeEvents()
        mainMenuViewModel.getCurrentHighScore()
        return binding.root
    }


    private fun handleBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            mainMenuViewModel.events.collect { events ->
                when (events) {
                    is MainMenuEvents.StartNewGame -> {
                        try {
                            findNavController().navigate(R.id.action_mainMenuFragment_to_startNewGameFragment)
                        } catch (_: Exception) {
                        }
                    }

                    is MainMenuEvents.SetCurrentHighScore -> {
                        val highScore = events.score
                        if(highScore==0){
                            binding.currentHighScore.gone()
                            binding.HighScoreText.gone()
                        }
                        else{
                            binding.currentHighScore.visible()
                            binding.HighScoreText.visible()
                            val text = "$highScore POINTS"
                            binding.currentHighScore.text = text
                        }
                    }
                }
            }
        }
    }
}

