package com.jenway.cuvvadogshowapplication.nestedList.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jenway.cuvvadogshowapplication.R;
import com.jenway.cuvvadogshowapplication.model.entity.SubBreedInfo;


/**
 * by Xu
 * Description: to show the sub breed info
 */

public class SubBreedViewHolder extends RecyclerView.ViewHolder {

    CheckBox subBreedSelect;
    TextView subBreedName;
    ImageView subBreedImage;
    RelativeLayout subbreedPanel;

    private Context context;

    public SubBreedViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        subBreedSelect = itemView.findViewById(R.id.sub_breed_item_cb);
        subBreedName = itemView.findViewById(R.id.sub_breed_item_tv);
        subBreedImage = itemView.findViewById(R.id.sub_breed_item_iv);
        subbreedPanel = itemView.findViewById(R.id.sub_breed_panel);
    }

    public void setData(SubBreedInfo breedInfo) {
        if (breedInfo.getIsShown()) {
            setVisibility(true);
            //subBreedSelect.setSelected(breedInfo.getSelecgtStatus());
            //fix some time the check box is not changing in time
            subBreedSelect.post(new Runnable() {
                @Override
                public void run() {
                    subBreedSelect.setChecked(breedInfo.getSelecgtStatus());
                }
            });
            subBreedName.setText(breedInfo.getName());
            if (null == breedInfo.getImageBitmap()) {
                Glide.with(context).clear(subBreedImage);
                subBreedImage.setImageBitmap(null);
            } else {
                Glide.with(context)
                        .load(breedInfo.getImageBitmap())
                        .into(subBreedImage);
            }
        } else {
            setVisibility(false);
        }
    }

    public void setVisibility(boolean visible) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (visible) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        } else {
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }
}
