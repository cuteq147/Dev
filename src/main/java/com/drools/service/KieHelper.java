package com.drools.service;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drools.base.IExecRule;
import com.drools.dao.FlowInfoMapper;
import com.drools.model.FlowInfo;
import com.drools.model.FlowInfoTO;
import com.drools.model.JudgeResult;
import com.drools.model.OrderInfo;
import com.drools.model.ProductSteel;
import com.drools.vo.TransOrderDTO;


@Service
public class KieHelper {
	@Autowired
    private RuleReloader ruleLoader;
	
	public Optional<StatelessKieSession> getKieSessionByFlowId(long flowId) {
		try {
			return Optional.of(ruleLoader.getKieContainerByFlowId(flowId).
									newStatelessKieSession("ksession_" + flowId));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			return Optional.ofNullable(null);
		}
	}
	
	
	public JudgeResult executeRule(long flowId,List<Object> lso, 
			BiFunction<List<Object>,StatelessKieSession,JudgeResult> iRule) {
		
		return getKieSessionByFlowId(flowId)
				.map(ks -> iRule.apply(lso, ks))
				.orElseGet(() -> {
					return new JudgeResult("Cannot find KieSession ");
									});
		
	}
}