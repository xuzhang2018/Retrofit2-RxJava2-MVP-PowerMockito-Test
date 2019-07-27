package com.jenway.cuvvadogshowapplication.nestedList.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.jenway.cuvvadogshowapplication.MyApplication;
import com.jenway.cuvvadogshowapplication.R;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;
import com.jenway.cuvvadogshowapplication.model.entity.BreedInfo;
import com.jenway.cuvvadogshowapplication.model.entity.SubBreedInfo;

import java.util.List;

public class NestedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BREED = 1;
    private static final int TYPE_SUB_BREED = 2;
    private Context mContext;
    private List<BaseEntity> mDataList;
    private LayoutInflater mInflater;
    private NestedListAdapter.OnItemClickListener mOnItemClickListener;  //to handle the hide/show sub breed
    private NestedListAdapter.OnItemSelectListener onItemSelectListener; //to handle the select event

    public NestedListAdapter(List<BaseEntity> dataList, Context context) {
        mContext = context;
        mDataList = dataList;
        mInflater = LayoutInflater.from(MyApplication.getInstance().getApplicationContext());
    }

    public void setItem(int position, BaseEntity item) {
        mDataList.set(position, item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BREED) {
            return new BreedViewHolder(mInflater.inflate(R.layout.breed_item_layout, parent, false), mContext);
        } else {
            return new SubBreedViewHolder(mInflater.inflate(R.layout.sub_breed_item_layout, parent, false), mContext);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BreedViewHolder) {
            ((BreedViewHolder) holder).setData((BreedInfo) mDataList.get(position));
            if (mOnItemClickListener != null) {
                ((BreedViewHolder) holder).showSubBreed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView imageView = (ImageView) v;
                        if (imageView.getTag().equals("hide")) {
                            imageView.setTag("show");
                            imageView.setImageResource(R.mipmap.baseline_expand_less_white_24);

                        } else {
                            imageView.setTag("hide");
                            imageView.setImageResource(R.mipmap.baseline_expand_more_white_18);
                        }
                        mOnItemClickListener.onItemClick(v, holder.getAdapterPosition(), imageView.getTag().toString());
                    }
                });
            }

            if (onItemSelectListener != null) {
                ((BreedViewHolder) holder).breedSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        onItemSelectListener.onItemSelect(holder.getAdapterPosition(), b);
                    }
                });
            }
        } else if (holder instanceof SubBreedViewHolder) {
            ((SubBreedViewHolder) holder).setData((SubBreedInfo) mDataList.get(position));

            if (onItemSelectListener != null) {
                ((SubBreedViewHolder) holder).subBreedSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        onItemSelectListener.onItemSelect(holder.getAdapterPosition(), b);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position) instanceof BreedInfo) {
            return TYPE_BREED;
        } else if (mDataList.get(position) instanceof SubBreedInfo) {
            return TYPE_SUB_BREED;
        }
        return TYPE_BREED;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemSelectListener(OnItemSelectListener mOnItemSelectListener) {
        this.onItemSelectListener = mOnItemSelectListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String status);
    }

    public interface OnItemSelectListener {
        void onItemSelect(int position, boolean isSelect);
    }


}
