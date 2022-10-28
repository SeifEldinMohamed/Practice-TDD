package com.raywenderlich.android.cocktails.game.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class QuestionTest {

    private lateinit var question: Question

    @Before
    fun setup() {
        question = Question("", "Cairo", "Giza")
    }

    @Test
    fun `when creating question, then shouldn't have answered option`() {
        // Arrange
        // Act
        val actual = question.answerOption
        // Assert
        Assert.assertNull(actual)
    }

    @Test
    fun `addAnswer(), with answering question, then should have answered option`() {
        // Arrange
        // Act
        question.addAnswer("correct")
        val actual = question.answerOption
        // Assert
        Assert.assertEquals("correct", actual)
    }

    @Test
    fun `addAnswer(), with answering correct, then should return true`() {
        // Arrange
        // Act
        val actual = question.addAnswer("correct")

        // Assert
        Assert.assertTrue(actual)
    }

    @Test
    fun `addAnswer(), with answering incorrect, then should return false`() {
        // Arrange
        // Act
        val actual = question.addAnswer("incorrect")

        // Assert
        Assert.assertFalse(actual)
    }

    @Test(expected = java.lang.IllegalArgumentException::class)
    fun `addAnswer(), with answering invalid option, then should throw exception`() {
        // Arrange
        // Act
        val actual = question.addAnswer("not valid")

    }

    @Test
    fun `getOptions(), then return shuffled list of options`() {
        // Arrange
        val expected = QUESTIONS_MOCK[0].getOptions()
        // Act
        val actual = question.getOptions()

        // Assert
        Assert.assertEquals(expected, actual)
    }
}