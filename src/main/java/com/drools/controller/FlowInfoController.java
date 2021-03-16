package com.drools.controller;

import com.drools.model.FlowInfoTO;
import com.drools.model.JudgeResult;
import com.drools.model.RuleCondition;
import com.drools.service.FlowInfoService;
import com.drools.vo.TransOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/v1/flows")
public class FlowInfoController {
    @Autowired
    private FlowInfoService flowInfoService;

    @RequestMapping(value = "/{flowId}", method= RequestMethod.GET)
    public List<FlowInfoTO> getFlows(@PathVariable Long flowId) {

        return flowInfoService.getFlows(flowId);
    }

    @PostMapping(value = "/0", consumes= MediaType.APPLICATION_JSON_VALUE,produces = "application/json")
    public void handleCreateFlowInfo(@RequestBody FlowInfoTO dto) {
        flowInfoService.saveFlowInfo(dto);
    }

}
