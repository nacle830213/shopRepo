package com.yad.sjjg.repo.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.yad.sjjg.repo.model.Record;
import org.junit.Test;

import java.util.Date;

public class util {
    public  static Record createRecord(Integer userid,Integer goodId,Integer type,Integer amount,double price){
        Record record = new Record();
        record.setGood(goodId);
        record.setUserid(userid);
        record.setType(type);
        record.setAmount(amount);
        record.setPrice(price);
        record.setTime(new Date());
        return  record;
    }
    @Test
    public  void  test(){
        Date now = new Date();
        String date = now.toLocaleString();
        String [] s = date.split(" ");
        String  start = s[0]+" 00:00:00";
        String  end = date;
        System.out.println(start);
        System.out.println(end);
    }
}
