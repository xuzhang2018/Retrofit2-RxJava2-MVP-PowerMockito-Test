package com.jenway.cuvvadogshowapplication.nestedList;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jenway.cuvvadogshowapplication.MyApplication;
import com.jenway.cuvvadogshowapplication.R;
import com.jenway.cuvvadogshowapplication.base.BaseFragment;
import com.jenway.cuvvadogshowapplication.dagger.DaggerNestedListInfoComponent;
import com.jenway.cuvvadogshowapplication.dagger.NestedListInfoModule;
import com.jenway.cuvvadogshowapplication.detailScreen.DetailScreenFragment;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;
import com.jenway.cuvvadogshowapplication.model.entity.BreedInfo;
import com.jenway.cuvvadogshowapplication.model.entity.SubBreedInfo;
import com.jenway.cuvvadogshowapplication.nestedList.adapter.NestedListAdapter;
import com.jenway.cuvvadogshowapplication.nestedList.mvp.NestedListInfoContract;
import com.jenway.cuvvadogshowapplication.nestedList.mvp.NestedListInfoPresenterImp;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NestedListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NestedListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NestedListFragment extends BaseFragment<NestedListInfoPresenterImp> implements NestedListInfoContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;


    private FloatingActionButton floatingActionButton;

    private RecyclerView mRecyclerView;
    private int lastPosition = 0;//RecyclerView last item positon
    private int lastOffset = 0;//RecyclerView offset
    private GridLayoutManager manager;
    private NestedListInfoPresenterImp imp;//use to save the data when take from stack
    private NestedListAdapter mNestedListAdapter;

    private Button testToastButton;
    private Button testDialogButton;

    public NestedListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NestedListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NestedListFragment newInstance(String param1, String param2) {
        NestedListFragment fragment = new NestedListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //only for test
        if (MyApplication.DEBUG) {
            testToastButton = view.findViewById(R.id.test_toast_button);
            testToastButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showToast("test toast");
                }
            });
            testDialogButton = view.findViewById(R.id.test_dialog_button);
            testDialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag().toString().equals("show")) {
                        view.setTag("hide");
                        mActivity.showDialog("test dialog");
                    } else {
                        view.setTag("show");
                        mActivity.dismissDialog();
                    }
                }
            });
        }


        mRecyclerView = view.findViewById(R.id.nested_rv);
        floatingActionButton = view.findViewById(R.id.breed_detail_screen_fab);

        //to go the detail screen fragment to show the images
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<BaseEntity> data = mPresenter.getBreedInfoSelectList();
                if (data.size() > 0) {
                    DetailScreenFragment detailScreenFragment = DetailScreenFragment.newInstance(data, "");
                    addFragment(detailScreenFragment);
                } else {
                    showToast("Please select at least one item.");
                }
            }
        });

        if (savedInstanceState != null && savedInstanceState.getStringArrayList("data") != null) {//to get status for mobile Portrait or Landscape
            setRecyclerView(savedInstanceState.getParcelableArrayList("data"));
            mPresenter.setBreedInfoFullList(savedInstanceState.getParcelableArrayList("data"));
            mPresenter.setBreedLoadingIndext(savedInstanceState.getInt("LoadingIndex"));
            mPresenter.setLastShowItemIndex(savedInstanceState.getInt("LastShowItemIndex"));
            lastPosition = savedInstanceState.getInt("LAST_POSITION");
            lastOffset = savedInstanceState.getInt("LAST_OFFSET");
            setTheRecyclerViewPosition();
        } else {
            if (null != imp) {// when the fragment take back from stack then load the recorded data bot refresh
                mPresenter = imp;
                setRecyclerView(mPresenter.getData());
                setTheRecyclerViewPosition();
            } else {//init the data
                mPresenter.loadBreedListData();
            }
        }

    }

    //back to recorded position
    @Override
    public void setTheRecyclerViewPosition() {
        if (null != manager) {
            manager.scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    //to save the status for mobile Portrait or Landscape
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("data", mPresenter.getData());
        outState.putInt("LoadingIndex", mPresenter.getLoadingIndexData());
        outState.putInt("LastShowItemIndex", mPresenter.getLastShowItemIndex());
        outState.putInt("LAST_POSITION", lastPosition);
        outState.putInt("LAST_OFFSET", lastOffset);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nested_list;
    }

    @Override
    protected void initInjector() {
        DaggerNestedListInfoComponent
                .builder()
                .appComponent(getAppComponent()).
                nestedListInfoModule(new NestedListInfoModule(this))
                .build()
                .inject(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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

    //to save status for fragment when take back from stack
    @Override
    public void onStop() {
        super.onStop();
        imp = mPresenter;
        if (null != manager) {
            View topView = manager.getChildAt(0);
            lastOffset = topView.getTop();
            lastPosition = manager.getPosition(topView);
        }
        //Prevent memory leaks
        mPresenter.unSubscribe();
    }


    @Override
    public void onResume() {
        super.onResume();
        setTheRecyclerViewPosition();
    }

    @Override
    public void setRecyclerView(final List<BaseEntity> breedInfoList) {

        mNestedListAdapter = new NestedListAdapter(breedInfoList, mActivity);
        manager = new GridLayoutManager(mActivity, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (breedInfoList.get(position) instanceof BreedInfo) {
                    return manager.getSpanCount();
                } else if (breedInfoList.get(position) instanceof SubBreedInfo) {
                    return 1;
                }
                return 0;
            }
        });

        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(mNestedListAdapter);
        mNestedListAdapter.setOnItemClickListener(new NestedListAdapter.OnItemClickListener() {//for show and hide sub breed
            @Override
            public void onItemClick(View view, int position, String status) {
                if (status.equals("hide")) {
                    mPresenter.hideTheSubBreed(position);
                } else {
                    mPresenter.showTheSubBeed(position);

                }
            }
        });

        mNestedListAdapter.setOnItemSelectListener(new NestedListAdapter.OnItemSelectListener() {// for select the item
            @Override
            public void onItemSelect(int position, boolean isSelect) {
                if (isSelect) {
                    mPresenter.addSelectItem(position);
                } else {
                    mPresenter.removeSelectItem(position);
                }
            }
        });

        //load the data if the last Visible ItemPosition reach to the not loaded image item
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //当RecyclerView滑动时触发
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //get visibly item number
                int lastVisibleItemPosition =
                        manager.findLastVisibleItemPosition();
                //if the item is the last one then load more breed and its image
//                if (lastVisibleItemPosition + 1 == mNestedListAdapter.getItemCount()) {
//                    mPresenter.loadMoreBreeds();
//                }
                if (lastVisibleItemPosition + 1 > mPresenter.getLastShowItemIndex()) {
                    mPresenter.loadMoreBreeds();
                }

            }
        });


    }

    @Override
    public void updateRecyclerViewItem(int position, BaseEntity item) {
        if (null != mNestedListAdapter) {
            mNestedListAdapter.setItem(position, item);
            //avoid to updating a view while recyclerview is calculating layout measurements.
            mRecyclerView.post(new Runnable() {
                public void run() {
                    mNestedListAdapter.notifyItemChanged(position);
                }
            });


        }
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
