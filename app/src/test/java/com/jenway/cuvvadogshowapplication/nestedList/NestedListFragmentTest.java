package com.jenway.cuvvadogshowapplication.nestedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * by Xu
 * Robolectrics4 has not support Androidx fragment yet
 * Only load the local data when the debug mod is on
 * check if the fragment lifecyle is working fine
 */
//@RunWith(RobolectricTestRunner.class)
public class NestedListFragmentTest {

//    private BaseFragment fragment;


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


    @Test
    public void testOnresume() throws Exception {
    }

    @Test
    public void testOnSaveInstanceState() throws Exception {
    }

    @Test
    public void testOnStop() throws Exception {
    }
}