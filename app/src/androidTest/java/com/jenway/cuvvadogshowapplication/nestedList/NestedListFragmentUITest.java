package com.jenway.cuvvadogshowapplication.nestedList;

import android.content.pm.ActivityInfo;

import androidx.test.espresso.ViewInteraction;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.jenway.cuvvadogshowapplication.MainActivity;
import com.jenway.cuvvadogshowapplication.R;
import com.jenway.cuvvadogshowapplication.RecyclerViewMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * by Xu
 * Only load the local data when the debug mod is on
 * check if the RecyclerView has displayed
 * Check to hide and show sub breeds
 * check the breed and subbreed checkbox
 * check the FloatingActionButton and go second fragment
 * check if the list keep the status (position and checked) in Protaiit and Landscape mode
 * check if the toast is working in fragment
 * check if the dialog in working in fragment
 */

@RunWith(AndroidJUnit4ClassRunner.class)
public class NestedListFragmentUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRecyclerViewShow() throws Exception {
        //init UI
        Thread.sleep(5000);
        //check if the RecyclerView has displayed
        onView(withId(R.id.nested_rv)).check(matches(isDisplayed()));
        //check if the RecyclerView has right order of the data
        onView(withRecyclerView(R.id.nested_rv).atPosition(2))
                .check(matches(hasDescendant(withText("airedale"))));
        onView(withRecyclerView(R.id.nested_rv).atPosition(3))
                .check(matches(hasDescendant(withText("bulldog"))));
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(7));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPosition(7)).check(matches(hasDescendant(withText("bullterrier"))));


        //test hide and show sub breed
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(4));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPosition(4)).check(matches(not(isDisplayed())));
        //show sub breed
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(3));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(3, R.id.breed_item_sub_breed)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(4));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPosition(4)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.nested_rv).atPosition(4)).check(matches(hasDescendant(withText("boston"))));
        //hide sub breed
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(3));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(3, R.id.breed_item_sub_breed)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(4));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPosition(4)).check(matches(not(isDisplayed())));


        //test the breed checkbox
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(7));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).perform(click());
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).check(matches(isChecked()));
        //uncheck
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).perform(click());
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).check(matches(isNotChecked()));


        //test the sub breed checkbox
        //show sub breed
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(3));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(3, R.id.breed_item_sub_breed)).perform(click());
        Thread.sleep(5000);
        //check
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(4));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(4, R.id.sub_breed_item_cb)).perform(click());
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(4, R.id.sub_breed_item_cb)).check(matches(isChecked()));
        //uncheck
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(4, R.id.sub_breed_item_cb)).perform(click());
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(4, R.id.sub_breed_item_cb)).check(matches(isNotChecked()));
    }

    @Test
    public void testListStaySameStatusWhenSwicthProtaiitAndLandscape() throws Exception {
        //init UI
        Thread.sleep(5000);
        mActivityRule.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Thread.sleep(2000);
        //test the breed checkbox
        onView(withId(R.id.nested_rv)).perform(scrollToPosition(7));
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).perform(click());
        Thread.sleep(1000);
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).check(matches(isChecked()));


        //change to landscape
        mActivityRule.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Thread.sleep(2000);
        //same position and checked
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).check(matches(isChecked()));

        //change to portrait
        mActivityRule.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Thread.sleep(2000);
        //same position and checked
        onView(withRecyclerView(R.id.nested_rv).atPositionOnView(7, R.id.breed_item_cb)).check(matches(isChecked()));

    }

    @Test
    public void testFloatingActionButton() throws Exception {
        //init UI
        Thread.sleep(5000);
        //check if the FloatingActionButton has displayed
        onView(withId(R.id.breed_detail_screen_fab)).check(matches(isDisplayed()));

        //show toast if no item has been checked
        onView(withId(R.id.breed_detail_screen_fab)).perform(click());
        Thread.sleep(1000);
        onView(withText("Please select at least one item."))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

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

        //
    }


    @Test
    public void testClickButtonToShowToast() throws Exception {
        //init UI
        Thread.sleep(5000);
        //click the toast test button
        onView(withId(R.id.test_toast_button))
                .perform(click());

        //wait for process 1 second and Toast with "test toast" appear
        Thread.sleep(1000);
        onView(withText("test toast"))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickButtonToShowDialog() throws Exception {
        //init UI
        Thread.sleep(5000);
        //click the dialog test button
        onView(withId(R.id.test_dialog_button))
                .perform(click());

        //wait for process 1 second and Toast with "test toast" appear
        Thread.sleep(1000);
        onView(withText("test dialog"))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }


}