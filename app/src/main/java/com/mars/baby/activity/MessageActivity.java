package com.mars.baby.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.MessageModel;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.ta.utdid2.android.utils.StringUtils;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;
import com.vondear.rxtools.view.RxToast;

import org.reactivestreams.Subscription;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MessageActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    @BindView(R.id.main_sw)
    SwipeRefreshLayout mainSw;
    @BindView(R.id.input_text)
    EditText inputText;
    @BindView(R.id.send)
    Button send;
    private int id;
    private MessageAdapter messageAdapter;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
        rxTitle.setTitle(getIntent().getStringExtra("name"));
        id = getIntent().getIntExtra("uid", 0);
        mainRv.setLayoutManager(new LinearLayoutManager(mContext));
        messageAdapter = new MessageAdapter(R.layout.msg_item, null);
        mainRv.setAdapter(messageAdapter);
        mainSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainSw.setRefreshing(false);
                getMessage();
            }
        });


        Startlun();
    }


    private void Startlun() {

        disposable = Observable.interval(1, 1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.d("SUMN", aLong + "次数");
                getMessage();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void getMessage() {
        RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "getMessage");
        requestParams.addQueryStringParameter("fid", StorageConstants.getUser().getData().getUid() + "");
        requestParams.addQueryStringParameter("tid", id + "");
        x.http().get(requestParams, new CustomCallback<MessageModel>() {
            @Override
            public void doSuccess(MessageModel messageModel) {
                if (messageModel.getData().size() > 0) {
                    messageAdapter.setNewData(messageModel.getData());
                    mainRv.smoothScrollToPosition(messageModel.getData().size() - 1);
                } else {
                    messageAdapter.setNewData(null);
                    EmptyData();
                }

            }

            @Override
            public void doError(int code, String msg) {

            }
        });

    }

    private void EmptyData() {
        View notDataView = getLayoutInflater().inflate(R.layout.layout_empty_data, (ViewGroup) mainRv.getParent(), false);
        messageAdapter.setEmptyView(notDataView);
    }

    @OnClick(R.id.send)
    public void onClick() {
        String content = inputText.getText().toString();
        if (!StringUtils.isEmpty(content)) {
            RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "AddMessage");
            requestParams.addQueryStringParameter("fid", StorageConstants.getUser().getData().getUid() + "");
            requestParams.addQueryStringParameter("tid", id + "");
            requestParams.addQueryStringParameter("message", content);
            x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                @Override
                public void doSuccess(SimpleModel simpleModel) {
                    inputText.setText("");
                }

                @Override
                public void doError(int code, String msg) {

                }
            });

        } else {
            RxToast.info("无法发送空内容");
        }


    }

    private class MessageAdapter extends BaseQuickAdapter<MessageModel.DataBean, BaseViewHolder> {
        public MessageAdapter(int layoutResId, @Nullable List<MessageModel.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MessageModel.DataBean item) {

            if (item.getType() == MessageModel.TYPE_SENT) {
                //收到消息后，则显示左边的消息布局，隐藏右边的消息布局
                helper.setGone(R.id.left_layout, true);
                helper.setGone(R.id.right_layout, false);
                helper.setText(R.id.left_msg, item.getMcontent());
            } else if (item.getType() == MessageModel.TYPE_RECEIVED) {
                helper.setGone(R.id.left_layout, false);
                helper.setGone(R.id.right_layout, true);
                helper.setText(R.id.right_msg, item.getMcontent());
            }

        }
    }
}
