package io.curly.commons.github;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author João Pedro Evangelista
 */
@Documented
@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Import({GitHubResolverAutoConfiguration.class})
public @interface EnableGitHubAuthenticationResolver {
}
