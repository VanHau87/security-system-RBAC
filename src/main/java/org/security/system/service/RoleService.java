package org.security.system.service;

import java.util.List;

import org.security.system.dto.RoleDto;

public interface RoleService {
	RoleDto getRoleById(Long id);
    List<RoleDto> getAllRoles();
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(Long id, RoleDto roleDto);
    void deleteRole(Long id);
}
