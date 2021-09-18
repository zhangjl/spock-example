package com.drivingrange.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zhangjl
 */
@Mapper
public interface CityMapper {
    /**
     * 查询
     * @param state
     * @return
     */
    @Select("SELECT * FROM CITY WHERE state = #{state}")
    City findByState(@Param("state") String state);
}
