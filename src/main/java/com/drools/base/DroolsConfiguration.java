package com.drools.base;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieServices;
import org.kie.api.builder.Results;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

@Slf4j
public class DroolsConfiguration {
	private static final String rPath = "src/main/resources/rules/order/"; 
    public static KieSession getKieSession(String filename,String groupName) throws IOException{
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        //String filePath = DroolsConfiguration.class.getClassLoader().getResource(filename).getPath();
        File f = Paths.get(rPath + filename).toFile();

        Files.list(Paths.get("src/main/resources"))
        .forEach(p -> log.info(p.toString()));
        
        log.info("filePath {}",f.getAbsolutePath());
        //File file = new File(URLDecoder.decode(filePath,"utf-8"));
        kbuilder.add(ResourceFactory.newFileResource(f), ResourceType.DRL);
        Collection<KiePackage> knowledgePackages = kbuilder.getKnowledgePackages();
        if (knowledgePackages.size() >0 ){
            for (KiePackage kiePackage:knowledgePackages) {
                System.out.println(kiePackage);
            }
        }
        
        boolean errorResult = kbuilder.hasErrors();
        
        if (errorResult){
            System.out.println("something wrong");
            KnowledgeBuilderErrors errors = kbuilder.getErrors();
            if(errors.size()>0){
                for(KnowledgeBuilderError error:errors){
                    System.out.println(error);
                }
                throw new  IllegalArgumentException("parseing Drools rule file " + filename + " error");
            }

        }
        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        Collection<KiePackage> ss = kbuilder.getKnowledgePackages();
        kbase.addPackages(ss);
        KieSession kieSession = kbase.newKieSession();
        
        //kieSession.getAgenda().getAgendaGroup(groupName).setFocus();
        return kieSession;
    }
    

    public static StatelessKieSession createStatelessKieSession(String kSessionName) {
        KieServices kieServices = getKieServices();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        log.info("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        Results results = kieContainer.verify();
        results.getMessages().stream().forEach(message ->
                log.info(">> Message ( " + message.getLevel() + " ): " + message.getText())
        );
        
        return kieContainer.newStatelessKieSession(kSessionName);
    }

    private static KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    private DroolsConfiguration() {
    }
}
