package com.smart.sperms.api.protocol;

import java.io.Serializable;

/**
 * 107 消息体
 */
public class DataBody107 implements Serializable {

    private boolean work;

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return "DataBody107{" +
                "work=" + work +
                '}';
    }
}
