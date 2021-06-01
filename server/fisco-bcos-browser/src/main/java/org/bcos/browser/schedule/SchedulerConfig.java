package org.bcos.browser.schedule;

import org.bcos.browser.base.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * Scheduling.
 * 
 */
@Component
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private Constants constants;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(() -> schedulerService.handleBlockChainInfo(),
            (context) -> new CronTrigger(constants.getCronBlockChainInfo())
                        .nextExecutionTime(context));

        taskRegistrar.addTriggerTask(() -> schedulerService.handleTxnByDay(),
            (context) -> new CronTrigger(constants.getCronTxnByDay())
                        .nextExecutionTime(context));

        taskRegistrar.addTriggerTask(() -> schedulerService.syncNodeInfo(),
            (context) -> new CronTrigger(constants.getCronAyncNode())
                .nextExecutionTime(context));
        
        taskRegistrar.addTriggerTask(() -> schedulerService.checkNodeActive(),
            (context) -> new CronTrigger(constants.getCronIfNodeActive())
                        .nextExecutionTime(context));

        taskRegistrar.addTriggerTask(() ->schedulerService.deleteTxnSchedule(),
                (context) -> new CronTrigger(constants.getCronDeleteTxn())
                        .nextExecutionTime(context));

        taskRegistrar.addTriggerTask(() -> schedulerService.syncChainUser(),
                (context) -> new CronTrigger(constants.getCronSyncChainUser())
                        .nextExecutionTime(context));

        taskRegistrar.addTriggerTask(() -> schedulerService.syncChainContract(),
                (context) -> new CronTrigger(constants.getCronSyncChainContract())
                        .nextExecutionTime(context));
    }
}
