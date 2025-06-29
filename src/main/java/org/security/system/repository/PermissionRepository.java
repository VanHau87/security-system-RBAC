package org.security.system.repository;

import java.util.Optional;

import org.security.system.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
	Optional<Permission> findByName(String name);
}
