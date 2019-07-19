package com.smart.sperms.dao.model;

import java.io.Serializable;

public class CaculationMethod implements Serializable {
    private String cmNo;

    private Integer cmDay;

    private Double cmFee;

    private Integer cmRemain;

    private static final long serialVersionUID = 1L;

    public String getCmNo() {
        return cmNo;
    }

    public void setCmNo(String cmNo) {
        this.cmNo = cmNo;
    }

    public Integer getCmDay() {
        return cmDay;
    }

    public void setCmDay(Integer cmDay) {
        this.cmDay = cmDay;
    }

    public Double getCmFee() {
        return cmFee;
    }

    public void setCmFee(Double cmFee) {
        this.cmFee = cmFee;
    }

    public Integer getCmRemain() {
        return cmRemain;
    }

    public void setCmRemain(Integer cmRemain) {
        this.cmRemain = cmRemain;
    }
}