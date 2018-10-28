package com.tay.behaviorcollector.Data;

/**
 * deprecated
 */

public abstract class DataResource {
    public int batchSize;
    public String resourceName;

    public abstract void activate();

    public abstract void stop();

    public abstract float[] getData();

    public DataResource(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
