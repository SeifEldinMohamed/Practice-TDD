package com.raywenderlich.android.cocktails.game.model

import org.junit.Assert
import org.junit.Test

class GameTest {

    @Test
    fun `incrementScore(), then increment current score`() {
        // Arrange
        val game = Game()

        // Act
        game.incrementScore()

        // Assert
        Assert.assertEquals(1, game.currentScore)
    }

    @Test
    fun `incrementScore(), with current score is above highest score, then increment highest score` () {
        // Arrange
        val game = Game()

        // Act
        game.incrementScore()

        // Assert
        Assert.assertEquals(1, game.highestScore)
    }

    @Test
    fun `incrementScore(), with current score is below highest score, then don't increment highest score` () {
        // Arrange
        val game = Game(10)

        // Act
        game.incrementScore()

        // Assert
        Assert.assertEquals(10, game.highestScore)
    }


}