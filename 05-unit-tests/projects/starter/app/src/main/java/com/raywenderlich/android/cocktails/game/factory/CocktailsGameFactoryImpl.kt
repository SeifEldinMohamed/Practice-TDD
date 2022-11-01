package com.raywenderlich.android.cocktails.game.factory

import com.raywenderlich.android.cocktails.common.network.Cocktail
import com.raywenderlich.android.cocktails.common.repository.Repository
import com.raywenderlich.android.cocktails.common.repository.RepositoryCallback
import com.raywenderlich.android.cocktails.game.model.Game
import com.raywenderlich.android.cocktails.game.model.Question
import com.raywenderlich.android.cocktails.game.model.Score

class CocktailsGameFactoryImpl(
    private val repository: Repository
) : CocktailsGameFactory {
    override fun buildGame(
        callback: CocktailsGameFactory.Callback
    ) {
        repository.getAlcoholic(
            object : RepositoryCallback<List<Cocktail>, String> {
                override fun onSuccess(cocktailList: List<Cocktail>) {
                    val questions = buildQuestions(cocktailList)
                    val score = Score(repository.getHighScore())
                    val game = Game(questions, score)
                    callback.onSuccess(game)
                }
                override fun onError(e: String) {
                    callback.onError()
                }
            })
    }
    private fun buildQuestions(cocktailList: List<Cocktail>) =
        cocktailList.mapIndexed { index, cocktail ->
        val otherCocktail = cocktailList.shuffled().first { it != cocktail }
            // first{}: Returns the first element matching the given predicate.
        Question(
            correctOption = cocktail.strDrink,
            incorrectOption = otherCocktail.strDrink,
            imageUrl = cocktail.strDrinkThumb
        )
    }

}