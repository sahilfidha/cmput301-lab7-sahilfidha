package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        onView(withId(R.id.button_clear)).perform(click());
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());
    }

    /**
     * Tests if clicking a city in the list correctly switches to ShowActivity.
     */
    @Test
    public void testActivitySwitch() {
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        // Verify we are in ShowActivity by checking for a view unique to it (the back button).
        onView(withId(R.id.button_back)).check(matches(isDisplayed()));
    }

    /**
     * Tests if the city name passed to ShowActivity is displayed correctly.
     */
    @Test
    public void testCityNameConsistency() {
        String testCity = "Calgary";
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        // Verify that the TextView in ShowActivity displays the correct city name.
        onView(withId(R.id.textView_showCity)).check(matches(withText(testCity)));
    }

    /**
     * Tests if the "BACK" button in ShowActivity returns the user to MainActivity.
     */
    @Test
    public void testBackButton() {
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        onView(withId(R.id.button_back)).perform(click());
        // Verify we are back in MainActivity by checking for a view unique to it (the ADD CITY button).
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}
