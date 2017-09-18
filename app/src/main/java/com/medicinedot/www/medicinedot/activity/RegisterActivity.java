package com.medicinedot.www.medicinedot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.medicinedot.www.medicinedot.R;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;

public class RegisterActivity extends SimpleTopbarActivity {

    private LinearLayout register_drugstore,register_supplier;
    private ImageView drugstore_image,supplier_image;
    private Button register_next;
    private int SELECTTYPE = -1;
    @Override
    protected Object getTopbarTitle() {
        return "选择身份";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        register_drugstore = (LinearLayout) findViewById(R.id.register_drugstore);
        register_drugstore.setOnClickListener(this);
        register_supplier = (LinearLayout) findViewById(R.id.register_supplier);
        register_supplier.setOnClickListener(this);
        drugstore_image = (ImageView) findViewById(R.id.drugstore_image);
        supplier_image = (ImageView) findViewById(R.id.supplier_image);
        //下一步
        register_next = (Button) findViewById(R.id.register_next);
        register_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.register_drugstore://药店
                drugstore_image.setImageResource(R.mipmap.register_drugstore_press);
                supplier_image.setImageResource(R.mipmap.register_supplier);
                SELECTTYPE = 1;
                break;
            case R.id.register_supplier://供应商
                drugstore_image.setImageResource(R.mipmap.register_drugstore);
                supplier_image.setImageResource(R.mipmap.register_supplier_press);
                SELECTTYPE = 2;
                break;
            case R.id.register_next:
                if (SELECTTYPE == -1){
                    ToastUtil.showToast("请选泽注册身份");
                }else if (SELECTTYPE==1){
                    Intent intent = new Intent(this,RegisterDrugstoreActivity.class);
                    intent.putExtra("SELECTTYPE",1);
                    startActivity(intent);
                    finish();
                }else if (SELECTTYPE==2){
                    Intent intent = new Intent(this,RegisterSupplierActivity.class);
                    intent.putExtra("SELECTTYPE",2);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

    }

    @Override
    public void onCancelResult() {

    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

    }

    @Override
    public void onParseErrorResult(int errorCode) {

    }

    @Override
    public void onFinishResult() {

    }
}
