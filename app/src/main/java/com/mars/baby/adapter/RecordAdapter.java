package com.mars.baby.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mars.baby.R;
import com.mars.baby.model.RecordListModel;
import com.mars.baby.utils.GlideUtils;
import com.mars.baby.utils.OtherUtils;
import com.ta.utdid2.android.utils.StringUtils;

import java.util.List;

public class RecordAdapter extends BaseQuickAdapter<RecordListModel.DataBean, BaseViewHolder> {
    public RecordAdapter(int layoutResId, @Nullable List<RecordListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordListModel.DataBean item) {
        helper.setText(R.id.record_time, OtherUtils.getTime(item.getGmtCreate()));
        helper.setText(R.id.record_content,item.getRcontent());
        if (StringUtils.isEmpty(item.getRpic())){
            helper.setGone(R.id.record_pic,false);
        }else {
            helper.setGone(R.id.record_pic,true);
            GlideUtils.loadImageView(item.getRpic(), (ImageView) helper.getView(R.id.record_pic));

        }

    }
}
