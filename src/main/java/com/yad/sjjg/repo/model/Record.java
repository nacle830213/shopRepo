package com.yad.sjjg.repo.model;

import java.util.Date;

public class Record {
    private  Integer Id ;
    private Date time;
    private  Integer userid;
    private  Integer good;
    private  Integer type;
    private  Integer amount;
    private  Double price;
    private  String to_from;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTo_from() {
        return to_from;
    }

    public void setTo_from(String to_from) {
        this.to_from = to_from;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
