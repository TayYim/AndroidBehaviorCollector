package com.tay.behaviorcollector.Scenario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Toast;

import com.gnepux.slideview.SlideView;
import com.tay.behaviorcollector.Data.DataCenter;
import com.tay.behaviorcollector.Data.DataResourceInterface;
import com.tay.behaviorcollector.R;
import com.tay.behaviorcollector.User;

/**
 * this activity is also used as a data resource
 */
public class SlidingActivity extends AppCompatActivity implements DataResourceInterface {

    private static final String SCENARIO_NAME = "Sliding";
    public int batchSize;
    public String resourceName;
    // action times
    public int counter;
    private DataCenter dataCenter;
    private SlideView slideView;
    // for data resource
    private float touchDataList[];
    private VelocityTracker mVelocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);

        // initial data resource
        this.batchSize = 5;
        touchDataList = new float[this.batchSize];
        this.resourceName = "slideTouch";
        counter = 0;

        // config slide view
        slideView = findViewById(R.id.slideView);
        slideView.addSlideListener(new SlideView.OnSlideListener() {
            /**
             * call when slide finished
             */
            @Override
            public void onSlideSuccess() {
                Toast.makeText(SlidingActivity.this,
                        "确认成功，还剩" + String.valueOf(User.getActionTimes() - counter - 1) + "次",
                        Toast.LENGTH_SHORT)
                        .show();

                counter++;
                dataCenter.stopCollecting();

                if (counter < User.getActionTimes()) {
                    // reset this activity and start again
                    slideView.reset();
                    bindDataResource();
                } else {
                    SlidingActivity.this.finish(); // kill this activity
                }
            }
        });

        bindDataResource();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * important!!! check (https://juejin.im/post/5a0fab1bf265da432d27ad70)
     *
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

    /**
     * bind data resource(this activity) to data center
     */
    private void bindDataResource() {
        dataCenter = new DataCenter(SCENARIO_NAME);
        dataCenter.addResource(this);
        // collect data 1 time / 20ms
        dataCenter.startCollecting(10, 20);
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
