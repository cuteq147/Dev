package com.drools.base;

import java.util.List;
import java.util.Map;

public interface IDroolsService {

    boolean executeStatelessKSRule(String kSessionName, List<Object> factObjList);
}
