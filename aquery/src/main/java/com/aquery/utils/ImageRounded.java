package com.aquery.utils;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * Created by ocittwo on 11/11/17.
 */

public class ImageRounded extends ImageViewTarget<Bitmap> {

    public ImageRounded(ImageView view) {
        super(view);
    }

    @Override
    protected void setResource(@Nullable Bitmap resource) {
        RoundedBitmapDrawable rounded =
                RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
        rounded.setCircular(true);
        view.setImageDrawable(rounded);
    }
}
