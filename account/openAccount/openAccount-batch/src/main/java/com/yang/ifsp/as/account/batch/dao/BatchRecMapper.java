package com.yang.ifsp.as.account.batch.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface BatchRecMapper {
    @Update("UPDATE rec_filename SET STATUS = #{status},createTime = #{createTime} WHERE file_name=#{fileName}")
    public int updateReadFile(@Param("createTime") Date date,@Param("fileName") String recFileName,@Param("status") Integer status);
}
