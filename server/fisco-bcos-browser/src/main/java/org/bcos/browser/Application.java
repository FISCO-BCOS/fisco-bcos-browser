package org.bcos.browser;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@EnableTransactionManagement
@MapperScan("org.bcos.browser.mapper")
@Slf4j
public class Application {
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(-1);
        resolver.setMaxUploadSize(5 * 1024 * 1024);
        return resolver;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("start success...");
    }

}
