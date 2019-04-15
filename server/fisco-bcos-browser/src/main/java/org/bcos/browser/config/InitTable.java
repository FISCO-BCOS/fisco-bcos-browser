package org.bcos.browser.config;

import lombok.Data;
import org.bcos.browser.base.Constants;
import org.bcos.browser.mapper.GroupMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class InitTable implements InitializingBean {
    @Autowired
    GroupMapper groupMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        groupMapper.createTbGroup(Constants.TB_GROUP);
        groupMapper.createTbContract(Constants.TB_CONTRACT);
        groupMapper.createTbFunction(Constants.TB_FUNCTION);
    }
}
