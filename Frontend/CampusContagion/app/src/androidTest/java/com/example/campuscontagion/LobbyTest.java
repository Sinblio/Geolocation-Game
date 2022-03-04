package com.example.campuscontagion;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.campuscontagion.activities.LobbyHostActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(AndroidJUnit4.class)
@LargeTest

public class LobbyTest {


    @Rule
    public ActivityTestRule<LobbyHostActivity> playActivityRule =
            new ActivityTestRule<>(LobbyHostActivity.class);


    @Test
    public void playTest() {
        onView(withId(R.id.btnPlayGame)).check(matches(isClickable()));
    }

    @Test
    public void refreshTest() {
        onView(withId(R.id.btnSockets)).check(matches(isClickable()));
    }


//
}