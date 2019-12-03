package com.yad.sjjg.repo.service;

import com.yad.sjjg.repo.mapper.GoodsMapper;
import com.yad.sjjg.repo.mapper.RecordMapper;
import com.yad.sjjg.repo.mapper.UrepoMapper;
import com.yad.sjjg.repo.mapper.UserMapper;
import com.yad.sjjg.repo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AllService {
        public  static Map<String ,LinkList> userpool = new HashMap<>();
        @Autowired
        private UserMapper userMapper;
        @Autowired
        private GoodsMapper goodsMapper;
        @Autowired
        private UrepoMapper urepoMapper;
        @Autowired
        private RecordMapper recordMapper;
        public LinkList build(List<GoodsDto> list, String account){
            LinkList link  = new LinkList();
            List<Urepo> urepos=urepoMapper.getUserGoods(account);
            User user = userMapper.findByaccount(account);
            for (Urepo urepo :urepos){
                goodsMapper.deletegood(urepo.getGood());
            }
            urepoMapper.clearUsergoods(account);
            int i=0;//0
            for (GoodsDto goodsDto:list){
                System.out.println(goodsDto);
                //创建 商品
                Goods newgoods = new Goods(goodsDto.getName(),goodsDto.getPrice(),goodsDto.getAmount());
                System.out.println(newgoods);
                link.insert(i,newgoods);//链表中加入商品
                i++;
                goodsMapper.create(newgoods); //存入商品数据
                int id  = goodsMapper.getId();//返回商品 id
                urepoMapper.create(account,id);//关联  商品 和 user
                Record record = new Record();
                record.setUserid(user.getId());
                record.setType(2);
                record.setTime(new Date());
                record.setGood(id);
                record.setAmount(newgoods.getAmount());
                recordMapper.insertReccord(record);
            }
            userpool.put(account,link);
            return  link;
        }


    public User logined(String account,String password) {
          User user =  userMapper.findByaccount(account);
          if (user!=null){
              if (user.getPassword().equals(password)){
                  return  user;
              }
          }
          return  null;
    }

    public User signup(String account, String password) {
           int i= userMapper.create(account,password);
           User user = userMapper.findByaccount(account);
            System.out.println(user.getPassword()+":"+user.getPassword());
            return  user;
        }

    public List<Goods> getGoods(String user) {
            List<Urepo> urepos =urepoMapper.getUserGoods(user);
            List<Goods> list = new LinkedList<>();
            for (Urepo urepo :urepos){
                Goods goods = goodsMapper.findById(urepo.getGood());
                list.add(goods);
            }
            return  list;
        }

    public void update(String account, Integer id, Integer amount,Integer type,Double price) {
        User user = userMapper.findByaccount(account);
        if (user!=null){
            Urepo urepo = urepoMapper.getUser(id);
            if (urepo!=null){
                Goods goods = goodsMapper.findById(id);
                Date time =  new Date();
                Integer a = type == 0 ? goods.getAmount()-amount: goods.getAmount()+amount;
                if (a>0)
                    goodsMapper.output(id,a,time);
                else{
                    goodsMapper.deletegood(id);
                    urepoMapper.delet(id);
                }
                Record record = new Record();
                record.setAmount(amount);
                record.setGood(id);
                record.setTime(time);
                record.setType(type);//0 为 出货；  1 补货；   2：初始化
                record.setUserid(user.getId());
                if (type==0){
                    record.setPrice(price);
                }else {
                    record.setPrice(goods.getPrice());
                }
                recordMapper.insertReccord(record);
            }
        }
        }

    public List<RecordDao> getRecords(String user) {
        User user1 = userMapper.findByaccount(user);
        List<Record> records = recordMapper.userRecords(user1.getId());
        List<RecordDao> recordDaoList = new LinkedList<>();
        String [] Types = {"出货","补货","进货"};
        for (Record record :records){
            Goods goods = goodsMapper.findById(record.getGood());
            RecordDao recordDao = new RecordDao();
            recordDao.setAmount(record.getAmount());
            recordDao.setGood(goods);
            recordDao.setTime(record.getTime());
            recordDao.setType(Types[record.getType()]);
            recordDao.setUser(userMapper.findById(record.getUserid()));
            if (record.getType()==0){
                recordDao.setPrice(record.getPrice());
            }else {
                recordDao.setPrice(goods.getPrice());
            }
            recordDaoList.add(recordDao);
        }
        return recordDaoList;
    }
    @Transactional
    public void add(List<GoodsDto> list, String account) {
        User user = userMapper.findByaccount(account);
        for (GoodsDto goodsDto:list){
            System.out.println(goodsDto);
            //创建 商品
            Goods newgoods = new Goods(goodsDto.getName(),goodsDto.getPrice(),goodsDto.getAmount());
            System.out.println(newgoods);
            goodsMapper.create(newgoods); //存入商品数据
            int id  = goodsMapper.getId();//返回商品 id
            urepoMapper.create(account,id);//关联  商品 和 user
            Record record = new Record();
            record.setUserid(user.getId());
            record.setType(2);
            record.setTime(new Date());
            record.setGood(id);
            record.setAmount(newgoods.getAmount());
            recordMapper.insertReccord(record);
        }
    }
}
