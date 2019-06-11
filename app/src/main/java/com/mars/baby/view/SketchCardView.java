package com.mars.baby.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.mars.baby.R;


public class SketchCardView extends CardView {
    private AppCompatImageView sketchImg;
    private AppCompatTextView sketchTv;

    public SketchCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_sketch, this, true);
        sketchImg = findViewById(R.id.sketch_img);
        sketchTv = findViewById(R.id.sketch_tv);

        setForeground(ContextCompat.getDrawable(context,R.drawable.card_foreground));
        setCardElevation(2);
    }

    public AppCompatImageView getSketchImg() {
        return sketchImg;
    }

    public void setSketchImg(@DrawableRes int resId) {
        this.sketchImg.setImageResource(resId);
    }

    public AppCompatTextView getSketchTv() {
        return sketchTv;
    }

    public void setSketchTv(String text) {
        this.sketchTv.setText(text);
    }

    public void setSketchTv(@StringRes int resId) {
        this.sketchTv.setText(resId);
    }

    public void setImgTv(@DrawableRes int img,String txt){
        this.sketchImg.setImageResource(img);
        this.sketchTv.setText(txt);
    }

}
