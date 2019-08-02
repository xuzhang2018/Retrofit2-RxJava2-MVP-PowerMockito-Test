package com.jenway.cuvvadogshowapplication.detailScreen;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.jenway.cuvvadogshowapplication.MainActivity;
import com.jenway.cuvvadogshowapplication.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.jenway.cuvvadogshowapplication.nestedList.NestedListFragmentUITest.withRecyclerView;

/**
 * by Xu
 * Only load the local data when the debug mod is on
 * check if the the page view is existed
 * check if user can swipe right two times=(images has been selected)
 * check if the nested-list keep the status (position and checked) after go back to the first fragment
 */


@RunWith(AndroidJUnit4ClassRunner.class)
public class DetailScreenFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUI() throws Exception {
        //init UI
        Thread.sleep(5000);
        //check if the FloatingActionButton has displayed
        onView(withId(R.id.breed_detail_screen_fab)).check(matches(isDisplayed()));

        //check items
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(7));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).perform(click());
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).check(matches(isChecked()));
        //show sub breed
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(3));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(3, R.id.breed_item_sub_breed)).perform(click());
        Thread.sleep(5000);
        //check sub breed
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(4));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(4, R.id.sub_breed_item_cb)).perform(click());
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(4, R.id.sub_breed_item_cb)).check(matches(isChecked()));
        //


        ViewInteraction pageView = onView(withId(R.id.breeds_vp));
        //the page view is not exist
        pageView.check(doesNotExist());
        onView(withId(R.id.breed_detail_screen_fab)).perform(click());
        Thread.sleep(2000);
        //the page view is displayed = the fragment shows
        pageView.check(matches(isDisplayed()));

        onView(withId(R.id.breeds_vp)).check(matches(isDisplayed())).perform(swipeLeft());
        // can swipe right two times
        onView(withId(R.id.breeds_vp)).check(matches(isDisplayed())).perform(swipeRight());
        onView(withId(R.id.breeds_vp)).check(matches(isDisplayed())).perform(swipeRight());
        //

        //test press back to nested fragment
        ViewInteraction list = onView(withId(R.id.nested_rv));
        list.check(doesNotExist());
        onView(isRoot()).perform(ViewActions.pressBack());
        Thread.sleep(2000);
        list.check(matches(isDisplayed()));

        //test back to the previous status
        //last position is 4 and checked
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(4, R.id.sub_breed_item_cb)).check(matches(isChecked()));
    }
}