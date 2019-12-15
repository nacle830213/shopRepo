package com.yad.sjjg.repo.service;

import com.yad.sjjg.repo.mapper.GoodsMapper;
import com.yad.sjjg.repo.mapper.RecordMapper;
import com.yad.sjjg.repo.mapper.UrepoMapper;
import com.yad.sjjg.repo.mapper.UserMapper;
import com.yad.sjjg.repo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
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
//            List<Urepo> urepos=urepoMapper.getUserGoods(account);
            User user = userMapper.findByaccount(account);
//            for (Urepo urepo :urepos){
////                goodsMapper.deletegood(urepo.getGood());
////            }
            Record record1 = new Record();
            record1.setTime(new Date());
            record1.setPrice(0.0);
            record1.setAmount(0);
            record1.setType(3);
            record1.setUserid(user.getId());
            record1.setGood(1);
            recordMapper.insertReccord(record1);
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
                record.setPrice(goodsDto.getPrice());
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
//                    goodsMapper.deletegood(id);
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
        String [] Types = {"出货","补货","进货","初始化清空仓库","本仓库转出","本仓库转入"};
        for (Record record :records){
            Goods goods = goodsMapper.findById(record.getGood());
            System.out.println(goods);
            RecordDao recordDao = new RecordDao();
            recordDao.setAmount(record.getAmount());
            recordDao.setGood(goods);
            recordDao.setTime(record.getTime());
            String to_from ="";
            if (record.getType()==4)
                to_from+="To"+record.getTo_from();
            if (record.getType()==5)
                to_from+="From"+record.getTo_from();
            recordDao.setType(Types[record.getType()]+to_from);
            recordDao.setUser(userMapper.findById(record.getUserid()));
            if (record.getType()==0){
                recordDao.setPrice(record.getPrice());
            }else if (record.getType()==3){
                recordDao.setPrice(record.getPrice());
            }
            else{
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
            record.setPrice(newgoods.getPrice());
            recordMapper.insertReccord(record);
        }
    }

    public boolean transfer(String user, String account, Integer id, Integer amount) {
            User user1 = userMapper.findByaccount(account);
            User user2 = userMapper.findByaccount(user);
            if (user1!=null){
                Goods goods = goodsMapper.findById(id);
                System.out.println(goods.getAmount()+"***"+amount);
                goods.setName(user+":"+goods.getName());
                System.out.println(goods.getAmount());
                int ids;
                if (goods.getAmount()<=0){
                    urepoMapper.deleteuserGood(user,id);//删除 已经时总量的转仓
                    goods.setAmount(amount);  //设置数量
                    goodsMapper.create(goods);
                    ids = goodsMapper.getId();
                    urepoMapper.create(account,ids);

                    System.out.println(ids+"**"+goods.getId());
                }else{
                    goodsMapper.output(id,goods.getAmount()-amount,new Date());
                    goods.setAmount(amount);
                    goodsMapper.create(goods);
                    ids = goodsMapper.getId();
                    urepoMapper.create(account,ids);
                    System.out.println(ids+"**"+goods.getId());
                }
                Record record = util.createRecord(user2.getId(),goods.getId(),4,amount,goods.getPrice());
                record.setTo_from(account);
                recordMapper.insertReccord(record);
                Record record1 = util.createRecord(user1.getId(),ids,5,goods.getAmount(),goods.getPrice());
                record1.setTo_from(user);
                recordMapper.insertReccord(record1);
                return  false;
            }else {
                return  true;
            }
    }

    public DateDispalyDto Datedisplay(String user) {
            User user1 = userMapper.findByaccount(user);
            DateDispalyDto temp = new DateDispalyDto() ;
            if (user1!=null){
                Date now = new Date();
                String date = now.toLocaleString();
                String [] s = date.split(" ");
                String  start = s[0]+" 00:00:00";
                String  end = date;
                List<Record> records = recordMapper.UserDateRecords(user1.getId(),start,end);
                DecimalFormat df = new DecimalFormat(".00");
                for (Record record :records){
                    Goods goods = goodsMapper.findById(record.getGood());
                    if (record.getType()==0){//出货时，把出货价格减去进货价格
                        Double profile=record.getAmount()*(record.getPrice()-goods.getPrice());
                        profile = Double.parseDouble(df.format(profile));
                        temp.setProfile(temp.getProfile()+profile);
                        temp.setOutput(temp.getOutput()+record.getAmount());
                    }
                    if (record.getType()==1 || record.getType()==2){//进货或者补货时
                        Double cost = record.getPrice()*record.getAmount();
                        cost = Double.parseDouble(df.format(cost));
                        temp.setCost(temp.getCost()+cost);
                        temp.setInput(temp.getInput()+record.getAmount());
                    }
                    if (record.getType()==4){
                        Double value = record.getPrice()*record.getAmount();
                        value = Double.parseDouble(df.format(value));
                        temp.setValue(temp.getValue()+value);
                        temp.setChangeNum(temp.getChangeNum()+record.getAmount());
                    }
                }
            }
            return temp;
    }
}
