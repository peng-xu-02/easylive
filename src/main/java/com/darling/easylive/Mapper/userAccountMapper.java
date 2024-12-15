package com.darling.easylive.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface userAccountMapper<T> extends BaseMapper{
   public int register(T user);

   public T getUserInfo(String username,String password);
}
