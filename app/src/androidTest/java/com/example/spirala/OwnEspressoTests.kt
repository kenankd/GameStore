package com.example.spirala

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.spirala.GameData.Companion.getAll
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OwnEspressoTests {


    /**
     * Scenario 1
     * Ovaj scenario testira otvaranje aplikacije u portrait mode-u,
     * zatim da li se klikom na jedan od elemenata recycler view-a game_list fragment homeItem mijenja fragmentom gameDetailsItem i da li se prikazuje ispravna igra i njeni podaci
     * Vracanje na fragment homeItem klikom na homeButton BottomNavigationBar-a
     */
    @Test
    fun testScenario1(){
        getAll()
        //otvaranje aktivnosti
        var homeRule: ActivityScenario<HomeActivity> = ActivityScenario.launch(HomeActivity::class.java)
        //click na drugu igricu po redu u recycler view-u tj. NBA 2K23
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        //provjera da li je su detalji o igri pravilno rasporedjeni na ekranu
        onView(withId(R.id.item_title_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.item_title_textview)).check(isCompletelyAbove(withId(R.id.cover_imageview)))
        onView(withId(R.id.platform_textview)).check(isCompletelyBelow(withId(R.id.cover_imageview)))
        onView(withId(R.id.platform_textview)).check(isTopAlignedWith(withId(R.id.developer_textview)))
        onView(withId(R.id.platform_textview)).check(isCompletelyLeftOf(withId(R.id.developer_textview)))
        onView(withId(R.id.release_date_textview)).check(isCompletelyBelow(withId(R.id.platform_textview)))
        onView(withId(R.id.release_date_textview)).check(isTopAlignedWith(withId(R.id.publisher_textview)))
        onView(withId(R.id.release_date_textview)).check(isCompletelyLeftOf(withId(R.id.publisher_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(isCompletelyBelow(withId(R.id.release_date_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(isTopAlignedWith(withId(R.id.genre_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(isCompletelyLeftOf(withId(R.id.genre_textview)))
        onView(withId(R.id.description_textview)).check(isCompletelyBelow(withId(R.id.genre_textview)))
        onView(withId(R.id.description_textview)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.description_textview)).check(isCompletelyAbove(withId(R.id.review_list)))
        //provjera da li je ispravna igra otvorena
        onView(withId(R.id.item_title_textview)).check(matches(withText(getAll()[1].title)))
        //povratak na home fragment
        onView(withId(R.id.homeItem)).perform(click())
        //provjera da li je fragment promijenjen
        onView(withId(R.id.search_button)).check(matches(isCompletelyDisplayed()))
    }
}