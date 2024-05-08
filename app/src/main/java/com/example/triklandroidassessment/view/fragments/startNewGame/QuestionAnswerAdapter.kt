package com.example.triklandroidassessment.view.fragments.startNewGame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.triklandroidassessment.databinding.OptionsItemBinding
import com.example.triklandroidassessment.model.dataModel.OptionItem

class QuestionAnswerAdapter(
    val onItemSelect: (selectedAnswer: String) -> Unit
) : RecyclerView.Adapter<QuestionAnswerAdapter.QuestionAnswerViewHolder>() {
    private val optionsList = ArrayList<OptionItem>()
    private lateinit var binding: OptionsItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): QuestionAnswerViewHolder {
        binding = OptionsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionAnswerViewHolder(binding)
    }

    fun addItems(list: List<OptionItem>) {
        optionsList.apply {
            this.clear()
            this.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun checkAnswerIfRightWrong(selectedAnswer: String, validAnswer: String) {
        val selectedItem = optionsList.find { it.option == selectedAnswer }
        val validItem = optionsList.find { it.option == validAnswer }
        when {
            selectedAnswer != validAnswer && selectedAnswer.isNotEmpty() -> {
                selectedItem?.isWrongAnswer = true
                validItem?.isRightAnswer = true
            }
            selectedAnswer.isEmpty() && validAnswer.isNotEmpty() -> {
                validItem?.isNoAnswerSelected = true
            }
            else -> {
                validItem?.isRightAnswer = true
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: QuestionAnswerViewHolder, position: Int
    ) {
        val item = optionsList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemSelect(optionsList[position].option)
        }
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    class QuestionAnswerViewHolder(
        private val binding: OptionsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(optionItem: OptionItem) {
            binding.model = optionItem
        }
    }


}