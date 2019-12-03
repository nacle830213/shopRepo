package com.yad.sjjg.repo.mapper;

import com.yad.sjjg.repo.model.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper {
    @Insert("insert into record (time,userid,good,type,amount,price) VALUES (#{time},#{userid},#{good},#{type},#{amount},#{price})")
    int insertReccord(Record record);

    @Select("select * from record where userid = #{userid}")
    List<Record> userRecords(Integer userid);
}
