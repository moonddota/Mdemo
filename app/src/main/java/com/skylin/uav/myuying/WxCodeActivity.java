package com.skylin.uav.myuying;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 * Created by moon on 2017/9/6.
 */

public class WxCodeActivity extends Activity {
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    private final String TAG = "tag";


    private Button button1;
    private TextView datas;
    private EditText edit;
    private ImageView imv;
    private Button button;
    private Bitmap mBitmap = null;

    private Button button2;
    private Button button3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxcode);
        button1 = (Button) findViewById(R.id.button1);
        datas = (TextView) findViewById(R.id.datas);
        edit = (EditText) findViewById(R.id.edit);
        imv = (ImageView) findViewById(R.id.imv);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textContent = edit.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(WxCodeActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                edit.setText("");
                //   普通的二维码
//                mBitmap = CodeUtils.createImage(textContent, 400, 400, null);
                //   带自定义背景
                mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.hand));
                imv.setImageBitmap(mBitmap);
            }
        });


        /**
         * 打开默认二维码扫描界面
         */
        button1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    String[] mPermissionList = new String[]{Manifest.permission.CAMERA};
                    requestPermissions(mPermissionList, 100);
                } else {
                    Intent intent = new Intent(getApplication(), CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WxCodeActivity.this, SecondActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    datas.setText("解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    datas.setText("解析二维码失败");
                }
            }
        }

        /**
         * 选择系统图片并解析
         */
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            datas.setText("解析结果:" + result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            datas.setText("解析二维码失败");                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
//    public void cameraTask(int viewId) {
//        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
//            // Have permission, do the thing!
//            onClick(viewId);
//        } else {
//            // Ask for one permission
//            EasyPermissions.requestPermissions(this, "需要请求camera权限",
//                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
//        }
//    }


}
