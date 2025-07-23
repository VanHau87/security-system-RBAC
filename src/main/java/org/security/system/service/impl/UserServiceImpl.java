package org.security.system.service.impl;

import java.util.List;

import org.security.system.dto.UserDto;
import org.security.system.mapper.UserMapper;
import org.security.system.model.User;
import org.security.system.repository.UserRepository;
import org.security.system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
	@Override
	public UserDto getUserById(Long id) {
		User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
		return userMapper.toDTO(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		//userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
		return userMapper.toDTOs(userRepository.findAll());
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = userMapper.toEntity(userDto);
		// Chỉ encode nếu là user mới, hoặc password thay đổi
		String password = userDto.getPassword();
        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
		User savedUser = userRepository.save(user);
		return userMapper.toDTO(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
		String password = userDto.getPassword();
		if (password != null && !password.isBlank()) {
	        user.setPassword(passwordEncoder.encode(password));
	    }
		
		userMapper.updateFromDto(userDto, user);
		User updatedUser = userRepository.save(user);
		return userMapper.toDTO(updatedUser);
	}

	@Override
	public UserDto deleteUserHard(Long id) {
		User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.delete(user);
		return userMapper.toDTO(user);
	}

	@Override
	public UserDto deleteUserSoft(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
		if (!user.isDeleted()) {
	        user.setDeleted(true);
	        // Không cần set deletedAt/deletedBy nữa, Listener sẽ tự động set khi gọi save/update!
	        userRepository.save(user);
	    }
		return userMapper.toDTO(user);
	}
	
	
}
