package org.bcos.browser;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Startup class.
 *
 */
@Slf4j
@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@MapperScan("org.bcos.browser.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("start success...");
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(-1);
        resolver.setMaxUploadSize(5 * 1024 * 1024);
        return resolver;
    }
}
