package com.example.campuscontagion;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.campuscontagion.activities.ManageAccountActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.instanceOf;


@RunWith(AndroidJUnit4.class)
@LargeTest



public class ManageAccountPageTest {

    @Rule
    public ActivityTestRule<ManageAccountActivity> rule  = new  ActivityTestRule<ManageAccountActivity>(ManageAccountActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("playerName", "TEST");
            return intent;
        }
    };



    @Test
    public void ensureIntentDataIsDisplayed() throws Exception {
        ManageAccountActivity activity = rule.getActivity();

        View viewById = activity.findViewById(R.id.txtPlayer);

        TextView textView = (TextView) viewById;

        assertThat(textView.getText().toString(),is("Settings for: TEST"));
    }
}

