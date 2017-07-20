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

@RunWith(AndroidJUnit4.class)
public class UserSettingsActivityTest {
    @Rule
    public ActivityTestRule<UserSettingsActivity> mActivityRule = new ActivityTestRule<>(UserSettingsActivity.class);

    @Test
    public void testUserSettings() {
        onView(withId(R.id.userView)).check(matches(withText("User Settings")));
        onView(withId(R.id.textUsername)).check(matches(withText("Username")));
        onView(withId(R.id.textView)).check(matches(withText("Change username")));
        onView(withId(R.id.textTelnumber)).check(matches(withText("Number")));
        onView(withId(R.id.textAboutNumber)).check(matches(withText("Change phone number")));
        onView(withId(R.id.textStatus)).check(matches(withText("Status")));
        onView(withId(R.id.textAboutStatus)).check(matches(withText("Are you coach?")));
        onView(withId(R.id.nsText)).check(matches(withText("Network Settings")));
        onView(withId(R.id.userView)).check(matches(withText("User Settings")));
        onView(withId(R.id.textIP)).check(matches(withText("IP")));
        onView(withId(R.id.IPView)).check(matches(withText("Server IP Address")));
        onView(withId(R.id.editUsername)).perform(typeText("karina"), closeSoftKeyboard());
        onView(withId(R.id.editTelNumber)).perform(typeText("29830407"), closeSoftKeyboard());
        onView(withId(R.id.checkBoxStatus)).perform(click());
        onView(withId(R.id.editIP)).perform(typeText("192.25.132.197"), closeSoftKeyboard());
        onView(withId(R.id.buttonSave)).perform(click());
    }
}
