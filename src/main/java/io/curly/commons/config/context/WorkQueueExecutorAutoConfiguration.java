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
package io.curly.commons.config.context;

import io.curly.commons.config.reactor.Reactor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.Environment;
import reactor.core.dispatch.wait.AgileWaitingStrategy;
import reactor.jarjar.com.lmax.disruptor.dsl.ProducerType;
import reactor.spring.context.config.EnableReactor;
import reactor.spring.core.task.WorkQueueAsyncTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Joao Pedro Evangelista
 */
@EnableAsync
@Configuration
@ConditionalOnClass({EnableReactor.class, WorkQueueAsyncTaskExecutor.class})
public class WorkQueueExecutorAutoConfiguration implements AsyncConfigurer {

	@Autowired
	@Reactor
	private Environment env;


	@Override
	public Executor getAsyncExecutor() {
		WorkQueueAsyncTaskExecutor executor = new WorkQueueAsyncTaskExecutor(env);
		executor.setName("WorkQueueAsyncTaskExecutor");
		executor.setBacklog(8048);
		executor.setWaitStrategy(new AgileWaitingStrategy());
		executor.setProducerType(ProducerType.MULTI);
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
