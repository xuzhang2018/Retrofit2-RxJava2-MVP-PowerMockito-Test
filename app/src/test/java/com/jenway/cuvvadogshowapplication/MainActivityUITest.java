package com.jenway.cuvvadogshowapplication;


import android.os.Build;
import android.widget.Toast;

import com.jenway.cuvvadogshowapplication.base.BaseActivity;
import com.jenway.cuvvadogshowapplication.detailScreen.DetailScreenFragment;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * by Xu
 * Only load the local data when the debug mod is on
 * check if the activity is showing
 * check if the fragment transfer work fine
 * check if the toast is working in activity
 * check if the dialog is working in activity
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class MainActivityUITest {

    ActivityController<MainActivity> activityController;
    BaseActivity activity;


    @Mock
    ArrayList<BaseEntity> param1;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(MainActivity.class).create();
        activity = activityController.get();
    }


    @After
    public void tearDown() throws Exception {
        activityController.stop().destroy();
        activity = null;
    }

    @Test
    public void testActivity() {
        assertNotNull(activity);
        assertEquals(activity.getLocalClassName(), "MainActivity");
    }

    @Test
    public void testFragmentTransfer() throws Exception {
        //first fragment
        assertEquals(activity.getSupportFragmentManager().getFragments().size(), 1);
        assertEquals(activity.getSupportFragmentManager().getFragments().get(0).getTag(), "NestedListFragment");

        //transfer to second fragment
        DetailScreenFragment detailScreenFragment = DetailScreenFragment.newInstance(param1, "");
        activity.addFragment(detailScreenFragment);
        assertEquals(activity.getSupportFragmentManager().getFragments().size(), 1);
        assertEquals(activity.getSupportFragmentManager().getFragments().get(0).getTag(), "DetailScreenFragment");

        //remove second fragment and go back first fragment by stack
        activity.removeFragment();
        assertEquals(activity.getSupportFragmentManager().getFragments().size(), 1);
        assertEquals(activity.getSupportFragmentManager().getFragments().get(0).getTag(), "NestedListFragment");

    }

    @Test
    public void testToast() throws Exception {
        Toast toast = ShadowToast.getLatestToast();
        //the toast is not appear
        assertNull(toast);

        activity.showToast("test toast");
        toast = ShadowToast.getLatestToast();
        // the toast is appear
        assertNotNull(toast);
        assertEquals("test toast", ShadowToast.getTextOfLatestToast());
    }

    /**
     * by Xu
     * Robolectrics4 has not support Androidx dialog yet
     */

    @Test
    public void testShowDialog() throws Exception {
//        AlertDialog dialog = ShadowAlertDialog.getLatestDialog();
//        //the dialog is not appear
//        assertNull(dialog);
//
//        activity.showDialog("test dialog");
//        dialog = ShadowAlertDialog.getLatestAlertDialog();
//        // the dialog has appeared
//        assertNotNull(dialog);
//        // get the shadow dialog to obtain the message
//        ShadowAlertDialog shadowDialog = Shadows.shadowOf(dialog);
//        assertEquals("test dialog", shadowDialog.getMessage());
//
//        activity.dismissDialog();
//        dialog = ShadowAlertDialog.getLatestAlertDialog();
//        //the dialog is not appear
//        assertNull(dialog);
    }
}