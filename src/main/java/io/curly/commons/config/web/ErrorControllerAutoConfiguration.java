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
package io.curly.commons.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author João Evangelista
 */
@Configuration
@ComponentScan
@ConditionalOnWebApplication
public class ErrorControllerAutoConfiguration {


	@RestController
	public static class WebErrorController implements ErrorController {

		private static final String PATH = "/error";

		@Autowired
		private ErrorAttributes errorAttributes;

		@RequestMapping(value = "/error", method = RequestMethod.GET)
		public RequestError onError(HttpServletResponse response, HttpServletRequest request) {
			return new RequestError(response.getStatus(), getErrorsAttrs(request));
		}

		@Override
		public String getErrorPath() {
			return PATH;
		}

		private Map<String, Object> getErrorsAttrs(HttpServletRequest servletRequest) {
			return errorAttributes.getErrorAttributes(new ServletRequestAttributes(servletRequest), false);
		}
	}

}
