package com.yad.sjjg.repo.model;

import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.yad.sjjg.repo.model.Status.*;

public class LinkList {
    public  Node head ;
    public  LinkList(){
        head = new Node();
    }
    public  void clear(){
        head.data = null;
        head.next = null;
    }
    public  Boolean isEmpty(){
        return  head.next==null;
    }
    public  Integer length(){
        Node p = head.next;
        Integer length = 0;
        while (p!=null){
            p=p.next;
            length++;
        }
        return  length;
    }
    public  Object get(int i)  {//获取第i个节点
        int j =0 ;
        Node p = head.next;
       while (j<i && p!=null){
           p=p.next;
           j++;
       }
        if (i>this.length() || i<0  || p==null)
            return ISNULL;
       return  p;
    }
    public  int indexOf(Object x ){
        Node  p = head .next;
        int j= 0;
        while (p!=null && p.data.equals(x)){
            p=p.next;
            j++;
        }
        return  p==null ? -1 : j;
    }
    public  Status insert(int index,Object x)  {
        Node  p =head;
        int j=-1;
        while (p!=null && j<index-1){
            p = p.next;
            j++;
        }
        if(j>index-1 || p ==null)
            return INSERTINDEXERROR;
        Node t = new Node(x);
        t.next = p.next;
        p.next = t;
        return  OK;
    }
    public  Status remove(int i)  {
        Node p = head;
        int j = -1;
        while (p.next!=null && j<i-1){
            p=p.next;
            j++;
        }
        if(j>i-1 || p==null)
            return REMOVEINDEXERROR;
        p=p.next.next;
        return  OK;
    }
    public  void dispaly(){
        Node p = head.next;
        int j = 0;
        while (p!=null){
            System.out.println(p.data);
            p=p.next;
            j++;
        }
    }

    public List<Goods> getlist() {
        Node p = head.next;
        int j = 0;
        List<Goods> list = new LinkedList<>();
        while (p!=null){
            list.add((Goods)p.data);
            p=p.next;
            j++;
        }
        return  list;
    }
}
