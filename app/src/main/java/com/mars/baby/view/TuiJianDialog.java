package com.mars.baby.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mars.baby.R;
import com.mars.baby.activity.PushOrderActivity;
import com.mars.baby.model.GoodsModel;
import com.vondear.rxtools.view.dialog.RxDialog;

public class TuiJianDialog extends RxDialog {
    private TextView title;
    private TextView content;

    private TextView mTvSure;
    private TextView mTvCancel;
    private GoodsModel.DataBean dataBean;

    public TuiJianDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.initView();
    }

    public TuiJianDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.initView();
    }

    public TuiJianDialog(GoodsModel.DataBean dataBean,Context context) {
        super(context);
        this.dataBean=dataBean;
        this.initView();

    }

    public TuiJianDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        this.initView();
    }

    private void initView() {
        View dialogView = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_tuijian, (ViewGroup) null);

        this.mTvCancel = (TextView) dialogView.findViewById(R.id.tv_cancle);
        this.title = dialogView.findViewById(R.id.tuijian_title);
        this.content = dialogView.findViewById(R.id.tuijian_content);

        title.setText(dataBean.getGname());
        content.setText(dataBean.getGcomment());

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        this.setContentView(dialogView);
    }


}
