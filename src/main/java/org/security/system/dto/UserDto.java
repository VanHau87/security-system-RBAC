package org.security.system.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
	
	@NotBlank(groups = OnUpdate.class)
	private Long id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String email;
	private String fullName;
    
    @Size(min=8, groups = OnCreate.class)
    @NotBlank(groups = OnCreate.class)
    private String password;
    
    private String phoneNumber;
    private String avatarUrl;
    private Boolean enabled;
    private Set<String> roles;
}
