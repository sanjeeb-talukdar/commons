package io.curly.commons.config.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * @author Joao Pedro Evangelista
 */
@Configuration
@ConditionalOnWebApplication
public class RequestLoggerConfiguration {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        return new CommonsRequestLoggingFilter();
    }
}
