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
package io.curly.commons.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author João Evangelista
 */
@Slf4j
public class ModelErrors {

	private static final String STATUS_CODE = "status";
	@JsonProperty
	private final Map<String, String> errors;

	@JsonCreator
	public ModelErrors(Errors errors) {
		log.debug("New API Error status -- Errors {}", HttpStatus.BAD_REQUEST, errors.getObjectName());
		this.errors = new HashMap<>();
		this.errors.put(STATUS_CODE, HttpStatus.BAD_REQUEST.toString());
		errors.getAllErrors().forEach(objectError ->
				this.errors.put(objectError.getObjectName(), objectError.getDefaultMessage()));
	}


}
