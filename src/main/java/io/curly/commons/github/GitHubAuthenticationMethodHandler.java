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

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;

/**
 * @author Joao Pedro Evangelista
 * @since 04/04/2015
 */
@Slf4j
public class GitHubAuthenticationMethodHandler implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return findMethodAnnotation(GitHubAuthentication.class, parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication == null) || !(authentication instanceof OAuth2Authentication)) {
            return null;
        }

        return from((OAuth2Authentication) authentication);
    }

    @SuppressWarnings("unchecked")
    private User from(OAuth2Authentication auth) {
        Authentication userAuthentication = auth.getUserAuthentication();
        if (userAuthentication != null) {
            try {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) userAuthentication.getDetails();
                return User.builder()
                        .id(String.valueOf(map.get("id")))
                        .avatarUrl(String.valueOf(map.get("avatar_url")))
                        .email(String.valueOf(map.get("email")))
                        .name(String.valueOf(map.get("name")))
                        .type(String.valueOf(map.get("type")))
                        .username(String.valueOf(map.get("login")))
                        .build();

            } catch (ClassCastException e) {
                log.error("Cannot build User due to a ClassCastException {}", e);
            }
        }
        log.debug("Returning no user due to previous errors or no UserAuthentication detected");
        return null;
    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }
}
