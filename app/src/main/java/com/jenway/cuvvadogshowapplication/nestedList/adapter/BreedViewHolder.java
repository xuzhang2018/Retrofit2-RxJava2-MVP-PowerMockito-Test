package com.jenway.cuvvadogshowapplication.nestedList.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jenway.cuvvadogshowapplication.R;
import com.jenway.cuvvadogshowapplication.model.entity.BreedInfo;


/**
 * by Xu
 * Description: to show the breed info
 */

public class BreedViewHolder extends RecyclerView.ViewHolder {
    CheckBox breedSelect;
    TextView breedName;
    ImageView breedImage, showSubBreed;

    private Context context;

    public BreedViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        breedSelect = itemView.findViewById(R.id.breed_item_cb);
        breedName = itemView.findViewById(R.id.breed_item_tv);
        breedImage = itemView.findViewById(R.id.breed_item_iv);
        showSubBreed = itemView.findViewById(R.id.breed_item_sub_breed);
    }

    public void setData(final BreedInfo breedInfo) {

        //fix some time the check box is not changing in time
        breedSelect.post(new Runnable() {
            @Override
            public void run() {
                breedSelect.setChecked(breedInfo.getSelecgtStatus());
            }
        });
        //breedSelect.setSelected(breedInfo.getSelecgtStatus());
        breedName.setText(breedInfo.getName());
        if (breedInfo.getSubBreedIndexList().size() == 0) {
            showSubBreed.setVisibility(View.INVISIBLE);
        } else {
            showSubBreed.setVisibility(View.VISIBLE);
        }

        if (null == breedInfo.getImageBitmap()) {
            Glide.with(context).clear(breedImage);
            breedImage.setImageBitmap(null);
        } else {
            Glide.with(context)
                    .load(breedInfo.getImageBitmap())
                    .into(breedImage);
        }
    }
}
