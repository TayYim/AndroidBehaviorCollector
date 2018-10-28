package com.tay.behaviorcollector.Data;

public interface DataResourceInterface {
    void activate();
    void stop();
    float[] getData();
    int getBatchSize();
}
