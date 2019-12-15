package com.yad.sjjg.repo.mapper;

import com.yad.sjjg.repo.model.Goods;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface GoodsMapper {

    @Insert("insert into goods  (name,price,amount,modified_time,create_time) VALUES (#{name},#{price},#{amount},#{modified_time},#{create_time})")
    int create(Goods newgoods);

    @Select("SELECT LAST_INSERT_ID()")
    int getId();

    @Select("select * from goods where  id = #{good}")
    Goods findById(Integer good);

    @Delete("delete from goods where id  = #{good}")
    void deletegood(Integer good);

    @Update("update goods set amount = #{i} ,modified_time = #{time} where id = #{id}")
    void output(Integer id, Integer i, Date time);

}
