package com.mars.baby.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.activity.AddBabyActivity;
import com.mars.baby.activity.AddRecordActivity;
import com.mars.baby.adapter.RecordAdapter;
import com.mars.baby.dialog.BabyDetailDialog;
import com.mars.baby.model.BabyListModel;
import com.mars.baby.model.RecordListModel;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.utils.GlideUtils;
import com.mars.baby.utils.OtherUtils;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.baby_item)
    Spinner babyItem;

    @BindView(R.id.home_baby_pic)
    ImageView homeBabyPic;
    @BindView(R.id.baby_name)
    TextView babyName;
    @BindView(R.id.baby_age)
    TextView babyAge;
    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    @BindView(R.id.main_sw)
    SwipeRefreshLayout mainSw;
    Unbinder unbinder;
    private List<BabyListModel.DataBean> dataList;
    private BabyListModel.DataBean baby;
    private RecordAdapter recordAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();

    }

    private void initData() {
        mainRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        recordAdapter=new RecordAdapter(R.layout.item_record,null);
        mainRv.setAdapter(recordAdapter);
        EmptyData();
        mainSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (baby!=null){
                    getRecord();
                }else {
                    mainSw.setRefreshing(false);
                }

            }
        });
        RequestParams params = new RequestParams(MyApplication.BASE_URL + "getBaby");
        params.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
        x.http().get(params, new CustomCallback<BabyListModel>() {
            @Override
            public void doSuccess(BabyListModel babyListModel) {
                dataList = babyListModel.getData();
                if (dataList.size() > 0) {
                    baby = dataList.get(0);
                    StorageConstants.BID=baby.getBid();
                    List<String> babystrings = new ArrayList<>();
                    for (BabyListModel.DataBean d : babyListModel.getData()) {
                        babystrings.add(d.getBname());
                    }
                    String[] strings = new String[babystrings.size()];
                    babystrings.toArray(strings);
                    ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strings);
                    //设置下拉列表的风格
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    babyItem.setAdapter(adapter);//将adapter 添加到spinner中
                    babyItem.setVisibility(View.VISIBLE);//设置默认值
                    babyItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            TextView tv = (TextView) view;

                            tv.setTextColor(getResources().getColor(R.color.white)); //设置颜色
                            tv.setTextSize(12.0f); //设置大小
                            tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL); //设置居中
                            baby = dataList.get(position);
                            StorageConstants.BID=baby.getBid();
                            changeBaby();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    baby = null;
                    babyName.setText("无");
                    babyAge.setText("0");
                    RxToast.info("当前账号无宝宝");
                }
            }

            @Override
            public void doError(int code, String msg) {

            }
        });


    }

    private void changeBaby() {
        GlideUtils.loadImageView(baby.getBpic(), homeBabyPic);
        babyName.setText(baby.getBnick());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar rz = Calendar.getInstance();
        try {
            rz.setTime(format.parse(baby.getBage()));
            babyAge.setText(OtherUtils.getDate(rz));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        getRecord();
    }
    private void getRecord(){
        RequestParams requestParams=new RequestParams(MyApplication.BASE_URL+"getRecord");
        requestParams.addQueryStringParameter("bid",baby.getBid()+"");
        x.http().get(requestParams, new CustomCallback<RecordListModel>() {
            @Override
            public void doSuccess(RecordListModel recordListModel) {
                mainSw.setRefreshing(false);
                if (recordListModel.getData().size()>0){
                    recordAdapter.setNewData(recordListModel.getData());
                }else {
                    recordAdapter.setNewData(null);
                    EmptyData();
                }


            }

            @Override
            public void doError(int code, String msg) {
                mainSw.setRefreshing(false);
            }
        });

    }
    private void EmptyData() {
        View notDataView = getLayoutInflater().inflate(R.layout.layout_empty_data, (ViewGroup) mainRv.getParent(), false);
        recordAdapter.setEmptyView(notDataView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.add_record, R.id.height_weight, R.id.photo, R.id.baby_add, R.id.baby_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_record:
                if (baby != null) {
                    Intent intent = new Intent(getActivity(), AddRecordActivity.class);
                    intent.putExtra("bid", baby.getBid());
                    startActivity(intent);
                } else {
                    RxToast.info("请添加宝宝");
                }

                break;
            case R.id.baby_add:
                startActivity(new Intent(getActivity(), AddBabyActivity.class));
                break;
            case R.id.height_weight:
                if (baby != null) {
                    BabyDetailDialog babyDetailDialog = new BabyDetailDialog(baby, getActivity());
                    babyDetailDialog.show();
                } else {
                    RxToast.info("请添加宝宝");
                }
                break;
            case R.id.photo:
                break;
            case R.id.baby_delete:
                if (baby != null) {
                    final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(getActivity());
                    rxDialogSureCancel.setTitle("提示");
                    rxDialogSureCancel.setContent("确定删除?");
                    rxDialogSureCancel.setSureListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "DeleteBaby");
                            requestParams.addQueryStringParameter("bid", baby.getBid() + "");
                            x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                                @Override
                                public void doSuccess(SimpleModel simpleModel) {
                                    if (simpleModel.getDataCode() == 100) {
                                        RxToast.success("删除成功");
                                        initData();
                                    } else {
                                        RxToast.error(simpleModel.getMessage());
                                    }
                                    rxDialogSureCancel.cancel();

                                }

                                @Override
                                public void doError(int code, String msg) {

                                }
                            });


                        }
                    });
                    rxDialogSureCancel.setCancelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rxDialogSureCancel.cancel();
                        }
                    });
                    rxDialogSureCancel.show();
                } else {
                    RxToast.info("无宝宝!");
                }

                break;
        }
    }
}
