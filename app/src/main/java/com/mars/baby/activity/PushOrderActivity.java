package com.mars.baby.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.JsonBean;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.utils.GetJsonDataUtil;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONArray;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushOrderActivity extends ActivityBase {

    @BindView(R.id.fujia_type)
    Spinner fujiaType;
    @BindView(R.id.fujia_childtype)
    Spinner fujiaChildtype;
    @BindView(R.id.fujia)
    CheckBox fujia;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private static boolean isLoaded = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了


                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    //  Toast.makeText(JsonDataActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    //  Toast.makeText(JsonDataActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.add_childtype)
    Spinner addChildtype;
    @BindView(R.id.add_address)
    EditText addAddress;
    @BindView(R.id.add_date)
    TextView addDate;
    @BindView(R.id.register_submit_bt)
    AppCompatButton registerSubmitBt;
    @BindView(R.id.choose_address)
    TextView chooseAddress;
    private String type;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_order);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
        type = getIntent().getStringExtra("type");
        rxTitle.setTitle(type);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        String[] strings = null;
        switch (type) {
            case "护理预约":
                strings = getResources().getStringArray(R.array.huli);
                break;
            case "课外学习":
                strings = getResources().getStringArray(R.array.kewai);
                break;
            case "早教课堂":
                strings = getResources().getStringArray(R.array.zaojiao);
                break;
            default:
                strings = getResources().getStringArray(R.array.huli);
                break;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        addChildtype.setAdapter(dataAdapter);

        fujiaType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.type)));
        fujiaType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeType((String) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fujia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fujiaType.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                fujiaChildtype.setVisibility(isChecked ? View.VISIBLE : View.GONE);

            }
        });
    }

    private void changeType(String type) {
        String[] strings = null;
        switch (type) {
            case "护理预约":
                strings = getResources().getStringArray(R.array.huli);
                break;
            case "课外学习":
                strings = getResources().getStringArray(R.array.kewai);
                break;
            case "早教课堂":
                strings = getResources().getStringArray(R.array.zaojiao);
                break;
            default:
                strings = getResources().getStringArray(R.array.huli);
                break;
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        fujiaChildtype.setAdapter(dataAdapter);

    }


    @OnClick({R.id.add_date, R.id.register_submit_bt, R.id.choose_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_date:
                if (datePickerDialog == null) {
                    initWheelYearMonthDayDialog();
                }
                datePickerDialog.show();
                break;
            case R.id.register_submit_bt:
                String child = (String) addChildtype.getSelectedItem();
                if (fujia.isChecked()) {
                    child = child + "\n" + fujiaType.getSelectedItem() +"-"+ fujiaChildtype.getSelectedItem();
                }
                RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "PlaceOrder");
                requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
                requestParams.addQueryStringParameter("type", type);
                requestParams.addQueryStringParameter("child", child);
                requestParams.addQueryStringParameter("address", chooseAddress.getText().toString() + addAddress.getText().toString());
                requestParams.addQueryStringParameter("time", addDate.getText().toString());
                x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                    @Override
                    public void doSuccess(SimpleModel simpleModel) {
                        if (simpleModel.getDataCode() == 100) {
                            RxToast.success("下单成功.请等待接单");
                            finish();
                        } else {
                            RxToast.error(simpleModel.getMessage());
                        }


                    }

                    @Override
                    public void doError(int code, String msg) {

                    }
                });


                break;
            case R.id.choose_address:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initWheelYearMonthDayDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this,
                // 绑定监听器(How the parent is notified that the date is set.)
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        String strFormat = "yyyy年MM月dd日";  //格式设定
                        SimpleDateFormat sdf = new SimpleDateFormat(strFormat, Locale.CHINA);
                        addDate.setText(sdf.format(c.getTime())); //设置日期

                        datePickerDialog.cancel();
                    }
                }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                chooseAddress.setText(tx);
                //Toast.makeText(JsonDataActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
