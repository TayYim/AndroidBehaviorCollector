package com.tay.behaviorcollector.Data;

import android.util.Log;

import com.tay.behaviorcollector.MainActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FileSaver {

    private String fileName;

    public FileSaver(String fileName) {
        this.fileName = fileName;
    }

    /**
     * get file path
     * @return
     */
    public  String getDir() {

        String dirPath = MainActivity.getContext().getExternalFilesDir(null).toString() + File.separator + "MeasureData";

        File dir = new File(dirPath);

        if (dir.exists()) {

            return dir.toString();


        } else {

            dir.mkdirs();

            Log.d("getDir", "保存路径不存在,");

            return dir.toString();
        }

    }

    /**
     * write data into the file
     * @param totalRows
     * @param resourceList
     * @param dataList
     */
    public  void saveFile(int totalRows,ArrayList resourceList, ArrayList dataList ){
        try {
            String filePath = getDir() + File.separator + fileName + ".csv";
            File file = new File(filePath);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            // set title
            bw.write( "X/p" + "," + "Y/p" + "," + "Vx/p.ms-1" + "," + "Vy/p.ms-1" + "," + "Pressed");
            bw.newLine();
            // write data
            String line;
            int batchSize;
            for (int row = 1; row <= totalRows; row++) {
                line = "";
                for (int i = 0; i < resourceList.size(); i++) {
                    batchSize = ((DataResourceInterface) resourceList.get(i)).getBatchSize();
                    for (int j = (row - 1) * batchSize; j < row * batchSize; j++) {
                        line += String.valueOf(((ArrayList) dataList.get(i)).get(j)) + ",";
                    }
                }
                bw.write(line);
                bw.newLine();
                Log.i(TAG, "saveFile: " + line);
            }
            bw.close();
            Log.i(TAG, "saveFile: saved!");
        } catch (IOException e) {
            Log.i(TAG, "saveFile: Error!");
            e.printStackTrace();
        }
    }
}
