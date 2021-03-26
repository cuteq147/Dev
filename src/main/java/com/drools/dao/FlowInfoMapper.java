package com.drools.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import com.drools.model.FlowInfoTO;

@Mapper
@Repository
public interface FlowInfoMapper {
@Results(id="flowinfo",value={
		@Result(property = "id", column = "fid"),
		@Result(property = "name", column = "name"),
		@Result(property = "actionContent", column = "aContent"),
		@Result(property = "conditionContent", column = "cContent"),
		@Result(property = "salienceNo", column = "salience_no"),
		@Result(property = "conditionId", column = "cId")
})
    //void save(RuleCondition rule);
	@Select("select f.fid,f.name,f.salience_no,a.content as aContent,c.content cContent,c.id cId "
			+ " from urxxxxxe_f_info f join  urxxxxx_rule_action a "
			+ " on f.action_id = a.id join urxxxxx_rule_condition c on f.condition_id = c.id"
			+ " where f.fid=#{flowId}")
	List<FlowInfoTO> findAll(@Param("flowId") long flowId);


	@ResultMap("flowinfo")
	@SelectProvider(type = FlowinfoSQLProvider.class, method = "geFlowsById")
	List<FlowInfoTO> getFlowsById(@Param("id") Long id);

    class FlowinfoSQLProvider implements ProviderMethodResolver {

		public static String geFlowsById(final Long id) {
			return new SQL(){{
				SELECT("f.fid,f.name,f.salience_no,a.content as aContent,c.content cContent,c.id cId");
				FROM("urxxxxxe_f_info f");
				INNER_JOIN("urxxxxx_rule_action a on a.id = f.action_id");
				INNER_JOIN("urxxxxx_rule_condition c on c.id = f.condition_id");
				if (id != 0) {
					WHERE("f.fid = #{id}");
				}
			}}.toString();
		}
	}

	@Insert("INSERT INTO urxxxxxe_f_info(fid, name, condition_id,action_id,salience_no) VALUES(#{id}, #{name}, #{conditionId},#{actionId}, #{salienceNo})")
	Long insertFlowInfo(FlowInfoTO flowInfo);
}