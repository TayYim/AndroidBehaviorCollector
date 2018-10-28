package com.tay.behaviorcollector.Data;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class TouchDataResource implements View.OnTouchListener, DataResourceInterface{

    private View target;
    private float touchDataList[];
    private VelocityTracker mVelocityTracker;
    public int batchSize;
    public String resourceName;

    public TouchDataResource(String resourceName, View target) {
        this.resourceName = resourceName;
        this.target = target;
        this.batchSize = 5;
        touchDataList = new float[this.batchSize];
    }

    @Override
    public void activate() {
        target.setOnTouchListener(this);
    }

    @Override
    public void stop() {
        target.setOnTouchListener(null);
    }

    @Override
    public float[] getData() {
        return this.touchDataList;
    }

    @Override
    public int getBatchSize() {
        return this.batchSize;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
        return true;
    }


}
