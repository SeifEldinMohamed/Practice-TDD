package com.raywenderlich.android.cocktails.game.factory

import com.raywenderlich.android.cocktails.common.network.Cocktail
import com.raywenderlich.android.cocktails.common.repository.Repository
import com.raywenderlich.android.cocktails.common.repository.RepositoryCallback

class CocktailsGameFactoryImpl(
    private val repository: Repository
) : CocktailsGameFactory {
    override fun buildGame(
        callback:
        CocktailsGameFactory.Callback
    ) {
        repository.getAlcoholic(
            object : RepositoryCallback<List<Cocktail>, String> {
                override fun onSuccess(cocktailList: List<Cocktail>) {

                }

                override fun onError(e: String) {

                }
            })
    }

}