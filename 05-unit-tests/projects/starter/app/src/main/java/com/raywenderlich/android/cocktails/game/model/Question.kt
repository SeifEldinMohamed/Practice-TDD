package com.raywenderlich.android.cocktails.game.model

class Question(
    val correctOption: String,
    val incorrectOption: String
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
}