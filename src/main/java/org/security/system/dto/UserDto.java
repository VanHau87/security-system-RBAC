package org.security.system.dto;

import java.util.Set;

public class UserDto {
	public Long id;
    public String username;
    public String email;
    public String fullName;
    public String phoneNumber;
    public String avatarUrl;
    public boolean enabled;
    public Set<String> roles;
}
