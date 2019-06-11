package com.mars.baby.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.activity.FocusActivity;
import com.mars.baby.activity.LoginActivity;
import com.mars.baby.activity.MineDetailActivity;
import com.mars.baby.activity.MineGetOrderActivity;
import com.mars.baby.activity.MineOrderActivity;
import com.mars.baby.activity.UserPushActivity;
import com.mars.baby.model.AttentModel;
import com.mars.baby.model.GoodsModel;
import com.mars.baby.model.OrderModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.utils.GlideUtils;
import com.mars.baby.view.SketchCardView;
import com.mars.baby.view.TuiJianDialog;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {


    @BindView(R.id.mine_quit)
    TextView mineQuit;
    @BindView(R.id.mine_pic)
    ImageView minePic;
    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mine_click)
    LinearLayout mineClick;
    @BindView(R.id.mine_order)
    SketchCardView mineOrder;
    @BindView(R.id.mine_detail)
    SketchCardView mineDetail;
    @BindView(R.id.mine_opinion)
    SketchCardView mineOpinion;
    Unbinder unbinder;
    @BindView(R.id.mine_getorder)
    SketchCardView mineGetorder;
    @BindView(R.id.mine_focus)
    SketchCardView mineFocus;
    @BindView(R.id.mine_tuijian)
    SketchCardView mineTuijian;
    @BindView(R.id.mine_push)
    SketchCardView minePush;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        mineFocus.setSketchTv("我的关注");
        mineTuijian.setSketchTv("商品推荐");
        mineOrder.setSketchTv("我的订单");
        mineGetorder.setSketchTv("我的接单");
        mineDetail.setSketchTv("我的信息");
        mineOpinion.setSketchTv("意见反馈");
        minePush.setSketchTv("我的发布");
        mineGetorder.setVisibility(StorageConstants.getUser().getData().getUtype() == 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mineName.setText(StorageConstants.getUser().getData().getUnickname());
        GlideUtils.loadImageView(StorageConstants.getUser().getData().getUpic(), minePic);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mine_quit, R.id.mine_order, R.id.mine_detail, R.id.mine_opinion, R.id.mine_getorder, R.id.mine_focus, R.id.mine_tuijian, R.id.mine_push})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_quit:
                StorageConstants.setUser(null);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.mine_order:
                startActivity(new Intent(getActivity(), MineOrderActivity.class));

                break;
            case R.id.mine_detail:
                Intent intent1 = new Intent(getActivity(), MineDetailActivity.class);
                startActivity(intent1);
                break;
            case R.id.mine_opinion:
                final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(getActivity());
                rxDialogEditSureCancel.setTitle("意见反馈");
                rxDialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogEditSureCancel.cancel();
                        RxToast.success("反馈成功");
                    }
                });
                rxDialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogEditSureCancel.cancel();
                    }
                });
                rxDialogEditSureCancel.show();
                break;
            case R.id.mine_getorder:
                startActivity(new Intent(getActivity(), MineGetOrderActivity.class));
                break;
            case R.id.mine_focus:
                startActivity(new Intent(getActivity(), FocusActivity.class));
                break;
            case R.id.mine_tuijian:
                if (StorageConstants.BID > 0) {
                    RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "getTuijianOrder");
                    requestParams.addQueryStringParameter("bid", StorageConstants.BID + "");
                    x.http().get(requestParams, new CustomCallback<GoodsModel>() {
                        @Override
                        public void doSuccess(GoodsModel goodsModel) {
                            if (goodsModel.getData().size() > 0) {
                                GoodsModel.DataBean dataBean = goodsModel.getData().get(0);
                                TuiJianDialog tuiJianDialog = new TuiJianDialog(dataBean, getActivity());
                                tuiJianDialog.show();
                            } else {
                                RxToast.info("我商品推荐");
                            }
                        }

                        @Override
                        public void doError(int code, String msg) {

                        }
                    });


                } else {
                    RxToast.info("无宝宝信息");
                }


                break;
            case R.id.mine_push:
                Intent intent2 = new Intent(getActivity(), UserPushActivity.class);
                intent2.putExtra("uid", StorageConstants.getUser().getData().getUid());
                startActivity(intent2);
                break;
        }
    }
}
