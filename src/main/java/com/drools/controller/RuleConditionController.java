package com.drools.controller;

import com.drools.model.RuleCondition;
import com.drools.service.FlowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/v1/conditions")
public class RuleConditionController {
    @Autowired
    private FlowInfoService flowInfoService;

    @RequestMapping(value = "/{ruleId}", method= RequestMethod.GET)
    public List<RuleCondition> getRuleCondition(@PathVariable Long ruleId) {

        return flowInfoService.getRuleCondition();
    }
}
