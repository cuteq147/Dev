package com.drools.base;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class DroolsInvokeServiceImpl implements IDroolsService {

    @Override
    public boolean executeStatelessKSRule(String kSessionName, List<Object> factObjList) {
        AtomicBoolean success = new AtomicBoolean(false);
        try {

            StatelessKieSession statelessKieSession = DroolsConfiguration.createStatelessKieSession(kSessionName);
            Optional.ofNullable(statelessKieSession).ifPresent(kSession -> {
                
                kSession.execute(factObjList);
                success.set(true);
            });

        } catch (Exception e) {
            success.set(false);
            log.info("kSessionName:{}", kSessionName, e);
        }
        return success.get();
    }


}
