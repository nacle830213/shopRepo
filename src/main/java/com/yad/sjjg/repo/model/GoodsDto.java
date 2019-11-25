package com.yad.sjjg.repo.model;

public class GoodsDto {
    private  String name ;
    private  Double price;
    private  Integer amount;

    @Override
    public String toString() {
        return "name:"+name+"price:"+price+"amount:"+amount;
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
}
