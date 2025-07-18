package org.security.system.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
	
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
	            || authentication.getPrincipal().equals("anonymousUser")) {
	            return Optional.empty();
        }
        return Optional.ofNullable(authentication.getName());
	}

}
