package org.security.system.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.security.system.dto.UserDto;
import org.security.system.model.Role;
import org.security.system.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	//Map each User
	@Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleToString")
	UserDto toDTO(User user);
	
	@Mapping(source = "roles", target = "roles", qualifiedByName = "mapStringToRole")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(UserDto source, @MappingTarget User target);
	
	@Mapping(source = "roles", target = "roles", qualifiedByName = "mapStringToRole")
	User toEntity(UserDto dto);
	
	//Map list of Users
	@Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleToString")
	List<UserDto> toDTOs(List<User> users);
	
	@Mapping(source = "roles", target = "roles", qualifiedByName = "mapStringToRole")
	List<User> toEntities(List<UserDto> dtos);
	
	@Named("mapRoleToString")
    default Set<String> mapRoleToString(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
	
	@Named("mapStringToRole")
    default Set<Role> mapStringToRole(Set<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream().map(name -> {
            Role role = new Role();
            role.setName(name);
            return role;
        }).collect(Collectors.toSet());
    }
}
