package com.raywenderlich.android.cocktails.game.model

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(GameTest::class, QuestionTest::class, ScoreTest::class)
class AppTestSuite