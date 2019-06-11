package com.mars.baby.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.model.OrderModel;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.ta.utdid2.android.utils.StringUtils;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSure;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class GetOrderAdapter extends BaseQuickAdapter<OrderModel.DataBean,BaseViewHolder> {
    private Context context;
    public GetOrderAdapter(Context context,int layoutResId, @Nullable List<OrderModel.DataBean> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderModel.DataBean item) {
        helper.setText(R.id.o_name,"出单人:"+ item.getOuser().getUnickname());

        if (item.getOstatus()==2){
            helper.setText(R.id.o_get,"查看评论");
            helper.setOnClickListener(R.id.o_get, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isEmpty(item.getOcomment())){
                        RxToast.warning("无评论");
                    }else {
                        final RxDialogSure rxDialogSure=new RxDialogSure(context);
                        rxDialogSure.setTitle("评论");
                        rxDialogSure.setContent(item.getOcomment());
                        rxDialogSure.show();
                        rxDialogSure.setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rxDialogSure.cancel();
                            }
                        });
                    }

                }
            });
        }else if (item.getOstatus()==1){

            helper.setText(R.id.o_get,"开始服务");
            helper.addOnClickListener(R.id.o_get);
        }else {

            helper.setText(R.id.o_get,"确认完成");
            helper.addOnClickListener(R.id.o_get);
        }

        helper.setText(R.id.o_type, item.getOtype() + "-" + item.getOchildtype());
        helper.setText(R.id.o_time, item.getOtime());
        helper.setText(R.id.o_address, item.getOaddress());
    }
}
