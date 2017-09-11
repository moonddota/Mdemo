package com.skylin.uav.myuying;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moon on 2017/9/11.
 */

public class ListActivity extends android.app.ListActivity implements AdapterView.OnItemClickListener{

    //返回码
    private static final int CODE = 1;
    //封装所有软件
    private List<String> mPackage = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里继承了ListActivity显示列表，不需要加载layout

        //获取手机上所有的包
        PackageManager manager = getPackageManager();
        //把他们装起来
        List<PackageInfo> packageInfos = manager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        //遍历
        for (PackageInfo pi : packageInfos) {
            //添加软件名和包名
            mPackage.add(pi.applicationInfo.loadLabel(manager) + "\n" + pi.packageName);
        }

        //官方的适配器
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mPackage);
        setListAdapter(arrayAdapter);
        //设置单击事件
        getListView().setOnItemClickListener(this);

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra("package", mPackage.get(i));
        setResult(CODE,intent);
//        finish();
    }
}
