package com.raywenderlich.android.cocktails.game.model

class Question(
    val question: String?=null,
    val correctOption: String,
    val incorrectOption: String,
    val imageUrl: String?=null
) {
    var answerOption: String? = null
        private set

    val isAnsweredCorrectly: Boolean
        get() = correctOption == answerOption

    fun addAnswer(answer: String): Boolean {
        if (answer != correctOption && answer != incorrectOption)
            throw IllegalArgumentException("Not a valid option")

        answerOption = answer
        return isAnsweredCorrectly
    }

    fun getOptions(): List<String> {
        return listOf(
            correctOption,
            incorrectOption
        )
    }
}