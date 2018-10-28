package com.tay.behaviorcollector.Scenario;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tay.behaviorcollector.Data.DataCenter;
import com.tay.behaviorcollector.Data.TouchDataResource;
import com.tay.behaviorcollector.R;

public class PlainActivity extends AppCompatActivity implements Scenario {

    private static final String TAG = "PlainActivity";
    private ConstraintLayout layout_plain;
    private DataCenter dataCenter;
    private static final String SCENARIO_NAME = "Plain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain);
        layout_plain = findViewById(R.id.layout_plain);

        bindDataResource();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: destroy");
        dataCenter.stopCollecting();
    }

    /**
     * use different data resource and add them to the data center
     */
    private void bindDataResource() {
        TouchDataResource touchDataResource;

        /**
         * initial touchDataResource and activate it
         */
        touchDataResource = new TouchDataResource("touch", layout_plain);

        dataCenter = new DataCenter(SCENARIO_NAME); // scenario name
        dataCenter.addResource(touchDataResource);
        dataCenter.startCollecting(10, 10);
    }
}
