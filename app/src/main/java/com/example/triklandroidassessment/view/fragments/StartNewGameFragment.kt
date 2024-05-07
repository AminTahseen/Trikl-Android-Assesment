package com.example.triklandroidassessment.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.triklandroidassessment.R
import com.example.triklandroidassessment.databinding.FragmentStartNewGameBinding
import com.example.triklandroidassessment.viewModel.StartNewGameViewModel

class StartNewGameFragment : Fragment() {
    private val startNewGameViewModel:StartNewGameViewModel by viewModels()
    private lateinit var startNewGameBinding: FragmentStartNewGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        startNewGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_new_game, container, false)
        startNewGameBinding.viewModel = startNewGameViewModel
        // Inflate the layout for this fragment
        observeEvents()
        return startNewGameBinding.root
    }
    private fun observeEvents() {

    }
}