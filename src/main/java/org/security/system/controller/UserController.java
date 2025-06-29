package org.security.system.controller;

import java.util.List;

import org.security.system.dto.ApiResponse;
import org.security.system.dto.UserDto;
import org.security.system.modelassembler.UserModel;
import org.security.system.modelassembler.UserModelAssembler;
import org.security.system.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	private final UserService userService;
    private final UserModelAssembler assembler;
    
    public UserController(UserService userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> getUser(@PathVariable Long id) {
        UserDto dto = userService.getUserById(id);
        UserModel model = assembler.toModel(dto);
		return ResponseEntity.ok(ApiResponse.success("User found", model));
    }
    @GetMapping
    public CollectionModel<UserModel> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return assembler.toCollectionModel(users);
    }
    @PostMapping
    public UserModel createUser(@RequestBody UserDto userDto) {
        UserDto dto = userService.createUser(userDto);
        return assembler.toModel(dto);
    }
    @PutMapping("/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto dto = userService.updateUser(id, userDto);
        return assembler.toModel(dto);
    }
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<ApiResponse<UserModel>> deleteHard(@PathVariable Long id) {
        UserDto deletedUser = userService.deleteUserHard(id);
		UserModel model = assembler.toModel(deletedUser);
		return ResponseEntity.ok(ApiResponse.success("User hard deleted", model));
    }
    @DeleteMapping("/{id}/soft")
    public ResponseEntity<ApiResponse<UserModel>> deleteSoft(@PathVariable Long id) {
        try {
        	UserDto deletedUser = userService.deleteUserSoft(id);
			UserModel model = assembler.toModel(deletedUser);
			return ResponseEntity.ok(ApiResponse.success("User soft deleted", model));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
		            .body(ApiResponse.error(404, e.getMessage()));
		}
    }
}
