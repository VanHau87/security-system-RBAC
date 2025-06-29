package org.security.system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class SoftDeletableEntity extends AuditableEntity {
	@Column(name = "deleted", nullable = false)
    protected boolean deleted = false;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    protected String deletedBy;
}
