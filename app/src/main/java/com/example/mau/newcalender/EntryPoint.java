package com.example.mau.newcalender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by mau on 9/8/2015.
 */
public class EntryPoint extends Activity implements View.OnClickListener {
    private Button btn_g3;
    private Button btn_g2;
    private Button btn_g1;

    ConnectionDetector cd;

    public String group;
    private boolean isInternetPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_point);

        btn_g1 = (Button) findViewById(R.id.group1);
        btn_g2 = (Button) findViewById(R.id.group2);
        btn_g3 = (Button) findViewById(R.id.group3);

        btn_g1.setOnClickListener(this);
        btn_g2.setOnClickListener(this);
        btn_g3.setOnClickListener(this);
        //detecting connection
        cd = new ConnectionDetector(this);
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            startListActivity();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.group3:
                group = "g3";
                break;
            case R.id.group2:
                group = "g2";
                break;
            case R.id.group1:
                group = "g1";
                break;
        }

        startListActivity();
    }
    public void startListActivity(){
        Intent intent = new Intent(EntryPoint.this, MainActivity.class);
        intent.putExtra("group", group);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
