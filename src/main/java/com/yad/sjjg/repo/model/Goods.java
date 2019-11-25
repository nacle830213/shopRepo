package com.yad.sjjg.repo.model;


import java.util.Date;
public class Goods {

    private   Integer  id;
    private  String name ;
    private  Double price;
    private  Integer amount;
    private   Date  modified_time;
    private   Date  create_time;
    public  Goods(){}
    public Goods(String name , Double price ,Integer amount){
        this.name=name;
        this.price=price;
        this.amount=amount;
        this.create_time = new Date();
        this.modified_time = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  ("物品单价:"+this.price+" 数量:"+this.amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
