package org.security.system.service.impl;

import java.time.LocalDateTime;

import org.security.system.service.SoftDeletable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.PreUpdate;

public class SoftDeleteListener {
	@PreUpdate
    public void setSoftDeleteAudit(Object entity) {
        if (entity instanceof SoftDeletable softDeletable) {
            if (softDeletable.isDeleted() && softDeletable.getDeletedAt() == null) {
                // Lấy current user từ AuditorAware
                String currentUser = getCurrentUser();
                softDeletable.setDeletedAt(LocalDateTime.now());
                softDeletable.setDeletedBy(currentUser);
            }
        }
    }

	private String getCurrentUser() {
		try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                return auth.getName();
            }
        } catch (Exception ignored) {}
        return "system";
	}
}
