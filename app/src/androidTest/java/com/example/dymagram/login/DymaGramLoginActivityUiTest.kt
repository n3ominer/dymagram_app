package com.example.dymagram.login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dymagram.R

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DymaGramLoginActivityUiTest {

    @Test
    fun testInputOnUsernameTextField() {
        ActivityScenario.launch(DymaGramLoginActivity::class.java)

        onView(withId(R.id.login_text_input_edit_text)).perform(typeText("Dyma"))
        onView(withId(R.id.password_text_input_edit_text)).perform(typeText("Password1."), closeSoftKeyboard());

        onView(withId(R.id.login_button)).perform(click())

        onView(withId(R.id.home_activity)).check(matches(isDisplayed()))
    }
}