package org.bcos.browser.config;

import lombok.Data;
import org.bcos.browser.service.TableService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class InitTable implements InitializingBean {
    @Autowired
    TableService tableService;

    @Override
    public void afterPropertiesSet() throws Exception {
        tableService.newCommonTable();
    }
}
