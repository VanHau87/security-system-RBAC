package org.security.system.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserDto {
	public Long id;
    public String username;
    public String email;
    public String fullName;
    private String password;
    public String phoneNumber;
    public String avatarUrl;
    public boolean enabled;
    public Set<String> roles;
}
