package com.ly.entity;

import java.util.Date;

public class Good {
    private Integer id;

    private String gname;

    private String serialnumber;

    private Double price;

    private Integer stockNumber;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", gname='" + gname + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", price=" + price +
                ", stockNumber=" + stockNumber +
                ", createTime=" + createTime +
                '}';
    }
}