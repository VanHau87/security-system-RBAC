package org.security.system.service;

import java.util.List;

import org.security.system.dto.PermissionDto;

public interface PermissionService {
	PermissionDto getPermissionById(Long id);
    List<PermissionDto> getAllPermissions();
    PermissionDto createPermission(PermissionDto permissionDto);
    PermissionDto updatePermission(Long id, PermissionDto permissionDto);
    void deletePermission(Long id);
}
