package com.raywenderlich.android.cocktails.game.model

class Game(questions:List<Question> ,highest: Int = 0) {
    var currentScore = 0
        private set

    var highestScore = highest
        private set

    var questions: List<Question> = questions
        private set

    fun incrementScore() {
        currentScore++
        if (currentScore > highestScore)
            highestScore = currentScore
    }

    fun nextQuestion(currentPosition: Int): Question? {
        return if (questions.size - 1 == currentPosition)
            null
        else
            questions[currentPosition + 1]
    }

    fun answer(question: Question, option: String) {
        if(question.addAnswer(option)){
            currentScore++
        }
    }



}