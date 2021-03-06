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

import java.util.Map;

/**
 * @author João Evangelista
 */
class RequestError {

	public Integer status;
	public String error;
	public String message;
	public String timeStamp;
	public String trace;

	public RequestError(int status, Map<String, Object> errorAttributes) {
		this.status = status;
		this.error = String.valueOf(errorAttributes.get("error"));
		this.message = String.valueOf(errorAttributes.get("message"));
		this.timeStamp = errorAttributes.get("timestamp").toString();
		this.trace = String.valueOf(errorAttributes.get("trace"));
	}
}
