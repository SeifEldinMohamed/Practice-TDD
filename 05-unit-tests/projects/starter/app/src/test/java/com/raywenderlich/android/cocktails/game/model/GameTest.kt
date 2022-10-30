package com.raywenderlich.android.cocktails.game.model

import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*

class GameTest {
    /*
        private fun createQuestionsList(): List<Question> {
        return listOf(
            Question(
                "what is the capital of egypt?",
                "Cairo",
                "Giza"
            ),
            Question(
                "what is the capital of USA?",
                "Washington",
                "New York"
            ),
            Question(
                "what is the capital of France?",
                "Paris",
                "Cairo"
            )
        )
    }
     */


    @Test
    fun `nextQuestion(), with available next question, then return next question` () {
        // Arrange
        val game = Game(QUESTIONS_MOCK)
        val expected = Question(
            "what is the capital of USA?",
            "Washington",
            "New York"
        )

        // Act
        val actual = game.nextQuestion(0)

        // Assert
        Assert.assertEquals(expected.question, actual?.question)
    }

    @Test
    fun `nextQuestion(), with final question reached, then return null` () {
        // Arrange
        val game = Game(QUESTIONS_MOCK)

        // Act
        val actual = game.nextQuestion(QUESTIONS_MOCK.size - 1)

        // Assert
        Assert.assertNull(actual)
    }

    @Test
    fun `answer(), when answering, then delegate to question` () {
        // Arrange
        val question = mock<Question>()
        val game = Game(listOf(question))
        // Act
        game.answer(question, "Option")
        // Assert
       // verify(question, times(1)).answer(eq("Option")) // You can omit times(1) as it’s the default
        verify(question).addAnswer(eq("Option"))
    }

    @Test
    fun `answer(), when answering right answer, then current score should increase` () {
        // Arrange
        val question = mock<Question>()
        whenever(question.addAnswer(anyString())).thenReturn(true)
        // Using whenever/method/thenReturn you’re
        // stubbing the question.answer() method to always return true. Notice here
        // you used the anyString() argument matcher as you don’t care which specific
        val score = mock<Score>()
        // String you need to stub the call
        val game = Game(listOf(question),score)
        // Act
        game.answer(question, "Option")
        // Assert
        verify(score).increment()
    }

    @Test
    fun `answer(), when answering wrong answer, then current score should not increase` () {
        // Arrange
        val question = mock<Question>()
        whenever(question.addAnswer(anyString())).thenReturn(false)

        val score = mock<Score>()
        val game = Game(listOf(question),score)
        // Act
        game.answer(question, "Option")
        // Assert
        verify(score, never()).increment()
    }
    /*
    Verify the method answer() was called on the Question mock. You used the
times(1) verification mode to check that the answer() method was called
exactly one time. You also used the eq argument matcher to check that the
answer() method was called with a String equal to Option
     */
    /**
     * Note: There are also other verification modes like never(), atLeast(),
    atMost() and other argument matchers, like eq(), same(), any() that you
    may use depending on your test
     * */

    /**
     * Kotlin classes and methods are final by default. Mockito won’t work
    with final classes/methods out of the box. To fix this you have the following options:
    • Use a mock-maker-inline extension to allow Mockito mock final classes/
    methods.
    • Add the open keyword to classes and methods that you’ll mock.
    • Create an interface and have the class implement the interface. Then, just mock
    the interface (interfaces are open by default).
     * */

}