package com.jenway.cuvvadogshowapplication.detailScreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.jenway.cuvvadogshowapplication.R;
import com.jenway.cuvvadogshowapplication.base.BaseFragment;
import com.jenway.cuvvadogshowapplication.dagger.DaggerDetailScreenComponent;
import com.jenway.cuvvadogshowapplication.dagger.DetailScreenModule;
import com.jenway.cuvvadogshowapplication.detailScreen.adapter.MyPagerAdapter;
import com.jenway.cuvvadogshowapplication.detailScreen.mvp.DetailScreenContract;
import com.jenway.cuvvadogshowapplication.detailScreen.mvp.DetailScreenPresenterImp;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailScreenFragment extends BaseFragment<DetailScreenPresenterImp> implements DetailScreenContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<BaseEntity> mParam1; //to receive the data
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;

    public DetailScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailScreenFragment newInstance(ArrayList<BaseEntity> param1, String param2) {
        DetailScreenFragment fragment = new DetailScreenFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.breeds_vp);
        LayoutInflater inflater = getLayoutInflater();
        mPresenter.loadBreedListData(inflater, mParam1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_screen;
    }

    @Override
    protected void initInjector() {
        DaggerDetailScreenComponent.builder()
                .appComponent(getAppComponent())
                .detailScreenModule(new DetailScreenModule(this))
                .build()
                .inject(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void setPageView(ArrayList<BaseEntity> breedInfoList) {
        pagerAdapter = new MyPagerAdapter(getContext(), breedInfoList);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onLoadInfoSuccess() {
        dismissLoadingDialog();
    }

    @Override
    public void onLoadInfoFail(String errorMsg) {
        dismissLoadingDialog();
        Toast.makeText(mActivity, errorMsg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoadingDialog(String msg) {
        mActivity.showDialog(msg);
    }

    @Override
    public void dismissLoadingDialog() {
        mActivity.dismissDialog();
    }

    @Override
    public void showToast(String message) {
        mActivity.showToast(message);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
