package com.raywenderlich.android.cocktails.game.factory

import com.raywenderlich.android.cocktails.common.repository.Repository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CocktailsGameFactoryTest {
    private lateinit var repository: Repository
    private lateinit var factory: CocktailsGameFactory
    @Before
    fun setup() {
        repository = mock()
        factory = CocktailsGameFactoryImpl(repository)
    }
    @Test
    fun buildGame_shouldGetCocktailsFromRepo() {
        factory.buildGame(mock())
        verify(repository).getAlcoholic(any())
    }
}