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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

import java.io.Serializable;
import java.security.Principal;

/**
 * Github user representation
 *
 * @author Joao Pedro Evangelista
 * @since 06/04/2015
 */

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class User implements Serializable, Principal {

    public static final String TYPE_USER = "User";

    public static final String TYPE_ORG = "Organization";

    private static final long serialVersionUID = -1211802439119529774L;

    private String id;

    private String email;

    private String avatarUrl;

    private String htmlUrl;

    private String username;

    private String name;

    private String type;
}
