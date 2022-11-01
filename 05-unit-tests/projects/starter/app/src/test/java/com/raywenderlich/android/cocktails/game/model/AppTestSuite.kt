package com.raywenderlich.android.cocktails.game.model

import com.raywenderlich.android.cocktails.game.factory.CocktailsGameFactoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(GameTest::class, QuestionTest::class, ScoreTest::class, CocktailsGameFactoryTest::class)
class AppTestSuite