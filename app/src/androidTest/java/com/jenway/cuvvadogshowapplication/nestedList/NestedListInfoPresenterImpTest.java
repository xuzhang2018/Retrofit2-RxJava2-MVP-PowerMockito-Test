package com.jenway.cuvvadogshowapplication.nestedList;

import com.google.gson.Gson;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;
import com.jenway.cuvvadogshowapplication.nestedList.mvp.NestedListInfoPresenterImp;
import com.jenway.cuvvadogshowapplication.network.NetBean;
import com.jenway.cuvvadogshowapplication.network.NetData;
import com.jenway.cuvvadogshowapplication.network.NetUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * by Xu
 * Description: only test the functions, the mvp test is in Junit Test folder
 */
public class NestedListInfoPresenterImpTest {

    private NestedListInfoPresenterImp imp;
    private Method[] methods;
    private Gson gson;
    private ArrayList<BaseEntity> breeds;

    @Before
    public void setUp() throws Exception {
        //get the methods by reflection
        methods = NestedListInfoPresenterImp.class.getDeclaredMethods();
        gson = new Gson();
        NetData data = gson.fromJson(NetUtil.getJson("breedlist.json"), NetData.class);
        breeds = NetBean.CovertTheNetDataToBreedList(data);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_updateShowList() throws Exception {
        imp = new NestedListInfoPresenterImp();
        imp.setBreedInfoFullList(breeds);
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("updateShowList")) {//find the method by name
                methods[i].setAccessible(true);
                //first show items
                Object o = methods[i].invoke(imp);
                Assert.assertEquals(22, ((ArrayList) o).size());//check how many entity has been added to the show list
                //second show items
                o = methods[i].invoke(imp);
                Assert.assertEquals(19, ((ArrayList) o).size());//check how many entity has been added to the show list
                //second show items
                o = methods[i].invoke(imp);
                Assert.assertEquals(2, ((ArrayList) o).size());
            }
        }
    }

    @Test
    public void test_getBreedRequireImageList() throws Exception {
        imp = new NestedListInfoPresenterImp();
        ArrayList<Integer> breedShowIndexList = new ArrayList<>();
        imp.setBreedInfoFullList(breeds);
        //
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("updateShowList")) {//find the method by name
                methods[i].setAccessible(true);
                //
                Object o = methods[i].invoke(imp);
                breedShowIndexList = (ArrayList) o;
            }
        }

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("getBreedRequireImageList")) {//find the method by name
                methods[i].setAccessible(true);
                //
                Object o = methods[i].invoke(imp, breedShowIndexList);
                List<BaseEntity> requireImageList = (ArrayList) o;
                Assert.assertEquals(15, requireImageList.size());
            }
        }
    }

    @Test
    public void test_getSubBreedIndexList() throws Exception {
        imp = new NestedListInfoPresenterImp();
        imp.setBreedInfoFullList(breeds);
        //
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("updateShowList")) {//find the method by name
                methods[i].setAccessible(true);
                methods[i].invoke(imp);
            }
        }

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("getSubBreedIndexList")) {//find the method by name
                methods[i].setAccessible(true);
                //first show items
                Object o = methods[i].invoke(imp, 3);
                ArrayList<Integer> subBreedIndexList = (ArrayList) o;
                Assert.assertEquals(3, subBreedIndexList.size());
            }
        }
    }

    @Test
    public void test_getSubBreedRequireImageList() throws Exception {
        imp = new NestedListInfoPresenterImp();
        imp.setBreedInfoFullList(breeds);
        ArrayList<Integer> subBreedIndexList = new ArrayList<>();
        //
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("updateShowList")) {//find the method by name
                methods[i].setAccessible(true);
                methods[i].invoke(imp);
            }
        }

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("getSubBreedIndexList")) {//find the method by name
                methods[i].setAccessible(true);
                //first show items
                Object o = methods[i].invoke(imp, 3);
                subBreedIndexList = (ArrayList) o;
            }
        }

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("getSubBreedRequireImageList")) {//find the method by name
                methods[i].setAccessible(true);
                //
                Object o = methods[i].invoke(imp, subBreedIndexList);
                List<BaseEntity> requireImageList = (ArrayList) o;
                Assert.assertEquals(3, requireImageList.size());
            }
        }
    }


}