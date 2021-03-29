package org.bcos.browser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        log.info("main run success...");
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

    /**
     * config time format.
     */
    @Bean(name = "mapperObject")
    public ObjectMapper getObjectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        ObjectMapper om = new ObjectMapper();
        om.registerModule(javaTimeModule);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }
}
