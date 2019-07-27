package com.jenway.cuvvadogshowapplication.nestedList.mvp;

import com.jenway.cuvvadogshowapplication.base.BasePresenter;
import com.jenway.cuvvadogshowapplication.base.BaseView;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;

import java.util.List;

public class NestedListInfoContract {
    public interface View extends BaseView {
        void setRecyclerView(List<BaseEntity> breedInfoList);

        void updateRecyclerViewItem(int position, BaseEntity item);

        void setTheRecyclerViewPosition();

        void onLoadInfoSuccess();

        void onLoadInfoFail(String errorMsg);
    }

    public interface Presenter extends BasePresenter<Presenter, View> {
        void loadBreedListData();

        void loadMoreBreeds();

        void updateSubBreedstatus(int breedPosition);

        void hideTheSubBreed(int position);

        void showTheSubBeed(int position);

        void addSelectItem(int position);

        void removeSelectItem(int position);

    }
}
