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
package io.curly.commons.config.reactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.core.Dispatcher;
import reactor.core.dispatch.RingBufferDispatcher;
import reactor.core.dispatch.wait.AgileWaitingStrategy;
import reactor.jarjar.com.lmax.disruptor.dsl.ProducerType;
import reactor.spring.context.config.EnableReactor;

/**
 * @author Joao Pedro Evangelista
 */
@Slf4j
@ConditionalOnClass({EventBus.class, Dispatcher.class})
@EnableReactor
@Configuration
public class ReactorAutoConfiguration {

	@Bean
	@Reactor
	Environment env() {
		return Environment.initializeIfEmpty().assignErrorJournal();
	}

	@Bean
	@Reactor
	@ConditionalOnMissingBean
	EventBus eventBus(Environment reactorEnv) {
		return EventBus.config()
				.env(reactorEnv)
				.dispatcher(new RingBufferDispatcher(
						"RingBufferDispatcher-Bean",
						1024,
						throwable -> log.error("Cannot process event on RingBufferDispatcher-Bean, nested exception is: ", throwable),
						ProducerType.SINGLE,
						new AgileWaitingStrategy()))
				.get();
	}


}
