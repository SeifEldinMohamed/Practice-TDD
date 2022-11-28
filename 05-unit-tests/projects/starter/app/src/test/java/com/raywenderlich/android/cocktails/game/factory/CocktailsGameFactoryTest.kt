package com.raywenderlich.android.cocktails.game.factory

import com.raywenderlich.android.cocktails.common.network.Cocktail
import com.raywenderlich.android.cocktails.common.repository.Repository
import com.raywenderlich.android.cocktails.common.repository.RepositoryCallback
import com.raywenderlich.android.cocktails.game.model.Game
import com.raywenderlich.android.cocktails.game.model.Question
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CocktailsGameFactoryTest {
    @Mock
    private lateinit var repository: Repository

    private lateinit var factory: CocktailsGameFactory

    private val cocktails = listOf(
        Cocktail("1", "Drink1", "image1"),
        Cocktail("2", "Drink2", "image2"),
        Cocktail("3", "Drink3", "image3"),
        Cocktail("4", "Drink4", "image4")
    )

    @Before
    fun setup() {
        factory = CocktailsGameFactoryImpl(repository)
    }

    @Test
    fun `buildGame()_then should Get Cocktails From Repo`() {
        factory.buildGame(mock())
        verify(repository).getAlcoholic(any())
    }

    @Test
    fun `buildGame()_then should Call OnSuccess`() {
        // Arrange
        val callback = mock<CocktailsGameFactory.Callback>()
        setUpRepositoryWithCocktails(repository)
        // Act
        factory.buildGame(callback)
        // Assert
        verify(callback).onSuccess(any())
    }

    private fun setUpRepositoryWithCocktails(
        repository: Repository
    ) {
        doAnswer {
            // 1
            val callback: RepositoryCallback<List<Cocktail>, String> = it.getArgument(0)
            callback.onSuccess(cocktails)
        }.whenever(repository).getAlcoholic(any())

        // Use doAnswer() when you want to stub a void method with generic Answer.
        // Stubbing voids requires different approach from when(Object) because
        // the compiler does not like void methods inside brackets...

        // In setUpRepositoryWithCocktails, you are using doAnswer to stub the
        // repository.getAlcoholic() method to always return success with a list of
        // cocktails. The doAnswer closure returns an InvocationOnMock type, with which you
        // can spy on its arguments. You then get the first argument of the method (which is
        // the callback), and call onSuccess() on it
    }

    @Test
    fun `buildGame()_then should Call OnError`() {
        // Arrange
        val callback = mock<CocktailsGameFactory.Callback>()
        setUpRepositoryWithError(repository)
        // Act
        factory.buildGame(callback)
        // Assert
        verify(callback).onError()
    }

    private fun setUpRepositoryWithError(
        repository: Repository
    ) {
        doAnswer {
            // 1
            val callback: RepositoryCallback<List<Cocktail>, String> = it.getArgument(0)
            callback.onError("error")
        }.whenever(repository).getAlcoholic(any())

        // Use doAnswer() when you want to stub a void method with generic Answer.
        // Stubbing voids requires different approach from when(Object) because
        // the compiler does not like void methods inside brackets...

        // In setUpRepositoryWithCocktails, you are using doAnswer to stub the
        // repository.getAlcoholic() method to always return success with a list of
        // cocktails. The doAnswer closure returns an InvocationOnMock type, with which you
        // can spy on its arguments. You then get the first argument of the method (which is
        // the callback), and call onSuccess() on it
    }

    @Test
    fun `buildGame(), then should Get High Score From Repo`() {
        // Arrange
        setUpRepositoryWithCocktails(repository)
        // Act
        factory.buildGame(mock())
        // Assert
        verify(repository).getHighScore()
    }

    @Test
    fun `buildGame(),when high score is greater than current score then should Build Game With HighScore`() {
        // Arrange
        setUpRepositoryWithCocktails(repository)
        val highScore = 100
        // Act
        whenever(repository.getHighScore()).thenReturn(highScore)
        // Assert
        factory.buildGame(object : CocktailsGameFactory.Callback {
            override fun onSuccess(game: Game) = Assert.assertEquals(highScore, game.score.highest)
            override fun onError() = Assert.fail()
        })
    }

    @Test
    fun `buildGame(), when mapping list of cocktails, then should Build Game With Questions`() {
        // Arrange
        setUpRepositoryWithCocktails(repository)
        // Act
        factory.buildGame(object : CocktailsGameFactory.Callback {
            // Assert
            override fun onSuccess(game: Game) {
                cocktails.forEach {
                    assertQuestion(
                        game.nextQuestion(),
                        it.strDrink,
                        it.strDrinkThumb
                    )
                }
            }

            override fun onError() = Assert.fail()
        })
    }

    private fun assertQuestion(
        question: Question?,
        correctOption: String,
        imageUrl: String?
    ) {
        Assert.assertNotNull(question)
        Assert.assertEquals(imageUrl, question?.imageUrl)
        Assert.assertEquals(correctOption, question?.correctOption)
        Assert.assertNotEquals(
            correctOption,
            question?.incorrectOption
        )
    }

}