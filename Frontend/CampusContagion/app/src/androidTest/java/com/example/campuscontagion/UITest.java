package com.example.campuscontagion;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.campuscontagion.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(AndroidJUnit4.class)
@LargeTest

public class UITest {


    @Rule
    public ActivityTestRule<MainActivity> loginActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void ServerStatusTest() {
        onView(withId(R.id.txtBob)).check(matches(withText("Status: Contagion Server is running.")));
    }

    @Test
    public void checkScreenObjectDisplay(){
        onView(withId(R.id.txtLoginBox)).check(matches(isDisplayed()));
        onView(withId(R.id.txtPasswordBox)).check(matches(isDisplayed()));

    }

    @Test
    public void checkButtons(){
        onView(withId(R.id.btnLobby)).check(matches(isClickable()));
        onView(withId(R.id.btnManageAccount)).check(matches(isClickable()));
        onView(withId(R.id.btnRegister)).check(matches(isClickable()));
    }


    @Test
    public void failLogin() throws InterruptedException {

        onView(withId(R.id.txtLoginBox)).perform(typeText("NonUser"),  closeSoftKeyboard());
        onView(withId(R.id.txtPasswordBox)).perform(typeText("12345"), closeSoftKeyboard());
        //Thread.sleep(5000);
        onView(withId(R.id.btnLobby)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.txtBob)).check(matches(withText("Login error.")));
    }


    @Test
    public void passLogin() throws InterruptedException {

        onView(withId(R.id.txtLoginBox)).perform(typeText("Jay"),  closeSoftKeyboard());
        onView(withId(R.id.txtPasswordBox)).perform(typeText("jjj"), closeSoftKeyboard());
        //Thread.sleep(5000);
        onView(withId(R.id.btnManageAccount)).perform(click());

        //onView(withId(R.id.btnJoinGame)).check(matches(isClickable()));
        //onView(withId(R.id.txtBob)).check(matches(withText("Login error.")));
    }

//
}
