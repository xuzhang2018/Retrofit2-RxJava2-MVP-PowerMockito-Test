package com.jenway.cuvvadogshowapplication.nestedList.mvp;

import android.graphics.Bitmap;

import com.jenway.cuvvadogshowapplication.api.RetrofitService;
import com.jenway.cuvvadogshowapplication.model.Config;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;
import com.jenway.cuvvadogshowapplication.model.entity.BreedInfo;
import com.jenway.cuvvadogshowapplication.model.entity.SubBreedInfo;
import com.jenway.cuvvadogshowapplication.network.NetBean;
import com.jenway.cuvvadogshowapplication.network.NetData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * by Xu
 * Description: handle the recycler view
 */

public class NestedListInfoPresenterImp implements NestedListInfoContract.Presenter {

    private final String TAG = this.getClass().getSimpleName();
    private NestedListInfoContract.View mView;
    private ArrayList<BaseEntity> breedInfoFullList = new ArrayList<>();
    private ArrayList<BaseEntity> breedInfoSelectList = new ArrayList<>();
    private int breedLoadedIndex = 0; //use to identity the loaded breed index, this is for loading the showing breed
    private int lastShowItemIndex = 0; //use to identity the showing item (breed and subbreed) with image, this is for loading more image when scrolling

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void setBreedInfoFullList(ArrayList<BaseEntity> items) {
        breedInfoFullList = items;
    }

    public void setBreedLoadingIndext(int index) {
        breedLoadedIndex = index;
    }

    //Load the full structure of the list of breed and sub breed
    @Override
    public void loadBreedListData() {
        mView.showLoadingDialog("Loading the Breed List");
        breedInfoFullList = new ArrayList<>();
        RetrofitService.getAll().subscribe(new Observer<NetData>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(NetData data) {
                if (disposable.isDisposed()) {

                } else {
                    breedInfoFullList = NetBean.CovertTheNetDataToBreedList(data);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onLoadInfoFail(e.getMessage());
            }

            @Override
            public void onComplete() {
                mView.setRecyclerView(breedInfoFullList);
                mView.onLoadInfoSuccess();
                //init the images
                loadMoreBreeds();

            }
        });
    }

