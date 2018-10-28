package com.tay.behaviorcollector.Data;

import android.util.Log;

import com.tay.behaviorcollector.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class DataCenter {
    public String scenarioName;
    public String userID;
    public ArrayList resourceList;
    public ArrayList dataList;
    private int collectCount; // count rows

    public DataCenter(String scenarioName) {
        this.scenarioName = scenarioName;
        this.userID = User.getUserID();
        resourceList = new ArrayList<DataResourceInterface>();
        dataList = new ArrayList<ArrayList>();
        collectCount = 0;
    }

    /**
     * add a data resource object to the list
     *
     * @param dataResource
     */
    public void addResource(DataResourceInterface dataResource) {
        resourceList.add(dataResource);
        dataList.add(new ArrayList<Float>());
    }

    /**
     * activate each data resource
     *
     * @param delay
     * @param period
     */
    public void startCollecting(long delay, long period) {
        /**
         * activate each data resource
         */
        for (int i = 0; i < resourceList.size(); i++) {
            DataResourceInterface resource = (DataResourceInterface) resourceList.get(i);
            resource.activate();
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                collectData();
                collectCount++;
            }
        };
        timer.schedule(task, delay, period);
    }

    /**
     * deactivate each data resource
     */
    public void stopCollecting() {
        for (int i = 0; i < resourceList.size(); i++) {
            DataResourceInterface resource = (DataResourceInterface) resourceList.get(i);
            resource.stop();
        }

        Log.i(TAG, "stopCollecting: stopped" + collectCount);

        // file name = userID_[scenario name]_time stamp
        String fileName = userID + "_[" + scenarioName + "]_" + String.valueOf(System.currentTimeMillis());
        new FileSaver(fileName).saveFile(collectCount, resourceList, dataList);
    }

    /**
     * record data from each resource
     */
    private void collectData() {
        DataResourceInterface resource;
        ArrayList currDataList;
        for (int i = 0; i < resourceList.size(); i++) {
            resource = (DataResourceInterface) resourceList.get(i);
            float data[] = resource.getData();

            Log.i(TAG, "collectData: "+ String.valueOf(data[0]));

            currDataList = (ArrayList) dataList.get(i);
            for (int j = 0; j < data.length; j++) {
                currDataList.add(data[j]);
            }
        }
    }


}
