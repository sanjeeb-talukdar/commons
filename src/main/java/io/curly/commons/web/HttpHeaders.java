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

/**
 * @author João Evangelista
 */
public class HttpHeaders {

    public static final String API_V1_JSON = "Accept=application/vnd.curly.v1+json";

    public static final String INTERNAL_V1_JSON = "Accept=application/internal.curly.v1+json";

    public static final String DEFAULT_API_V1_JSON = "Accept=application/vnd.curly.v1+json,*";

    public static final String DEFAULT_INTERNAL_V1_JSON = "Accept=application/internal.curly.v1+json,*";

    public static final String API_V1_HAL = "Accept=application/vnd.curly.v1+hal+json";

    public static final String INTERNAL_V1_HAL = "Accept=application/internal.curly.v1+hal+json";

    public static final String DEFAULT_API_V1_HAL = "Accept=application/vnd.curly.v1+hal+json";

    public static final String DEFAULT_INTERNAL_V1_HAL = "Accept=application/internal.curly.v1+hal+json";

    public static String getName(String fullHeader) {
        return fullHeader.split("=")[0];
    }

    public static String getValue(String fullHeader) {
        return fullHeader.split("=")[1];
    }
}
