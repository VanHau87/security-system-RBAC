package org.security.system.service;

import java.util.List;

import org.security.system.dto.UserDto;

public interface UserService {
	UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    UserDto deleteUserSoft(Long id);
    UserDto deleteUserHard(Long id);
}
