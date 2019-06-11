package com.mars.baby;

import com.google.gson.Gson;
import com.mars.baby.model.UserModel;
import com.mars.baby.utils.SPUtils;


public class StorageConstants {
    public static int BID=-1;

    private static final String USER_DETAIL = "USER_DETAIL";

    private static UserModel shareUserModel;


    public static UserModel getUser() {
        if (shareUserModel == null) {
            shareUserModel = new Gson().fromJson(SPUtils.getInstance().getString(USER_DETAIL),UserModel.class);
        }
        return shareUserModel;
    }

    public static void setUser(UserModel shareUserModel) {
        if (shareUserModel !=null){
            StorageConstants.shareUserModel = shareUserModel;
            SPUtils.getInstance().put(USER_DETAIL, new Gson().toJson(shareUserModel));
        }else {
            SPUtils.getInstance().put(USER_DETAIL,"");
        }

    }
}

