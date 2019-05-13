package com.rjpa.mic.repository.dao;


import com.rjpa.mic.repository.model.CteQicai;
import com.rjpa.mic.repository.model.CteQicaiExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CteQicaiMapper {
    int countByExample(CteQicaiExample example);

    int deleteByExample(CteQicaiExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CteQicai record);

    int insertSelective(CteQicai record);

    List<CteQicai> selectByExample(CteQicaiExample example);

    CteQicai selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CteQicai record, @Param("example") CteQicaiExample example);

    int updateByExample(@Param("record") CteQicai record, @Param("example") CteQicaiExample example);

    int updateByPrimaryKeySelective(CteQicai record);

    int updateByPrimaryKey(CteQicai record);

    @Select("SELECT * FROM CTE_QICAI WHERE qname = #{qname}")
    CteQicai findByQname(@Param("qname") String name);
}