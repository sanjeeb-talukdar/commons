package io.curly.commons.mongo;

import io.curly.commons.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

/**
 * @author João Pedro Evangelista
 */
public class PrincipalAuditorAware implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        String currentUser = SecurityUtils.getCurrentUser();
        if (StringUtils.hasText(currentUser)) {
            return currentUser;
        } else {
            return "";
        }
    }
}
