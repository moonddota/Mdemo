package com.skylin.uav.myuying;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by moon on 2017/9/4.
 */

public class APP extends Application {

    @Override
    public void onCreate() {

        SpeechUtility.createUtility(APP.this, "appid=59acc4bf");
        super.onCreate();

        ZXingLibrary.initDisplayOpinion(this);
    }
}
