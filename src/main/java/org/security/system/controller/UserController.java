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
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> getUser(@PathVariable Long id) {
        UserDto dto = userService.getUserById(id);
        UserModel model = UserModelAssembler.buildModel(dto);
		return ResponseEntity.ok(ApiResponse.success("User found", model));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<CollectionModel<UserModel>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        CollectionModel<UserModel> collectionModel = UserModelAssembler.buildCollection(users);
        return ResponseEntity.ok(ApiResponse.success("Users found", collectionModel));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<UserModel>> createUser(@RequestBody UserDto dto) {
        UserDto savedUser = userService.createUser(dto);
        UserModel model = UserModelAssembler.buildModel(savedUser);
        return ResponseEntity.ok(ApiResponse.success("Created user successfully", model));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        UserModel model = UserModelAssembler.buildModel(updatedUser);
		return ResponseEntity.ok(ApiResponse.success("User updated", model));
    }
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<ApiResponse<UserModel>> deleteHard(@PathVariable Long id) {
        UserDto deletedUser = userService.deleteUserHard(id);
        UserModel model = UserModelAssembler.buildModel(deletedUser);
		return ResponseEntity.ok(ApiResponse.success("User hard deleted", model));
    }
    @DeleteMapping("/{id}/soft")
    public ResponseEntity<ApiResponse<UserModel>> deleteSoft(@PathVariable Long id) {
        try {
        	UserDto deletedUser = userService.deleteUserSoft(id);
        	UserModel model = UserModelAssembler.buildModel(deletedUser);
			return ResponseEntity.ok(ApiResponse.success("User soft deleted", model));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
		            .body(ApiResponse.error(404, e.getMessage()));
		}
    }
}
