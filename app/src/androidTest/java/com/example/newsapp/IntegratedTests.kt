package com.example.newsapp

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.example.newsapp.activities.Query
import com.example.newsapp.fragments.ToastMatcher
import org.junit.Test


class IntegratedTests(){
    @Test
    fun startDateAndEndDateEmpty() {
        val activityScenario = ActivityScenario.launch(Query::class.java)
        val search = "trump"
        onView(withId(R.id.messageInput)).perform(ViewActions.typeText(search.toString()))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.cbPolitics)).perform(ViewActions.click())
        onView(withId(R.id.sendBtn)).perform(ViewActions.click())
        onView(withId(R.id.recyclerViewFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun noInternet() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        lateinit var instrumentationContext: Context
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        val wifi = instrumentationContext.getSystemService(WIFI_SERVICE) as WifiManager
        wifi.isWifiEnabled = false

        //test if toast is displayed
        onView(withText("There are network connectivity problems"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        wifi.isWifiEnabled = true

    }



}