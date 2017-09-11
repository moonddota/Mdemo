package com.skylin.uav.myuying;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Created by moon on 2017/9/6.
 */

public class HomeActivity extends Activity{

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        permissionsing();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void permissionsing() {
        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] mPermissionList = new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_NETWORK_STATE,Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_SETTINGS,
                    Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.VIBRATE,Manifest.permission.WAKE_LOCK,Manifest.permission.NFC};
            this.requestPermissions(mPermissionList, 100);
        }
    }


    public void goSpeech(View view){
        startActivity(new Intent(HomeActivity.this,SpeechActivity.class));
    }

    public void goWxCode(View view){
        startActivity(new Intent(HomeActivity.this,WxCodeActivity.class));
    }
    public void goNFC(View view){
       WorkActivity.startActivity(HomeActivity.this);
    }
    public void goPhoneX(View view){
        startActivity(new Intent(HomeActivity.this,ListActivity.class));
    }


}
