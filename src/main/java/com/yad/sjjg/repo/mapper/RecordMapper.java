package com.yad.sjjg.repo.mapper;

import com.yad.sjjg.repo.model.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper {
    @Insert("insert into record (time,userid,good,type,amount,price,to_from) VALUES (#{time},#{userid},#{good},#{type},#{amount},#{price},#{to_from})")
    int insertReccord(Record record);

    @Select("select * from record where userid = #{userid}")
    List<Record> userRecords(Integer userid);

    @Select("SELECT  * FROM record WHERE  userid = #{id} AND time  > #{start} AND  time < #{end}")
    List<Record> UserDateRecords(Integer id, String start, String end);
}
