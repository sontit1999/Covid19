package com.example.covid19.base;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingUntil {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide
                .with(view.getContext())
                .load(imageUrl)
                .into(view);

    }
}
