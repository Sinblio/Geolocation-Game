package com.example.campuscontagion;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;


@RunWith(AndroidJUnit4.class)
@LargeTest

// Check to make sure the location requests are not throwing errors and returning false
// when they should be returning true
public class GamePlayTest {


    @Rule
    public ActivityTestRule<GamePlay> playActivityRule =
            new ActivityTestRule<>(GamePlay.class);


    @Test
    public void playTest() {
        GamePlay g = mock(GamePlay.class);

        assertThat(g.makePlayerLocationRequestSocket(), is(true));
    }

    @Test
    public void playTestTwo() {
        GamePlay g = mock(GamePlay.class);

        assertThat(g.makePlayerLocationRequestSocketTwo(), is(true));
    }


//
}