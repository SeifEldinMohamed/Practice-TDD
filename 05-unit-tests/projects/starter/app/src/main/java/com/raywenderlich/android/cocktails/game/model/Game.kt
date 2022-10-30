package com.raywenderlich.android.cocktails.game.model

class Game(private val questions: List<Question>, private val score: Score = Score(0)) {

    fun nextQuestion(currentPosition: Int): Question? {
        return if (questions.size - 1 == currentPosition)
            null
        else
            questions[currentPosition + 1]
    }

    fun answer(question: Question, option: String) {
        if (question.addAnswer(option)) {
            score.increment()
        }
    }

}