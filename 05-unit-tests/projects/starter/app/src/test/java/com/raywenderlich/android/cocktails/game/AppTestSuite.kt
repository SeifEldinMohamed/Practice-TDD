package com.raywenderlich.android.cocktails.game

import com.raywenderlich.android.cocktails.game.factory.CocktailsGameFactoryTest
import com.raywenderlich.android.cocktails.game.model.GameTest
import com.raywenderlich.android.cocktails.game.model.QuestionTest
import com.raywenderlich.android.cocktails.game.model.ScoreTest
import com.raywenderlich.android.cocktails.game.repository.RepositoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    GameTest::class,
    QuestionTest::class,
    ScoreTest::class,
    CocktailsGameFactoryTest::class,
    RepositoryTest::class
)
class AppTestSuite