package com.raywenderlich.android.cocktails.game.repository

import android.content.SharedPreferences
import com.raywenderlich.android.cocktails.common.network.CocktailsApi
import com.raywenderlich.android.cocktails.common.repository.Repository
import com.raywenderlich.android.cocktails.common.repository.RepositoryImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class) // to instruct that you are going to write tests using Mockito.
class RepositoryTest {
    private lateinit var repository: Repository
    @Mock
    private lateinit var api: CocktailsApi
    @Mock
    private lateinit var sharedPreferences: SharedPreferences
    @Mock
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        // Arrange
        whenever(sharedPreferences.edit())
            .thenReturn(sharedPreferencesEditor)
        repository = RepositoryImpl(api, sharedPreferences)
    }
    @Test
    fun `saveScore(), then should save to SharedPreferences`() {
        // Arrange
        val score = 100

        // Act
        repository.saveHighScore(score)

        // Assert

        // Creates InOrder object that allows verifying mocks in order.
        // Accepts a lambda to allow easy evaluation.
        // Alias for Mockito.inOrder.
        inOrder(sharedPreferencesEditor) {
            verify(sharedPreferencesEditor).putInt(any(), eq(score)) // eq(): Object argument that is equal to the given value.
            verify(sharedPreferencesEditor).apply()
        }
    } // Use inOrder to check that the subsequent verifications are executed in the exact order

    @Test
    fun `getScore(), then should get score From SharedPreferences`() {
        // Act
        repository.getHighScore()
        // Assert
        verify(sharedPreferences).getInt(any(), any())
    }

    @Test
    fun `saveScore(), when score is lower than high score, then should not save it` () {
        // Arrange
        val newHighScore = 10
        val previousSavedHighScore = 100
        val spyRepository: Repository = spy(repository)
        doReturn(previousSavedHighScore).whenever(spyRepository).getHighScore()
        // Act
        spyRepository.saveHighScore(newHighScore)
        // Assert
        verify(sharedPreferencesEditor, never()).putInt(any(), eq(newHighScore))

        // In this test you are stubbing the getHighScore() method but you also need to call
        //the real saveHighScore() method on the same object, which is a real object,
        //CocktailsRepositoryImpl. To do that you need a spy instead of a mock. Using a
        //spy will let you call the methods of a real object, while also tracking every
        //interaction, just as you would do with a mock. When setting up spies, you need to use
        //doReturn/whenever/method to stub a method.
    }
}

/**
 * In this test you are stubbing the getHighScore() method but you also need to call
the real saveHighScore() method on the same object, which is a real object,
CocktailsRepositoryImpl. To do that you need a spy instead of a mock. Using a
spy will let you call the methods of a real object, while also tracking every
interaction, just as you would do with a mock. When setting up spies, you need to use
doReturn/whenever/method to stub a method.
 * */