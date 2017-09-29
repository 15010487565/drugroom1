package com.medicinedot.www.medicinedot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medicinedot.www.medicinedot.R;
import com.medicinedot.www.medicinedot.entity.GlobalParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.ToastUtil;

public class RegisterDrugstoreActivity extends SimpleTopbarActivity {

    private EditText invitationcode;
    private Button next;
    private int SELECTTYPE = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerdrugstore);
        SELECTTYPE = getIntent().getIntExtra("SELECTTYPE",-1);
        invitationcode = (EditText) findViewById(R.id.invitationcode);
        invitationcode.setOnFocusChangeListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
    }
    private String invitation_code;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.next://登陆
                invitation_code = invitationcode.getText().toString().trim();
                if ("".equals(invitation_code)||invitation_code==null){
//                    Intent intent = new Intent(this, RegisterSupplierActivity.class);
//                    intent.putExtra("SELECTTYPE",SELECTTYPE);
//                    intent.putExtra("invitecode",invitation_code);
//                    startActivity(intent);
                    ToastUtil.showToast("邀请码不能为空！");
                    return;
                }
                createDialogshow();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("invitecode", invitation_code);
                okHttpGet(100, GlobalParam.INVITECODE, params);
                break;

        }
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode ==200){
            Intent intent = new Intent(this, RegisterSupplierActivity.class);
            intent.putExtra("SELECTTYPE",SELECTTYPE);
            intent.putExtra("invitecode",invitation_code);
            startActivity(intent);
        }else {
            ToastUtil.showToast(returnMsg);
        }
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
