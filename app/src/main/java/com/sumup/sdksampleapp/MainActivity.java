package com.sumup.sdksampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.sumup.merchant.api.SumUpAPI;
import com.sumup.merchant.api.SumUpLogin;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE_LOGIN = 1;

    private TextView mResultCode;
    private TextView mResultMessage;
    private TextView mTxCode;
    private TextView mReceiptSent;
    private TextView mTxInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Please go to https://me.sumup.com/developers to get your Affiliate Key by entering the application ID of your app. (e.g. com.sumup.sdksampleapp)
                SumUpLogin sumupLogin = SumUpLogin.builder("7ca84f17-84a5-4140-8df6-6ebeed8540fc").build();
                SumUpAPI.openLoginActivity(MainActivity.this, sumupLogin, REQUEST_CODE_LOGIN);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        resetViews();
        if (resultCode == 1){
            Intent subIntent = new Intent(MainActivity.this, SubMainActivity.class);
            startActivity(subIntent);
        } else {
            if (data != null) {
                Bundle extra = data.getExtras();
                mResultCode.setText("Result code: " + extra.getInt(SumUpAPI.Response.RESULT_CODE));
                mResultMessage.setText("Message: " + extra.getString(SumUpAPI.Response.MESSAGE));
            }
        }
    }

    private void resetViews() {
        mResultCode.setText("");
        mResultMessage.setText("");
        mTxCode.setText("");
        mReceiptSent.setText("");
        mTxInfo.setText("");
    }

    private void findViews() {
        mResultCode = (TextView) findViewById(R.id.result);
        mResultMessage = (TextView) findViewById(R.id.result_msg);
        mTxCode = (TextView) findViewById(R.id.tx_code);
        mReceiptSent = (TextView) findViewById(R.id.receipt_sent);
        mTxInfo = (TextView) findViewById(R.id.tx_info);
    }
}
