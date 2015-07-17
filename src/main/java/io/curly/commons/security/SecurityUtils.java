package io.curly.commons.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.LinkedHashMap;

/**
 * @author João Pedro Evangelista
 */
public class SecurityUtils {

    private static final String SYSTEM_USER = "system";
    private static final String NO_OP_USER = "noOp";

    @SuppressWarnings("unchecked")
    public static String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            Object details = ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
            if (details instanceof LinkedHashMap) {
                LinkedHashMap<String, Object> detailsMap = (LinkedHashMap<String, Object>) details;
                return String.valueOf(detailsMap.get("id"));
            }
        } else {
            return authentication.getName() != null ? authentication.getName() : null;
        }
        throw new IllegalStateException("No valid user found on the context!");
    }
}
