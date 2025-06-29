package org.security.system.service;

import java.time.LocalDateTime;

public interface SoftDeletable {
	void setDeleted(boolean deleted);
    boolean isDeleted();

    void setDeletedAt(LocalDateTime deletedAt);
    LocalDateTime getDeletedAt();

    void setDeletedBy(String deletedBy);
    String getDeletedBy();
}
