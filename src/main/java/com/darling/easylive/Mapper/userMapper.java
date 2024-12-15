package com.darling.easylive.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.darling.easylive.Pojo.dto.userDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface userMapper extends BaseMapper<userDto> {
}
