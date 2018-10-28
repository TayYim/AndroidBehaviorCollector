package com.tay.behaviorcollector.Scenario;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;

import com.gnepux.slideview.SlideView;
import com.tay.behaviorcollector.Data.DataCenter;
import com.tay.behaviorcollector.Data.DataResourceInterface;
import com.tay.behaviorcollector.Data.TouchDataResource;
import com.tay.behaviorcollector.R;

/**
 * this activity is also used as a data resource
 */
public class SlidingActivity extends AppCompatActivity implements DataResourceInterface {

    private static final String TAG = "SlidingActivity";
    private DataCenter dataCenter;
    private static final String SCENARIO_NAME = "Sliding";

    // for data resource
    private float touchDataList[];
    private VelocityTracker mVelocityTracker;
    public int batchSize;
    public String resourceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);

        this.batchSize = 5;
        touchDataList = new float[this.batchSize];
        this.resourceName = "slideTouch";

        bindDataResource();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: destroy");
        dataCenter.stopCollecting();
    }

    /**
     * important!!! check (https://juejin.im/post/5a0fab1bf265da432d27ad70)
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float pressed;

        //在onTouchEvent(MotionEvent ev)中
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();//获得VelocityTracker类实例
        }
        mVelocityTracker.addMovement(event);//将事件加入到VelocityTracker类实例中
        //判断当ev事件是MotionEvent.ACTION_UP时：计算速率
        final VelocityTracker velocityTracker = mVelocityTracker;
        // 1 provides pixels per ms
        velocityTracker.computeCurrentVelocity(1); //设置units的值为1，意思为一毫秒时间内运动了多少个像素

        // 设置手指是否接触屏幕变量：pressed
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            pressed = 1;
        } else {
            pressed = 0;
        }

        touchDataList[0] = event.getRawX();
        touchDataList[1] = event.getRawY();
        touchDataList[2] = velocityTracker.getXVelocity();
        touchDataList[3] = velocityTracker.getYVelocity();
        touchDataList[4] = pressed;
        return super.dispatchTouchEvent(event);
    }

    private void bindDataResource() {
        TouchDataResource touchDataResource;

        /**
         * initial touchDataResource and activate it
         */

        dataCenter = new DataCenter(SCENARIO_NAME); // scenario name
        dataCenter.addResource(this);
        dataCenter.startCollecting(10, 10);
    }

    @Override
    public void activate() {

    }

    @Override
    public void stop() {

    }

    @Override
    public float[] getData() {
        return this.touchDataList;
    }

    @Override
    public int getBatchSize() {
        return this.batchSize;
    }

}
