package com.drools.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.drools.model.RuleCondition;

@Mapper
@Repository
public interface RuleConditionMapper {
@Results({
	@Result(property = "id", column = "id"),
	@Result(property = "name", column = "name"),
	@Result(property = "content", column = "content")
})
    //void save(RuleCondition rule);
	@Select("select * from ur09051_rule_condition ")
	List<RuleCondition> findAll();
}