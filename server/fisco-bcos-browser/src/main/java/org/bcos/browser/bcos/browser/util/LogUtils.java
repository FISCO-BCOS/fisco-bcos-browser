package org.bcos.browser.bcos.browser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logUtils
 */
public class LogUtils {

    private static final Logger MONILOGGER  = LoggerFactory.getLogger("appmonitor");
    
    private static final Logger ERRLOGGER  = LoggerFactory.getLogger("error");

    public static Logger getMonitorLogger() {
        return MONILOGGER;
    }
    
    public static Logger getErrorLogger() {
    	return ERRLOGGER;
    }

}
