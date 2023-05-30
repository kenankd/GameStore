package com.example.spirala

import android.content.pm.ActivityInfo
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.MainActivity


import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OwnEspressoTests {
    //otvaranje aktivnosti
    @get:Rule
    var homeRule:ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)
    //custom matcher koji sam dodao zbog errora vise viewova u hijerarhiji u testu 3
    private fun withIndex(matcher: Matcher<View?>, index: Int): Any {
        return object : TypeSafeMatcher<View>() {
            var currentIndex = 0
            var viewObjHash = 0
            override fun describeTo(description: Description) {
                description.appendText(String.format("with index: %d ", index))
                matcher.describeTo(description)
            }
            override fun matchesSafely(view: View): Boolean {
                if (matcher.matches(view) && currentIndex++ == index) {
                    viewObjHash = view.hashCode()
                }
                return view.hashCode() == viewObjHash
            }
        }
    }
    /**
     * Scenario 1
     * Ovaj scenario testira otvaranje aplikacije u portrait mode-u,
     * zatim da li se klikom na jedan od elemenata recycler view-a game_list fragment homeItem mijenja fragmentom gameDetailsItem i da li se prikazuje ispravna igra i njeni podaci
     * Vracanje na fragment homeItem klikom na homeButton BottomNavigationBar-a
     */
    @Test
    fun testScenario1(){
        //click na drugu igricu po redu u recycler view-u tj. NBA 2K23
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        //provjera da li su detalji o igrici pravilno rasporedjeni na ekranu
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
        //provjera da li je fragment promijenjen (search button se nalazi samo na home fragmentu)
        onView(withId(R.id.search_button)).check(matches(isCompletelyDisplayed()))
    }
    /**
     * Scenario 2
     * Ovaj scenario testira da li aplikacija ispravno pamti koja je zadnja igra otvorena u portrait orijentaciji,
     * tj. da li otvara posljednju otvorenu igricu klikom na details menu item
     * Takodjer se testira da li je details dugme disabled na samom otvaranju aplikacije, i da li je enabled kada se vratimo iz gamedetailfragmenta
     */
    @Test
    fun testScenario2(){
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.item_title_textview)).check(matches(withText(getAll()[0].title)))
        onView(withId(R.id.homeItem)).perform(click())
        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))
        onView(withId(R.id.gameDetailsItem)).perform(click())
        onView(withId(R.id.item_title_textview)).check(matches(withText(getAll()[0].title)))
    }
    /**
     * Scenario 3
     * Ovaj scenario otvara aplikaciju u landscape orijentaciji
     * Testira da li je prva otvorena igra prva iz liste igri tj. Fifa 23
     * Vrsi se scroll recycler viewa i testira da li se klikom na petu igru otvara peta igra
     * Zatim se vraca na portrait i testira da li je i dalje sve ok
     */
    @Test
    fun testScenario3(){
        val games : List<Game> = getAll()
        //promjena orijentacije aplikacije u LANDSCAPE
        homeRule.scenario.onActivity{ activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        //provjera da li se otvori igrica Fifa 23
        onView(withIndex(withId(R.id.item_title_textview),0) as Matcher<View>?).check(matches(withText(games[0].title)))
        //klik na drugi element recycler view-a
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        //provjera da li je otvorena igrica koja se nalazi na drugom mjestu recycler view-a
        onView(withIndex(withId(R.id.item_title_textview),0) as Matcher<View>?).check(matches(withText(games[1].title)))
        //scroll do zadnjeg elementa recycler view-a
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(games.size-1))
        //klik na zadnji element recycler view-a
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        //provjera da li je otvorena igrica koja se nalazi na zadnjem mjestu recycler view-a
        onView(withIndex(withId(R.id.item_title_textview),0) as Matcher<View>?).check(matches(withText(games[5].title)))
        //promjena orijentacije na portrait mode
        homeRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        //da li se vidi menu item tj. da li je pravilno vracen portrait mode
        onView(withIndex(withId(R.id.homeItem),0) as Matcher<View>?).check(matches(
            isCompletelyDisplayed()
        ))
        //ponovna provjera kao u testu 1 da se uvjerimo da sve radi kao prije nakon vracanja iz landscape orijentacije
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.item_title_textview)).check(matches(withText(getAll()[3].title)))
        }
    }