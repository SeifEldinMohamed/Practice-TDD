package com.raywenderlich.android.cocktails.game.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.raywenderlich.android.cocktails.common.repository.Repository
import com.raywenderlich.android.cocktails.game.factory.CocktailsGameFactory
import com.raywenderlich.android.cocktails.game.model.Game
import com.raywenderlich.android.cocktails.game.model.Question
import com.raywenderlich.android.cocktails.game.model.Score
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class CocktailsGameViewModelTest {
    @get:Rule
    val taskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: Repository
    private lateinit var factory: CocktailsGameFactory
    private lateinit var viewModel: CocktailsGameViewModel
    private lateinit var game: Game

    private lateinit var loadingObserver: Observer<Boolean>
    private lateinit var errorObserver: Observer<Boolean>
    private lateinit var scoreObserver: Observer<Score>
    private lateinit var questionObserver: Observer<Question>

    @Before
    fun setup() {
        // 1
        repository = mock()
        factory = mock()
        viewModel = CocktailsGameViewModel(repository, factory)
        // 2
        game = mock()
        // 3
        loadingObserver = mock()
        errorObserver = mock()
        scoreObserver = mock()
        questionObserver = mock()

        viewModel.getLoading().observeForever(loadingObserver)
        viewModel.getScore().observeForever(scoreObserver)
        viewModel.getQuestion().observeForever(questionObserver)
        viewModel.getError().observeForever(errorObserver)
    }

    private fun setUpFactoryWithSuccessGame(game: Game) {
        doAnswer {
            val callback: CocktailsGameFactory.Callback =
                it.getArgument(0) // Returns casted argument at the given index.
            callback.onSuccess(game)
        }.whenever(factory).buildGame(any())
    }

    private fun setUpFactoryWithError() {
        doAnswer {
            val callback: CocktailsGameFactory.Callback =
                it.getArgument(0)
            callback.onError()
        }.whenever(factory).buildGame(any())
    }

    @Test
    fun `init(), then should Build Game`() {
        viewModel.initGame()
        verify(factory).buildGame(any())
    }

    @Test
    fun `init(), then should Show Loading`() {
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(true))
        // onChanged(): Called when the data is changed.
        // eq(): Object argument that is equal to the given value.
    }

    @Test
    fun `init(), then should Hide Error`() {
        viewModel.initGame()
        // verify(errorObserver).onChanged(eq(false))
        Assert.assertFalse(viewModel.getError().value!!)
    }

    @Test
    fun `init(), when Factory Returns Error, then should Show Error`() {
        setUpFactoryWithError()
        viewModel.initGame()
        verify(errorObserver).onChanged(eq(true))
    }

    @Test
    fun `init(), when Factory Returns Error,then should Hide Loading`() {
        setUpFactoryWithError()
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun `init(), when Factory Returns Success,then should Hide Loading`() {
        setUpFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(false))
    }

    @Test
    fun `init(), when Factory Returns Success,then should Hide error`() {
        setUpFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(errorObserver, times(2)).onChanged(eq(false))
    }

    @Test
    fun `init(), when Factory Returns Success, then should ShowS core`() {
        val score = mock<Score>()
        whenever(game.score).thenReturn(score)
        setUpFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(scoreObserver).onChanged(eq(score))
    }

    @Test
    fun `init(), when Factory Returns Success, then should Show First Question`() {
        val question = mock<Question>()
        whenever(game.nextQuestion()).thenReturn(question)
        setUpFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(questionObserver).onChanged(eq(question))
    }

    @Test
    fun `nextQuestion(), then should Show Question`() {
        val question1 = mock<Question>()
        val question2 = mock<Question>()
        whenever(game.nextQuestion())
            .thenReturn(question1)
            .thenReturn(question2)
        setUpFactoryWithSuccessGame(game)
        viewModel.initGame()
        viewModel.nextQuestion()
        verify(questionObserver).onChanged(eq(question2))
    }

    @Test
    fun `answerQuestion(), when answer a question, then should Delegate To Game, save High Score show Question And Score`() {
        val score = mock<Score>()
        val question = mock<Question>()
        whenever(game.score).thenReturn(score)
        setUpFactoryWithSuccessGame(game)
        viewModel.initGame()
        viewModel.answerQuestion(question, "VALUE")
        inOrder(game, repository, questionObserver, scoreObserver) {
            verify(game).answer(eq(question), eq("VALUE"))
            verify(repository).saveHighScore(any())
            verify(scoreObserver).onChanged(eq(score))
            verify(questionObserver).onChanged(eq(question))
        }
        // inOrder(): Creates InOrder object that allows verifying mocks in order. Accepts a lambda to allow easy evaluation.
        // ( to check the methods are called exactly in the specified order )
    }
}

/**
@get:Rule. This is a test rule. A test rule is a tool to change
the way tests run, sometimes adding additional checks or running code before and
after your tests. Android Architecture Components uses a background executor that
is asynchronous (Default) to do its magic.
InstantTaskExecutorRule is a rule that swaps out
that executor and replaces it with synchronous one. This will make sure that, when
you’re using LiveData with the ViewModel, it’s all run synchronously in the tests.
 **/

// 1- Your ViewModel will require a CocktailsRepository to save the highscore and a
// CocktailsGameFactory to build a game. These are dependencies, so you need to mock them.

// 2- You’ll use a Game mock to stub some of its methods and verify you call methods on it

// 3- You need a few mocked observers because the Activity will observe LiveData
// objects exposed by the ViewModel. In the UI, you’ll show a loading view when
// retrieving the cocktails from the API and an error view if there’s an error
// retrieving the cocktails, score updates and questions. Because there’s no lifecycle
// here, you can use the observeForever() method.

/** observeForever(observer):
 * Adds the given observer to the observers list. This call is similar to
 * observe(LifecycleOwner, Observer) with a LifecycleOwner, which is always active.
 * This means that the given observer will receive all events and will never be automatically
 * removed. You should manually call removeObserver(Observer) to stop observing this LiveData.
 **/

/**
Note: There are multiple ways you can verify the result. For example, instead
of using verify(loadingObserver).onChanged(eq(true)), you could
replace it with Assert.assertTrue(viewModel.getLoading().value!!)
instead to achieve the same result. This alternative compares the last value of
the LiveData to the expected one instead of making sure a method was called
with that data.
 **/

// whenever(): Enables stubbing methods. Use it when you want the mock to return particular value when particular method is called.