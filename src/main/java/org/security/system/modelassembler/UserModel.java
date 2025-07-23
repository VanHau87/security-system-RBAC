package org.security.system.modelassembler;

import org.security.system.dto.UserDto;
import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserModel extends RepresentationModel<UserModel> {
	private Long id;
	private String username;
	private String email;
	private String fullName;
	private String phoneNumber;
	private String avatarUrl;
	private boolean enabled;
    
	public UserModel() {}
	
    public UserModel(UserDto dto) {
        this.id = dto.getId();
        this.username = dto.getUsername();
        this.email = dto.getEmail();
        this.fullName = dto.getFullName();
        this.phoneNumber = dto.getPhoneNumber();
        this.avatarUrl = dto.getAvatarUrl();
        this.enabled = dto.getEnabled();
    }
}
