package com.example.triklandroidassessment.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.triklandroidassessment.R
import com.example.triklandroidassessment.databinding.OptionsItemBinding
import com.example.triklandroidassessment.model.dataModel.OptionItem

class QuestionAnswerAdapter(
    val onItemSelect: (selectedAnswer: String) -> Unit
) : RecyclerView.Adapter<QuestionAnswerAdapter.QuestionAnswerViewHolder>() {
    private val optionsList = ArrayList<OptionItem>()
    private lateinit var binding: OptionsItemBinding

    // private lateinit var mainHolder: QuestionAnswerViewHolder

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): QuestionAnswerViewHolder {
        binding = OptionsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionAnswerViewHolder(binding)
    }

    fun addItems(list: List<OptionItem>) {
        optionsList.clear()
        optionsList.addAll(list)
        notifyDataSetChanged()
    }

    fun checkAnswerIfRightWrong(selectedAnswer: String, validAnswer: String) {
        if (selectedAnswer != validAnswer) {
            val selectedItem = optionsList.find {
                it.option == selectedAnswer
            }
            selectedItem?.isWrongAnswer = true
            val validItem = optionsList.find {
                it.option == validAnswer
            }
            validItem?.isRightAnswer = true

        } else {
            val validItem = optionsList.find {
                it.option == validAnswer
            }
            validItem?.isRightAnswer = true
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: QuestionAnswerViewHolder, position: Int
    ) {
        val item =optionsList[position]
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