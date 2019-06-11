package com.mars.baby.utils;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.mars.baby.R;
import com.vondear.rxtools.RxTool;



public class GlideUtils {
    //默认加载
    public static void loadImageView(String path, ImageView mImageView) {

        Glide.with(RxTool.getContext()).load(path).placeholder(R.drawable.ic_empty_zhihu).into(mImageView);
    }

}
