package com.gsitm.netshared.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gsitm.netshared.mybatis.type.CamelCaseMap;

@Repository
public interface DemoMapper {

	List<CamelCaseMap> selectUser();
}
