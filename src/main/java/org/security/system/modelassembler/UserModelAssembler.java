package org.security.system.modelassembler;

import org.security.system.controller.UserController;
import org.security.system.dto.UserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

	public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

	@Override
	public UserModel toModel(UserDto dto) {
		UserModel model = new UserModel(dto);
		// Link chính (self)
        model.add(linkTo(methodOn(UserController.class).getUser(dto.id)).withSelfRel());

        // Link update
        model.add(linkTo(methodOn(UserController.class).updateUser(dto.id, null)).withRel("update"));

        // Link delete hard
        model.add(linkTo(methodOn(UserController.class).deleteHard(dto.id)).withRel("delete_hard"));
        
        // Link delete soft
        model.add(linkTo(methodOn(UserController.class).deleteSoft(dto.id)).withRel("delete_soft"));

        // Link lấy tất cả user
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
		
        return model;
	}

}
