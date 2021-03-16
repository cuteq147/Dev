package com.drools.service;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drools.dao.FlowInfoMapper;
import com.drools.model.FlowInfo;
import com.drools.model.FlowInfoTO;


@Service
public class RuleInfoService {
	@Autowired
	private FlowInfoMapper flowInfoMapper;
	
	public List<FlowInfo> queryFlowInfo(long flowId) {
		List<FlowInfoTO> tos = flowInfoMapper.getFlowsById(flowId);
		return tos.stream()
				.map(x -> toFlowInfo(x))
				.collect(Collectors.toList());
		
	}
	private FlowInfo toFlowInfo(FlowInfoTO to) {
		FlowInfo f =  new FlowInfo();
		f.setId(to.getId());
		f.setName(to.getName());
		f.setConditionId(to.getConditionId());
		f.setContent(generatorContent(to));
		return f;
	}
	
	private String generatorContent(FlowInfoTO to) {
		String content = "package rules.flow_{0};\n" +
                " import com.drools.model.*;\n" + 
                " dialect  \"mvel\" \n" +
                " rule \"rule_{1}\"\n" +
                " salience {4} \n"+
                "    when\n" +
                "        {2}\n" +
                "    then\n" +
                "        {3}\n" +
                " end\n";
		return MessageFormat.format(content,to.getId(),to.getConditionId(), 
				to.getConditionContent(),to.getActionContent(),Long.toString(to.getSalienceNo()));
	}
	
	
}