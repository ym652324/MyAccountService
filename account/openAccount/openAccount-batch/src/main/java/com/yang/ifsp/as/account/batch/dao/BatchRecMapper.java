//package com.yang.ifsp.as.account.batch.dao;
//
//import com.yang.ifsp.as.account.batch.model.CustInfoRecModel;
//import com.yang.ifsp.as.account.batch.model.RecFileInfo;
//import org.apache.ibatis.annotations.*;
//
//import java.util.Date;
//
//public interface BatchRecMapper {
//
//    @Update("UPDATE recFileName SET STATUS = #{status},createTime = #{createTime} WHERE fileName=#{fileName}")
//    int updateReadFile(@Param("createTime") Date date,@Param("fileName") String recFileName,@Param("status") Integer status);
//
//
//    @Select("SELECT fileName,status FROM recFileName where fileName = #{fileName}")
//    @Results({
//            @Result(column = "fileName",property = "fileName"),
//            @Result(column = "status",property = "status")
//    })
//    RecFileInfo selectRecFile(String fileName);
//
//
//    @Insert("INSERT INTO recFileName (id,fileName,status,createTime)\n"+
//            "VALUES\n"+
//            "\t(#{fId},#{fileName},#{status},#{createTime})")
//    int insertReadFile(RecFileInfo recFileInfo);
//
//
//    @Insert("insert into custInfoRec(\n"+
//            "fileName,\n"+
//            "fId,\n"+
//            "custName,\n"+
//            "mobilePhone,\n"+
//            "bindCard,\n"+
//            "idNo,\n"+
//            "image,\n"+
//            "eAccount,\n"+
//            "respCode,\n"+
//            "respMsg,\n"+
//            "errorData,\n"+
//            "arriveTime,\n"+
//            "values(\n"+
//            "#{fileName},\n"+
//            "#{fId},\n"+
//            "#{custName},\n"+
//            "#{mobilePhone},\n"+
//            "#{bindCard},\n"+
//            "#{idNo},\n"+
//            "#{image},\n"+
//            "#{eAccount},\n"+
//            "#{respCode},\n"+
//            "#{respMsg},\n"+
//            "#{errorData},\n"+
//            "#{createTime},\n"+")"
//    )
//    int insertCustInfo(CustInfoRecModel custInfoRecModel);
//}
