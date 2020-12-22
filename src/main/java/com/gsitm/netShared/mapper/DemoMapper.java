package com.gsitm.netShared.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsitm.netShared.mybatis.type.CamelCaseMap;

@Repository
public interface DemoMapper {

	List<CamelCaseMap> selectUser();
}
