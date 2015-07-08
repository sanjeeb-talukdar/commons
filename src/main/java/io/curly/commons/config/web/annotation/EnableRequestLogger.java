package io.curly.commons.config.web.annotation;

import io.curly.commons.config.web.RequestLoggerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Joao Pedro Evangelista
 */
@Import({RequestLoggerConfiguration.class})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface EnableRequestLogger {
}
