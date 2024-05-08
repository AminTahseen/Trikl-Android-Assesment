package com.example.triklandroidassessment.view.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.triklandroidassessment.R
import com.example.triklandroidassessment.databinding.FragmentStartNewGameBinding
import com.example.triklandroidassessment.events.MainMenuEvents
import com.example.triklandroidassessment.events.StartNewGameEvents
import com.example.triklandroidassessment.model.dataModel.OptionItem
import com.example.triklandroidassessment.utils.gone
import com.example.triklandroidassessment.utils.visible
import com.example.triklandroidassessment.view.adapter.QuestionAnswerAdapter
import com.example.triklandroidassessment.viewModel.StartNewGameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.log

@AndroidEntryPoint
class StartNewGameFragment : Fragment() {
    private val startNewGameViewModel: StartNewGameViewModel by viewModels()
    private lateinit var startNewGameBinding: FragmentStartNewGameBinding
    private lateinit var adapter: QuestionAnswerAdapter
    private lateinit var timer: CountDownTimer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        startNewGameBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_start_new_game, container, false)
        startNewGameBinding.viewModel = startNewGameViewModel
        // Inflate the layout for this fragment
        setAdapter()
        set10SecondsTimer()
        handleBackPress()
        observeEvents()
        return startNewGameBinding.root
    }

    private fun handleBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    //  navController.popBackStack()
                    findNavController().navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun set10SecondsTimer() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                startNewGameBinding.timer.text = "$secondsRemaining secs"
            }

            override fun onFinish() {
                startNewGameBinding.timer.text = "0 sec"
                // Add any actions you want to perform after the timer finishes here
            }
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            startNewGameViewModel.events.observe(viewLifecycleOwner) { events ->
                when (events) {
                    is StartNewGameEvents.ShowLoader -> {
                        showHideProgress(events.show)
                    }

                    is StartNewGameEvents.SetQuestion -> {
                        val text = "Question ${events.number} of ${events.total}"
                        val question = events.question
                        startNewGameBinding.ongoingQuestionNum.text = text
                        startNewGameBinding.ongoingQuestion.text = question

                    }

                    is StartNewGameEvents.SetOptions -> {
                        val list = events.list.map {
                            OptionItem(it)
                        }
                        adapter.addItems(list.shuffled())
                    }

                    StartNewGameEvents.Finish -> {
                        Log.d("gameFinish", "finishedddd")
                    }

                    is StartNewGameEvents.StartStop10SecondsTimer -> {
                        if (events.stopTimer) {
                            timer.cancel()
                        } else {
                            set10SecondsTimer()
                            timer.start()
                        }
                    }

                    is StartNewGameEvents.CheckRightWrong -> {
                        adapter.checkAnswerIfRightWrong(events.selected, events.valid)
                    }

                    is StartNewGameEvents.ShowToast -> {
                        Toast.makeText(activity, events.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> {

                    }
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = QuestionAnswerAdapter {
            Log.d("selectedmasas", it)
            startNewGameViewModel.verifyAnswer(it)
        }
        startNewGameBinding.optionsRecyclerView.adapter = adapter
    }

    private fun showHideProgress(show: Boolean) {
        if (show) {
            startNewGameBinding.progress1.visible()
            startNewGameBinding.progress2.visible()
            startNewGameBinding.ongoingQuestion.gone()
            startNewGameBinding.marks.gone()
            startNewGameBinding.optionsRecyclerView.gone()
        } else {
            startNewGameBinding.progress1.gone()
            startNewGameBinding.progress2.gone()
            startNewGameBinding.ongoingQuestion.visible()
            startNewGameBinding.marks.visible()
            startNewGameBinding.optionsRecyclerView.visible()
        }
    }
}