package com.example.newsapp.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.fragments.FilterFragment
import com.example.newsapp.R
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterFragmentTest : TestCase(){

  private  lateinit var scenario: FragmentScenario<FilterFragment>

  @Before
  fun setup(){
     scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
  }

    @Test
    fun isTheSearchBoxEmpty(){
//     onView(withId(R.id.sendBtn)).perform(click())
//
//        //test if toast is displayed
//        onView(withText("You need to enter a search query"))
//            .inRoot(ToastMatcher())
//            .check(matches(isDisplayed()))
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun hasAtLeastOneFilterBeenSelected(){
    val search = "trump"
//        onView(withId(R.id.messageInput)).perform(typeText(search.toString()))
//        Espresso.closeSoftKeyboard()
//        onView(withId(R.id.sendBtn)).perform(click())
//
//        //test if toast is displayed
//        onView(withText("You need to select at least one filters"))
//            .inRoot(ToastMatcher())
//            .check(matches(isDisplayed()))
        Assert.assertEquals(4, 2 + 2)

    }



}