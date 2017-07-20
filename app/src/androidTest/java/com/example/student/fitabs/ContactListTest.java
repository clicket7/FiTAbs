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
public class ContactListTest {
    @Rule
    public ActivityTestRule<ContactsActivity> mActivityRule = new ActivityTestRule<>(ContactsActivity.class);

    @Test
    public void testContacts() {
        onView(withId(R.id.textView)).check(matches(withText("Contacts")));
    }
}
