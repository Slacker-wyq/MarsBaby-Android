package com.mars.baby.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mars.baby.R;
import com.mars.baby.model.BabyListModel;
import com.mars.baby.utils.GlideUtils;
import com.mars.baby.utils.OtherUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BabyDetailDialog extends Dialog {
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.baby_name)
    TextView babyName;
    @BindView(R.id.baby_sex)
    TextView babySex;
    @BindView(R.id.baby_age)
    TextView babyAge;
    @BindView(R.id.baby_nick)
    TextView babyNick;
    @BindView(R.id.baby_weight)
    TextView babyWeight;
    @BindView(R.id.baby_height)
    TextView babyHeight;
    @BindView(R.id.register_submit_bt)
    AppCompatButton registerSubmitBt;
    private BabyListModel.DataBean dataBean;
    private Unbinder unbinder;

    public BabyDetailDialog(BabyListModel.DataBean dataBean, @NonNull Context context) {
        super(context, R.style.CommonDialog);
        this.dataBean = dataBean;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_baby);
        unbinder = ButterKnife.bind(this);
        setUpWindow();
        initData();

    }

    private void initData() {
        GlideUtils.loadImageView(dataBean.getBpic(), pic);
        babyName.setText(dataBean.getBname());
        babyNick.setText(dataBean.getBnick());
        babySex.setText(dataBean.getBsex());
        babyHeight.setText(dataBean.getBheight() + "");
        babyWeight.setText(dataBean.getBweight() + "");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar rz = Calendar.getInstance();
        try {
            rz.setTime(format.parse(dataBean.getBage()));
            babyAge.setText(OtherUtils.getDate(rz));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void setUpWindow() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.horizontalMargin = 0;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    @OnClick(R.id.register_submit_bt)
    public void onClick() {
        BabyDetailDialog.this.cancel();
    }
}
