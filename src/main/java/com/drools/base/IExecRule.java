package com.drools.base;

import java.util.List;

import org.kie.api.runtime.StatelessKieSession;

import com.drools.model.JudgeResult;
import com.drools.vo.TransOrderDTO;

public interface IExecRule {
	public JudgeResult execRule(List<Object> o, StatelessKieSession statelessKS);
}
