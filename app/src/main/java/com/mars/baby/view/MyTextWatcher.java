package com.mars.baby.view;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 自定义text watcher  避免重复写不需要的方法

 */
public class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
