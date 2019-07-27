package com.jenway.cuvvadogshowapplication.detailScreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.jenway.cuvvadogshowapplication.R;
import com.jenway.cuvvadogshowapplication.model.entity.BaseEntity;

import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {

    LayoutInflater mLayoutInflater;
    private ArrayList<BaseEntity> data;
    private Context mContext;

    public MyPagerAdapter(Context context, ArrayList<BaseEntity> data) {
        this.data = data;
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.breed_image_item_layout, container, false);

        ImageView imageView = itemView.findViewById(R.id.breed_detail_screen_image);
        if (null == data.get(position).getImageBitmap()) {
            Glide.with(mContext).clear(imageView);
            imageView.setImageBitmap(null);
        } else {
            Glide.with(mContext)
                    .load(data.get(position).getImageBitmap())
                    .into(imageView);
        }
        container.addView(itemView);
        return itemView;
    }
}