    //load the image of the breed
    @Override
    public void loadMoreBreeds() {
        ArrayList<Integer> breedShowIndexList = updateShowList();
        mView.showLoadingDialog("Loading the Breed Images");
        List<BaseEntity> requireImageList = getBreedRequireImageList(breedShowIndexList);
        //
        if (requireImageList.size() > 0) {
            lastShowItemIndex = requireImageList.get(requireImageList.size() - 1).getIndex();
            RetrofitService.getTheBreedImages(requireImageList).subscribe(new SingleObserver<List<Bitmap>>() {
                private Disposable disposable;

                @Override
                public void onSubscribe(Disposable d) {
                    disposable = d;
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(List<Bitmap> bitmaps) {
                    AddImageToList(requireImageList, bitmaps);
                    mView.onLoadInfoSuccess();
                }

                @Override
                public void onError(Throwable e) {
                    mView.onLoadInfoFail(e.getMessage());
                }
            });
        } else {
            //if the images already exist just hide the dialog
            mView.onLoadInfoSuccess();
        }
    }

    //get require list for retrofit to use
    private List<BaseEntity> getBreedRequireImageList(List<Integer> breedShowIndexList) {
        List<BaseEntity> requireImageList = new ArrayList<>();
        //get require list for retrofit to use
        for (int i = 0; i < breedShowIndexList.size(); i++) {
            if (breedInfoFullList.get(breedShowIndexList.get(i)) instanceof BreedInfo && null == breedInfoFullList.get(breedShowIndexList.get(i)).getImageBitmap()) {
                requireImageList.add(breedInfoFullList.get(breedShowIndexList.get(i)));
            }
        }
        return requireImageList;
    }

    //load the sub breed image
    @Override
    public void updateSubBreedstatus(int breedPosition) {
        mView.showLoadingDialog("Loading the sub Breed Info");
        List<Integer> subBreedIndexList = getSubBreedIndexList(breedPosition);
        if (subBreedIndexList.size() == 0) {
            mView.showToast("No SubBreed for " + breedInfoFullList.get(breedPosition).getName());
            mView.onLoadInfoSuccess();
        } else {
            List<BaseEntity> requireImageList = getSubBreedRequireImageList(subBreedIndexList);
            if (requireImageList.size() > 0) {
                RetrofitService.getTheSubBreedImages(requireImageList).subscribe(new SingleObserver<List<Bitmap>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Bitmap> bitmaps) {
                        AddImageToList(requireImageList, bitmaps);
                        mView.onLoadInfoSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onLoadInfoFail(e.getMessage());
                    }
                });
            } else {
                for (int i = 0; i < subBreedIndexList.size(); i++) {
                    mView.updateRecyclerViewItem(subBreedIndexList.get(i), breedInfoFullList.get(subBreedIndexList.get(i)));
                }
                mView.onLoadInfoSuccess();
            }

        }
    }

    private List<Integer> getSubBreedIndexList(int breedPosition) {
        return ((BreedInfo) breedInfoFullList.get(breedPosition)).getSubBreedIndexList();
    }

    private List<BaseEntity> getSubBreedRequireImageList(List<Integer> subBreedIndexList) {
        List<BaseEntity> requireImageList = new ArrayList<>();
        for (int i = 0; i < subBreedIndexList.size(); i++) {
            SubBreedInfo subBreedInfo = (SubBreedInfo) breedInfoFullList.get(subBreedIndexList.get(i));
            subBreedInfo.setIsShown(true);
            if (null == subBreedInfo.getImageBitmap()) {
                requireImageList.add(subBreedInfo);
            }
        }
        return requireImageList;
    }

    @Override
    public NestedListInfoContract.Presenter attachView(NestedListInfoContract.View view) {
        mView = view;
        return this;
    }

    //Prevent memory leaks
    @Override
    public void unSubscribe() {
        compositeDisposable.clear();
    }


    @Override
    public void hideTheSubBreed(int position) {
        mView.showLoadingDialog("Hide the sub Breed Info");
        List<Integer> subBreedIndexList = getSubBreedIndexList(position);
        if (subBreedIndexList.size() == 0) {
            mView.showToast("No SubBreed for " + breedInfoFullList.get(position).getName());
        } else {
            for (int i = 0; i < subBreedIndexList.size(); i++) {
                SubBreedInfo subBreedInfo = (SubBreedInfo) breedInfoFullList.get(subBreedIndexList.get(i));
                subBreedInfo.setIsShown(false);
                mView.updateRecyclerViewItem(subBreedInfo.getIndex(), subBreedInfo);
            }
        }
        mView.onLoadInfoSuccess();
    }

    @Override
    public void showTheSubBeed(int position) {
        updateSubBreedstatus(position);
    }

    @Override
    public void addSelectItem(int position) {
        breedInfoFullList.get(position).setSelectStatus(true);
        breedInfoSelectList.add(breedInfoFullList.get(position));
    }

    @Override
    public void removeSelectItem(int position) {
        breedInfoFullList.get(position).setSelectStatus(false);
        breedInfoSelectList.remove(breedInfoFullList.get(position));
    }

    public ArrayList<BaseEntity> getData() {
        return breedInfoFullList;
    }

    public int getLoadingIndexData() {
        return breedLoadedIndex;
    }

    public ArrayList<BaseEntity> getBreedInfoSelectList() {
        return breedInfoSelectList;
    }

    public int getLastShowItemIndex() {
        return lastShowItemIndex;
    }

    public void setLastShowItemIndex(int lastShowItemIndex) {
        this.lastShowItemIndex = lastShowItemIndex;
    }

    //get the breed list will have show on in the recycler view
    private ArrayList<Integer> updateShowList() {
        ArrayList<Integer> breedShowIndexList = new ArrayList<>();
        int count = 0;
        for (int i = breedLoadedIndex; i < breedInfoFullList.size(); i++) {
            if (count == Config.maxloadingItemCount) {
                if (i <= breedInfoFullList.size() - 1) {
                    breedLoadedIndex = i;
                }
                break;
            }
            breedShowIndexList.add(i);
            if (breedInfoFullList.get(i) instanceof BreedInfo) {
                if (i + 1 <= breedInfoFullList.size() - 1) {
                    breedLoadedIndex = i + 1;
                }
                count++;
            }
        }
        return breedShowIndexList;
    }

    //add the image to the list
    private void AddImageToList(List<BaseEntity> requireImageList, List<Bitmap> bitmaps) {
        for (int i = 0; i < requireImageList.size(); i++) {
            requireImageList.get(i).setImageBitmap(bitmaps.get(i));
            mView.updateRecyclerViewItem(requireImageList.get(i).getIndex(), requireImageList.get(i));
        }
    }

}