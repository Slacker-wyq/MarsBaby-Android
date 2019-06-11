package com.mars.baby.utils;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.stream.MalformedJsonException;
import com.mars.baby.model.BaseModel;
import com.vondear.rxtools.RxNetTool;
import com.vondear.rxtools.RxTool;
import com.vondear.rxtools.view.RxToast;

import org.xutils.common.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public abstract class CustomCallback<T extends BaseModel> implements Callback.CommonCallback<String> {

    public static final int Code_TimeOut = 1000;
    public static final int Code_UnConnected = 1001;
    public static final int Code_MalformedJson = 1020;
    public static final int Code_Default = 1003;
    public static final String CONNECT_EXCEPTION = "网络连接异常";//，请检查您的网络状态
    public static final String SOCKET_TIMEOUT_EXCEPTION = "网络连接超时";//，请检查您的网络状态，稍后重试
    public static final String MALFORMED_JSON_EXCEPTION = "数据解析错误";
    public static final String REQUEST_ERROR = "请求错误";
    Type mType;

    public CustomCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    @Override
    public void onSuccess(String result) {
        T t = new Gson().fromJson(result, mType);
        if (t.getCode() != -1) {
            doSuccess(t);
        } else {
            RxToast.error("服务器异常,请联系管理员");
            doError(t.getCode(), t.getMessage());
        }
    }

    @Override
    public void onError(Throwable throwable, boolean isOnCallback) {
        if (RxNetTool.isAvailable(RxTool.getContext())) {
            if (throwable instanceof SocketTimeoutException) {
                doError(Code_TimeOut, SOCKET_TIMEOUT_EXCEPTION);
                RxToast.error(SOCKET_TIMEOUT_EXCEPTION);
            } else if (throwable instanceof ConnectException) {
                doError(Code_UnConnected, CONNECT_EXCEPTION);
                RxToast.error(CONNECT_EXCEPTION);
            } else if (throwable instanceof UnknownHostException) {
                doError(Code_UnConnected, CONNECT_EXCEPTION);
                RxToast.error(CONNECT_EXCEPTION);
            } else if (throwable instanceof MalformedJsonException) {
                doError(Code_MalformedJson, MALFORMED_JSON_EXCEPTION);
                RxToast.error(MALFORMED_JSON_EXCEPTION);
            }
        } else {
            doError(Code_UnConnected, "无网络");
            RxToast.error("无网络");
        }

    }


    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    public abstract void doSuccess(T t);

    public abstract void doError(int code, String msg);
}
