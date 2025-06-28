package org.security.system.dto;

import java.util.Set;

public class RoleDto {
	public Long id;
    public String name;
    public String description;
    public Set<String> permissions;
}
