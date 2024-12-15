package com.darling.easylive.Mapper;

import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T> {
    public Integer insert(@Param("bean") T t);
}
