package com.darling.easylive.Mapper;

import com.darling.easylive.Pojo.user;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface userAccountMapper<T> {
   public int register(T user);

   public T getUserInfo(String username,String password);
}
