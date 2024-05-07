package com.example.triklandroidassessment.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.triklandroidassessment.R
import com.example.triklandroidassessment.databinding.FragmentMainMenuBinding
import com.example.triklandroidassessment.viewModel.MainMenuViewModel
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.triklandroidassessment.events.MainMenuEvents

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
        observeEvents()
        return binding.root
    }

    private fun observeEvents() {
        mainMenuViewModel.events.observe(viewLifecycleOwner) { events ->
            when (events) {
                is MainMenuEvents.StartNewGame -> {
                    findNavController().navigate(R.id.action_mainMenuFragment_to_startNewGameFragment)

                }

                else -> {

                }
            }
        }
    }

}