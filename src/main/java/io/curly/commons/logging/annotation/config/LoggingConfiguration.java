/*
 *        Copyright 2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.curly.commons.logging.annotation.config;

import io.curly.commons.logging.MethodExecutionAspectInterceptor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;

/**
 * @author Joao Pedro Evangelista
 */
@Configuration
@ComponentScan("curly.commons.logging")
@EnableMBeanExport
@EnableAspectJAutoProxy
@ConditionalOnClass({JoinPoint.class, GaugeService.class, Around.class})
public class LoggingConfiguration {

	@Bean
	@ConditionalOnMissingBean MethodExecutionAspectInterceptor methodExecutionAspectInterceptor() {
		return new MethodExecutionAspectInterceptor();
	}
}
