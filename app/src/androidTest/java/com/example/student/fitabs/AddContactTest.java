package com.example.student.fitabs;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by student on 17.20.7.
 */

@RunWith(AndroidJUnit4.class)
public class AddContactTest {
    @Rule
    public ActivityTestRule<AddContactActivity> mActivityRule = new ActivityTestRule<>(AddContactActivity.class);

    @Test
    public void testUserSettings() {
        onView(withId(R.id.textContactUsername)).check(matches(withText("Username")));
        onView(withId(R.id.textView)).check(matches(withText("Change username")));
        onView(withId(R.id.textTelnumber)).check(matches(withText("Number")));
        onView(withId(R.id.textAboutNumber)).check(matches(withText("Change phone number")));
        onView(withId(R.id.createUsername)).perform(typeText("NEW contact"), closeSoftKeyboard());
        onView(withId(R.id.createTelnum)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.saveContact)).perform(click());
    }
}
