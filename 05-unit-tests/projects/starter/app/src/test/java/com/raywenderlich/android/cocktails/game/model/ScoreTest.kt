package com.raywenderlich.android.cocktails.game.model

import org.junit.Assert
import org.junit.Test


class ScoreTest {

    @Test
    fun `incrementScore(), then increment current score`() {
        // Arrange
        val score = Score()

        // Act
        score.increment()

        // Assert
        Assert.assertEquals(1, score.current)
    }

    @Test
    fun `incrementScore(), with current score is above highest score, then increment highest score` () {
        // Arrange
        val score = Score()

        // Act
        score.increment()

        // Assert
        Assert.assertEquals(1, score.highest)
    }

    @Test
    fun `incrementScore(), with current score is below highest score, then don't increment highest score` () {
        // Arrange
        val score = Score(10)

        // Act
        score.increment()

        // Assert
        Assert.assertEquals(10, score.highest)
    }
}