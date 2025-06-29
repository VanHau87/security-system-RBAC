package org.security.system.modelassembler;

import java.util.Set;

import org.security.system.dto.UserDto;
import org.springframework.hateoas.RepresentationModel;

public class UserModel extends RepresentationModel<UserModel> {
	public Long id;
    public String username;
    public String email;
    public String fullName;
    public String phoneNumber;
    public String avatarUrl;
    public boolean enabled;
    public Set<String> roles;
    
    public UserModel(UserDto dto) {
        this.id = dto.id;
        this.username = dto.username;
        this.email = dto.email;
        this.fullName = dto.fullName;
        this.phoneNumber = dto.phoneNumber;
        this.avatarUrl = dto.avatarUrl;
        this.enabled = dto.enabled;
        this.roles = dto.roles;
    }
}
