package com.jenway.cuvvadogshowapplication.nestedList;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jenway.cuvvadogshowapplication.base.BaseFragment;
import com.jenway.cuvvadogshowapplication.nestedList.adapter.NestedListAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)

public class NestedListFragmentTest {

    private BaseFragment fragment;
    private LinearLayoutManager mockLayoutManager;
    private NestedListAdapter mockNestedListAdapter;

    @Before
    public void setUp() throws Exception {
//        mockLayoutManager = PowerMockito.mock(LinearLayoutManager.class);
//        fragment = NestedListFragment.newInstance("","");
//        //Start the fragment!
//        Intent intent = new Intent();
//        intent.putExtra("testK", "testV");
//        FragmentController controller=FragmentController.createController();
//
//        SupportFragmentController<NestedListFragment> controller = SupportFragmentController.of(sampleFragment, SampleActivity.class, intent);
    }

    @After
    public void tearDown() throws Exception {
    }
}