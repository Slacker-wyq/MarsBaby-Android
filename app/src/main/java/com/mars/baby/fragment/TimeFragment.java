package com.mars.baby.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.activity.OrderActivity;
import com.mars.baby.activity.PushOrderActivity;
import com.mars.baby.activity.RenZhengActivity;
import com.vondear.rxtools.view.RxTitle;
import com.vondear.rxtools.view.RxToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends Fragment {


    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.huli)
    CardView huli;
    @BindView(R.id.kewai)
    CardView kewai;
    @BindView(R.id.zaojiao)
    CardView zaojiao;
    @BindView(R.id.renzheng)
    CardView renzheng;
    Unbinder unbinder;

    public TimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        rxTitle.getLlLeft().setVisibility(View.INVISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.huli, R.id.kewai, R.id.zaojiao, R.id.renzheng})
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), PushOrderActivity.class);
        switch (view.getId()) {
            case R.id.huli:
                intent.putExtra("type", "护理预约");
                startActivity(intent);
                break;
            case R.id.kewai:
                intent.putExtra("type", "课外学习");
                startActivity(intent);
                break;
            case R.id.zaojiao:
                intent.putExtra("type", "早教课堂");
                startActivity(intent);
                break;
            case R.id.renzheng:
                if (StorageConstants.getUser().getData().getUapply() == 0) {
                    Intent intent1 = new Intent(getActivity(), RenZhengActivity.class);
                    startActivity(intent1);
                } else if (StorageConstants.getUser().getData().getUapply() == 1) {
                    RxToast.info("已申请,请耐心等待审核!");
                } else {
                    Intent intent2 = new Intent(getActivity(), OrderActivity.class);
                    startActivity(intent2);
                }

                break;
        }
    }
}
