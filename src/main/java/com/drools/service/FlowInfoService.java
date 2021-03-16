package com.drools.service;

import com.drools.dao.FlowInfoMapper;
import com.drools.dao.RuleConditionMapper;
import com.drools.model.FlowInfoTO;
import com.drools.model.RuleCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowInfoService {
    @Autowired
    FlowInfoMapper flowInfoMapper;

    @Autowired
    RuleConditionMapper ruleConditionMapper;

    public List<FlowInfoTO> getFlows(Long id){
        return flowInfoMapper.getFlowsById(id);
    }


    public List<RuleCondition> getRuleCondition(){
        return ruleConditionMapper.findAll();
    }

    public void saveFlowInfo(FlowInfoTO to){
        flowInfoMapper.insertFlowInfo(to);
    }
}
