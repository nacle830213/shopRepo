package com.yad.sjjg.repo.mapper;

import com.yad.sjjg.repo.model.Urepo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UrepoMapper {
    @Insert("insert into urepo  (user,good) VALUES (#{useraccount},#{goodId})")
    int create(String  useraccount,Integer goodId);
    @Select("select * from urepo where user = #{user}")
    List<Urepo> getUserGoods(String user);

    @Delete("delete from urepo where user  = #{account}")
    void clearUsergoods(String account);

    @Select("select * from urepo where good = #{id}")
    Urepo getUser(Integer id);

    @Delete("delete from urepo where good  = #{id}")
    void delet(Integer id);
}
