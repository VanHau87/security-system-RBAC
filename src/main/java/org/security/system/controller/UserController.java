package org.security.system.controller;

import java.util.List;

import org.security.system.dto.ApiResponse;
import org.security.system.dto.OnCreate;
import org.security.system.dto.OnUpdate;
import org.security.system.dto.UserDto;
import org.security.system.modelassembler.UserModel;
import org.security.system.modelassembler.UserModelAssembler;
import org.security.system.modelassembler.UserRepresentationAssembler;
import org.security.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/prs.hal-forms+json")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	private UserRepresentationAssembler userAssembler;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserModel>> getUser(@PathVariable Long id) {
        UserDto dto = userService.getUserById(id);
        //UserModel model = UserModelAssembler.buildModel(dto);
        EntityModel<UserModel> model = userAssembler.toModel(dto);
		return ResponseEntity.ok(model);
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserModel>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        //CollectionModel<UserModel> collectionModel = UserModelAssembler.buildCollection(users);
        return ResponseEntity.ok(userAssembler.toCollectionModel(users));
    }
    @PostMapping
    public ResponseEntity<?> createUser(@Validated(OnCreate.class) @RequestBody UserDto dto) {
        try {
			UserDto savedUser = userService.createUser(dto);
			return ResponseEntity.ok(userAssembler.toModel(savedUser));
		} catch (CompromisedPasswordException e) {
			return ResponseEntity.badRequest().body("Password đã bị lộ, vui lòng chọn mật khẩu khác!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        try {
			UserDto updatedUser = userService.updateUser(userDto);
			return ResponseEntity.ok(userAssembler.toModel(updatedUser));
		} catch (CompromisedPasswordException e) {
			return ResponseEntity.badRequest().body("Password đã bị lộ, vui lòng chọn mật khẩu khác!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    	
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
