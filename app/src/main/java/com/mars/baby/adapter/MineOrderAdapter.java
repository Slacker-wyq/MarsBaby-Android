package com.mars.baby.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.OrderModel;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.model.UserModel;
import com.mars.baby.utils.CustomCallback;
import com.ta.utdid2.android.utils.StringUtils;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class MineOrderAdapter extends BaseQuickAdapter<OrderModel.DataBean, BaseViewHolder> {
    private Context context;
    public MineOrderAdapter(Context context,int layoutResId, @Nullable List<OrderModel.DataBean> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderModel.DataBean item) {
        if (item.getOstatus() == 0) {
            helper.setText(R.id.o_name, "等待接单");
        } else if (item.getOstatus() == 1) {
            helper.setText(R.id.o_name,"接单人:"+ item.getOtuser().getUnickname());
        }
        if (item.getOstatus()==2){
            helper.setGone(R.id.o_get, StringUtils.isEmpty(item.getOcomment()));
        }else {
            helper.setGone(R.id.o_get, false);
        }
        helper.setOnClickListener(R.id.o_get, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(context);
                rxDialogEditSureCancel.setTitle("输入评论");
                rxDialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogEditSureCancel.cancel();
                        RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "AddOrderComment");
                        requestParams.addQueryStringParameter("oid", item.getOid()+ "");
                        requestParams.addQueryStringParameter("comment", rxDialogEditSureCancel.getEditText().getText().toString());
                        x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                            @Override
                            public void doSuccess(SimpleModel simpleModel) {
                                if (simpleModel.getDataCode() == 100) {
                                    RxToast.success("评论成功");
                                }else {
                                    RxToast.error("评论失败");
                                }
                            }

                            @Override
                            public void doError(int code, String msg) {

                            }
                        });
                    }
                });
                rxDialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogEditSureCancel.cancel();
                    }
                });
                rxDialogEditSureCancel.show();
            }
        });
        helper.setText(R.id.o_type, item.getOtype() + "-" + item.getOchildtype());
        helper.setText(R.id.o_time, item.getOtime());
        helper.setText(R.id.o_address, item.getOaddress());
    }
}
