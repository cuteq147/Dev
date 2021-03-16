package com.drools.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.drools.model.JudgeResult;
import com.drools.service.KieHelper;
import com.drools.service.RuleReloader;
import com.drools.service.TransOrderService;
import com.drools.vo.TransOrderDTO;

@RestController
@RequestMapping(value = "/transOrder")
public class TransOrderContorller {
	@Autowired
	private TransOrderService service;
	
	@Autowired
	private RuleReloader ruleReloader;
	
	@PostMapping(value = "/trans", consumes=MediaType.APPLICATION_JSON_VALUE,produces = "application/json")
	public JudgeResult handleTransOrder(@RequestBody TransOrderDTO dto) {
		return service.executeTransOrderDRL(dto);
	}
	
	@PostMapping(value = "/flows/{flowId}", consumes=MediaType.APPLICATION_JSON_VALUE,produces = "application/json")
	public JudgeResult dynaTransOrder(@RequestBody TransOrderDTO dto,@PathVariable Long flowId) {
		return service.dynaExecTransOrder(flowId, dto);
	}
	
	@RequestMapping(value = "/reload/flows/{flowId}", method=RequestMethod.GET)
	public String handleReloadTst(@PathVariable Long flowId) {
		
		return ruleReloader.reload(flowId);
	}

}
