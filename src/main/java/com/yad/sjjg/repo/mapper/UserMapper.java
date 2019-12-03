package com.yad.sjjg.repo.mapper;

import com.yad.sjjg.repo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where account=#{account}")
    User findByaccount(String account);

    @Insert("insert into goods  (account,password) VALUES (#{account},#{password})")
    int create(String account, String password);

    @Select("select * from user where id = #{userid}")
    User findById(Integer userid);
}
