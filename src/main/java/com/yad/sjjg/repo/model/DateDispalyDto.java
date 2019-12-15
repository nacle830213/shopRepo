package com.yad.sjjg.repo.model;

public class DateDispalyDto {
    private  Double Profile;//收益
    private  Double cost;  //成本
    private  Integer output;//出货数
    private  Integer input;//进货，补货数
    private  Double  value;
    private  Integer changeNum;

    public DateDispalyDto() {
        this.value = 0.0;
        this.changeNum=0;
        this.cost=0.0;
        this.Profile=0.0;
        this.input=0;
        this.output=0;
    }

    public Double getProfile() {
        return Profile;
    }

    public void setProfile(Double profile) {
        Profile = profile;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getOutput() {
        return output;
    }

    public void setOutput(Integer output) {
        this.output = output;
    }

    public Integer getInput() {
        return input;
    }

    public void setInput(Integer input) {
        this.input = input;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
    }
}
