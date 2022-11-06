package com.diu.swan.test.model

data class QuestionsModel(
    val questions: List<Question>
) {
    data class Question(
        val answers: List<String>,
        val correctIndex: Int,
        val question: String
    )
}