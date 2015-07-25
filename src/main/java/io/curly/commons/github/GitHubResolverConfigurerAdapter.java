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
package io.curly.commons.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author João Pedro Evangelista
 */

public class GitHubResolverConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    @Qualifier("repositoryExporterHandlerAdapter")
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void init() throws Exception {
        final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>(requestMappingHandlerAdapter.getArgumentResolvers());
        resolvers.add(0, gitHubAuthenticationMethodHandler());
        requestMappingHandlerAdapter.setArgumentResolvers(resolvers);
    }

    @Bean
    GitHubAuthenticationMethodHandler gitHubAuthenticationMethodHandler() {
        return new GitHubAuthenticationMethodHandler();
    }
}
