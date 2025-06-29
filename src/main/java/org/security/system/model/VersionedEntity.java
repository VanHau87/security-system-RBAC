package org.security.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class VersionedEntity extends SoftDeletableEntity {
	@Version
    @Column(name = "version")
    protected Long version;
}
