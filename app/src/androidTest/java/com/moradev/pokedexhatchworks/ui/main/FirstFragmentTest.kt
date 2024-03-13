package com.moradev.pokedexhatchworks.ui.main

import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FirstFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1) val scenarioRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setUp(){

    }

    @Test
    fun recyclerViewShowed(){


//        onView(withId(R.id.rvList)).perform(typeText("Bulbasaur"))
//        onView(withId(R.id.rvList)).perform(scrollTo())


    }

    @Test
    fun checkMainActivity(){
        scenarioRule.scenario.onActivity {

        }
    }


    @Test
    fun testFoo(){
        hiltRule.inject()
    }
}

