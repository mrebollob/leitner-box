package com.mrebollob.leitnerbox.domain.model

import com.mrebollob.leitnerbox.domain.extension.empty

data class Question(
    val id: String,
    val question: QuestionItem,
    val response: QuestionItem,
    val level: Int,
    val failures: Int
) {
    companion object {
        fun empty() = Question(
            String.empty(),
            QuestionItem.empty(),
            QuestionItem.empty(),
            Int.empty(),
            Int.empty()
        )
    }
}

data class QuestionItem(
    val title: String,
    val detail: String,
    val imageUrl: String
) {
    companion object {
        fun empty() = QuestionItem(
            String.empty(),
            String.empty(),
            String.empty()
        )
    }
}