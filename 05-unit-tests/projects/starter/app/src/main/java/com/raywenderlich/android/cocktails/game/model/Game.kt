package com.raywenderlich.android.cocktails.game.model

class Game(private val questions: List<Question>, val score: Score = Score(0)) {
    private var questionIndex = -1
    fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size) {
            questionIndex++
            return questions[questionIndex]
        }
        return null
    }

    fun answer(question: Question, option: String) {
        if (question.addAnswer(option)) {
            score.increment()
        }
        else { // wrong answer
            score.incrementWrongAnswer()
        }
    }

}