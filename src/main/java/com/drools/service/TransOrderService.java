package com.drools.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drools.base.DroolsConfiguration;
import com.drools.base.IDroolsService;
import com.drools.base.IExecRule;
import com.drools.dao.FlowInfoMapper;
import com.drools.dao.RuleConditionMapper;
import com.drools.model.ProductSteel;
import com.drools.model.RuleCondition;
import com.drools.vo.TransOrderDTO;
import com.drools.model.FlowInfoTO;
import com.drools.model.JudgeResult;
import com.drools.model.OrderInfo;

@Service
public class TransOrderService implements BiFunction<List<Object>,StatelessKieSession,JudgeResult>{
	
	@Autowired
	private IDroolsService droolsService;
	
	@Autowired
	private RuleConditionMapper ruleConditionMapper;
	
	@Autowired
	private FlowInfoMapper flowInfoMapper;
	
	@Autowired
	private KieHelper helper;



	public JudgeResult executeTransOrderDRL(TransOrderDTO dto) {

		List<Object> factObjList = new ArrayList<>();
		ProductSteel ps = dto.getPt();
		OrderInfo oi = new OrderInfo("30","00003","Y","AA","WHITE","304","TW");
		JudgeResult jResult = new JudgeResult("");
		factObjList.add(ps);
		factObjList.add(oi);
		factObjList.add(jResult);
		droolsService.executeStatelessKSRule("TransOrderKS", factObjList);

		return jResult;
	}
	
	public JudgeResult dynaExecTransOrder(long flowId, TransOrderDTO dto) {
		return helper.executeRule(flowId, combineInput(dto), this);
	}
	
	private List<Object> combineInput(TransOrderDTO dto){
		List<Object> factObjList = new ArrayList<>();
		ProductSteel ps = dto.getPt();
		OrderInfo oi = new OrderInfo("30","00003","Y","AA","WHITE","304","TW");
		factObjList.add(ps);
		factObjList.add(oi);
		return factObjList;
	}
	
	@Override
	public JudgeResult apply(List<Object> dto, StatelessKieSession statelessKS) {
		JudgeResult jResult = new JudgeResult("");
		dto.add(jResult);
		statelessKS.execute(dto);
		return jResult;
	}
}
