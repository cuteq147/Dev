package com.drools.service;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel.KieSessionType;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drools.base.DroolsInvokeServiceImpl;
import com.drools.model.FlowInfo;

import lombok.extern.slf4j.Slf4j;


/**
 * @author ur09051
 *
 */
@Slf4j
@Service
public class RuleReloader {
	private static final String rulePath = "src/main/resources/rules/flow_{0}/rule_{1}.drl";
	
	private final ConcurrentMap<String, KieContainer> kieContainerMap = new ConcurrentHashMap<>();
	
	@Autowired
	private RuleInfoService ruleInfoService;
	
	private String buildContainerName(long id) {
		
		return "KContainer_"+id;
	}
	
	private String buildKbaseName(long flowId) {
        return "kbase_" + flowId;
    }

    private String buildKsessionName(long flowId) {
        return "ksession_" + flowId;
    }

    KieContainer getKieContainerByFlowId(long flowId) {
        return kieContainerMap.get(buildContainerName(flowId));
    }
    
    public String reload(long flowId) {
    	KieServices kieServices = KieServices.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(buildKbaseName(flowId));
        //kieBaseModel.setDefault(true);
        kieBaseModel.addPackage(MessageFormat.format("rules.flow_{0}", String.valueOf(flowId)));
        kieBaseModel.newKieSessionModel(buildKsessionName(flowId)).setType(KieSessionType.STATELESS);
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        List<FlowInfo> flows= ruleInfoService.queryFlowInfo(flowId);
        if(flows.size()==0) return "Cannot get flow info";
        flows.stream().forEach(info ->{
        	String fullPath = MessageFormat.format(rulePath, String.valueOf(flowId), String.valueOf(info.getConditionId()));
            kieFileSystem.write(fullPath, info.getContent());
        });
        
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
        	log.info("build error {}",results.getMessages());
            
            throw new IllegalStateException("rule error");
        }
       
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        kieContainerMap.put(buildContainerName(flowId), kieContainer);
        return "Success";
    }
	
	
}